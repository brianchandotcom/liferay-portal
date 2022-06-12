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

package com.liferay.object.storage.salesforce.internal.web.cache;

import com.liferay.object.storage.salesforce.internal.configuration.SalesforceConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class SalesforceAccessTokenWebCacheItem implements WebCacheItem {

	public static JSONObject get(
		SalesforceConfiguration salesforceConfiguration) {

		return (JSONObject)WebCachePoolUtil.get(
			StringBundler.concat(
				SalesforceAccessTokenWebCacheItem.class.getName(),
				StringPool.POUND, salesforceConfiguration.consumerKey(),
				StringPool.POUND, salesforceConfiguration.consumerSecret(),
				StringPool.POUND, salesforceConfiguration.username()),
			new SalesforceAccessTokenWebCacheItem(salesforceConfiguration));
	}

	public SalesforceAccessTokenWebCacheItem(
		SalesforceConfiguration salesforceConfiguration) {

		_salesforceConfiguration = salesforceConfiguration;
	}

	@Override
	public JSONObject convert(String key) {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Get Salesforce access token for consumer key " +
						_salesforceConfiguration.consumerKey());
			}

			Http.Options options = new Http.Options();

			options.setParts(
				HashMapBuilder.put(
					"client_id", _salesforceConfiguration.consumerKey()
				).put(
					"client_secret", _salesforceConfiguration.consumerSecret()
				).put(
					"grant_type", "password"
				).put(
					"password", _salesforceConfiguration.password()
				).put(
					"username", _salesforceConfiguration.username()
				).build());
			options.setLocation(
				_salesforceConfiguration.loginURL() + "/services/oauth2/token");
			options.setPost(true);

			return JSONFactoryUtil.createJSONObject(
				HttpUtil.URLtoString(options));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	@Override
	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	private static final long _REFRESH_TIME = Time.MINUTE * 45;

	private static final Log _log = LogFactoryUtil.getLog(
		SalesforceAccessTokenWebCacheItem.class);

	private final SalesforceConfiguration _salesforceConfiguration;

}