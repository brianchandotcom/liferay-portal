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

import com.liferay.data.cleanup.internal.upgrade.util.LayoutTypeSettingsUtil;
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
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.social.privatemessaging.model.UserThread'");

		runSQL(
			"delete from Portlet where portletId = " +
				"'com_liferay_social_privatemessaging_web_portlet_" +
					"PrivateMessagingPortlet'");

		runSQL(
			"delete from PortletPreferences where portletId =" +
				"'com_liferay_social_privatemessaging_web_portlet_" +
					"PrivateMessagingPortlet'");

		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.social.privatemessaging.service'");
		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.social.privatemessaging.web'");

		runSQL("delete from ServiceComponent where buildNamespace = 'PM'");

		_deleteThreads();

		runSQL("drop table PM_UserThread");

		LayoutTypeSettingsUtil.removePortletId(
			connection,
			"com_liferay_social_privatemessaging_web_portlet_" +
				"PrivateMessagingPortlet");
	}

	private void _deleteThreads() throws Exception {
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