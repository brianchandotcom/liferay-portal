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
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.person.apio.architect.identifier.MyUserAccountIdentifier;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.workflow.apio.architect.identifier.WorkflowTaskIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose user WorkflowTask resources
 * through a web API. The resources are mapped from the internal model {@link
 * WorkflowTask}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class MyUserAccountWorkflowTasksNestedCollectionRouter
	implements NestedCollectionRouter
		<WorkflowTask, Long, WorkflowTaskIdentifier, Long,
		 MyUserAccountIdentifier> {

	@Override
	public NestedCollectionRoutes<WorkflowTask, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<WorkflowTask, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Reference
	protected WorkflowTaskManager workflowTaskManager;

	private PageItems<WorkflowTask> _getPageItems(
			Pagination pagination, long userId, Company company)
		throws WorkflowException {

		List<WorkflowTask> workflowTasks =
			workflowTaskManager.getWorkflowTasksByUser(
				company.getCompanyId(), userId, null,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);
		int count = workflowTaskManager.getWorkflowTaskCountByUser(
			company.getCompanyId(), userId, null);

		return new PageItems<>(workflowTasks, count);
	}

}