/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.export.builder;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.TaskForm;
import com.liferay.portal.workflow.kaleo.definition.TaskFormReference;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskFormLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = NodeBuilder.class)
public class TaskNodeBuilder
	extends BaseNodeBuilder<Task> implements NodeBuilder {

	@Override
	public NodeType getNodeType() {
		return NodeType.TASK;
	}

	@Override
	protected Task createNode(KaleoNode kaleoNode) throws PortalException {
		KaleoTask kaleoTask = _kaleoTaskLocalService.getKaleoNodeKaleoTask(
			kaleoNode.getKaleoNodeId());

		Task task = new Task(kaleoNode.getName(), kaleoNode.getDescription());

		task.setAssignments(
			buildAssigments(
				KaleoTask.class.getName(), kaleoTask.getKaleoTaskId()));

		Set<TaskForm> taskForms = _buildTaskForms(kaleoTask.getKaleoTaskId());

		task.addTaskForms(taskForms);

		return task;
	}

	private Set<TaskForm> _buildTaskForms(long kaleoTaskId)
		throws PortalException {

		List<KaleoTaskForm> kaleoTaskForms =
			_kaleoTaskFormLocalService.getKaleoTaskForms(kaleoTaskId);

		Set<TaskForm> taskForms = new TreeSet<>();

		for (KaleoTaskForm kaleoTaskForm : kaleoTaskForms) {
			TaskForm taskForm = new TaskForm(
				kaleoTaskForm.getName(), kaleoTaskForm.getPriority());

			taskForm.setDescription(kaleoTaskForm.getDescription());
			taskForm.setMetadata(kaleoTaskForm.getMetadata());

			if (Validator.isNotNull(kaleoTaskForm.getFormDefinition())) {
				taskForm.setFormDefinition(kaleoTaskForm.getFormDefinition());
			}
			else {
				TaskFormReference taskFormReference = new TaskFormReference();

				taskFormReference.setCompanyId(
					kaleoTaskForm.getFormCompanyId());
				taskFormReference.setFormUuid(kaleoTaskForm.getFormUuid());
				taskFormReference.setFormId(kaleoTaskForm.getFormId());
				taskFormReference.setGroupId(kaleoTaskForm.getFormGroupId());

				taskForm.setTaskFormReference(taskFormReference);
			}

			taskForms.add(taskForm);
		}

		return taskForms;
	}

	@Reference
	private KaleoTaskFormLocalService _kaleoTaskFormLocalService;

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

}