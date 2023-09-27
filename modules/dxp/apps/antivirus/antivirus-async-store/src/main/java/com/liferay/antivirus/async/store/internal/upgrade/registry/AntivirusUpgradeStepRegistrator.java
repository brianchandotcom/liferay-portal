/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.internal.upgrade.registry;

import com.liferay.antivirus.async.store.internal.upgrade.v1_0_0.SchedulerJobUpgradeProcess;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(service = UpgradeStepRegistrator.class)
public class AntivirusUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			new SchedulerJobUpgradeProcess(_schedulerEngineHelper));
	}

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

}