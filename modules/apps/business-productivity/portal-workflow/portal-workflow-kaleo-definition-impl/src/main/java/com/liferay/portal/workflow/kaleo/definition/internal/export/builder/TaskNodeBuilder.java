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

package com.liferay.portal.workflow.kaleo.definition.internal.export.builder;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.definition.Assignment;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"node.type=TASK"}, service = NodeBuilder.class
)
public class TaskNodeBuilder
	extends BaseNodeBuilder<Task> implements NodeBuilder {

	@Override
	protected Task createNode(KaleoNode kaleoNode) throws PortalException {
		KaleoTask kaleoTask = _kaleoTaskLocalService.getKaleoNodeKaleoTask(
			kaleoNode.getKaleoNodeId());

		Task task = new Task(kaleoNode.getName(), kaleoNode.getDescription());

		Set<Assignment> assignments = buildAssigments(
			KaleoTask.class.getName(), kaleoTask.getKaleoTaskId());

		task.setAssignments(assignments);

		return task;
	}

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

}