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

package com.liferay.wiki.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Akos Thurzo
 */
public class UpgradeWikiPageResource extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateWikiPageResources();
	}

	protected long getGroupId(long resourcePrimKey) throws Exception {
		long groupId = 0;

		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId from WikiPage where resourcePrimKey = ?")) {

			ps.setLong(1, resourcePrimKey);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					groupId = rs.getLong("groupId");
				}
			}
		}

		return groupId;
	}

	protected void updateWikiPageResources() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select resourcePrimKey from WikiPageResource");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long resourcePrimKey = rs.getLong("resourcePrimKey");

				runSQL(
					StringBundler.concat(
						"update WikiPageResource set groupId = ",
						getGroupId(resourcePrimKey),
						" where resourcePrimKey = ", resourcePrimKey));
			}
		}
	}

}