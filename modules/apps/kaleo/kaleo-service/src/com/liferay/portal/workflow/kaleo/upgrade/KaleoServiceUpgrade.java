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

package com.liferay.portal.workflow.kaleo.upgrade;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.workflow.kaleo.upgrade.v1_0_0.UpgradeKaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.upgrade.v1_1_0.UpgradeWorkflowContext;
import com.liferay.portal.workflow.kaleo.upgrade.v1_2_0.UpgradeKaleoLog;
import com.liferay.portal.workflow.kaleo.upgrade.v1_2_0.UpgradeKaleoNotificationRecipient;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.springframework.context.ApplicationContext;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = KaleoServiceUpgrade.class)
public class KaleoServiceUpgrade {

	@Reference(
		target = "(org.springframework.context.service.name=com.liferay.portal.workflow.kaleo.service)",
		unbind = "-"
	)
	protected void setApplicationContext(
		ApplicationContext applicationContext) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	protected void upgrade() throws PortalException {
		List<UpgradeProcess> upgradeProcesses = new ArrayList<>();

		upgradeProcesses.add(new UpgradeKaleoTaskInstanceToken());
		upgradeProcesses.add(new UpgradeWorkflowContext());
		upgradeProcesses.add(new UpgradeKaleoLog());
		upgradeProcesses.add(new UpgradeKaleoNotificationRecipient());

		_releaseLocalService.updateRelease(
			"com.liferay.portal.workflow.kaleo.service", upgradeProcesses, 1, 1,
			false);
	}

	private ReleaseLocalService _releaseLocalService;

}