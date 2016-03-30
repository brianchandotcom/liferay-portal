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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_0_0.util.GroupTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Eudaldo Alonso
 * @author Manuel de la Peña
 */
public class UpgradeGroup extends UpgradeProcess {

	protected void checkGlobalFriendlyURL(long companyId) throws Exception {
		String friendlyURL = GroupConstants.GLOBAL_FRIENDLY_URL;

		long globalGroupId = getGroupId(companyId, friendlyURL);

		if (globalGroupId == 0) {
			return;
		}

		long groupId = globalGroupId;
		int increment = 1;

		while (groupId > 0) {
			friendlyURL = friendlyURL + increment;

			groupId = getGroupId(companyId, friendlyURL);

			increment++;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Modifying friendlyURL " + GroupConstants.GLOBAL_FRIENDLY_URL +
					" of groupId " + globalGroupId + " to " + friendlyURL);
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"update Group_ set friendlyURL = ? where groupId = ?")) {

			ps.setString(1, friendlyURL);
			ps.setLong(2, globalGroupId);

			ps.execute();
		}
	}

	protected void checkGlobalFriendlyURLs() throws Exception {
		try (Statement s = connection.createStatement()) {
			String query = "select companyId from Company";

			try (ResultSet rs = s.executeQuery(query)) {
				while (rs.next()) {
					checkGlobalFriendlyURL(rs.getLong(1));
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		alter(GroupTable.class, new AlterColumnType("name", "STRING null"));

		checkGlobalFriendlyURLs();
	}

	protected long getGroupId(long companyId, String friendlyURL)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId from Group_ where companyId = ? and " +
					"friendlyURL = ?")) {

			ps.setLong(1, companyId);
			ps.setString(2, friendlyURL);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong(1);
				}
			}
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeGroup.class);

}