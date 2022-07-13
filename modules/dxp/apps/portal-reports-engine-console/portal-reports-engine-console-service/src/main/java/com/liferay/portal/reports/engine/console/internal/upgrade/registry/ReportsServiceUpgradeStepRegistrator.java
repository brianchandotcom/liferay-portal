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

package com.liferay.portal.reports.engine.console.internal.upgrade.registry;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
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
public class ReportsServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		try {
			BaseUpgradeServiceModuleRelease baseUpgradeServiceModuleRelease =
				new BaseUpgradeServiceModuleRelease() {

					@Override
					protected String getNewBundleSymbolicName() {
						return "com.liferay.portal.reports.engine.console." +
							"service";
					}

					@Override
					protected String getOldBundleSymbolicName() {
						return "reports-portlet";
					}

				};

			baseUpgradeServiceModuleRelease.upgrade();
		}
		catch (UpgradeException upgradeException) {
			throw new RuntimeException(upgradeException);
		}

		registry.register(
			"0.0.1", "1.0.0",
			new com.liferay.portal.reports.engine.console.internal.upgrade.
				v1_0_0.ReportDefinitionUpgradeProcess(),
			new com.liferay.portal.reports.engine.console.internal.upgrade.
				v1_0_0.ReportEntryUpgradeProcess());

		registry.register(
			"1.0.0", "1.0.1",
			UpgradeProcessFactory.alterColumnType(
				"Reports_Definition", "reportParameters", "TEXT null"),
			UpgradeProcessFactory.alterColumnType(
				"Reports_Entry", "reportParameters", "TEXT null"),
			UpgradeProcessFactory.alterColumnType(
				"Reports_Entry", "errorMessage", "STRING null"),
			new UpgradeKernelPackage(), new UpgradeLastPublishDate());
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

}