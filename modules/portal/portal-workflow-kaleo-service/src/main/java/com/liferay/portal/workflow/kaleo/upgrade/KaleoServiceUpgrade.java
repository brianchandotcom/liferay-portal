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

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.upgrade.v1_0_0.UpgradeKaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.upgrade.v1_1_0.UpgradeWorkflowContext;
import com.liferay.portal.workflow.kaleo.upgrade.v1_2_0.UpgradeKaleoLog;
import com.liferay.portal.workflow.kaleo.upgrade.v1_2_0.UpgradeKaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.upgrade.v1_3_0.UpgradeKaleoInstance;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class KaleoServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.portal.workflow.kaleo.service", "0.0.1", "1.0.0",
			new UpgradeKaleoTaskInstanceToken());

		registry.register(
			"com.liferay.portal.workflow.kaleo.service", "1.0.0", "1.1.0",
			new UpgradeWorkflowContext());

		registry.register(
			"com.liferay.portal.workflow.kaleo.service", "1.1.0", "1.2.0",
			new UpgradeKaleoLog(), new UpgradeKaleoNotificationRecipient());

		registry.register(
			"com.liferay.portal.workflow.kaleo.service", "1.2.0", "1.3.0",
			new UpgradeKaleoInstance());
	}

}