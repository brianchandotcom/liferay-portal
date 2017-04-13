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

package com.liferay.marketplace.internal.upgrade.v2_0_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Adam Brandizzi
 */
public class UpgradeApp extends UpgradeProcess {

	protected void deleteApp(String contextName)
		throws IOException, SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"select appId from Marketplace_Module where contextName = ?")) {

			ps.setString(1, contextName);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				long appId = rs.getLong("appId");

				runSQL("delete from Marketplace_Module where appId = " + appId);
				runSQL("delete from Marketplace_App where appId = " + appId);
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		for (String contextName : _CONTEXT_NAMES) {
			deleteApp(contextName);
		}
	}

	private static final String[] _CONTEXT_NAMES = {
		"jasperreports-web", "reports-portlet", "drools-web"
	};

}