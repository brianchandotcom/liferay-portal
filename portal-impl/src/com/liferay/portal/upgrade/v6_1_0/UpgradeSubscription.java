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
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Juan Fernández
 */
public class UpgradeSubscription extends UpgradeProcess {

	protected void addSubscription(
			long subscriptionId, long companyId, long userId, String userName,
			Date createDate, Date modifiedDate, long classNameId, long classPK,
			String frequency)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("insert into Subscription (subscriptionId, companyId, ");
			sb.append("userId, userName, createDate, modifiedDate, ");
			sb.append("classNameId, classPK, frequency) values (?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, subscriptionId);
			ps.setLong(2, companyId);
			ps.setLong(3, userId);
			ps.setString(4, userName);
			ps.setDate(5, createDate);
			ps.setDate(6, modifiedDate);
			ps.setLong(7, classNameId);
			ps.setLong(8, classPK);
			ps.setString(9, frequency);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!PropsValues.DISCUSSION_SUBSCRIBE_BY_DEFAULT) {
			return;
		}

		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			updateMBMessages(companyId);
		}
	}

	protected void updateMBMessages(long companyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("select userId, MIN(userName) as userName, ");
			sb.append("classNameId, classPK, MIN(createDate) as createDate, ");
			sb.append("MIN(modifiedDate) as modifiedDate from MBMessage ");
			sb.append("where (companyId = " + companyId + ") and ");
			sb.append("(classNameId != 0) and (parentMessageId != 0) ");
			sb.append("group by userId, classNameId, classPK");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Date createDate = rs.getDate("createDate");
				Date modifiedDate = rs.getDate("modifiedDate");
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");

				long subscriptionId = increment();
				String frequency = "instant";

				addSubscription(
					subscriptionId, companyId, userId, userName, createDate,
					modifiedDate, classNameId, classPK, frequency);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}