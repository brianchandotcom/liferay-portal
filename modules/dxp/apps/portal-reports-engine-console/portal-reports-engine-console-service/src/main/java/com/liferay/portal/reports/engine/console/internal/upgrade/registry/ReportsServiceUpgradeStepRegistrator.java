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

import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.reports.engine.console.internal.upgrade.v1_0_1.UpgradeKernelPackage;
import com.liferay.portal.reports.engine.console.internal.upgrade.v1_0_1.UpgradeLastPublishDate;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.release.ReleaseRenamingUpgradeStep;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wesley Gong
 * @author Calvin Keum
 */
@Component(service = UpgradeStepRegistrator.class)
public class ReportsServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerReleaseCreationUpgradeSteps(
			new ReleaseRenamingUpgradeStep(
				"com.liferay.portal.reports.engine.console.service",
				"reports-portlet", _releaseLocalService));

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

	@Reference
	private ReleaseLocalService _releaseLocalService;

}