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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Juan Fernández
 */
public class UpgradeSubscription extends UpgradeProcess {

	protected void addSubscription(
			long subscriptionId, long companyId, long userId, String userName,
			Timestamp createDate, Timestamp modifiedDate, long classNameId,
			long classPK, String frequency)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("insert into Subscription (subscriptionId, companyId, ");
		sb.append("userId, userName, createDate, modifiedDate, classNameId, ");
		sb.append("classPK, frequency) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setLong(1, subscriptionId);
			ps.setLong(2, companyId);
			ps.setLong(3, userId);
			ps.setString(4, userName);
			ps.setTimestamp(5, createDate);
			ps.setTimestamp(6, modifiedDate);
			ps.setLong(7, classNameId);
			ps.setLong(8, classPK);
			ps.setString(9, frequency);

			ps.executeUpdate();
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

	protected boolean hasSubscription(
			long companyId, long userId, long classNameId, long classPK)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) from Subscription where companyId = ? and " +
					"userId = ? and classNameId = ? and classPK = ?")) {

			ps.setLong(1, companyId);
			ps.setLong(2, userId);
			ps.setLong(3, classNameId);
			ps.setLong(4, classPK);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	protected void updateMBMessages(long companyId) throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer(
				String.valueOf(companyId))) {

			StringBundler sb = new StringBundler(7);

			sb.append("select userId, MIN(userName) as userName, ");
			sb.append("classNameId, classPK, MIN(createDate) as createDate, ");
			sb.append("MIN(modifiedDate) as modifiedDate from MBMessage ");
			sb.append("where (companyId = ");
			sb.append(companyId);
			sb.append(") and (classNameId != 0) and (parentMessageId != 0) ");
			sb.append("group by userId, classNameId, classPK");

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString());
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long userId = rs.getLong("userId");
					String userName = rs.getString("userName");
					Timestamp createDate = rs.getTimestamp("createDate");
					Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
					long classNameId = rs.getLong("classNameId");
					long classPK = rs.getLong("classPK");

					if (hasSubscription(
							companyId, userId, classNameId, classPK)) {

						continue;
					}

					long subscriptionId = increment();
					String frequency = "instant";

					addSubscription(
						subscriptionId, companyId, userId, userName, createDate,
						modifiedDate, classNameId, classPK, frequency);
				}
			}
		}
	}

}