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

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Miguel Pastor
 */
public class UpgradeModules extends UpgradeProcess {

	protected void addRelease(String... bundleSymbolicNames)
		throws SQLException {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(5);

			sb.append("insert into Release_ (mvccVersion, releaseId, ");
			sb.append("createDate, modifiedDate, servletContextName, ");
			sb.append("schemaVersion, buildNumber, buildDate, verified, ");
			sb.append("state_, testString) values (?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			for (String bundleSymbolicName : bundleSymbolicNames) {
				ps.setLong(1, 0);
				ps.setLong(2, increment());
				ps.setTimestamp(3, timestamp);
				ps.setTimestamp(4, timestamp);
				ps.setString(5, bundleSymbolicName);
				ps.setString(6, "0.0.1");
				ps.setInt(7, 001);
				ps.setTimestamp(8, timestamp);
				ps.setBoolean(9, false);
				ps.setInt(10, 0);
				ps.setString(11, ReleaseConstants.TEST_STRING);

				ps.addBatch();
			}

			ps.executeBatch();
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateExtractedModules();

		updateConvertedLegacyModules();
	}

	protected boolean hasServiceComponent(String buildNamespace)
		throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select serviceComponentId from ServiceComponent " +
					"where buildNamespace = ?");

			ps.setString(1, buildNamespace);

			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		return false;
	}

	protected void updateConvertedLegacyModules()
		throws IOException, SQLException {

		for (String[] convertedLegacyModule : _convertedLegacyModules) {
			String oldServletContextName = convertedLegacyModule[0];
			String newServletContextName = convertedLegacyModule[1];
			String buildNamespace = convertedLegacyModule[2];

			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = connection.prepareStatement(
					"select servletContextName, buildNumber from Release_ " +
						"where servletContextName = ?");

				ps.setString(1, oldServletContextName);

				rs = ps.executeQuery();

				if (!rs.next()) {
					if (hasServiceComponent(buildNamespace)) {
						addRelease(newServletContextName);
					}
				}
				else {
					updateServletContextName(
						oldServletContextName, newServletContextName);
				}
			}
			finally {
				DataAccess.cleanUp(ps, rs);
			}
		}
	}

	protected void updateExtractedModules() throws SQLException {
		addRelease(_bundleSymbolicNames);
	}

	protected void updateServletContextName(
			String oldServletContextName, String newServletContextName)
		throws IOException, SQLException {

		runSQL(
			"update Release_ set servletContextName = '" +
				newServletContextName + "' where servletContextName = '" +
					oldServletContextName + "'");
	}

	private static final String[] _bundleSymbolicNames = new String[] {
		"com.liferay.amazon.rankings.web", "com.liferay.asset.browser.web",
		"com.liferay.asset.categories.navigation.web",
		"com.liferay.asset.publisher.web",
		"com.liferay.asset.tags.compiler.web",
		"com.liferay.asset.tags.navigation.web",
		"com.liferay.blogs.recent.bloggers.web", "com.liferay.blogs.web",
		"com.liferay.bookmarks.service", "com.liferay.bookmarks.web",
		"com.liferay.calendar.web", "com.liferay.comment.page.comments.web",
		"com.liferay.currency.converter.web", "com.liferay.dictionary.web",
		"com.liferay.document.library.web",
		"com.liferay.dynamic.data.lists.service",
		"com.liferay.dynamic.data.lists.web",
		"com.liferay.dynamic.data.mapping.service",
		"com.liferay.exportimport.service", "com.liferay.exportimport.web",
		"com.liferay.flags.web", "com.liferay.hello.velocity.web",
		"com.liferay.iframe.web", "com.liferay.invitation.web",
		"com.liferay.item.selector.web",
		"com.liferay.journal.content.search.web",
		"com.liferay.journal.content.web", "com.liferay.journal.service",
		"com.liferay.journal.web", "com.liferay.layout.admin.web",
		"com.liferay.loan.calculator.web", "com.liferay.message.boards.web",
		"com.liferay.mobile.device.rules.web", "com.liferay.my.account.web",
		"com.liferay.nested.portlets.web", "com.liferay.network.utilities.web",
		"com.liferay.password.generator.web", "com.liferay.plugins.admin.web",
		"com.liferay.polls.service", "com.liferay.portal.instances.web",
		"com.liferay.portal.lock.service",
		"com.liferay.portal.scheduler.quartz",
		"com.liferay.portal.settings.web",
		"com.liferay.portlet.configuration.web", "com.liferay.portlet.css.web",
		"com.liferay.quick.note.web", "com.liferay.ratings.page.ratings.web",
		"com.liferay.rss.web", "com.liferay.search.web",
		"com.liferay.server.admin.web", "com.liferay.shopping.service",
		"com.liferay.shopping.web", "com.liferay.site.browser.web",
		"com.liferay.site.my.sites.web",
		"com.liferay.site.navigation.breadcrumb.web",
		"com.liferay.site.navigation.directory.web",
		"com.liferay.site.navigation.language.web",
		"com.liferay.site.navigation.menu.web",
		"com.liferay.site.navigation.site.map.web",
		"com.liferay.social.activities.web", "com.liferay.social.activity.web",
		"com.liferay.social.group.statistics.web",
		"com.liferay.social.requests.web",
		"com.liferay.social.user.statistics.web", "com.liferay.staging.bar.web",
		"com.liferay.translator.web", "com.liferay.trash.web",
		"com.liferay.unit.converter.web", "com.liferay.web.proxy.web",
		"com.liferay.wiki.service", "com.liferay.wiki.web",
		"com.liferay.xsl.content.web"
	};
	private static final String[][] _convertedLegacyModules = {
		{
			"calendar-portlet", "com.liferay.calendar.service", "Calendar"
		},
		{
			"social-networking-portlet",
			"com.liferay.social.networking.service", "SN"
		},
		{
			"marketplace-portlet", "com.liferay.marketplace.service",
			"Marketplace"
		},
		{
			"kaleo-web", "com.liferay.portal.workflow.kaleo.service", "Kaleo"
		},
		{
			"microblogs-portlet", "com.liferay.microblogs.service", "Microblogs"
		}
	};

}