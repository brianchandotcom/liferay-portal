/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.dao.orm.common.SQLTransformer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alejandro Tardín
 */
public class PrivateMessagingUpgradeProcess extends BaseUpgradeProcess {

	public PrivateMessagingUpgradeProcess(
		MBThreadLocalService mbThreadLocalService) {

		_mbThreadLocalService = mbThreadLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_deleteMBThreads();

		removePortletData(
			new String[] {"com.liferay.social.privatemessaging.web"},
			new String[] {"1_WAR_privatemessagingportlet"},
			new String[] {
				"com_liferay_social_privatemessaging_web_portlet_" +
					"PrivateMessagingPortlet"
			});

		removeServiceData(
			"PM", new String[] {"com.liferay.social.privatemessaging.service"},
			new String[] {
				"com.liferay.social.privatemessaging.model.UserThread"
			},
			new String[] {"PM_UserThread"});
	}

	private void _deleteMBThreads() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					"select mbThreadId from PM_UserThread"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				_mbThreadLocalService.deleteMBThread(resultSet.getLong(1));
			}
		}
	}

	private final MBThreadLocalService _mbThreadLocalService;

}