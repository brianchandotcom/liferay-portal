/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;

import java.util.List;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 * @author Raymond Augé
 */
public class WorkflowDefinitionManagerUtil {

	public static WorkflowDefinition deployWorkflowDefinition(
			long companyId, long userId, String title, InputStream inputStream)
		throws WorkflowException {

		return getWorkflowDefinitionManager().deployWorkflowDefinition(
			companyId, userId, title, inputStream);
	}

	public static int getActiveWorkflowDefinitionCount(long companyId)
		throws WorkflowException {

		return getWorkflowDefinitionManager().getActiveWorkflowDefinitionCount(
			companyId);
	}

	public static int getActiveWorkflowDefinitionCount(
			long companyId, String name)
		throws WorkflowException {

		return getWorkflowDefinitionManager().getActiveWorkflowDefinitionCount(
			companyId, name);
	}

	public static List<WorkflowDefinition> getActiveWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return getWorkflowDefinitionManager().getActiveWorkflowDefinitions(
			companyId, start, end, orderByComparator);
	}

	public static List<WorkflowDefinition> getActiveWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return getWorkflowDefinitionManager().getActiveWorkflowDefinitions(
			companyId, name, start, end, orderByComparator);
	}

	public static WorkflowDefinition getLatestKaleoDefinition(
			long companyId, String name)
		throws WorkflowException {

		return getWorkflowDefinitionManager().getLatestKaleoDefinition(
			companyId, name);
	}

	public static WorkflowDefinition getWorkflowDefinition(
			long companyId, String name, int version)
		throws WorkflowException {

		return getWorkflowDefinitionManager().getWorkflowDefinition(
			companyId, name, version);
	}

	public static int getWorkflowDefinitionCount(long companyId)
		throws WorkflowException {

		return getWorkflowDefinitionManager().getWorkflowDefinitionCount(
			companyId);
	}

	public static int getWorkflowDefinitionCount(long companyId, String name)
		throws WorkflowException {

		return getWorkflowDefinitionManager().getWorkflowDefinitionCount(
			companyId, name);
	}

	public static WorkflowDefinitionManager getWorkflowDefinitionManager() {
		PortalRuntimePermission.checkGetBeanProperty(
			WorkflowDefinitionManagerUtil.class);

		return _workflowDefinitionManager;
	}

	public static List<WorkflowDefinition> getWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return getWorkflowDefinitionManager().getWorkflowDefinitions(
			companyId, start, end, orderByComparator);
	}

	public static List<WorkflowDefinition> getWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return getWorkflowDefinitionManager().getWorkflowDefinitions(
			companyId, name, start, end, orderByComparator);
	}

	public static void undeployWorkflowDefinition(
			long companyId, long userId, String name, int version)
		throws WorkflowException {

		getWorkflowDefinitionManager().undeployWorkflowDefinition(
			companyId, userId, name, version);
	}

	public static WorkflowDefinition updateActive(
			long companyId, long userId, String name, int version,
			boolean active)
		throws WorkflowException {

		return getWorkflowDefinitionManager().updateActive(
			companyId, userId, name, version, active);
	}

	public static WorkflowDefinition updateTitle(
			long companyId, long userId, String name, int version, String title)
		throws WorkflowException {

		return getWorkflowDefinitionManager().updateTitle(
			companyId, userId, name, version, title);
	}

	public static void validateWorkflowDefinition(InputStream inputStream)
		throws WorkflowException {

		getWorkflowDefinitionManager().validateWorkflowDefinition(inputStream);
	}

	public void setWorkflowDefinitionManager(
		WorkflowDefinitionManager workflowDefinitionManager) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_workflowDefinitionManager = workflowDefinitionManager;
	}

	private static WorkflowDefinitionManager _workflowDefinitionManager;

}