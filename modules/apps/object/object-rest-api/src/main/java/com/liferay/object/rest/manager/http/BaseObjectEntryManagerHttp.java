/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.manager.http;

import com.liferay.object.rest.manager.exception.ObjectEntryManagerHttpException;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;

import java.net.HttpURLConnection;

/**
 * @author Guilherme Camacho
 */
public abstract class BaseObjectEntryManagerHttp
	implements ObjectEntryManagerHttp {

	@Override
	public JSONObject delete(long companyId, long groupId, String location) {
		try {
			return _invoke(
				companyId, groupId, location, Http.Method.DELETE, null);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	@Override
	public JSONObject get(long companyId, long groupId, String location) {
		try {
			return _invoke(companyId, groupId, location, Http.Method.GET, null);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	@Override
	public JSONObject patch(
		long companyId, long groupId, String location,
		JSONObject bodyJSONObject) {

		try {
			return _invoke(
				companyId, groupId, location, Http.Method.PATCH,
				bodyJSONObject);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	@Override
	public JSONObject post(
		long companyId, long groupId, String location,
		JSONObject bodyJSONObject) {

		try {
			return _invoke(
				companyId, groupId, location, Http.Method.POST, bodyJSONObject);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	@Override
	public JSONObject put(
		long companyId, long groupId, String location,
		JSONObject bodyJSONObject) {

		try {
			return _invoke(
				companyId, groupId, location, Http.Method.PUT, bodyJSONObject);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private JSONObject _invoke(
			long companyId, long groupId, String location, Http.Method method,
			JSONObject bodyJSONObject)
		throws Exception {

		byte[] bytes = _invokeAsBytes(
			companyId, groupId, location, method, bodyJSONObject);

		if (bytes == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		return JSONFactoryUtil.createJSONObject(new String(bytes));
	}

	private byte[] _invokeAsBytes(
			long companyId, long groupId, String location, Http.Method method,
			JSONObject bodyJSONObject)
		throws Exception {

		Http.Options options = new Http.Options();

		if (bodyJSONObject != null) {
			options.addHeader(
				HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		}

		JSONObject jsonObject = getAccessToken(companyId, groupId);

		options.addHeader(
			"Authorization", "Bearer " + jsonObject.getString("access_token"));

		if (bodyJSONObject != null) {
			options.setBody(
				bodyJSONObject.toString(), ContentTypes.APPLICATION_JSON,
				StringPool.UTF8);
		}

		options.setFollowRedirects(false);
		options.setLocation(
			getBaseURL(companyId, groupId) + StringPool.FORWARD_SLASH +
				location);
		options.setMethod(method);

		if (_log.isDebugEnabled()) {
			_log.debug("Proxy connector calling URL: " + options.getLocation());
		}

		byte[] bytes = HttpUtil.URLtoByteArray(options);

		Http.Response response = options.getResponse();

		if ((response.getResponseCode() < HttpURLConnection.HTTP_OK) ||
			(response.getResponseCode() >=
				HttpURLConnection.HTTP_MULT_CHOICE)) {

			throw new ObjectEntryManagerHttpException(
				StringBundler.concat(
					"Unexpected response code ", response.getResponseCode(),
					" with response message: ", new String(bytes)));
		}

		return bytes;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseObjectEntryManagerHttp.class);

}