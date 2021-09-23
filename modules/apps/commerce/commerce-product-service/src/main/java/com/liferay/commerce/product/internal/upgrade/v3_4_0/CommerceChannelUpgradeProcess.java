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

package com.liferay.commerce.product.internal.upgrade.v3_4_0;

import com.liferay.account.settings.AccountEntryGroupSettings;
import com.liferay.commerce.account.configuration.CommerceAccountGroupServiceConfiguration;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.util.AccountEntryAllowedTypesUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Riccardo Alberti
 */
public class CommerceChannelUpgradeProcess extends UpgradeProcess {

	public CommerceChannelUpgradeProcess(
		AccountEntryGroupSettings accountEntryGroupSettings,
		ConfigurationProvider configurationProvider) {

		_accountEntryGroupSettings = accountEntryGroupSettings;
		_configurationProvider = configurationProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select CommerceChannel.siteGroupId, Group_.groupId from ",
					"CommerceChannel inner join Group_ on ",
					"CommerceChannel.commerceChannelId = Group_.classPK and ",
					"Group_.classNameId = ",
					ClassNameLocalServiceUtil.getClassNameId(
						CommerceChannel.class)));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long siteGroupId = resultSet.getLong(1);

				if (siteGroupId == 0) {
					continue;
				}

				long groupId = resultSet.getLong(2);

				_accountEntryGroupSettings.setAllowedTypes(
					siteGroupId, _getAllowedTypes(groupId));
			}
		}
	}

	private String[] _getAllowedTypes(long commerceChannelGroupId)
		throws ConfigurationException {

		CommerceAccountGroupServiceConfiguration
			commerceAccountGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					CommerceAccountGroupServiceConfiguration.class,
					new GroupServiceSettingsLocator(
						commerceChannelGroupId,
						CommerceAccountConstants.SERVICE_NAME));

		return AccountEntryAllowedTypesUtil.getAllowedTypes(
			commerceAccountGroupServiceConfiguration.commerceSiteType());
	}

	private final AccountEntryGroupSettings _accountEntryGroupSettings;
	private final ConfigurationProvider _configurationProvider;

}