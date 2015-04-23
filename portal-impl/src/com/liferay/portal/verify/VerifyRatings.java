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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Alberto Chaparro
 */
public class VerifyRatings extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		normalizeRatingStats();
	}

	protected void normalizeRatingStats() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("update RatingsStats set totalEntries = ");
			sb.append("coalesce((select count(1) ?), 0), totalScore = ");
			sb.append("coalesce((select sum(RatingsEntry.score) ?), 0), ");
			sb.append("averageScore = coalesce((select ");
			sb.append("sum(RatingsEntry.score) / count(1) ?), 0)");

			String update = StringUtil.replace(
				sb.toString(), "?", _FROM_WHERE_CLAUSE);

			ps = con.prepareStatement(update);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private static final String _FROM_WHERE_CLAUSE =
		"from RatingsEntry where RatingsStats.classPK = RatingsEntry.classPK " +
		"and RatingsStats.classNameId = RatingsEntry.classNameId group by " +
		"classNameId, classPK";

}