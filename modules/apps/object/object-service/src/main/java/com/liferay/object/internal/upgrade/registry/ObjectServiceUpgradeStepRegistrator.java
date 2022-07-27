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

package com.liferay.object.internal.upgrade.registry;

import com.liferay.object.internal.upgrade.v1_1_0.ObjectDefinitionUpgradeProcess;
import com.liferay.object.internal.upgrade.v1_2_0.util.ObjectViewColumnTable;
import com.liferay.object.internal.upgrade.v1_2_0.util.ObjectViewTable;
import com.liferay.object.internal.upgrade.v2_0_0.ObjectFieldUpgradeProcess;
import com.liferay.object.internal.upgrade.v2_1_0.ObjectFieldBusinessTypeUpgradeProcess;
import com.liferay.object.internal.upgrade.v2_2_0.util.ObjectValidationRuleTable;
import com.liferay.object.internal.upgrade.v2_3_0.util.ObjectFieldSettingTable;
import com.liferay.object.internal.upgrade.v2_4_0.util.ObjectViewSortColumnTable;
import com.liferay.object.internal.upgrade.v2_5_0.util.ObjectViewColumnUpgradeProcess;
import com.liferay.object.internal.upgrade.v3_0_0.ObjectFieldSettingUpgradeProcess;
import com.liferay.object.internal.upgrade.v3_17_0.util.ObjectStateFlowTable;
import com.liferay.object.internal.upgrade.v3_17_0.util.ObjectStateTable;
import com.liferay.object.internal.upgrade.v3_17_0.util.ObjectStateTransitionTable;
import com.liferay.object.internal.upgrade.v3_19_0.util.ObjectFilterTable;
import com.liferay.object.internal.upgrade.v3_2_0.ObjectValidationRuleUpgradeProcess;
import com.liferay.object.internal.upgrade.v3_3_0.util.ObjectViewFilterColumnTable;
import com.liferay.object.internal.upgrade.v3_4_0.ObjectActionUpgradeProcess;
import com.liferay.object.internal.upgrade.v3_9_0.ObjectLayoutBoxUpgradeProcess;
import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.step.util.UpgradeStepFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ObjectServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0", new ObjectDefinitionUpgradeProcess());

		registry.register(
			"1.1.0", "1.2.0", ObjectViewTable.create(),
			ObjectViewColumnTable.create());

		registry.register("1.2.0", "2.0.0", new ObjectFieldUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0", new ObjectFieldBusinessTypeUpgradeProcess());

		registry.register("2.1.0", "2.2.0", ObjectValidationRuleTable.create());

		registry.register("2.2.0", "2.3.0", ObjectFieldSettingTable.create());

		registry.register("2.3.0", "2.4.0", ObjectViewSortColumnTable.create());

		registry.register(
			"2.4.0", "2.5.0", new ObjectViewColumnUpgradeProcess());

		registry.register(
			"2.5.0", "3.0.0", new ObjectFieldSettingUpgradeProcess());

		registry.register(
			"3.0.0", "3.1.0",
			new com.liferay.object.internal.upgrade.v3_1_0.
				ObjectFieldBusinessTypeUpgradeProcess());

		registry.register(
			"3.1.0", "3.2.0", new ObjectValidationRuleUpgradeProcess());

		registry.register(
			"3.2.0", "3.3.0", ObjectViewFilterColumnTable.create());

		registry.register("3.3.0", "3.4.0", new ObjectActionUpgradeProcess());

		registry.register(
			"3.4.0", "3.5.0",
			new com.liferay.object.internal.upgrade.v3_5_0.
				ObjectDefinitionUpgradeProcess());

		registry.register(
			"3.5.0", "3.6.0",
			new com.liferay.object.internal.upgrade.v3_6_0.
				ObjectFieldUpgradeProcess());

		registry.register(
			"3.6.0", "3.7.0",
			new com.liferay.object.internal.upgrade.v3_7_0.
				ObjectActionUpgradeProcess());

		registry.register("3.7.0", "3.8.0", new DummyUpgradeStep());

		registry.register(
			"3.8.0", "3.9.0", new ObjectLayoutBoxUpgradeProcess(),
			new com.liferay.object.internal.upgrade.v3_9_0.
				ObjectViewColumnUpgradeProcess());

		registry.register(
			"3.9.0", "3.10.0",
			new com.liferay.object.internal.upgrade.v3_10_0.
				ObjectDefinitionUpgradeProcess());

		registry.register(
			"3.10.0", "3.11.0",
			new com.liferay.object.internal.upgrade.v3_11_0.
				ObjectActionUpgradeProcess());

		registry.register(
			"3.11.0", "3.12.0",
			new com.liferay.object.internal.upgrade.v3_13_0.
				ObjectDefinitionUpgradeProcess());

		registry.register(
			"3.12.0", "3.13.0",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"ObjectEntry", "objectEntryId"},
						{"ObjectField", "objectFieldId"}
					};
				}

			});

		registry.register(
			"3.13.0", "3.14.0",
			new com.liferay.object.internal.upgrade.v3_14_0.
				ObjectFieldUpgradeProcess());

		registry.register(
			"3.14.0", "3.15.0",
			UpgradeStepFactory.addColumns(
				"ObjectRelationship", "parameterObjectFieldId LONG"));

		registry.register(
			"3.15.0", "3.16.0",
			UpgradeStepFactory.addColumns("ObjectField", "state_ BOOLEAN"));

		registry.register(
			"3.16.0", "3.17.0", ObjectStateFlowTable.create(),
			ObjectStateTable.create(), ObjectStateTransitionTable.create());

		registry.register(
			"3.17.0", "3.18.0",
			new com.liferay.object.internal.upgrade.v3_18_0.
				ObjectFieldUpgradeProcess(_portalUUID));

		registry.register("3.18.0", "3.19.0", ObjectFilterTable.create());

		registry.register(
			"3.19.0", "3.19.1",
			UpgradeStepFactory.alterColumnTypes(
				"ObjectFieldSetting", "VARCHAR(255) null", "value"));

		registry.register(
			"3.19.1", "3.19.2",
			new com.liferay.object.internal.upgrade.v3_19_2.
				ObjectFieldUpgradeProcess());
	}

	@Reference
	private PortalUUID _portalUUID;

}