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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

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
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select distinct classNameId from RatingsEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				upgradeRatingsEntry(rs.getLong("classNameId"));
			}
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
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

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update RatingsEntry set score = score / ? where classNameId " +
					"= ?");

			ps.setInt(1, normalizationFactor);
			ps.setLong(2, classNameId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void upgradeRatingsEntryThumbs(long classNameId)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update RatingsEntry set score = ? where score = ? and " +
					"classNameId = ?");

			ps.setDouble(1, 0);
			ps.setDouble(2, -1);
			ps.setLong(3, classNameId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void upgradeRatingsStats() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("select classNameId, classPK, count(1) as totalEntries, ");
		sb.append("sum(RatingsEntry.score) as totalScore, ");
		sb.append("sum(RatingsEntry.score) / count(1) as averageScore from ");
		sb.append("RatingsEntry group by classNameId, classPK");

		String selectSQL = sb.toString();

		String updateSQL =
			"update RatingsStats set totalEntries = ?, totalScore = ?, " +
				"averageScore = ? where classNameId = ? and classPK = ?";

		try (PreparedStatement ps1 = connection.prepareStatement(selectSQL);
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(updateSQL))) {

			while (rs.next()) {
				ps2.setInt(1, rs.getInt("totalEntries"));
				ps2.setDouble(2, rs.getDouble("totalScore"));
				ps2.setDouble(3, rs.getDouble("averageScore"));
				ps2.setLong(4, rs.getLong("classNameId"));
				ps2.setLong(5, rs.getLong("classPK"));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}