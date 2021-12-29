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

package com.liferay.change.tracking.web.internal.upgrade.v1_0_5;

import com.liferay.change.tracking.web.internal.constants.PublicationRoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Cheryl Tang
 */
public class PublicationsAdminRoleNameUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update Role_ set name = ?, title = NULL where name = ?")) {

			preparedStatement.setString(1, PublicationRoleConstants.NAME_ADMIN);
			preparedStatement.setString(2, _NAME_INVITER);

			preparedStatement.executeUpdate();
		}
	}

	private static final String _NAME_INVITER =
		"com_liferay_change_tracking_web_portlet_PublicationsPortlet.inviter";

}