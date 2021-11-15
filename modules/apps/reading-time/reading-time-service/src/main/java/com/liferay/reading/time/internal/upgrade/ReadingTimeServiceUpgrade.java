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

package com.liferay.reading.time.internal.upgrade;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.reading.time.internal.upgrade.v2_0_0.util.ReadingTimeEntryTable;

import org.osgi.service.component.annotations.Component;

/**
 * @author José Ángel Jiménez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ReadingTimeServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.SQLSERVER) {
			registry.register(
				"1.0.0", "2.0.0",
				new BaseSQLServerDatetimeUpgradeProcess(
					new Class<?>[] {ReadingTimeEntryTable.class}));
		}
		else {
			registry.register("1.0.0", "2.0.0", new DummyUpgradeStep());
		}

		registry.register(
			"2.0.0", "2.1.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {"ReadingTimeEntry"};
				}

			});

		registry.register(
			"2.1.0", "2.2.0", new CTModelUpgradeProcess("ReadingTimeEntry"));
	}

}