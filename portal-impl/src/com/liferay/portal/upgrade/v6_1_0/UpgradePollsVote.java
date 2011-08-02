/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Hugo Huijser
 */
public class UpgradePollsVote extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("select distinct User_.companyId, User_.userId, ");
			sb.append("User_.firstName, User_.middleName, User_.lastName ");
			sb.append("from User_ inner join PollsVote on PollsVote.userId = ");
			sb.append("User_.userId where PollsVote.userName is null or ");
			sb.append("PollsVote.userName = ''");

			ps = con.prepareStatement(sb.toString());

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

				sb = new StringBundler(6);

				sb.append("update PollsVote set companyId = ");
				sb.append(companyId);
				sb.append(", userName = '");
				sb.append(fullName);
				sb.append("' where userId = ");
				sb.append(userId);

				runSQL(sb.toString());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}