/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.configuration.manager;

import com.liferay.friendly.url.configuration.FriendlyURLSeparatorCompanyConfiguration;
import com.liferay.friendly.url.configuration.manager.FriendlyURLSeparatorConfigurationManager;
import com.liferay.friendly.url.provider.FriendlyURLSeparatorProvider;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = FriendlyURLSeparatorConfigurationManager.class)
public class FriendlyURLSeparatorConfigurationManagerImpl
	implements FriendlyURLSeparatorConfigurationManager {

	@Override
	public String getFriendlyURLSeparatorsJSON(long companyId)
		throws ConfigurationException {

		FriendlyURLSeparatorCompanyConfiguration
			friendlyURLSeparatorCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FriendlyURLSeparatorCompanyConfiguration.class, companyId);

		return friendlyURLSeparatorCompanyConfiguration.
			friendlyURLSeparatorsJSON();
	}

	@Override
	public void updateFriendlyURLSeparatorCompanyConfiguration(
			long companyId, String friendlyURLSeparatorsJSON)
		throws ConfigurationException {

		PortalCache<Long, JSONObject> portalCache =
			(PortalCache<Long, JSONObject>)_multiVMPool.getPortalCache(
				FriendlyURLSeparatorProvider.class.getName());

		portalCache.remove(companyId);

		_configurationProvider.saveCompanyConfiguration(
			FriendlyURLSeparatorCompanyConfiguration.class, companyId,
			HashMapDictionaryBuilder.<String, Object>put(
				"friendlyURLSeparatorsJSON", friendlyURLSeparatorsJSON
			).build());

		try {
			portalCache.put(
				companyId,
				_jsonFactory.createJSONObject(friendlyURLSeparatorsJSON));
		}
		catch (JSONException jsonException) {
			throw new ConfigurationException(jsonException);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MultiVMPool _multiVMPool;

}