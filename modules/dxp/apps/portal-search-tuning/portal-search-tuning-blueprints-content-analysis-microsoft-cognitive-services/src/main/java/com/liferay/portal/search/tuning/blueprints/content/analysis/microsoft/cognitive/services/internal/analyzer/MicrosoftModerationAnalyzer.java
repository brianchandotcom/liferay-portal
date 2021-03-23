/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.content.analysis.microsoft.cognitive.services.internal.analyzer;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.content.analysis.constants.ModerationReason;
import com.liferay.portal.search.tuning.blueprints.content.analysis.microsoft.cognitive.services.internal.configuration.MicrosoftContentModeratorConfiguration;
import com.liferay.portal.search.tuning.blueprints.content.analysis.request.ContentAnalysisRequest;
import com.liferay.portal.search.tuning.blueprints.content.analysis.response.ModerationAnalysisResponse;
import com.liferay.portal.search.tuning.blueprints.content.analysis.response.ModerationAnalysisResponse.ModerationAnalysisResponseBuilder;
import com.liferay.portal.search.tuning.blueprints.content.analysis.spi.analyzer.ModerationAnalyzer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URISyntaxException;

import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.portal.search.tuning.blueprints.content.analysis.microsoft.cognitive.services.internal.configuration.MicrosoftContentModeratorConfiguration",
	immediate = true, property = "name=microsoft-cognitive-services",
	service = ModerationAnalyzer.class
)
public class MicrosoftModerationAnalyzer implements ModerationAnalyzer {

	@Override
	public Optional<ModerationAnalysisResponse> analyze(
		ContentAnalysisRequest contentAnalysisRequest) {

		if (!_microsoftContentModeratorConfiguration.isEnabled()) {
			return Optional.empty();
		}

		if (Validator.isNull(
				_microsoftContentModeratorConfiguration.apiEndpoint()) ||
			Validator.isNull(
				_microsoftContentModeratorConfiguration.subscriptionKey())) {

			_log.error("API endpoint or subscription key empty");

			return Optional.empty();
		}

		String mimeType = contentAnalysisRequest.getMimeType();

		if (mimeType.startsWith("text")) {
			return _screenText(contentAnalysisRequest);
		}

		return Optional.empty();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_microsoftContentModeratorConfiguration =
			ConfigurableUtil.createConfigurable(
				MicrosoftContentModeratorConfiguration.class, properties);

		_poolingHttpClientConnectionManager =
			new PoolingHttpClientConnectionManager();

		_poolingHttpClientConnectionManager.setMaxTotal(10);
		_poolingHttpClientConnectionManager.setValidateAfterInactivity(30000);

		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		httpClientBuilder.setConnectionManager(
			_poolingHttpClientConnectionManager);

		httpClientBuilder.useSystemProperties();

		_closeableHttpClient = httpClientBuilder.build();
	}

	@Deactivate
	protected void deactivate() {
		if (_closeableHttpClient != null) {
			try {
				_closeableHttpClient.close();
			}
			catch (IOException ioException) {
				_log.error("Unable to close client", ioException);
			}

			_closeableHttpClient = null;
		}

		if (_poolingHttpClientConnectionManager != null) {
			_poolingHttpClientConnectionManager.close();

			_poolingHttpClientConnectionManager = null;
		}
	}

	private Optional<ModerationAnalysisResponse> _createResponse(
		JSONObject screenJSONObject) {

		if (_log.isDebugEnabled()) {
			_log.debug(screenJSONObject);
		}

		ModerationAnalysisResponseBuilder moderationAnalysisResponseBuilder =
			new ModerationAnalysisResponseBuilder();

		moderationAnalysisResponseBuilder.details(screenJSONObject);
		moderationAnalysisResponseBuilder.reporter(
			"Microsoft Cognitive Services");

		JSONArray termsJSONArray = screenJSONObject.getJSONArray("Terms");
		JSONObject pIIJSONObject = screenJSONObject.getJSONObject("PII");
		JSONObject classificationJSONObject = screenJSONObject.getJSONObject(
			"Classification");

		if ((termsJSONArray != null) && (termsJSONArray.length() > 0)) {
			moderationAnalysisResponseBuilder.moderationReason(
				ModerationReason.PROFANITY);
			moderationAnalysisResponseBuilder.moderationRecommended(true);
		}
		else if ((pIIJSONObject != null) && (pIIJSONObject.length() > 0)) {
			moderationAnalysisResponseBuilder.moderationReason(
				ModerationReason.PERSONAL_INFORMATION);
			moderationAnalysisResponseBuilder.moderationRecommended(true);
		}
		else if ((classificationJSONObject != null) &&
				 (classificationJSONObject.length() > 0)) {

			boolean reviewRecommended = classificationJSONObject.getBoolean(
				"ReviewRecommended");

			if (reviewRecommended) {
				moderationAnalysisResponseBuilder.moderationReason(
					ModerationReason.SEXUALLY_EXPLICIT);
				moderationAnalysisResponseBuilder.moderationRecommended(true);
			}
		}

		return Optional.of(moderationAnalysisResponseBuilder.build());
	}

	private HttpPost _getHttpPost(ContentAnalysisRequest contentAnalysisRequest)
		throws UnsupportedEncodingException, URISyntaxException {

		URIBuilder builder = new URIBuilder(
			_microsoftContentModeratorConfiguration.apiEndpoint());

		_setParameters(builder, contentAnalysisRequest);

		HttpPost httpPost = new HttpPost(builder.build());

		httpPost.setHeader(
			"Content-Type", contentAnalysisRequest.getMimeType());
		httpPost.setHeader(
			"Ocp-Apim-Subscription-Key",
			_microsoftContentModeratorConfiguration.subscriptionKey());

		httpPost.setEntity(
			new StringEntity(contentAnalysisRequest.getPayloadAsString()));

		return httpPost;
	}

	private String _getLanguage(ContentAnalysisRequest contentAnalysisRequest) {
		String languageId = contentAnalysisRequest.getParameter("languageId");

		if ((languageId == null) || !languageId.contains("_")) {
			return null;
		}

		return languageId.substring(0, languageId.indexOf("_"));
	}

	private Optional<JSONObject> _getScreening(
		ContentAnalysisRequest contentAnalysisRequest) {

		try (CloseableHttpResponse closeableHttpResponse =
				_closeableHttpClient.execute(
					_getHttpPost(contentAnalysisRequest))) {

			if (_log.isTraceEnabled()) {
				StatusLine statusLine = closeableHttpResponse.getStatusLine();

				_log.trace(
					"Server returned status " + statusLine.getStatusCode());
			}

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				StringBundler sb = new StringBundler(4);

				sb.append("Microsoft cognitive services returned ");
				sb.append(statusLine.getStatusCode());
				sb.append(" with response ");
				sb.append(
					EntityUtils.toString(closeableHttpResponse.getEntity()));

				_log.error(sb.toString());
			}

			return Optional.of(
				_jsonFactory.createJSONObject(
					EntityUtils.toString(closeableHttpResponse.getEntity())));
		}
		catch (Exception exception) {
			_log.error(exception.getMessage(), exception);
		}

		return Optional.empty();
	}

	private Optional<ModerationAnalysisResponse> _screenText(
		ContentAnalysisRequest contentAnalysisRequest) {

		Optional<JSONObject> screenJSONObjectOptional = _getScreening(
			contentAnalysisRequest);

		if (screenJSONObjectOptional.isPresent()) {
			return _createResponse(screenJSONObjectOptional.get());
		}

		return Optional.empty();
	}

	private void _setParameters(
		URIBuilder builder, ContentAnalysisRequest contentAnalysisRequest) {

		builder.setParameter(
			"autocorrect",
			GetterUtil.getString(
				contentAnalysisRequest.getParameter("autocorrect"), "false"));

		boolean classify = GetterUtil.getBoolean(
			contentAnalysisRequest.getParameter("classify"),
			_microsoftContentModeratorConfiguration.classifyByDefault());

		builder.setParameter("classify", String.valueOf(classify));

		String language = _getLanguage(contentAnalysisRequest);

		if (!Validator.isBlank(language)) {
			builder.setParameter("language", language);
		}

		String listId = contentAnalysisRequest.getParameter("listId");

		if (!Validator.isBlank(listId)) {
			builder.setParameter("listId", listId);
		}

		boolean pii = GetterUtil.getBoolean(
			contentAnalysisRequest.getParameter("pii"),
			_microsoftContentModeratorConfiguration.pIIByDefault());

		builder.setParameter("pii", String.valueOf(pii));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MicrosoftModerationAnalyzer.class);

	private CloseableHttpClient _closeableHttpClient;

	@Reference
	private JSONFactory _jsonFactory;

	private volatile MicrosoftContentModeratorConfiguration
		_microsoftContentModeratorConfiguration;
	private PoolingHttpClientConnectionManager
		_poolingHttpClientConnectionManager;

}