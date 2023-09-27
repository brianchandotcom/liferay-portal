/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.web.cache;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.search.internal.configuration.AsahSearchKeywordsConfiguration;

import java.net.HttpURLConnection;

/**
 * @author Petteri Karttunen
 */
public class AsahSearchKeywordsWebCacheItem implements WebCacheItem {

	public static JSONObject get(
		AnalyticsConfiguration analyticsConfiguration,
		AsahSearchKeywordsConfiguration asahSearchKeywordsConfiguration,
		long companyId, String displayLanguageId, long groupId, int minCounts,
		int size, String sort) {

		try {
			return (JSONObject)WebCachePoolUtil.get(
				StringBundler.concat(
					AsahSearchKeywordsWebCacheItem.class.getName(),
					StringPool.POUND, companyId, StringPool.POUND, minCounts,
					StringPool.POUND, displayLanguageId, StringPool.POUND,
					groupId, StringPool.POUND, sort),
				new AsahSearchKeywordsWebCacheItem(
					analyticsConfiguration, asahSearchKeywordsConfiguration,
					displayLanguageId, groupId, minCounts, size, sort));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	public AsahSearchKeywordsWebCacheItem(
		AnalyticsConfiguration analyticsConfiguration,
		AsahSearchKeywordsConfiguration asahSearchKeywordsConfiguration,
		String displayLanguageId, long groupId, int minCounts, int size,
		String sort) {

		_analyticsConfiguration = analyticsConfiguration;
		_asahSearchKeywordsConfiguration = asahSearchKeywordsConfiguration;
		_displayLanguageId = displayLanguageId;
		_groupId = groupId;
		_minCounts = minCounts;
		_size = size;
		_sort = sort;
	}

	@Override
	public JSONObject convert(String key) {
		try {
			Http.Options options = new Http.Options();

			options.addHeader(
				"OSB-Asah-Faro-Backend-Security-Signature",
				_analyticsConfiguration.
					liferayAnalyticsFaroBackendSecuritySignature());
			options.addHeader(
				"OSB-Asah-Project-ID",
				_analyticsConfiguration.liferayAnalyticsProjectId());

			String url = _getURL();

			if (_log.isDebugEnabled()) {
				_log.debug("Reading " + url);
			}

			options.setLocation(url);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				HttpUtil.URLtoString(options));

			_validateResponse(jsonObject, options.getResponse());

			return jsonObject;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public long getRefreshTime() {
		return _asahSearchKeywordsConfiguration.cacheTimeout();
	}

	private String _getURL() {
		StringBundler sb = new StringBundler(11);

		sb.append(_analyticsConfiguration.liferayAnalyticsFaroBackendURL());
		sb.append("/api/1.0/pages/search-keywords?minCounts=");
		sb.append(_minCounts);

		if (!Validator.isBlank(_displayLanguageId)) {
			sb.append("&displayLanguageId=");
			sb.append(_displayLanguageId);
		}

		if (_groupId > 0) {
			sb.append("&groupId=");
			sb.append(_groupId);
		}

		sb.append("&size=");
		sb.append(_size);
		sb.append("&sort=");
		sb.append(_sort);

		return sb.toString();
	}

	private void _validateResponse(
		JSONObject jsonObject, Http.Response response) {

		if ((response.getResponseCode() == HttpURLConnection.HTTP_OK) &&
			jsonObject.has("_embedded")) {

			return;
		}

		throw new RuntimeException(
			StringBundler.concat(
				"Response body: ", jsonObject, "\nResponse code: ",
				response.getResponseCode()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahSearchKeywordsWebCacheItem.class);

	private final AnalyticsConfiguration _analyticsConfiguration;
	private final AsahSearchKeywordsConfiguration
		_asahSearchKeywordsConfiguration;
	private final String _displayLanguageId;
	private final long _groupId;
	private final int _minCounts;
	private final int _size;
	private final String _sort;

}