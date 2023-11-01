/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.search.internal.configuration.AsahIndividualsConfiguration;

import java.net.HttpURLConnection;

import java.security.MessageDigest;

/**
 * @author Gustavo Lima
 */
public class AsahIndividualsWebCacheItem implements WebCacheItem {

	public static JSONObject get(
		AnalyticsConfiguration analyticsConfiguration,
		AsahIndividualsConfiguration asahIndividualsConfiguration,
		String contentType, long companyId, String displayLanguageId,
		String endPointName, String endPointUsage, long groupId, int minCounts,
		int page, int rangeKey, int size, String sort, long userId) {

		try {
			return (JSONObject)WebCachePoolUtil.get(
				StringBundler.concat(
					AsahIndividualsWebCacheItem.class.getName(),
					StringPool.POUND, companyId, StringPool.POUND, minCounts,
					StringPool.POUND, displayLanguageId, StringPool.POUND,
					groupId, StringPool.POUND, sort),
				new AsahIndividualsWebCacheItem(
					analyticsConfiguration, asahIndividualsConfiguration,
					contentType, displayLanguageId, endPointName, endPointUsage,
					groupId, minCounts, page, rangeKey, size, sort, userId));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	public AsahIndividualsWebCacheItem(
		AnalyticsConfiguration analyticsConfiguration,
		AsahIndividualsConfiguration asahIndividualsConfiguration,
		String contentType, String displayLanguageId, String endPointName,
		String endPointUsage, long groupId, int minCounts, int page,
		int rangeKey, int size, String sort, long userId) {

		_analyticsConfiguration = analyticsConfiguration;
		_asahIndividualsConfiguration = asahIndividualsConfiguration;
		_contentType = contentType;
		_displayLanguageId = displayLanguageId;
		_endPointName = endPointName;
		_endPointUsage = endPointUsage;
		_groupId = groupId;
		_minCounts = minCounts;
		_page = page;
		_rangeKey = rangeKey;
		_size = size;
		_sort = sort;
		_userId = userId;
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
		return _asahIndividualsConfiguration.cacheTimeout();
	}

	private String _getHashedEmail() {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

			messageDigest.update(
				PortalUtil.getUserEmailAddress(
					_userId
				).getBytes());

			byte[] digest = messageDigest.digest();

			StringBuilder sb = new StringBuilder();

			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return StringPool.BLANK;
	}

	private String _getURL() {
		StringBundler sb = new StringBundler(24);

		sb.append(_analyticsConfiguration.liferayAnalyticsFaroBackendURL());
		sb.append("/api/1.0/");
		sb.append(_endPointName);

		sb.append("/");
		sb.append(_getHashedEmail());
		sb.append("/");
		sb.append(_endPointUsage);
		sb.append("?");

		if (_minCounts > 0) {
			sb.append("&minCounts=");
			sb.append(_minCounts);
		}

		if (!Validator.isBlank(_contentType)) {
			sb.append("&contentType=");
			sb.append(_contentType);
		}

		if (!Validator.isBlank(_displayLanguageId)) {
			sb.append("&displayLanguageId=");
			sb.append(_displayLanguageId);
		}

		if (_groupId > 0) {
			sb.append("&groupId=");
			sb.append(_groupId);
		}

		if (_page > 0) {
			sb.append("&page=");
			sb.append(_page);
		}

		if (_size != 5) {
			sb.append("&size=");
			sb.append(_size);
		}

		if (_rangeKey != 7) {
			sb.append("&rangeKey=");
			sb.append(_rangeKey);
		}

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
		AsahIndividualsWebCacheItem.class);

	private final AnalyticsConfiguration _analyticsConfiguration;
	private final AsahIndividualsConfiguration _asahIndividualsConfiguration;
	private final String _contentType;
	private final String _displayLanguageId;
	private final String _endPointName;
	private final String _endPointUsage;
	private final long _groupId;
	private final int _minCounts;
	private final int _page;
	private final int _rangeKey;
	private final int _size;
	private final String _sort;
	private final long _userId;

}