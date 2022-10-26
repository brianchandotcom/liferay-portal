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

package com.liferay.account.internal.settings;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.AccountEntryTypeException;
import com.liferay.account.internal.configuration.AccountEntryGroupConfiguration;
import com.liferay.account.settings.AccountEntryGroupSettings;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
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