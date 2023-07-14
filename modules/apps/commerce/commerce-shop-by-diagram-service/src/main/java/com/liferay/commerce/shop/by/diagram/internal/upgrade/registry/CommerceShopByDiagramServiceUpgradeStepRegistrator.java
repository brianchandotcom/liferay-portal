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

package com.liferay.commerce.shop.by.diagram.internal.upgrade.registry;

import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cheryl Tang
 */
@Component(service = UpgradeStepRegistrator.class)
public class CommerceShopByDiagramServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected void doUpgrade() throws Exception {
					if (hasTable("CSDiagramEntry") &&
						hasTable("CSDiagramPin") &&
						hasTable("CSDiagramSetting")) {

						upgradeModuleTableMVCCVersions();
					}
				}

				@Override
				protected String[] getTableNames() {
					return new String[] {
						"CSDiagramEntry", "CSDiagramPin", "CSDiagramSetting"
					};
				}

			});

		registry.register(
			"1.1.0", "1.2.0",
			new CTModelUpgradeProcess(
				"CSDiagramEntry", "CSDiagramPin", "CSDiagramSetting") {

				@Override
				protected void doUpgrade() throws Exception {
					if (hasTable("CSDiagramEntry") &&
						hasTable("CSDiagramPin") &&
						hasTable("CSDiagramSetting")) {

						super.doUpgrade();
					}
				}

			});

		registry.register("1.2.0", "1.2.1", new DummyUpgradeStep());
	}

}