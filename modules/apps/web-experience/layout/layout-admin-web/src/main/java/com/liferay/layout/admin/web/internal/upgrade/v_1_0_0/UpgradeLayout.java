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

package com.liferay.layout.admin.web.internal.upgrade.v_1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jürgen Kappler
 */
public class UpgradeLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateLayouts();
	}

	protected void updateLayout(long plid, String typeSettings)
		throws Exception {

		if (Validator.isNull(typeSettings)) {
			return;
		}

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.load(typeSettings);

		typeSettingsProperties.setProperty(
			"embeddedLayoutURL", typeSettingsProperties.getProperty("url"));

		typeSettingsProperties.remove("url");

		updateTypeSettings(plid, typeSettingsProperties.toString());
	}

	protected void updateLayouts() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select plid, typeSettings from Layout where type_ = ?")) {

			ps.setString(1, "embedded");

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long plid = rs.getLong("plid");
					String typeSettings = rs.getString("typeSettings");

					updateLayout(plid, typeSettings);
				}
			}
		}
	}

	protected void updateTypeSettings(long plid, String typeSettings)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update Layout set typeSettings = ? where plid = ?")) {

			ps.setString(1, typeSettings);
			ps.setLong(2, plid);

			ps.executeUpdate();
		}
	}

}