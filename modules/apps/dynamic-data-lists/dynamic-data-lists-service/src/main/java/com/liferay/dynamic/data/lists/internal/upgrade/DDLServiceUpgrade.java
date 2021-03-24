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

package com.liferay.dynamic.data.lists.internal.upgrade;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_0.SchemaUpgradeProcess;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_0.UpgradeKernelPackage;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_1.RecordGroupUpgradeProcess;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_1_1.VersionUserIdUpgradeProcess;
import com.liferay.dynamic.data.lists.internal.upgrade.v2_0_0.util.DDLRecordSetTable;
import com.liferay.dynamic.data.lists.internal.upgrade.v2_0_0.util.DDLRecordSetVersionTable;
import com.liferay.dynamic.data.lists.internal.upgrade.v2_0_0.util.DDLRecordTable;
import com.liferay.dynamic.data.lists.internal.upgrade.v2_0_0.util.DDLRecordVersionTable;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	service = {DDLServiceUpgrade.class, UpgradeStepRegistrator.class}
)
public class DDLServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "0.0.2", new SchemaUpgradeProcess());

		registry.register("0.0.2", "0.0.3", new UpgradeKernelPackage());

		registry.register("0.0.3", "1.0.0", new UpgradeLastPublishDate());

		registry.register("1.0.0", "1.0.1", new RecordGroupUpgradeProcess());

		registry.register(
			"1.0.1", "1.0.2",
			new com.liferay.dynamic.data.lists.internal.upgrade.v1_0_2.
				SchemaUpgradeProcess());

		registry.register(
			"1.0.2", "1.1.0",
			new com.liferay.dynamic.data.lists.internal.upgrade.v1_1_0.
				DDLRecordUpgradeProcess(),
			new com.liferay.dynamic.data.lists.internal.upgrade.v1_1_0.
				DDLRecordSetUpgradeProcess(),
			new com.liferay.dynamic.data.lists.internal.upgrade.v1_1_0.
				DDLRecordSetVersionUpgradeProcess(_counterLocalService));

		registry.register("1.1.0", "1.1.1", new VersionUserIdUpgradeProcess());

		registry.register(
			"1.1.1", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {
					DDLRecordSetTable.class, DDLRecordSetVersionTable.class,
					DDLRecordTable.class, DDLRecordVersionTable.class
				}));

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.dynamic.data.lists.internal.upgrade.v2_1_0.
				SchemaUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0",
			new com.liferay.dynamic.data.lists.internal.upgrade.v2_2_0.
				SchemaUpgradeProcess());

		registry.register(
			"2.2.0", "2.3.0",
			new CTModelUpgradeProcess(
				"DDLRecord", "DDLRecordSet", "DDLRecordSetVersion",
				"DDLRecordVersion"));
	}

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}