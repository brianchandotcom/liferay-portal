/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.settings;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.AccountEntryTypeException;
import com.liferay.account.internal.configuration.AccountEntryGroupConfiguration;
import com.liferay.account.settings.AccountEntryGroupSettings;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = AccountEntryGroupSettings.class)
public class AccountEntryGroupSettingsImpl
	implements AccountEntryGroupSettings, ConfigurationModelListener {

	@Override
	public String[] getAllowedTypes(long groupId) {
		try {
			AccountEntryGroupConfiguration accountEntryGroupConfiguration =
				_configurationProvider.getGroupConfiguration(
					AccountEntryGroupConfiguration.class, groupId);

			return accountEntryGroupConfiguration.allowedTypes();
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return AccountConstants.ACCOUNT_ENTRY_TYPES_DEFAULT_ALLOWED_TYPES;
	}

	@Override
	public void setAllowedTypes(long groupId, String[] allowedTypes)
		throws AccountEntryTypeException {

		try {
			if (allowedTypes == null) {
				_configurationProvider.deleteGroupConfiguration(
					AccountEntryGroupConfiguration.class, groupId);

				return;
			}

			_configurationProvider.saveGroupConfiguration(
				AccountEntryGroupConfiguration.class, groupId,
				HashMapDictionaryBuilder.<String, Object>put(
					"allowedTypes", allowedTypes
				).build());
		}
		catch (ConfigurationException configurationException) {
			throw new AccountEntryTypeException(
				"Invalid account type", configurationException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryGroupSettingsImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}