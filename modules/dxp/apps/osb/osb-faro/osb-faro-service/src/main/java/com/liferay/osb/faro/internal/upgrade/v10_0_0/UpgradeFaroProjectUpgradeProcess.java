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

package com.liferay.osb.faro.internal.upgrade.v10_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Matthew Kong
 */
public class UpgradeFaroProjectUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateIncidentReportEmailAddresses();
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"OSBFaro_FaroProject", "incidentReportEmailAddresses STRING")
		};
	}

	private long _getSiteOwnerRoleId() throws Exception {
		String roleName = StringUtil.quote(
			RoleConstants.SITE_OWNER, StringPool.APOSTROPHE);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select roleId from Role_ where name = " + roleName);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getLong(1);
			}

			throw new Exception("Could not find site owner role ID");
		}
	}

	private void _updateIncidentReportEmailAddresses() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("select OSBFaro_FaroUser.emailAddress,");
		sb.append("OSBFaro_FaroProject.faroProjectId from ");
		sb.append("OSBFaro_FaroProject inner join OSBFaro_FaroUser on ");
		sb.append("OSBFaro_FaroProject.groupId = OSBFaro_FaroUser.groupId ");
		sb.append("where OSBFaro_FaroUser.roleId = ");
		sb.append(_getSiteOwnerRoleId());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				try (PreparedStatement updatePreparedStatement =
						connection.prepareStatement(
							"update OSBFaro_FaroProject set " +
								"incidentReportEmailAddresses = ? where " +
									"faroProjectId = ?")) {

					updatePreparedStatement.setString(
						1, "[\"" + resultSet.getString(1) + "\"]");
					updatePreparedStatement.setLong(2, resultSet.getLong(2));

					updatePreparedStatement.executeUpdate();
				}
			}
		}
	}

}