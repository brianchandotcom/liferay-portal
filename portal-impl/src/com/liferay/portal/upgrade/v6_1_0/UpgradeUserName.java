/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Hugo Huijser
 */
public class UpgradeUserName extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateTable("BookmarksEntry", false);
		updateTable("BookmarksFolder", false);
		updateTable("IGFolder", false);
		updateTable("IGImage", false);
		updateTable("PollsVote", true);
	}

	protected void updateTable(String tableName, boolean setCompanyId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(11);

			sb.append("select distinct User_.companyId, User_.userId, ");
			sb.append("User_.firstName, User_.middleName, User_.lastName ");
			sb.append("from User_ inner join ");
			sb.append(tableName);
			sb.append(" on ");
			sb.append(tableName);
			sb.append(".userId = User_.userId where ");
			sb.append(tableName);
			sb.append(".userName is null or ");
			sb.append(tableName);
			sb.append(".userName = ''");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String firstName = rs.getString("firstName");
				String middleName = rs.getString("middleName");
				String lastName = rs.getString("lastName");

				FullNameGenerator fullNameGenerator =
					FullNameGeneratorFactory.getInstance();

				String fullName = fullNameGenerator.getFullName(
					firstName, middleName, lastName);

				fullName = fullName.replace(
					StringPool.APOSTROPHE, StringPool.DOUBLE_APOSTROPHE);

				if (setCompanyId) {
					sb = new StringBundler(8);

					sb.append("update ");
					sb.append(tableName);
					sb.append(" set companyId = ");
					sb.append(companyId);
					sb.append(", userName = '");
					sb.append(fullName);
					sb.append("' where userId = ");
					sb.append(userId);
				}
				else {
					sb = new StringBundler(6);

					sb.append("update ");
					sb.append(tableName);
					sb.append(" set userName = '");
					sb.append(fullName);
					sb.append("' where userId = ");
					sb.append(userId);
				}

				runSQL(sb.toString());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}