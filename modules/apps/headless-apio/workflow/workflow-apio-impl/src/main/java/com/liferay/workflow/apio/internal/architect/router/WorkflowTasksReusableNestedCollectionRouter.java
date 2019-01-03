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

package com.liferay.workflow.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ReusableNestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.workflow.apio.architect.identifier.ReusableWorkflowTaskIdentifier;
import com.liferay.workflow.apio.architect.identifier.WorkflowTaskIdentifier;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose user workflow task resources
 * through a web API. The resources are mapped from the internal model {@code
 * WorkflowTask}.
 *
 * @author Eduardo Pérez
 * @author Sarai Díaz
 * @author Víctor Galán
 */
@Component(immediate = true, service = ReusableNestedCollectionRouter.class)
public class WorkflowTasksReusableNestedCollectionRouter
	implements ReusableNestedCollectionRouter
		<WorkflowTask, Long, WorkflowTaskIdentifier,
		 ReusableWorkflowTaskIdentifier> {

	@Override
	public NestedCollectionRoutes
		<WorkflowTask, Long, ReusableWorkflowTaskIdentifier> collectionRoutes(
			NestedCollectionRoutes.Builder
				<WorkflowTask, Long, ReusableWorkflowTaskIdentifier> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class, CurrentUser.class
		).build();
	}

	@Reference
	protected WorkflowTaskManager workflowTaskManager;

	private PageItems<WorkflowTask> _getPageItems(
			Pagination pagination,
			ReusableWorkflowTaskIdentifier reusableWorkflowTaskIdentifier,
			Company company, CurrentUser currentUser)
		throws WorkflowException {

		List<WorkflowTask> workflowTasks = new ArrayList<>();
		int count = 0;

		ReusableWorkflowTaskIdentifier.WorkflowTaskType workflowTaskType =
			reusableWorkflowTaskIdentifier.getWorkflowTaskType();

		if (workflowTaskType.equals(
				ReusableWorkflowTaskIdentifier.WorkflowTaskType.TO_ME)) {

			workflowTasks = workflowTaskManager.getWorkflowTasksByUser(
				company.getCompanyId(), currentUser.getUserId(), null,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);
			count = workflowTaskManager.getWorkflowTaskCountByUser(
				company.getCompanyId(), currentUser.getUserId(), null);
		}
		else if (workflowTaskType.equals(
					ReusableWorkflowTaskIdentifier.WorkflowTaskType.
						TO_MY_ROLES)) {

			workflowTasks = workflowTaskManager.getWorkflowTasksByUserRoles(
				company.getCompanyId(), currentUser.getUserId(), null,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);
			count = workflowTaskManager.getWorkflowTaskCountByUserRoles(
				company.getCompanyId(), currentUser.getUserId(), null);
		}

		return new PageItems<>(workflowTasks, count);
	}

}