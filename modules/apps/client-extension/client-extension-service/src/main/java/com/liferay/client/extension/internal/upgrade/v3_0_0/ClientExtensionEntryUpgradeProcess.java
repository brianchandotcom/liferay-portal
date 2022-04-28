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

package com.liferay.client.extension.internal.upgrade.v3_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Brian Wing Shun Chan
 */
public class ClientExtensionEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateResourcePermissions();

		// TODO Copy RemoteAppEntry

		runSQL("drop table RemoteAppEntry");
	}

	private void _updateResourcePermissions() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ResourcePermission set name = ? where name = ?")) {

			preparedStatement.setString(
				1, "com.liferay.remote.app.model.RemoteAppEntry");
			preparedStatement.setString(
				2, "com.liferay.client.extension.model.ClientExtensionEntry");

			preparedStatement.executeUpdate();
		}
	}

}