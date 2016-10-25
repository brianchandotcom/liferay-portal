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

package com.liferay.portal.workflow.kaleo.internal.upgrade;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_0_0.UpgradeKaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_1_0.UpgradeWorkflowContext;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_1.UpgradeKaleoLog;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_1.UpgradeKaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.UpgradeClassNames;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.UpgradeKaleoAction;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.UpgradeKaleoDefinition;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_2.UpgradeKaleoClassNameAndKaleoClassPK;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_3.UpgradeBlogsEntryClassName;

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
			new UpgradeKaleoTaskInstanceToken(),
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v1_0_0.
				UpgradeSchema());

		registry.register(
			"com.liferay.portal.workflow.kaleo.service", "1.0.0", "1.1.0",
			new UpgradeWorkflowContext());

		registry.register(
			"com.liferay.portal.workflow.kaleo.service", "1.1.0", "1.2.0",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_0.
				UpgradeSchema());

		registry.register(
			"com.liferay.portal.workflow.kaleo.service", "1.2.0", "1.2.1",
			new UpgradeKaleoLog(), new UpgradeKaleoNotificationRecipient());

		registry.register(
			"com.liferay.portal.workflow.kaleo.service", "1.2.1", "1.3.0",
			new UpgradeClassNames(), new UpgradeKaleoAction(),
			new UpgradeKaleoDefinition());

		registry.register(
			"com.liferay.portal.workflow.kaleo.service", "1.3.0", "1.3.1",
			new com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_1.
				UpgradeSchema());

		registry.register(
			"com.liferay.portal.workflow.kaleo.service", "1.3.1", "1.3.2",
			new UpgradeKaleoClassNameAndKaleoClassPK());

		registry.register(
			"com.liferay.portal.workflow.kaleo.service", "1.3.2", "1.3.3",
			new UpgradeBlogsEntryClassName());
	}

}