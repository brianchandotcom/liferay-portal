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

package com.liferay.search.experiences.internal.web.cache;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.search.experiences.blueprint.exception.InvalidWebCacheItemException;
import com.liferay.search.experiences.internal.configuration.AsahSearchKeywordsConfiguration;
import com.liferay.search.experiences.internal.util.AsahUtil;

import java.net.HttpURLConnection;

/**
 * @author Petteri Karttunen
 */
public class AsahSearchKeywordsWebCacheItem implements WebCacheItem {

	public static JSONObject get(
		AsahSearchKeywordsConfiguration asahSearchKeywordsConfiguration,
		long companyId, int count, String displayLanguageId, long groupId,
		int size, String sort) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-159643")) ||
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
			throw new InvalidWebCacheItemException(exception);
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

		throw new InvalidWebCacheItemException(
			StringBundler.concat(
				"Server returned ", response.getResponseCode(), ". Response: ",
				jsonObject.toString()));
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