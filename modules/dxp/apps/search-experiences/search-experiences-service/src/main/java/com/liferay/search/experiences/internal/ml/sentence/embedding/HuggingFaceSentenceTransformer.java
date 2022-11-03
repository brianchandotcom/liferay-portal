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

package com.liferay.search.experiences.internal.ml.sentence.embedding;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false, immediate = true,
	property = "search.experiences.sentence.transformer.name=huggingFace",
	service = SentenceTransformer.class
)
public class HuggingFaceSentenceTransformer
	extends BaseSentenceTransformer implements SentenceTransformer {

	public Double[] getSentenceEmbedding(
		SemanticSearchConfiguration semanticSearchConfiguration, String text) {

		String input = getInput(
			semanticSearchConfiguration.maxCharacterCount(), text,
			semanticSearchConfiguration.textTruncationStrategy());

		if (Validator.isBlank(input)) {
			return new Double[0];
		}

		return _getSentenceEmbedding(semanticSearchConfiguration, input);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		_poolingHttpClientConnectionManager =
			new PoolingHttpClientConnectionManager();

		_poolingHttpClientConnectionManager.setDefaultMaxPerRoute(2);
		_poolingHttpClientConnectionManager.setMaxTotal(10);

		httpClientBuilder.setConnectionManager(
			_poolingHttpClientConnectionManager);

		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom(
		).setCookieSpec(
			CookieSpecs.STANDARD
		);

		httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());

		_closeableHttpClient = httpClientBuilder.build();
	}

	@Deactivate
	protected void deactivate() {
		int retry = 0;

		while (retry < 10) {
			PoolStats poolStats =
				_poolingHttpClientConnectionManager.getTotalStats();

			int availableConnections = poolStats.getAvailable();

			if (availableConnections <= 0) {
				break;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						toString(), " is waiting on ", availableConnections,
						" connections"));
			}

			_poolingHttpClientConnectionManager.closeIdleConnections(
				200, TimeUnit.MILLISECONDS);

			try {
				Thread.sleep(500);
			}
			catch (InterruptedException interruptedException) {
				if (_log.isDebugEnabled()) {
					_log.debug(interruptedException);
				}
			}

			retry++;
		}

		_poolingHttpClientConnectionManager.shutdown();
	}

	private JSONArray _getJSONArray(JSONArray jsonArray1) {
		JSONArray jsonArray2 = jsonArray1.getJSONArray(0);

		if (jsonArray2 != null) {
			return _getJSONArray(jsonArray2);
		}

		return jsonArray1;
	}

	private Double[] _getSentenceEmbedding(
		SemanticSearchConfiguration semanticSearchConfiguration, String text) {

		try {
			Http.Options options = new Http.Options();

			JSONObject jsonObject = JSONUtil.put("inputs", text);

			options.addHeader(
				HttpHeaders.AUTHORIZATION,
				"Bearer " +
					semanticSearchConfiguration.huggingFaceAccessToken());
			options.addHeader(
				HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

			if (semanticSearchConfiguration.enableGPU()) {
				options.addHeader("x-use-gpu", "true");
			}

			options.setBody(
				jsonObject.toString(), ContentTypes.APPLICATION_JSON,
				StringPool.UTF8);
			options.setLocation(
				"https://api-inference.huggingface.co/models/" +
					semanticSearchConfiguration.model());
			options.setPost(true);

			String responseJSON = _urlToString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() ==
					HttpURLConnection.HTTP_UNAVAILABLE) {

				options.addHeader("x-wait-for-model", "true");
				options.setTimeout(
					semanticSearchConfiguration.modelTimeout() * 1000);

				responseJSON = _urlToString(options);
			}

			if (!_isJSONArray(responseJSON)) {
				throw new IllegalArgumentException(responseJSON);
			}
			else if (!_isValidResponse(responseJSON)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Invalid response: " + responseJSON);
				}

				throw new IllegalArgumentException(
					"The selected model is not valid for creating sentence " +
						"embeddings");
			}
			else {
				List<Double> list = JSONUtil.toDoubleList(
					_getJSONArray(_jsonFactory.createJSONArray(responseJSON)));

				return list.toArray(new Double[0]);
			}
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private boolean _isJSONArray(String s) {
		if (StringUtil.startsWith(s, "[") && StringUtil.endsWith(s, "]")) {
			return true;
		}

		return false;
	}

	private boolean _isValidResponse(String s) {
		if (StringUtil.startsWith(s, "[[") && StringUtil.endsWith(s, "]]")) {
			return true;
		}

		return false;
	}

	private String _urlToString(Http.Options options) throws IOException {
		URI uri = null;

		try {
			uri = HttpComponentsUtil.getURI(options.getLocation());
		}
		catch (URISyntaxException uriSyntaxException) {
			throw new IOException(uriSyntaxException);
		}

		HttpPost httpPost = new HttpPost(uri);

		Http.Body body = options.getBody();

		httpPost.setEntity(
			new StringEntity(body.getContent(), body.getCharset()));

		Map<String, String> headers = options.getHeaders();

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}

		try (CloseableHttpResponse closeableHttpResponse =
				_closeableHttpClient.execute(httpPost)) {

			Http.Response response = options.getResponse();

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			response.setResponseCode(statusLine.getStatusCode());

			for (Header header : closeableHttpResponse.getAllHeaders()) {
				response.addHeader(header.getName(), header.getValue());
			}

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				return StringBundler.concat(
					"(", statusLine.getStatusCode(), ") ",
					statusLine.getReasonPhrase());
			}

			return EntityUtils.toString(closeableHttpResponse.getEntity());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HuggingFaceSentenceTransformer.class);

	private CloseableHttpClient _closeableHttpClient;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	private PoolingHttpClientConnectionManager
		_poolingHttpClientConnectionManager;

}