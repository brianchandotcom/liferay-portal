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

package com.liferay.knowledge.base.upgrade.v0_0_1;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.upgrade.v7_0_0.UpgradeRelease;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(immediate = true, service = UpgradeServiceRelease.class)
public class UpgradeServiceRelease {

	@Activate
	protected void activate() throws UpgradeException {
		UpgradeRelease upgradeRelease = new UpgradeRelease() {

			@Override
			protected void upgradeSchemaVersion() throws Exception {
				try (LoggingTimer loggingTimer = new LoggingTimer();
					PreparedStatement ps = connection.prepareStatement(
						"select distinct buildNumber from Release_ " +
							"where schemaVersion is null and " +
								"servletContextName = ?")) {

					ps.setString(1, _SERVLET_CONTEXT_NAME);

					try (ResultSet rs = ps.executeQuery()) {
						while (rs.next()) {
							String buildNumber = rs.getString("buildNumber");

							String schemaVersion = toSchemaVersion(buildNumber);

							runSQL(
								"update Release_ set schemaVersion = '" +
								schemaVersion + "' where buildNumber = " +
								buildNumber + " and schemaVersion is null and" +
								" servletContextName = '" +
								_SERVLET_CONTEXT_NAME + "'");
						}
					}
				}
			}

		};

		upgradeRelease.upgrade();
	}

	@Reference(unbind = "-")
	protected void setUpgradeServiceModule(
		UpgradeServiceModule upgradeServiceModule) {
	}

	private static final String _SERVLET_CONTEXT_NAME =
		"com.liferay.knowledge.base.service";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeServiceRelease.class);

}