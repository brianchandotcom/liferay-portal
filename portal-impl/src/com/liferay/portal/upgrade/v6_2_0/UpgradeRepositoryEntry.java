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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Date;

/**
 * @author Mate Thurzo
 */
public class UpgradeRepositoryEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler();

			sb.append("select Repository.companyId, repositoryEntryId from");
			sb.append(" RepositoryEntry inner join Repository on");
			sb.append(" Repositoryentry.repositoryId =");
			sb.append(" Repository.repositoryId");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long repositoryEntryId = rs.getLong("repositoryEntryId");

				updateRepositoryEntry(companyId, repositoryEntryId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected Object[] getDefaultUserIdName(long companyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Object[] defaultUserIdName = new Object[2];

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select userId, firstName, middleName, lastName from User_" +
					" where companyId = ? and defaultUser = ?");

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			rs = ps.executeQuery();

			if (rs.next()) {
				long userId = rs.getLong("userId");
				String firstName = rs.getString("firstName");
				String middleName = rs.getString("middleName");
				String lastName = rs.getString("lastName");

				FullNameGenerator fullNameGenerator =
					FullNameGeneratorFactory.getInstance();

				defaultUserIdName[0] = userId;
				defaultUserIdName[1] = fullNameGenerator.getFullName(
					firstName, middleName, lastName);
			}

			return defaultUserIdName;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateRepositoryEntry(long companyId, long repositoryEntryId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update RepositoryEntry set companyId = ?, userId = ?," +
					" userName = ?, createDate = ?, modifiedDate = ? where" +
						" repositoryEntryId = ?");

			Object[] defaultUserIdName = getDefaultUserIdName(companyId);
			Timestamp now = new Timestamp((new Date()).getTime());

			ps.setLong(1, companyId);
			ps.setLong(2, (Long)defaultUserIdName[0]);
			ps.setString(3, (String)defaultUserIdName[1]);
			ps.setTimestamp(4, now);
			ps.setTimestamp(5, now);
			ps.setLong(6, repositoryEntryId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}