/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.web.cache;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;

import java.net.HttpURLConnection;

/**
 * @author Gustavo Lima
 */
public abstract class BaseAsahWebCacheItem implements WebCacheItem {

	public BaseAsahWebCacheItem(
		AnalyticsConfiguration analyticsConfiguration, String basePath,
		String displayLanguageId, long groupId, int minCounts, String path,
		int size, String sort) {

		this.analyticsConfiguration = analyticsConfiguration;
		this.basePath = basePath;
		this.displayLanguageId = displayLanguageId;
		this.groupId = groupId;
		this.minCounts = minCounts;
		this.path = path;
		this.size = size;
		this.sort = sort;
	}

	@Override
	public JSONObject convert(String key) {
		try {
			Http.Options options = new Http.Options();

			options.addHeader(
				"OSB-Asah-Faro-Backend-Security-Signature",
				analyticsConfiguration.
					liferayAnalyticsFaroBackendSecuritySignature());
			options.addHeader(
				"OSB-Asah-Project-ID",
				analyticsConfiguration.liferayAnalyticsProjectId());

			String url = getURL();

			if (Validator.isBlank(url)) {
				return JSONFactoryUtil.createJSONObject();
			}

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

	protected abstract String getURL();

	protected AnalyticsConfiguration analyticsConfiguration;
	protected String basePath;
	protected String displayLanguageId;
	protected long groupId;
	protected int minCounts;
	protected String path;
	protected int size;
	protected String sort;

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
		BaseAsahWebCacheItem.class);

}