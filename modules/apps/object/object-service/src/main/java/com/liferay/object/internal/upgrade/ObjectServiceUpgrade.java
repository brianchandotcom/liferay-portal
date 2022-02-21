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
import com.liferay.object.internal.upgrade.v1_2_0.ObjectViewUpgradeProcess;
import com.liferay.object.internal.upgrade.v2_0_0.ObjectFieldUpgradeProcess;
import com.liferay.object.internal.upgrade.v2_1_0.ObjectFieldBusinessTypeUpgradeProcess;
import com.liferay.object.internal.upgrade.v2_2_0.ObjectValidationRuleUpgradeProcess;
import com.liferay.object.internal.upgrade.v2_3_0.ObjectFieldSettingUpgradeProcess;
import com.liferay.object.internal.upgrade.v2_4_0.ObjectViewSortColumnUpgradeProcess;
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

		registry.register("1.1.0", "1.2.0", new ObjectViewUpgradeProcess());

		registry.register("1.2.0", "2.0.0", new ObjectFieldUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0", new ObjectFieldBusinessTypeUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0", new ObjectValidationRuleUpgradeProcess());

		registry.register(
			"2.2.0", "2.3.0", new ObjectFieldSettingUpgradeProcess());

		registry.register(
			"2.3.0", "2.4.0", new ObjectViewSortColumnUpgradeProcess());
	}

}