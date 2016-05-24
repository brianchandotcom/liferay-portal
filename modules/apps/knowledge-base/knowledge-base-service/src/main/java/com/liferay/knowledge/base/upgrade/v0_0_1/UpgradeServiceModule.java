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
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.v7_0_0.UpgradeModules;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(immediate = true, service = UpgradeServiceModule.class)
public class UpgradeServiceModule {

	@Activate
	protected void activate() throws UpgradeException {
		UpgradeModules upgradeModules = new UpgradeModules() {

			@Override
			public String[] getBundleSymbolicNames() {
				return new String[0];
			}

			@Override
			public String[][] getConvertedLegacyModules() {
				return new String[][] {
					{
						"knowledge-base-portlet",
						"com.liferay.knowledge.base.service", "KB"
					}
				};
			}

			@Override
			protected void updateConvertedLegacyModules()
				throws IOException, SQLException {

				try (PreparedStatement ps = connection.prepareStatement(
						"select servletContextName from Release_ where " +
							"servletContextName = ?")) {

					String newServletContextName =
						getConvertedLegacyModules()[0][1];

					ps.setString(1, newServletContextName);

					try (ResultSet rs = ps.executeQuery()) {
						if (!rs.next()) {
							super.updateConvertedLegacyModules();
						}
					}
				}
			}

		};

		upgradeModules.upgrade();
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeServiceModule.class);

}