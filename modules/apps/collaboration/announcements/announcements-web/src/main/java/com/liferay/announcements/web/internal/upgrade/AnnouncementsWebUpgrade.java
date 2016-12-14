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

package com.liferay.announcements.web.internal.upgrade;

import com.liferay.announcements.web.internal.upgrade.v1_0_2.UpgradePermission;
import com.liferay.portal.kernel.upgrade.BaseReplacePortletId;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.release.BaseUpgradeWebModuleRelease;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class AnnouncementsWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		BaseUpgradeWebModuleRelease upgradeWebModuleRelease =
			new BaseUpgradeWebModuleRelease() {

				@Override
				protected String getBundleSymbolicName() {
					return "com.liferay.announcements.web";
				}

				@Override
				protected String[] getPortletIds() {
					return new String[] {
						"1_WAR_soannouncementsportlet", "84",
						PortletKeys.ANNOUNCEMENTS
					};
				}

			};

		try {
			upgradeWebModuleRelease.upgrade();
		}
		catch (UpgradeException ue) {
			throw new RuntimeException(ue);
		}

		registry.register(
			"com.liferay.announcements.web", "0.0.0", "1.0.2",
			new DummyUpgradeStep());

		UpgradeStep upgradePortletId = new BaseReplacePortletId() {

			@Override
			protected void doUpgrade() throws Exception {
				StringBundler sb = new StringBundler(5);

				sb.append("select P1.portletPreferencesId from ");
				sb.append("PortletPreferences P1 inner join ");
				sb.append("PortletPreferences P2 on P1.plid = P2.plid where ");
				sb.append("P1.portletId = '1_WAR_soannouncementsportlet' and ");
				sb.append("P2.portletId = '84'");

				try (PreparedStatement ps1 = connection.prepareStatement(
						sb.toString());
					PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"delete from PortletPreferences where " +
								"portletPreferencesId = ?")) {

					ResultSet rs = ps1.executeQuery();

					int deleteCount = 0;

					while (rs.next()) {
						ps2.setLong(1, rs.getLong(1));

						ps2.addBatch();

						deleteCount++;
					}

					if (deleteCount > 0) {
						ps2.executeBatch();
					}
				}

				super.doUpgrade();
			}

			@Override
			protected String[][] getRenamePortletIdsArray() {
				return new String[][] {
					new String[] {
						"1_WAR_soannouncementsportlet",
						PortletKeys.ANNOUNCEMENTS
					},
					new String[] {"84", PortletKeys.ANNOUNCEMENTS}
				};
			}

		};

		registry.register(
			"com.liferay.announcements.web", "0.0.1", "1.0.1",
			upgradePortletId);

		// See LPS-65946

		registry.register(
			"com.liferay.announcements.web", "1.0.0", "1.0.1",
			upgradePortletId);

		registry.register(
			"com.liferay.announcements.web", "1.0.1", "1.0.2",
			new UpgradePermission());
	}

}