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

package com.liferay.object.internal.upgrade;

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
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ObjectServiceUpgrade implements UpgradeStepRegistrator {

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
	}

}