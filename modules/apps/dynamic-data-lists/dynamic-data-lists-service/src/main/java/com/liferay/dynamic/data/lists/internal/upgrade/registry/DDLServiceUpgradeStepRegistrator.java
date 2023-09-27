/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.upgrade.registry;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_0.SchemaUpgradeProcess;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_0.UpgradeKernelPackage;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_1.RecordGroupUpgradeProcess;
import com.liferay.dynamic.data.lists.internal.upgrade.v2_0_0.util.DDLRecordSetTable;
import com.liferay.dynamic.data.lists.internal.upgrade.v2_0_0.util.DDLRecordSetVersionTable;
import com.liferay.dynamic.data.lists.internal.upgrade.v2_0_0.util.DDLRecordTable;
import com.liferay.dynamic.data.lists.internal.upgrade.v2_0_0.util.DDLRecordVersionTable;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(service = UpgradeStepRegistrator.class)
public class DDLServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

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

		registry.register(
			"1.1.0", "1.1.1",
			UpgradeProcessFactory.alterColumnType(
				"DDLRecordSet", "versionUserId", "LONG"));

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
			UpgradeProcessFactory.addColumns(
				"DDLRecord", "className VARCHAR(300) null", "classPK LONG"));

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