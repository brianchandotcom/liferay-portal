/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author István András Dézsi
 */
public class PreupgradeVerifyCompanyUsers extends PreupgradeVerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		if (StartupHelperUtil.isDBNew()) {
			return;
		}

		CompanyLocalServiceUtil.forEachCompanyId(
			companyId -> {
				_verifyCompanyAdminUser(companyId);
				_verifyCompanyGuestUser(companyId);
			});

		if (ListUtil.isNotEmpty(_verifyMessages)) {
			for (String verifyMessage : _verifyMessages) {
				_log.error(verifyMessage);
			}

			throw new VerifyException(
				StringUtil.merge(_verifyMessages, StringPool.COMMA_AND_SPACE));
		}
	}

	private void _verifyCompanyAdminUser(long companyId) throws Exception {
		boolean hasColumn = hasColumn("User_", "type_");

		StringBundler sb = new StringBundler(5);

		sb.append("select count(*) from User_ inner join Users_Roles on ");
		sb.append("User_.userId = Users_Roles.userId inner join Role_ on ");
		sb.append("Users_Roles.roleId = Role_.roleId where Role_.name = ? ");
		sb.append("and User_.companyId = ? and Role_.companyId = ?");

		if (hasColumn) {
			sb.append(" and User_.type_ = ?");
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.setString(1, RoleConstants.ADMINISTRATOR);

			preparedStatement.setLong(2, companyId);
			preparedStatement.setLong(3, companyId);

			if (hasColumn) {
				preparedStatement.setInt(4, UserConstants.TYPE_REGULAR);
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count == 0) {
						_verifyMessages.add(
							"No admin user was found for company " + companyId);
					}
				}
			}
		}
	}

	private void _verifyCompanyGuestUser(long companyId) throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select count(*) from User_ where companyId = ? and ");

		if (hasColumn("User_", "defaultUser")) {
			sb.append("defaultUser = [$TRUE$]");
		}
		else {
			sb.append("type_ = ");
			sb.append(UserConstants.TYPE_GUEST);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(sb.toString()))) {

			preparedStatement.setLong(1, companyId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count == 0) {
						_verifyMessages.add(
							"No guest user was found for company " + companyId);
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PreupgradeVerifyCompanyUsers.class);

	private final List<String> _verifyMessages = new ArrayList<>();

}