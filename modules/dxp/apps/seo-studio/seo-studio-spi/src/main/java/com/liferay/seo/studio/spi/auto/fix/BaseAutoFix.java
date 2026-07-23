/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.spi.auto.fix;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Objects;

/**
 * @author David Truong
 */
public abstract class BaseAutoFix implements AutoFix {

	@Override
	public Response apply(
			String accessToken, String baseURL,
			String cachedSiteExternalReferenceCode, String pagePath,
			String value)
		throws Exception {

		String friendlyURLPath = _stripLayoutSetPrefix(pagePath);

		int index = friendlyURLPath.indexOf(CharPool.SLASH, 1);

		if (index == -1) {
			return _buildErrorResponse(
				"The page URL does not contain a page friendly URL",
				Response.Status.BAD_REQUEST);
		}

		String siteExternalReferenceCode = cachedSiteExternalReferenceCode;

		if (Validator.isNull(siteExternalReferenceCode)) {
			siteExternalReferenceCode = _resolveSiteExternalReferenceCode(
				accessToken, baseURL, friendlyURLPath.substring(0, index));
		}

		if (Validator.isNull(siteExternalReferenceCode)) {
			return _buildErrorResponse(
				"Unable to resolve the site from the page URL",
				Response.Status.NOT_FOUND);
		}

		JSONObject sitePageJSONObject = _resolveSitePage(
			accessToken, baseURL, friendlyURLPath.substring(index),
			siteExternalReferenceCode);

		if (sitePageJSONObject == null) {
			return _buildErrorResponse(
				"Unable to resolve the page from the page URL",
				Response.Status.NOT_FOUND);
		}

		String languageId = sitePageJSONObject.getString("languageId");

		String sitePageURL = StringBundler.concat(
			baseURL, "/o/headless-admin-site/v1.0/sites/",
			siteExternalReferenceCode, "/site-pages/",
			sitePageJSONObject.getString("externalReferenceCode"));

		int responseCode = _patch(
			accessToken,
			getPatchBody(
				languageId, sitePageJSONObject.getString("type"), value),
			sitePageURL);

		if ((responseCode < 200) || (responseCode >= 300)) {
			return _buildErrorResponse(
				"The customer instance rejected the fix",
				Response.Status.BAD_GATEWAY);
		}

		JSONObject patchedSitePageJSONObject = _get(accessToken, sitePageURL);

		if ((patchedSitePageJSONObject == null) ||
			!Objects.equals(
				value,
				JSONUtil.getValue(
					patchedSitePageJSONObject,
					getVerificationPaths(languageId)))) {

			return _buildErrorResponse(
				"The customer instance did not persist the fix",
				Response.Status.BAD_GATEWAY);
		}

		return Response.ok(
			JSONUtil.put(
				"success", true
			).toString(),
			MediaType.APPLICATION_JSON
		).build();
	}

	protected abstract String getPatchBody(
		String languageId, String type, String value);

	protected abstract String[] getVerificationPaths(String languageId);

	private Response _buildErrorResponse(
		String message, Response.Status status) {

		return Response.status(
			status
		).entity(
			JSONUtil.put(
				"error", message
			).toString()
		).type(
			MediaType.APPLICATION_JSON
		).build();
	}

	private JSONObject _get(String accessToken, String url) throws Exception {
		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
		options.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		options.setLocation(url);

		String responseString = HttpUtil.URLtoString(options);

		Http.Response response = options.getResponse();

		if (response.getResponseCode() != HttpServletResponse.SC_OK) {
			return null;
		}

		return JSONFactoryUtil.createJSONObject(responseString);
	}

	private int _patch(String accessToken, String body, String url)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.setBody(body, ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation(url);
		options.setMethod(Http.Method.PATCH);

		String responseString = HttpUtil.URLtoString(options);

		Http.Response response = options.getResponse();

		int responseCode = response.getResponseCode();

		if (((responseCode < 200) || (responseCode >= 300)) &&
			_log.isWarnEnabled()) {

			_log.warn(
				StringBundler.concat(
					"Remote update to ", url, " returned ", responseCode, ": ",
					responseString));
		}

		return responseCode;
	}

	private String _resolveSiteExternalReferenceCode(
			String accessToken, String baseURL, String siteFriendlyURLPath)
		throws Exception {

		JSONObject sitesJSONObject = _get(
			accessToken,
			baseURL + "/o/headless-admin-site/v1.0/sites?pageSize=200");

		if (sitesJSONObject == null) {
			return null;
		}

		JSONArray itemsJSONArray = sitesJSONObject.getJSONArray("items");

		if (itemsJSONArray == null) {
			return null;
		}

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			if (Objects.equals(
					itemJSONObject.getString("friendlyUrlPath"),
					siteFriendlyURLPath)) {

				return itemJSONObject.getString("externalReferenceCode");
			}
		}

		return null;
	}

	private JSONObject _resolveSitePage(
			String accessToken, String baseURL, String pageFriendlyURLPath,
			String siteExternalReferenceCode)
		throws Exception {

		JSONObject sitePagesJSONObject = _get(
			accessToken,
			StringBundler.concat(
				baseURL, "/o/headless-admin-site/v1.0/sites/",
				siteExternalReferenceCode, "/site-pages?pageSize=200"));

		if (sitePagesJSONObject == null) {
			return null;
		}

		JSONArray itemsJSONArray = sitePagesJSONObject.getJSONArray("items");

		if (itemsJSONArray == null) {
			return null;
		}

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			JSONObject friendlyURLPathI18nJSONObject =
				itemJSONObject.getJSONObject("friendlyUrlPath_i18n");

			if (friendlyURLPathI18nJSONObject == null) {
				continue;
			}

			for (String languageId : friendlyURLPathI18nJSONObject.keySet()) {
				if (!Objects.equals(
						friendlyURLPathI18nJSONObject.getString(languageId),
						pageFriendlyURLPath)) {

					continue;
				}

				JSONObject pageSettingsJSONObject =
					itemJSONObject.getJSONObject("pageSettings");

				return JSONUtil.put(
					"externalReferenceCode",
					itemJSONObject.getString("externalReferenceCode")
				).put(
					"languageId", languageId
				).put(
					"type", pageSettingsJSONObject.getString("type")
				);
			}
		}

		return null;
	}

	private String _stripLayoutSetPrefix(String path) {
		if (path.startsWith("/web/")) {
			return path.substring(4);
		}

		if (path.startsWith("/group/")) {
			return path.substring(6);
		}

		return path;
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseAutoFix.class);

}