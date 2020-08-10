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

package com.liferay.commerce.machine.learning.internal.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.commerce.machine.learning.internal.configuration.CommerceMLConfiguration;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceClientFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	configurationPid = "com.liferay.commerce.machine.learning.internal.configuration.CommerceMLConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = CommerceMLGatewayClient.class
)
public class CommerceMLGatewayClientImpl implements CommerceMLGatewayClient {

	@Override
	public File downloadCommerceMLJobResult(
			String applicationId, String resourceName,
			UnicodeProperties unicodeProperties)
		throws PortalException {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		httpClientBuilder.useSystemProperties();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			URIBuilder uriBuilder = new URIBuilder(
				getCallbackURL(unicodeProperties, _STORAGE_DOWNLOAD_URL_PATH));

			uriBuilder = uriBuilder.addParameter(
				"applicationId", applicationId);

			uriBuilder = uriBuilder.addParameter("resourceName", resourceName);

			HttpGet httpGet = new HttpGet(uriBuilder.build());

			CloseableHttpResponse httpResponse = closeableHttpClient.execute(
				httpGet);

			HttpEntity httpEntity = httpResponse.getEntity();

			if (httpEntity != null) {
				return FileUtil.createTempFile(httpEntity.getContent());
			}
		}
		catch (Exception e) {
			throw new PortalException(e);
		}

		return null;
	}

	@Override
	public CommerceMLJobState getCommerceMLJobState(
			String applicationId, UnicodeProperties unicodeProperties)
		throws PortalException {

		try {
			JSONWebServiceClient jsonWebServiceClient = getJSONWebServiceClient(
				unicodeProperties);

			return jsonWebServiceClient.doGetToObject(
				CommerceMLJobState.class, _ML_STATE_URL_PATH, "applicationId",
				applicationId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new PortalException(e);
		}
	}

	@Override
	public CommerceMLJobState startCommerceMLJob(
			UnicodeProperties unicodeProperties)
		throws PortalException {

		try {
			JSONWebServiceClient jsonWebServiceClient = getJSONWebServiceClient(
				unicodeProperties);

			String json = jsonWebServiceClient.doPostAsJSON(
				_ML_START_URL_PATH, unicodeProperties);

			return _objectMapper.readValue(json, CommerceMLJobState.class);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new PortalException(e);
		}
	}

	@Override
	public void uploadCommerceMLJobResource(
			String resourceName, InputStream resourceInputStream,
			UnicodeProperties unicodeProperties)
		throws PortalException {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		httpClientBuilder.useSystemProperties();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			MultipartEntityBuilder multipartEntityBuilder =
				MultipartEntityBuilder.create();

			multipartEntityBuilder.addBinaryBody(
				"file", resourceInputStream, ContentType.MULTIPART_FORM_DATA,
				resourceName);

			HttpPost httpPost = new HttpPost(
				getCallbackURL(unicodeProperties, _STORAGE_UPLOAD_URL_PATH));

			httpPost.setEntity(multipartEntityBuilder.build());

			CloseableHttpResponse httpResponse = closeableHttpClient.execute(
				httpPost);

			StatusLine statusLine = httpResponse.getStatusLine();

			int statusCode = statusLine.getStatusCode();

			if ((statusCode < 200) || (statusCode >= 300)) {
				throw new IOException(
					StringBundler.concat(
						"HTTP ", statusCode, " - Error during upload of file: ",
						statusLine.getReasonPhrase()));
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Upload complete with status: " +
						statusLine.getStatusCode());
			}
		}
		catch (IOException e) {
			throw new PortalException(e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commerceMLConfiguration = ConfigurableUtil.createConfigurable(
			CommerceMLConfiguration.class, properties);

		_jsonWebServiceClient = null;
	}

	protected String getCallbackURL(
			UnicodeProperties contextProperties, String path)
		throws MalformedURLException {

		String baseURL = contextProperties.getProperty(
			_COMMERCE_ML_BASE_URL,
			_commerceMLConfiguration.commerceMLBaseURL());

		URL url = new URL(baseURL + path);

		return url.toString();
	}

	protected JSONWebServiceClient getJSONWebServiceClient(
			UnicodeProperties contextProperties)
		throws Exception {

		if (_jsonWebServiceClient != null) {
			return _jsonWebServiceClient;
		}

		URL url = new URL(
			contextProperties.getProperty(
				_COMMERCE_ML_BASE_URL,
				_commerceMLConfiguration.commerceMLBaseURL()));

		Map<String, Object> properties = new HashMap<>();

		properties.put("hostName", url.getHost());
		properties.put("hostPort", String.valueOf(url.getPort()));
		properties.put("protocol", url.getProtocol());

		_jsonWebServiceClient = _jsonWebServiceClientFactory.getInstance(
			properties, false);

		return _jsonWebServiceClient;
	}

	private static final String _COMMERCE_ML_BASE_URL = "commerce.ml.base.url";

	private static final String _ML_START_URL_PATH = "/ml/start";

	private static final String _ML_STATE_URL_PATH = "/ml/status";

	private static final String _STORAGE_DOWNLOAD_URL_PATH =
		"/storage/download";

	private static final String _STORAGE_UPLOAD_URL_PATH = "/storage/upload";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMLGatewayClientImpl.class);

	private CommerceMLConfiguration _commerceMLConfiguration;
	private JSONWebServiceClient _jsonWebServiceClient;

	@Reference
	private JSONWebServiceClientFactory _jsonWebServiceClientFactory;

	private final ObjectMapper _objectMapper = new ObjectMapper();

}