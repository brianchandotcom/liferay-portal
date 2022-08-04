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

package com.liferay.client.extension.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradePortletId extends BasePortletIdUpgradeProcess {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return getRenamePortletIdsArray(
			connection, "remote_app_",
			"com_liferay_remote_app_web_internal_portlet_" +
				"RemoteAppEntryPortlet_");
	}

	protected String[][] getRenamePortletIdsArray(
		Connection connection, String oldPortletIdPrefix,
		String newPortletIdPrefix) {

		List<String[]> portletIds = new ArrayList<>();

		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select clientExtensionEntryId from ClientExtensionEntry")) {

			while (resultSet.next()) {
				long clientExtensionEntryId = resultSet.getLong(
					"clientExtensionEntryId");

				portletIds.add(
					new String[] {
						oldPortletIdPrefix + clientExtensionEntryId,
						newPortletIdPrefix + clientExtensionEntryId
					});
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return portletIds.toArray(new String[0][0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletId.class);

}