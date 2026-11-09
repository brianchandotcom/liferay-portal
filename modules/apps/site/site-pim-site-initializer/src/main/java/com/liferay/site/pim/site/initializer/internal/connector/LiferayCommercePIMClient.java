/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.pim.site.initializer.internal.connector;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.site.pim.site.initializer.exception.PIMConnectorException;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Tancredi Covioli
 */
public class LiferayCommercePIMClient {

	public LiferayCommercePIMClient(
		String clientId, String clientSecret, Http http,
		JSONFactory jsonFactory, String url) {

		_clientId = clientId;
		_clientSecret = clientSecret;
		_http = http;
		_jsonFactory = jsonFactory;
		_url = url;
	}

	public void authenticate() throws Exception {
		Http.Options options = new Http.Options();

		options.addPart("client_id", _clientId);
		options.addPart("client_secret", _clientSecret);
		options.addPart("grant_type", "client_credentials");
		options.setLocation(_url + "/o/oauth2/token");
		options.setPost(true);

		String responseString = _invoke(
			JSONUtil.put(
				"client_id", _clientId
			).put(
				"grant_type", "client_credentials"
			),
			options);

		if (!_isSuccessfulResponse(options.getResponse())) {
			throw new PIMConnectorException(
				"unable-to-authenticate-with-the-remote-instance");
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(responseString);

		_accessToken = jsonObject.getString("access_token");
	}

	public JSONArray getLogJSONArray() {
		return _logJSONArray;
	}

	public boolean hasSiteTaxonomyVocabulary(String externalReferenceCode)
		throws Exception {

		Http.Options options = _createOptions(
			null, Http.Method.GET,
			StringBundler.concat(
				"/o/headless-admin-taxonomy/v1.0/sites/L_GLOBAL",
				"/taxonomy-vocabularies/by-external-reference-code/",
				URLCodec.encodeURL(externalReferenceCode)));

		_invoke(null, options);

		Http.Response response = options.getResponse();

		if (response.getResponseCode() == HttpServletResponse.SC_NOT_FOUND) {
			return false;
		}

		_validateResponse(response);

		return true;
	}

	public JSONObject postImportTask(JSONArray jsonArray) throws Exception {
		Http.Options options = _createOptions(
			jsonArray, Http.Method.POST,
			StringBundler.concat(
				"/o/headless-batch-engine/v1.0/import-task",
				"/com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product",
				"?createStrategy=UPSERT&importStrategy=ON_ERROR_CONTINUE"));

		String responseString = _invoke(jsonArray, options);

		_validateResponse(options.getResponse());

		return _jsonFactory.createJSONObject(responseString);
	}

	public void putOptionByExternalReferenceCode(
			String externalReferenceCode, JSONObject jsonObject)
		throws Exception {

		Http.Options options = _createOptions(
			jsonObject, Http.Method.PUT,
			StringBundler.concat(
				"/o/headless-commerce-admin-catalog/v1.0/options",
				"/by-externalReferenceCode/",
				URLCodec.encodeURL(externalReferenceCode)));

		_invoke(jsonObject, options);

		_validateResponse(options.getResponse());
	}

	public void putSiteTaxonomyCategoryByExternalReferenceCode(
			String externalReferenceCode, JSONObject jsonObject)
		throws Exception {

		Http.Options options = _createOptions(
			jsonObject, Http.Method.PUT,
			StringBundler.concat(
				"/o/headless-admin-taxonomy/v1.0/sites/L_GLOBAL",
				"/taxonomy-categories/by-external-reference-code/",
				URLCodec.encodeURL(externalReferenceCode)));

		_invoke(jsonObject, options);

		_validateResponse(options.getResponse());
	}

	public void putSiteTaxonomyVocabularyByExternalReferenceCode(
			String externalReferenceCode, JSONObject jsonObject)
		throws Exception {

		Http.Options options = _createOptions(
			jsonObject, Http.Method.PUT,
			StringBundler.concat(
				"/o/headless-admin-taxonomy/v1.0/sites/L_GLOBAL",
				"/taxonomy-vocabularies/by-external-reference-code/",
				URLCodec.encodeURL(externalReferenceCode)));

		_invoke(jsonObject, options);

		_validateResponse(options.getResponse());
	}

	private Http.Options _createOptions(
		Object body, Http.Method method, String path) {

		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
		options.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + _accessToken);

		if (body != null) {
			options.addHeader(
				HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
			options.setBody(
				body.toString(), ContentTypes.APPLICATION_JSON,
				StringPool.UTF8);
		}

		options.setLocation(_url + path);
		options.setMethod(method);

		return options;
	}

	private String _invoke(Object requestBody, Http.Options options)
		throws Exception {

		JSONObject jsonObject = JSONUtil.put(
			"request",
			JSONUtil.put(
				"body", requestBody
			).put(
				"method", String.valueOf(options.getMethod())
			).put(
				"url", options.getLocation()
			));

		_logJSONArray.put(jsonObject);

		try {
			String responseString = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			jsonObject.put(
				"response",
				JSONUtil.put(
					"body", _toLogValue(responseString)
				).put(
					"status", response.getResponseCode()
				));

			return responseString;
		}
		catch (IOException ioException) {
			jsonObject.put(
				"response", JSONUtil.put("error", ioException.getMessage()));

			throw new PIMConnectorException(
				"unable-to-connect-to-the-remote-instance", ioException);
		}
	}

	private boolean _isSuccessfulResponse(Http.Response response) {
		if ((response.getResponseCode() >= HttpServletResponse.SC_OK) &&
			(response.getResponseCode() <
				HttpServletResponse.SC_MULTIPLE_CHOICES)) {

			return true;
		}

		return false;
	}

	private Object _toLogValue(String responseString) throws Exception {
		if (!JSONUtil.isJSONObject(responseString)) {
			return responseString;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(responseString);

		jsonObject.remove("access_token");

		return jsonObject;
	}

	private void _validateResponse(Http.Response response) throws Exception {
		if ((response.getResponseCode() ==
				HttpServletResponse.SC_UNAUTHORIZED) ||
			(response.getResponseCode() == HttpServletResponse.SC_FORBIDDEN)) {

			throw new PIMConnectorException(
				"unable-to-authenticate-with-the-remote-instance");
		}

		if (!_isSuccessfulResponse(response)) {
			throw new PIMConnectorException(
				"the-remote-instance-returned-an-error");
		}
	}

	private String _accessToken;
	private final String _clientId;
	private final String _clientSecret;
	private final Http _http;
	private final JSONFactory _jsonFactory;
	private final JSONArray _logJSONArray = JSONUtil.putAll();
	private final String _url;

}