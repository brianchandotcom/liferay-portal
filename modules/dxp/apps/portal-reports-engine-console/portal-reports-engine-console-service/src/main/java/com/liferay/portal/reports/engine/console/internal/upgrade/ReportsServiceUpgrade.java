/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.internal.upgrade;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.reports.engine.console.internal.upgrade.v1_0_1.UpgradeKernelPackage;
import com.liferay.portal.reports.engine.console.internal.upgrade.v1_0_1.UpgradeLastPublishDate;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.release.BaseUpgradeServiceModuleRelease;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wesley Gong
 * @author Calvin Keum
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ReportsServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		try {
			BaseUpgradeServiceModuleRelease upgradeServiceModuleRelease =
				new BaseUpgradeServiceModuleRelease() {

					@Override
					protected String getNewBundleSymbolicName() {
						return
							"com.liferay.portal.reports.engine.console.service";
					}

					@Override
					protected String getOldBundleSymbolicName() {
						return "reports-portlet";
					}

				};

			upgradeServiceModuleRelease.upgrade();
		}
		catch (UpgradeException ue) {
			throw new RuntimeException(ue);
		}

		registry.register(
			"com.liferay.portal.reports.engine.console.service", "0.0.1",
			"1.0.0",
			new com.liferay.portal.reports.engine.console.internal.upgrade.
				v1_0_0.UpgradeReportDefinition(),
			new com.liferay.portal.reports.engine.console.internal.upgrade.
				v1_0_0.UpgradeReportEntry());

		registry.register(
			"com.liferay.portal.reports.engine.console.service", "1.0.0",
			"1.0.1", new UpgradeKernelPackage(), new UpgradeLastPublishDate(),
			new com.liferay.portal.reports.engine.console.internal.upgrade.
				v1_0_1.UpgradeReportDefinition(),
			new com.liferay.portal.reports.engine.console.internal.upgrade.
				v1_0_1.UpgradeReportEntry());
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

}