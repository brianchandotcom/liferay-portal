/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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