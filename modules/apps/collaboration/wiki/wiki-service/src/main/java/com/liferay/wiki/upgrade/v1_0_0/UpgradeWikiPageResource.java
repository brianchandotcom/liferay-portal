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

package com.liferay.wiki.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Akos Thurzo
 */
public class UpgradeWikiPageResource extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select resourcePrimKey from WikiPageResource");

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePrimKey = rs.getLong("resourcePrimKey");

				long groupId = getGroupId(resourcePrimKey);

				runSQL(
					"update WikiPageResource set groupId = " +
						groupId + " where resourcePrimKey = " +
							resourcePrimKey);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected long getGroupId(long resourcePrimKey) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		long groupId = 0;

		try {
			ps = connection.prepareStatement(
				"select groupId from WikiPage where resourcePrimKey = ?");

			ps.setLong(1, resourcePrimKey);

			rs = ps.executeQuery();

			if (rs.next()) {
				groupId = rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		return groupId;
	}

}