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

package com.liferay.portal.search.web.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.search.web.constants.SearchPortletKeys;

import java.sql.PreparedStatement;

/**
 * @author Bryan Engler
 */
public class UpgradeSearchPortlet extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradePortletPreferencesPortletId();
	}

	protected void upgradePortletPreferencesPortletId() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"update PortletPreferences set portletId = ? where " +
					"portletId= ? and plid = ?")) {

			ps.setString(
				1, SearchPortletKeys.SEARCH + "_INSTANCE_templateSearch");
			ps.setString(2, SearchPortletKeys.SEARCH);
			ps.setLong(3, 0);

			ps.executeUpdate();
		}
	}

}