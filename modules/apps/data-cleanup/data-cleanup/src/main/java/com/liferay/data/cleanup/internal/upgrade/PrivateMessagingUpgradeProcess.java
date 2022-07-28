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