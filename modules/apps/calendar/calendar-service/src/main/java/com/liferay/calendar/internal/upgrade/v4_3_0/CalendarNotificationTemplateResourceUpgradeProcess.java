/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.upgrade.v4_3_0;

import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Feliphe Marinho
 */
public class CalendarNotificationTemplateResourceUpgradeProcess
	extends UpgradeProcess {

	public CalendarNotificationTemplateResourceUpgradeProcess(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select calendarNotificationTemplateId, companyId, userId " +
					"from CalendarNotificationTemplate");

			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				_resourceLocalService.addResources(
					resultSet.getLong("companyId"), 0,
					resultSet.getLong("userId"),
					CalendarNotificationTemplate.class.getName(),
					resultSet.getLong("calendarNotificationTemplateId"), false,
					false, false);
			}
		}
	}

	private final ResourceLocalService _resourceLocalService;

}