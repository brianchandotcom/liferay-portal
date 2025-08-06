/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback} from 'react';

import {
	getWorkflowTasksAssignedToMe,
	getWorkflowTasksAssignedToMyRoles,
} from '../../../common/services/WorkflowService';
import {WorkflowTask as WorkflowTaskResponse} from '../../../common/types/WorkflowTask';
import {WorkflowTask} from '../types/WorkflowTask';
import getPaginatedList from '../util/getPaginatedList';

interface WorkflowTasksProps {
	items: WorkflowTask[];
	totalCount: number;
}

function filterWorkflowTasks(items: WorkflowTaskResponse[]) {
	const filteredWorkflowTasks = items.filter(
		(item) => !item.completed && item.name === 'review'
	);

	const transformedWorkflowTasks = filteredWorkflowTasks.map((task) => {
		const workflowLogs = task.workflowLogs.filter(
			(item) => item.type === 'TaskAssign'
		);

		return {
			assetTitle: task.objectReviewed.assetTitle,
			assetType: task.objectReviewed.assetType,
			assignedDate: workflowLogs[0].dateCreated,
			auditUser: workflowLogs[0].auditPerson.name,
			auditUserImageURL: workflowLogs[0].auditPerson.image,
			dueDate: task.dateDue,
			isComplete: task.completed,
			name: task.name,
			workflowTaskId: task.id,
		};
	});

	return transformedWorkflowTasks.sort((a, b) => {
		const dateA = new Date(a.assignedDate);
		const dateB = new Date(b.assignedDate);

		return dateB.getTime() - dateA.getTime();
	});
}

export default function useFetchWorkflowTasks({
	delta,
	page,
	selectedItem,
	setWorkflowTasks,
}: {
	delta: number;
	page: number;
	selectedItem: {label: string; value: string};
	setWorkflowTasks: React.Dispatch<React.SetStateAction<WorkflowTasksProps>>;
}) {
	const fetchWorkflowTasks = useCallback(async () => {
		try {
			if (selectedItem.value === 'assigned-to-me') {
				const res = await getWorkflowTasksAssignedToMe({
					page: -1,
					pageSize: -1,
				});

				const items = filterWorkflowTasks(res.items);

				setWorkflowTasks({
					items: getPaginatedList({delta, items, page}),
					totalCount: items.length,
				});
			}
			else {
				const res = await getWorkflowTasksAssignedToMyRoles({
					page: -1,
					pageSize: -1,
				});

				const items = filterWorkflowTasks(res.items);

				setWorkflowTasks({
					items: getPaginatedList({delta, items, page}),
					totalCount: items.length,
				});
			}
		}
		catch (error) {
			console.error(error);
			setWorkflowTasks({items: [], totalCount: 0});
		}
	}, [delta, page, selectedItem.value, setWorkflowTasks]);

	return {fetchWorkflowTasks};
}
