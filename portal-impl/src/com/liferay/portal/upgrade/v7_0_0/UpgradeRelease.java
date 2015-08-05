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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.ReleaseConstants;
import com.liferay.portal.upgrade.UpgradeMVCC;
import com.liferay.portal.upgrade.v7_0_0.util.ReleaseTable;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Miguel Pastor
 */
public class UpgradeRelease extends UpgradeProcess {

	protected void addVersionColumn() throws Exception {
		Connection connection = DataAccess.getUpgradeOptimizedConnection();

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		UpgradeMVCC upgradeMVCC = new UpgradeMVCC();

		upgradeMVCC.upgradeMVCC(databaseMetaData, "Release_");
	}

	@Override
	protected void doUpgrade() throws Exception {
		addVersionColumn();

		transformBuildNumber();

		registerModuleReleases();
	}

	protected void registerModuleReleases() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"INSERT INTO Release_ VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			DatabaseMetaData databaseMetaData = con.getMetaData();

			boolean supportsBatchUpdates =
				databaseMetaData.supportsBatchUpdates();

			for (String app : _MODULES_WITH_UPGRADE) {
				ps.setLong(1, increment());
				ps.setTimestamp(2, timestamp);
				ps.setTimestamp(3, timestamp);
				ps.setString(4, app);
				ps.setString(5, "-1.0.0.0");
				ps.setTimestamp(6, timestamp);
				ps.setInt(7, 1);
				ps.setInt(8, 0);
				ps.setString(9, ReleaseConstants.TEST_STRING);
				ps.setInt(10, 1);

				if (supportsBatchUpdates) {
					ps.addBatch();
				}
				else {
					ps.executeUpdate();
				}
			}

			if (supportsBatchUpdates) {
				ps.executeBatch();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void transformBuildNumber() throws Exception {
		try {
			runSQL("alter_column_type Release_ buildNumber STRING");
		}
		catch (SQLException sqle) {
			upgradeTable(
				ReleaseTable.TABLE_NAME, ReleaseTable.TABLE_COLUMNS,
				ReleaseTable.TABLE_SQL_CREATE,
				ReleaseTable.TABLE_SQL_ADD_INDEXES);
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select distinct buildNumber from Release_");

			rs = ps.executeQuery();

			while (rs.next()) {
				updateToNewFormat(rs.getString("buildNumber"));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateToNewFormat(String oldFormattedVersion)
		throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Release_ set buildNumber = ? where buildNumber = ?");

			char[] chars = oldFormattedVersion.toCharArray();

			StringBundler sb = new StringBundler(2 * chars.length);

			int i = 0;

			for (; i < chars.length - 1; i++) {
				sb.append(chars[i]);
				sb.append(".");
			}

			sb.append(chars[i]);

			ps.setString(1, sb.toString());
			ps.setString(2, oldFormattedVersion);

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String[] _MODULES_WITH_UPGRADE = new String[] {
		"com.liferay.amazon.rankings.web", "com.liferay.announcements.web",
		"com.liferay.asset.browser.web",
		"com.liferay.asset.categories.admin.web",
		"com.liferay.asset.categories.navigation.web",
		"com.liferay.asset.publisher.web", "com.liferay.asset.tag.admin.web",
		"com.liferay.asset.tags.compiler.web",
		"com.liferay.asset.tags.navigation.web",
		"com.liferay.blogs.recent.bloggers.web", "com.liferay.blogs.web",
		"com.liferay.bookmarks.service", "com.liferay.bookmarks.web",
		"com.liferay.calendar.service", "com.liferay.calendar.web",
		"com.liferay.comment.page.comments.web",
		"com.liferay.currency.converter.web", "com.liferay.dictionary.web",
		"com.liferay.dynamic.data.lists.service",
		"com.liferay.dynamic.data.lists.web",
		"com.liferay.dynamic.data.mapping.service", "com.liferay.expando.web",
		"com.liferay.exportimport.web", "com.liferay.flags.page.flags.web",
		"com.liferay.hello.velocity.web", "com.liferay.iframe.web",
		"com.liferay.invitation.web", "com.liferay.item.selector.web",
		"com.liferay.journal.content.search.web",
		"com.liferay.journal.content.web", "com.liferay.journal.service",
		"com.liferay.journal.web", "com.liferay.layout.admin.web",
		"com.liferay.layout.prototype.web",
		"com.liferay.layout.set.prototype.web",
		"com.liferay.loan.calculator.web", "com.liferay.marketplace.service",
		"com.liferay.microblogs.service", "com.liferay.mobile.device.rules.web",
		"com.liferay.nested.portlets.web", "com.liferay.network.utilities.web",
		"com.liferay.password.generator.web",
		"com.liferay.password.policies.admin.web", "com.liferay.polls.service",
		"com.liferay.portal.instances.web", "com.liferay.portal.lock.service",
		"com.liferay.portal.workflow.kaleo.service",
		"com.liferay.portlet.configuration.web", "com.liferay.portlet.css.web",
		"com.liferay.quick.note.web", "com.liferay.ratings.page.ratings.web",
		"com.liferay.rss.web", "com.liferay.search.web",
		"com.liferay.site.admin.web", "com.liferay.site.browser.web",
		"com.liferay.site.memberships.web", "com.liferay.site.my.sites.web",
		"com.liferay.site.teams.web",
		"com.liferay.site.navigation.breadcrumb.web",
		"com.liferay.site.navigation.directory.web",
		"com.liferay.site.navigation.language.web",
		"com.liferay.site.navigation.menu.web",
		"com.liferay.site.navigation.site.map.web",
		"com.liferay.social.activities.web", "com.liferay.social.activity.web",
		"com.liferay.social.requests.web", "com.liferay.staging.bar.web",
		"com.liferay.translator.web", "com.liferay.trash.web",
		"com.liferay.unit.converter.web", "com.liferay.user.groups.admin.web",
		"com.liferay.web.proxy.web", "com.liferay.wiki.service",
		"com.liferay.wiki.web", "com.liferay.xsl.content.web"
	};

}