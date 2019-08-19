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

package com.liferay.journal.internal.upgrade.v3_0_2;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jonathan McCann
 */
public class UpgradeUrlTitle extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateFriendlyURLEntryLocalization();
		updateUrlTitle();
	}

	protected void updateFriendlyURLEntryLocalization() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select friendlyURLEntryLocalizationId, urlTitle from " +
					"FriendlyURLEntryLocalization where classNameId = ? and " +
						"(urlTitle like ? or urlTitle like ?)")) {

			ps1.setLong(1, PortalUtil.getClassNameId(JournalArticle.class));
			ps1.setString(2, "%/%");
			ps1.setString(3, "%.%");

			ResultSet rs = ps1.executeQuery();

			while (rs.next()) {
				long friendlyURLEntryLocalizationId = rs.getLong(1);
				String urlTitle = rs.getString(2);

				try (PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update FriendlyURLEntryLocalization set " +
								"urlTitle = ? where " +
									"friendlyURLEntryLocalizationId = ?")) {

					ps2.setString(
						1,
						FriendlyURLNormalizerUtil.
							normalizeWithPeriodsAndSlashes(urlTitle));
					ps2.setLong(2, friendlyURLEntryLocalizationId);

					ps2.executeUpdate();
				}
			}
		}
	}

	protected void updateUrlTitle() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select id_, urlTitle from JournalArticle where urlTitle " +
					"like ? or urlTitle like ?")) {

			ps1.setString(1, "%/%");
			ps1.setString(2, "%.%");

			ResultSet rs = ps1.executeQuery();

			while (rs.next()) {
				long id = rs.getLong(1);
				String urlTitle = rs.getString(2);

				try (PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update JournalArticle set urlTitle = ? where " +
								"id_ = ?")) {

					ps2.setString(
						1,
						FriendlyURLNormalizerUtil.
							normalizeWithPeriodsAndSlashes(urlTitle));
					ps2.setLong(2, id);

					ps2.executeUpdate();
				}
			}
		}
	}

}