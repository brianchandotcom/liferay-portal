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

package com.liferay.data.engine.internal.upgrade;

import com.liferay.data.engine.internal.upgrade.v1_0_0.SchemaUpgradeProcess;
import com.liferay.data.engine.internal.upgrade.v2_0_0.UpgradeCompanyId;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeCTModel;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true,
	service = {DEServiceUpgrade.class, UpgradeStepRegistrator.class}
)
public class DEServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("1.0.0", "1.1.0", new SchemaUpgradeProcess());

		registry.register("1.1.0", "1.1.1", new DummyUpgradeStep());

		registry.register("1.1.1", "2.0.0", new UpgradeCompanyId());

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.data.engine.internal.upgrade.v2_1_0.
				DEDataDefinitionFieldLinkUpgradeProcess());

		registry.register(
			"2.1.0", "2.1.1",
			new com.liferay.data.engine.internal.upgrade.v2_1_1.
				DEDataDefinitionFieldLinkUpgradeProcess());

		registry.register(
			"2.1.1", "2.1.2",
			new com.liferay.data.engine.internal.upgrade.v2_1_2.
				DEDataDefinitionFieldLinkUpgradeProcess());

		registry.register(
			"2.1.2", "2.2.0",
			new UpgradeMVCCVersion() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"DEDataDefinitionFieldLink", "DEDataListView"
					};
				}

			},
			new UpgradeCTModel("DEDataDefinitionFieldLink", "DEDataListView"));
	}

}