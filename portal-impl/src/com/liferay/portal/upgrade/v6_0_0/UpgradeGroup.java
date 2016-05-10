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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Wesley Gong
 * @deprecated As of 7.0.1, with no direct replacement
 */
@Deprecated
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateGlobalFriendlyURLs();
		updateParentGroupId();
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

	protected Object[] getLayout(long plid) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(_GET_LAYOUT)) {
			ps.setLong(1, plid);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					long groupId = rs.getLong("groupId");

					return new Object[] {groupId};
				}

				return null;
			}
		}
	}

	protected String getNewFriendlyURL(long companyId, long groupId)
		throws Exception {

		String friendlyURL = null;

		int i = 1;

		while (groupId > 0) {
			friendlyURL = GroupConstants.GLOBAL_FRIENDLY_URL + i;

			groupId = getGroupId(companyId, friendlyURL);

			i++;
		}

		return friendlyURL;
	}

	protected void updateGlobalFriendlyURL(long companyId) throws Exception {
		long groupId = getGroupId(
			companyId, GroupConstants.GLOBAL_FRIENDLY_URL);

		if (groupId == 0) {
			return;
		}

		String friendlyURL = getNewFriendlyURL(companyId, groupId);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Updating friendly URL " + GroupConstants.GLOBAL_FRIENDLY_URL +
					" of global group " + groupId + " to " + friendlyURL);
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"update Group_ set friendlyURL = ? where groupId = ?")) {

			ps.setString(1, friendlyURL);
			ps.setLong(2, groupId);

			ps.execute();
		}
	}

	protected void updateGlobalFriendlyURLs() throws Exception {
		try (Statement s = connection.createStatement()) {
			String query = "select companyId from Company";

			try (ResultSet rs = s.executeQuery(query)) {
				while (rs.next()) {
					updateGlobalFriendlyURL(rs.getLong(1));
				}
			}
		}
	}

	protected void updateParentGroupId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				"com.liferay.portal.model.Layout");

			try (PreparedStatement ps = connection.prepareStatement(
					"select groupId, classPK from Group_ where classNameId = " +
						classNameId);
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long classPK = rs.getLong("classPK");

					Object[] layout = getLayout(classPK);

					if (layout != null) {
						long layoutGroupId = (Long)layout[0];

						runSQL(
							"update Group_ set parentGroupId = " +
								layoutGroupId + " where groupId = " + groupId);
					}
				}
			}
		}
	}

	private static final String _GET_LAYOUT =
		"select * from Layout where plid = ?";

	private static final Log _log = LogFactoryUtil.getLog(UpgradeGroup.class);

}