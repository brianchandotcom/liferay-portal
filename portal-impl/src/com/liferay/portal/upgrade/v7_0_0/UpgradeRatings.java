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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sergio González
 */
public class UpgradeRatings extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeRatingsEntry();
		upgradeRatingsStats();
	}

	protected void upgradeRatingsEntry() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select distinct classNameId from RatingsEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				upgradeRatingsEntry(rs.getLong("classNameId"));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void upgradeRatingsEntry(long classNameId) throws Exception {
		String className = PortalUtil.getClassName(classNameId);

		if (ArrayUtil.contains(
				PropsValues.RATINGS_UPGRADE_THUMBS_CLASS_NAMES, className)) {

			upgradeRatingsEntryThumbs(classNameId);
		}
		else {
			int defaultRatingsStarsNormalizationFactor = GetterUtil.getInteger(
				PropsUtil.get(
					PropsKeys.RATINGS_UPGRADE_STARS_NORMALIZATION_FACTOR,
					new Filter("default")),
				5);

			int ratingsStarsNormalizationFactor = GetterUtil.getInteger(
				PropsUtil.get(
					PropsKeys.RATINGS_UPGRADE_STARS_NORMALIZATION_FACTOR,
					new Filter(className)),
				defaultRatingsStarsNormalizationFactor);

			upgradeRatingsEntryStars(
				classNameId, ratingsStarsNormalizationFactor);
		}
	}

	protected void upgradeRatingsEntryStars(
			long classNameId, int normalizationFactor)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update RatingsEntry set score = score / ? " +
					"where classNameId = ?");

			ps.setInt(1, normalizationFactor);
			ps.setLong(2, classNameId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void upgradeRatingsEntryThumbs(long classNameId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update RatingsEntry set score = ? where score = ? and " +
					"classNameId = ?");

			ps.setDouble(1, 0);
			ps.setDouble(2, -1);
			ps.setLong(3, classNameId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void upgradeRatingsStats() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			DatabaseMetaData databaseMetaData = con.getMetaData();

			boolean supportsBatchUpdates =
				databaseMetaData.supportsBatchUpdates();

			ps = con.prepareStatement(
				"select classNameId, classPK, count(1) as totalEntries, " +
					"sum(re.score) as totalScore, sum(re.score) / count(1) " +
						"as averageScore from RatingsEntry re group by " +
							"classNameId, classPK");

			rs = ps.executeQuery();

			ps = con.prepareStatement(
				"update RatingsStats set totalEntries = ?, totalScore = ?, " +
					"averageScore = ? where classNameId = ? and classPK = ?");

			int count = 0;

			while (rs.next()) {
				ps.setInt(1, rs.getInt("totalEntries"));
				ps.setDouble(2, rs.getDouble("totalScore"));
				ps.setDouble(3, rs.getDouble("averageScore"));
				ps.setLong(4, rs.getLong("classNameId"));
				ps.setLong(5, rs.getLong("classPK"));

				if (supportsBatchUpdates) {
					ps.addBatch();

					if (count == PropsValues.HIBERNATE_JDBC_BATCH_SIZE) {
						ps.executeBatch();

						count = 0;
					}
					else {
						count++;
					}
				}
				else {
					ps.executeUpdate();
				}
			}

			if (supportsBatchUpdates && (count > 0)) {
				ps.executeBatch();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}