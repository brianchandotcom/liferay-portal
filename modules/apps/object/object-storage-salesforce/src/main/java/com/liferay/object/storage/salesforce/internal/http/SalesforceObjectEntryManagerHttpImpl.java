/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.storage.salesforce.internal.http;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.rest.manager.exception.ObjectEntryManagerHttpException;
import com.liferay.object.rest.manager.http.BaseObjectEntryManagerHttp;
import com.liferay.object.rest.manager.http.ObjectEntryManagerHttp;
import com.liferay.object.storage.salesforce.configuration.SalesforceConfiguration;
import com.liferay.object.storage.salesforce.internal.web.cache.SalesforceAccessTokenWebCacheItem;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(
	property = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
	service = ObjectEntryManagerHttp.class
)
public class SalesforceObjectEntryManagerHttpImpl
	extends BaseObjectEntryManagerHttp implements ObjectEntryManagerHttp {

	@Override
	public JSONObject getAccessToken(long companyId, long groupId) {
		int retry = 0;

		while (retry < 3) {
			JSONObject jSONObject = SalesforceAccessTokenWebCacheItem.get(
				_getSalesforceConfiguration(companyId, groupId));

			if (jSONObject != null) {
				return jSONObject;
			}

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

		throw new ObjectEntryManagerHttpException(
			"Unable to authenticate with Salesforce");
	}

	@Override
	public String getBaseURL(long companyId, long groupId) {
		JSONObject jsonObject = getAccessToken(companyId, groupId);

		return jsonObject.getString("instance_url") + "/services/data/v54.0";
	}

	private SalesforceConfiguration _getSalesforceConfiguration(
		long companyId, long groupId) {

		try {
			if (groupId == 0) {
				return _configurationProvider.getCompanyConfiguration(
					SalesforceConfiguration.class, companyId);
			}

			return _configurationProvider.getGroupConfiguration(
				SalesforceConfiguration.class, groupId);
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SalesforceObjectEntryManagerHttpImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}