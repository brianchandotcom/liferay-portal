/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v1_10_1;

import com.liferay.commerce.configuration.CommerceAccountGroupServiceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Alec Sloan
 */
public class CommerceSiteTypeUpgradeProcess extends UpgradeProcess {

	public CommerceSiteTypeUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		GroupLocalService groupLocalService,
		ConfigurationProvider configurationProvider) {

		_classNameLocalService = classNameLocalService;
		_groupLocalService = groupLocalService;
		_configurationProvider = configurationProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select siteGroupId from CommerceChannel")) {

			while (resultSet.next()) {
				long groupId = resultSet.getLong("siteGroupId");

				Settings settings = FallbackKeysSettingsUtil.getSettings(
					new GroupServiceSettingsLocator(
						_getCommerceChannelGroupIdBySiteGroupId(groupId),
						CommerceConstants.SERVICE_NAME_COMMERCE_ACCOUNT));

				ModifiableSettings modifiableSettings =
					settings.getModifiableSettings();

				CommerceAccountGroupServiceConfiguration
					commerceAccountGroupServiceConfiguration =
						_configurationProvider.getConfiguration(
							CommerceAccountGroupServiceConfiguration.class,
							new GroupServiceSettingsLocator(
								groupId,
								CommerceConstants.
									SERVICE_NAME_COMMERCE_ACCOUNT));

				modifiableSettings.setValue(
					"commerceSiteType",
					String.valueOf(
						commerceAccountGroupServiceConfiguration.
							commerceSiteType()));

				modifiableSettings.store();
			}
		}
	}

	private long _getCommerceChannelGroupIdBySiteGroupId(long groupId)
		throws SQLException {

		long companyId = 0;
		long commerceChannelId = 0;

		String sql =
			"select * from CommerceChannel where siteGroupId = " + groupId;

		try (Statement s = connection.createStatement()) {
			s.setMaxRows(1);

			try (ResultSet resultSet = s.executeQuery(sql)) {
				if (resultSet.next()) {
					companyId = resultSet.getLong("companyId");
					commerceChannelId = resultSet.getLong("commerceChannelId");
				}
			}
		}

		Group group = _groupLocalService.fetchGroup(
			companyId,
			_classNameLocalService.getClassNameId(
				CommerceChannel.class.getName()),
			commerceChannelId);

		if (group != null) {
			return group.getGroupId();
		}

		return 0;
	}

	private final ClassNameLocalService _classNameLocalService;
	private final ConfigurationProvider _configurationProvider;
	private final GroupLocalService _groupLocalService;

}