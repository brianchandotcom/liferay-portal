/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PropsUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author István András Dézsi
 */
public class PreupgradeVerifyDefaultUsers extends PreupgradeVerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		if (StartupHelperUtil.isDBNew()) {
			return;
		}

		verifyDefaultAdminUser();
		verifyDefaultGuestUser();
	}

	protected void verifyDefaultAdminUser() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("select count(*) from User_ inner join Users_Roles on ");
		sb.append("User_.userId = Users_Roles.userId inner join Role_ on ");
		sb.append("Users_Roles.roleId = Role_.roleId where User_.screenName ");
		sb.append("= ? and Role_.name = ?");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.setString(
				1, PropsUtil.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME));
			preparedStatement.setString(2, RoleConstants.ADMINISTRATOR);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count == 0) {
						throw new VerifyException(
							"Default admin user not found");
					}
				}
			}
		}
	}

	protected void verifyDefaultGuestUser() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select count(*) from User_ where ");

		if (hasColumn("User_", "defaultUser")) {
			sb.append("defaultUser = [$TRUE$]");
		}
		else {
			sb.append("type_ = ");
			sb.append(UserConstants.TYPE_GUEST);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(sb.toString()));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				int count = resultSet.getInt(1);

				if (count == 0) {
					throw new VerifyException("Default guest user not found");
				}
			}
		}
	}

}