/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.internal.web.cache;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
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
import com.liferay.portal.search.internal.util.AsahUtil;

import java.net.HttpURLConnection;

/**
 * @author Petteri Karttunen
 */
public class AsahSearchKeywordsWebCacheItem implements WebCacheItem {

	public static JSONObject get(
		AsahSearchKeywordsConfiguration asahSearchKeywordsConfiguration,
		long companyId, int count, String displayLanguageId, long groupId,
		int size, String sort) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-159643") ||
			!AsahUtil.isAnalyticsEnabled(companyId)) {

			return JSONFactoryUtil.createJSONObject();
		}

		try {
			return (JSONObject)WebCachePoolUtil.get(
				StringBundler.concat(
					AsahSearchKeywordsWebCacheItem.class.getName(),
					StringPool.POUND, companyId, StringPool.POUND, count,
					StringPool.POUND, displayLanguageId, StringPool.POUND,
					groupId, StringPool.POUND, sort),
				new AsahSearchKeywordsWebCacheItem(
					asahSearchKeywordsConfiguration, companyId, count,
					displayLanguageId, groupId, size, sort));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	public AsahSearchKeywordsWebCacheItem(
		AsahSearchKeywordsConfiguration asahSearchKeywordsConfiguration,
		long companyId, int count, String displayLanguageId, long groupId,
		int size, String sort) {

		_asahSearchKeywordsConfiguration = asahSearchKeywordsConfiguration;
		_companyId = companyId;
		_count = count;
		_displayLanguageId = displayLanguageId;
		_groupId = groupId;
		_size = size;
		_sort = sort;
	}

	@Override
	public JSONObject convert(String key) {
		try {
			Http.Options options = new Http.Options();

			options.addHeader(
				"OSB-Asah-Faro-Backend-Security-Signature",
				AsahUtil.getAsahFaroBackendSecuritySignature(_companyId));
			options.addHeader(
				"OSB-Asah-Project-ID", AsahUtil.getAsahProjectId(_companyId));

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
		if (AsahUtil.isAnalyticsEnabled(_companyId)) {
			return _asahSearchKeywordsConfiguration.cacheTimeout();
		}

		return 0;
	}

	private String _getURL() {
		StringBundler sb = new StringBundler(11);

		sb.append(AsahUtil.getAsahFaroBackendURL(_companyId));
		sb.append("/api/1.0/pages/search-keywords?counts=");
		sb.append(_count);

		if (Validator.isBlank(_displayLanguageId)) {
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

	private final AsahSearchKeywordsConfiguration
		_asahSearchKeywordsConfiguration;
	private final long _companyId;
	private final int _count;
	private final String _displayLanguageId;
	private final long _groupId;
	private final int _size;
	private final String _sort;

}