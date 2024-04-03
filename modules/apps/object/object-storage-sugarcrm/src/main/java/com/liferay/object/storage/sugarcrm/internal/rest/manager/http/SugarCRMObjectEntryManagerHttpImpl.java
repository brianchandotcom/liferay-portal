/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.storage.sugarcrm.internal.rest.manager.http;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.rest.manager.exception.ObjectEntryManagerHttpException;
import com.liferay.object.rest.manager.http.BaseObjectEntryManagerHttp;
import com.liferay.object.rest.manager.http.ObjectEntryManagerHttp;
import com.liferay.object.storage.sugarcrm.configuration.SugarCRMConfiguration;
import com.liferay.object.storage.sugarcrm.internal.web.cache.SugarCRMAccessTokenWebCacheItem;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(
	property = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_SUGARCRM,
	service = ObjectEntryManagerHttp.class
)
public class SugarCRMObjectEntryManagerHttpImpl
	extends BaseObjectEntryManagerHttp implements ObjectEntryManagerHttp {

	@Override
	public JSONObject getAccessToken(long companyId, long groupId) {
		JSONObject jSONObject = SugarCRMAccessTokenWebCacheItem.get(
			_getSugarCRMConfiguration(companyId, groupId));

		if (jSONObject == null) {
			throw new ObjectEntryManagerHttpException(
				"Unable to authenticate with SugarCRM");
		}

		return jSONObject;
	}

	@Override
	public String getBaseURL(long companyId, long groupId) {
		SugarCRMConfiguration sugarCRMConfiguration = _getSugarCRMConfiguration(
			companyId, groupId);

		return sugarCRMConfiguration.baseURL();
	}

	private SugarCRMConfiguration _getSugarCRMConfiguration(
		long companyId, long groupId) {

		try {
			if (groupId == 0) {
				return _configurationProvider.getCompanyConfiguration(
					SugarCRMConfiguration.class, companyId);
			}

			return _configurationProvider.getGroupConfiguration(
				SugarCRMConfiguration.class, groupId);
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}