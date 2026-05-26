/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.configuration.manager;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.site.configuration.manager.LLMSConfigurationManager;
import com.liferay.site.internal.configuration.LLMSCompanyConfiguration;
import com.liferay.site.internal.configuration.LLMSGroupConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan McCann
 */
@Component(service = LLMSConfigurationManager.class)
public class LLMSConfigurationManagerImpl implements LLMSConfigurationManager {

	@Override
	public String getCompanyContent(long companyId)
		throws ConfigurationException {

		LLMSCompanyConfiguration llmsCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				LLMSCompanyConfiguration.class, companyId);

		return llmsCompanyConfiguration.content();
	}

	@Override
	public String getGroupContent(long companyId, long groupId)
		throws ConfigurationException {

		LLMSGroupConfiguration llmsGroupConfiguration =
			_configurationProvider.getGroupConfiguration(
				LLMSGroupConfiguration.class, companyId, groupId);

		return llmsGroupConfiguration.content();
	}

	@Override
	public boolean isCompanyEnabled(long companyId)
		throws ConfigurationException {

		LLMSCompanyConfiguration llmsCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				LLMSCompanyConfiguration.class, companyId);

		return llmsCompanyConfiguration.enabled();
	}

	@Override
	public boolean isGroupEnabled(long companyId, long groupId)
		throws ConfigurationException {

		LLMSGroupConfiguration llmsGroupConfiguration =
			_configurationProvider.getGroupConfiguration(
				LLMSGroupConfiguration.class, companyId, groupId);

		return llmsGroupConfiguration.enabled();
	}

	@Override
	public void saveLLMSCompanyConfiguration(
			long companyId, boolean enabled, String content)
		throws ConfigurationException {

		_configurationProvider.saveCompanyConfiguration(
			LLMSCompanyConfiguration.class, companyId,
			HashMapDictionaryBuilder.<String, Object>put(
				"content", content
			).put(
				"enabled", enabled
			).build());
	}

	@Override
	public void saveLLMSGroupConfiguration(
			long companyId, long groupId, boolean enabled, String content)
		throws ConfigurationException {

		_configurationProvider.saveGroupConfiguration(
			LLMSGroupConfiguration.class, companyId, groupId,
			HashMapDictionaryBuilder.<String, Object>put(
				"content", content
			).put(
				"enabled", enabled
			).build());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}