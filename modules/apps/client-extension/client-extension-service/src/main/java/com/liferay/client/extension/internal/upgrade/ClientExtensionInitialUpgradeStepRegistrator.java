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
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.sql.Connection;

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

		// TODO Remove suppression in source-formatter-suppressions.xml

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			if (!dbInspector.hasTable("RemoteAppEntry")) {
				return;
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			return;
		}

		registry.registerInitialUpgradeSteps(
			new com.liferay.client.extension.internal.upgrade.v1_0_1.
				RemoteAppEntryUpgradeProcess(),
			new com.liferay.client.extension.internal.upgrade.v2_0_0.
				RemoteAppEntryUpgradeProcess(),
			new com.liferay.client.extension.internal.upgrade.v2_1_0.
				ResourcePermissionsUpgradeProcess(),
			new com.liferay.client.extension.internal.upgrade.v2_2_0.
				RemoteAppEntryUpgradeProcess(),
			new com.liferay.client.extension.internal.upgrade.v2_3_0.
				RemoteAppEntryUpgradeProcess(),
			new com.liferay.client.extension.internal.upgrade.v2_4_0.
				RemoteAppEntryUpgradeProcess(),
			new com.liferay.client.extension.internal.upgrade.v2_5_0.
				RemoteAppEntryUpgradeProcess(),
			new com.liferay.client.extension.internal.upgrade.v3_0_0.
				ClientExtensionEntryUpgradeProcess());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClientExtensionInitialUpgradeStepRegistrator.class);

}