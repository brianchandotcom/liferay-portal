/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.internal.upgrade.v16_0_0;

import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Matthew Kong
 */
public class UpgradeUserGroupRole extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select roleId from Role_ where name in (?, ?, ?)")) {

			ps.setString(1, RoleConstants.SITE_ADMINISTRATOR);
			ps.setString(2, RoleConstants.SITE_MEMBER);
			ps.setString(3, RoleConstants.SITE_OWNER);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				try (PreparedStatement psUpdate = connection.prepareStatement(
						"delete from UserGroupRole where roleId = ?")) {

					psUpdate.setLong(1, rs.getLong(1));

					psUpdate.executeUpdate();
				}
			}
		}
	}

}