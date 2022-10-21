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

package com.liferay.message.boards.moderation.internal.upgrade.registry;

import com.liferay.message.boards.moderation.internal.upgrade.v1_0_0.MBModerationWorkflowDefinitionUpgradeProcess;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.runtime.WorkflowEngine;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class MBModerationUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			new MBModerationWorkflowDefinitionUpgradeProcess(
				_companyLocalService, _workflowDefinitionManager));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

	@Reference
	private WorkflowEngine _workflowEngine;

}