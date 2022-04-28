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

package com.liferay.client.extension.internal.upgrade;

import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = true, immediate = true, service = UpgradeStepRegistrator.class
)
public class ClientExtensionInitialUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialUpgradeSteps(
			new UpgradeProcess() {

				@Override
				protected void doUpgrade() throws Exception {
					DBInspector dbInspector = new DBInspector(connection);

					if (!dbInspector.hasTable("RemoteAppEntry")) {
						return;
					}

					for (UpgradeProcess upgradeProcess :
							new UpgradeProcess[] {
								new com.liferay.client.extension.internal.
									upgrade.v1_0_1.RemoteAppEntryUpgradeProcess(),
								new com.liferay.client.extension.internal.
									upgrade.v2_0_0.RemoteAppEntryUpgradeProcess(),
								new com.liferay.client.extension.internal.
									upgrade.v2_1_0.ResourcePermissionsUpgradeProcess(),
								new com.liferay.client.extension.internal.
									upgrade.v2_2_0.RemoteAppEntryUpgradeProcess(),
								new com.liferay.client.extension.internal.
									upgrade.v2_3_0.RemoteAppEntryUpgradeProcess(),
								new com.liferay.client.extension.internal.
									upgrade.v2_4_0.RemoteAppEntryUpgradeProcess(),
								new com.liferay.client.extension.internal.
									upgrade.v2_5_0.RemoteAppEntryUpgradeProcess(),
								new com.liferay.client.extension.internal.
									upgrade.v3_0_0.ClientExtensionEntryUpgradeProcess()
							}) {

						upgradeProcess.upgrade();
					}
				}

			});
	}

}