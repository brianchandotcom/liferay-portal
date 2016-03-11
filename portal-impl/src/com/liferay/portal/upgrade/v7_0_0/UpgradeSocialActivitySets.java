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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.counter.kernel.model.Counter;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Adolfo Pérez
 */
public class UpgradeSocialActivitySets extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		int count = getSocialActivitySetsCount();

		if (count > 0) {
			return;
		}

		long increment = increment();

		long delta = getDelta(increment);

		insertSocialActivitySets(delta);

		updateSocialActivityForeignKeys(delta);

		count = getSocialActivitySetsCount();

		CounterLocalServiceUtil.increment(Counter.class.getName(), count);
	}

	protected long getDelta(long increment) throws Exception {
		try (Statement s = connection.createStatement()) {
			String query = "SELECT MIN(activityId) FROM SocialActivity";

			try (ResultSet rs = s.executeQuery(query)) {
				if (rs.next()) {
					long minActivityId = rs.getLong(1);

					return increment - minActivityId;
				}

				return 0;
			}
		}
	}

	protected int getSocialActivitySetsCount() throws Exception {
		try (Statement s = connection.createStatement()) {
			String query = "SELECT COUNT(activitySetId) FROM SocialActivitySet";

			try (ResultSet rs = s.executeQuery(query)) {
				if (rs.next()) {
					return rs.getInt(1);
				}

				return 0;
			}
		}
	}

	protected void insertSocialActivitySets(long delta) throws Exception {
		try (Statement s = connection.createStatement()) {
			StringBundler sb = new StringBundler(7);

			sb.append("INSERT INTO SocialActivitySet ");
			sb.append("SELECT (activityId + ");
			sb.append(delta);
			sb.append(") AS activitySetId, groupId, companyId, userId, ");
			sb.append("createDate, createDate AS modifiedDate, classNameId, ");
			sb.append("classPK, type_, extraData, 1 as activityCount ");
			sb.append("FROM SocialActivity WHERE mirrorActivityId = 0");

			s.execute(sb.toString());
		}
	}

	protected void updateSocialActivityForeignKeys(long delta)
		throws Exception {

		String query =
			"UPDATE SocialActivity SET activitySetId = (activityId + ?)";

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, delta);

			ps.execute();
		}
	}

}