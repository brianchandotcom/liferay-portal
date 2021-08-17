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

package com.liferay.remote.app.admin.web.internal.upgrade;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class RemoteAppAdminWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		UpgradeStep upgradePortletId = new BasePortletIdUpgradeProcess() {

			@Override
			protected String[][] getRenamePortletIdsArray() {
				List<String[]> renamePortletIdsArray = new ArrayList<>();

				for (RemoteAppEntry remoteAppEntry :
						_remoteAppEntryLocalService.getRemoteAppEntries(
							QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

					renamePortletIdsArray.add(
						new String[] {
							"remote_app_" +
								remoteAppEntry.getRemoteAppEntryId(),
							"com_liferay_remote_app_admin_web_internal_" +
								"portlet_RemoteAppPortlet#" +
									remoteAppEntry.getRemoteAppEntryId()
						});
				}

				return renamePortletIdsArray.toArray(new String[0][0]);
			}

		};

		registry.register("0.0.0", "1.0.0", upgradePortletId);
	}

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

}