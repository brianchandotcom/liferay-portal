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
import com.liferay.portal.model.ReleaseConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Miguel Pastor
 */
public class UpgradeModules extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"insert into Release_ values  (?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"?, ?)");

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			for (String bundleSymbolicName : _bundleSymbolicNames) {
				ps.setLong(1, increment());
				ps.setTimestamp(2, timestamp);
				ps.setTimestamp(3, timestamp);
				ps.setString(4, bundleSymbolicName);
				ps.setInt(5, 001);
				ps.setTimestamp(6, timestamp);
				ps.setInt(7, 1);
				ps.setInt(8, 0);
				ps.setString(9, ReleaseConstants.TEST_STRING);
				ps.setString(10, "0.0.1");
				ps.setLong(11, 0);

				ps.addBatch();
			}

			ps.executeBatch();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String[] _bundleSymbolicNames = new String[] {
		"com.liferay.amazon.rankings.web", "com.liferay.announcements.web",
		"com.liferay.asset.browser.web",
		"com.liferay.asset.categories.admin.web",
		"com.liferay.asset.categories.navigation.web",
		"com.liferay.asset.publisher.web", "com.liferay.asset.tags.admin.web",
		"com.liferay.asset.tags.compiler.web",
		"com.liferay.asset.tags.navigation.web",
		"com.liferay.blogs.recent.bloggers.web", "com.liferay.blogs.web",
		"com.liferay.bookmarks", "com.liferay.bookmarks.web",
		"com.liferay.calendar", "com.liferay.calendar.web",
		"com.liferay.comment.page.comments.web",
		"com.liferay.currency.converter.web", "com.liferay.dictionary.web",
		"com.liferay.document.library.web", "com.liferay.dynamic.data.lists",
		"com.liferay.dynamic.data.lists.web",
		"com.liferay.dynamic.data.mapping", "com.liferay.expando.web",
		"com.liferay.exportimport.web", "com.liferay.flags.page.flags.web",
		"com.liferay.hello.velocity.web", "com.liferay.iframe.web",
		"com.liferay.invitation.web", "com.liferay.item.selector.web",
		"com.liferay.journal", "com.liferay.journal.content.search.web",
		"com.liferay.journal.content.web", "com.liferay.journal.web",
		"com.liferay.layout.admin.web", "com.liferay.layout.prototype.web",
		"com.liferay.layout.set.prototype.web",
		"com.liferay.loan.calculator.web", "com.liferay.marketplace",
		"com.liferay.message.boards.web", "com.liferay.microblogs",
		"com.liferay.microblogs.web", "com.liferay.mobile.device.rules.web",
		"com.liferay.my.account.web", "com.liferay.nested.portlets.web",
		"com.liferay.network.utilities.web",
		"com.liferay.password.generator.web",
		"com.liferay.password.policies.admin.web",
		"com.liferay.plugins.admin.web", "com.liferay.polls",
		"com.liferay.portal.instances.web", "com.liferay.portal.lock",
		"com.liferay.portal.settings.web", "com.liferay.portal.workflow.kaleo",
		"com.liferay.portlet.configuration.web", "com.liferay.portlet.css.web",
		"com.liferay.quick.note.web.uprade;",
		"com.liferay.ratings.page.ratings.web", "com.liferay.roles.admin.web",
		"com.liferay.rss.web", "com.liferay.search.web", "com.liferay.shopping",
		"com.liferay.shopping.web", "com.liferay.site.admin.web",
		"com.liferay.site.browser.web", "com.liferay.site.memberships.web",
		"com.liferay.site.my.sites.web",
		"com.liferay.site.navigation.breadcrumb.web",
		"com.liferay.site.navigation.directory.web",
		"com.liferay.site.navigation.language.web",
		"com.liferay.site.navigation.menu.web",
		"com.liferay.site.navigation.site.map.web",
		"com.liferay.site.teams.web", "com.liferay.social.activities.web",
		"com.liferay.social.activity.web",
		"com.liferay.social.group.statistics.web",
		"com.liferay.social.requests.web",
		"com.liferay.social.user.statistics.web", "com.liferay.staging.bar.web",
		"com.liferay.translator.web", "com.liferay.trash.web",
		"com.liferay.unit.converter.web", "com.liferay.user.groups.admin.web",
		"com.liferay.users.admin.web", "com.liferay.web.proxy.web",
		"com.liferay.wiki", "com.liferay.wiki.web",
		"com.liferay.xsl.content.web"
	};

}