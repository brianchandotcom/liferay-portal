/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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