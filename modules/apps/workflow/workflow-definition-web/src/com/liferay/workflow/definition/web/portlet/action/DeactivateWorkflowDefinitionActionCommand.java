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

package com.liferay.workflow.definition.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.util.PortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"mvc.command.name=deactivateWorkflowDefinition",
		"javax.portlet.name=" + PortletKeys.WORKFLOW_DEFINITION
	},
	service = ActionCommand.class
)
public class DeactivateWorkflowDefinitionActionCommand
	extends RestoreWorkflowDefinitionActionCommand {

	@Override
	protected boolean isActive() {
		return false;
	}

}