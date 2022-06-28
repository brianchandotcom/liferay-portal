/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import React, {useContext, useEffect, useState} from 'react';

import {Autocomplete} from '../../../../../../shared/components/autocomplete/Autocomplete.es';
import {ModalContext} from '../../../ModalProvider.es';

function Item({
	assetTitle,
	assetType,
	assignee: currentAssignee,
	data = {},
	id,
	instanceId,
	label,
}) {
	const {bulkReassign, setBulkReassign} = useContext(ModalContext);
	const {reassignedTasks, reassigning, useSameAssignee} = bulkReassign;

	const {assigneeId} =
		reassignedTasks.find(({workflowTaskId}) => workflowTaskId === id) || {};
	const {workflowTaskAssignableUsers: users = []} = data;

	const {assignableUsers = []} =
		users.find(({workflowTaskId}) => workflowTaskId === id) || {};

	const {name: assigneeName} =
		assignableUsers.find((assignee) => assignee.id === assigneeId) || {};

	const [defaultValue, setDefaultValue] = useState(assigneeName);

	const handleSelect = (newAssignee) => {
		let workflowTasks = reassignedTasks;

		const currentTask = workflowTasks.find(
			({workflowTaskId}) => workflowTaskId === id
		);

		if (currentTask && !newAssignee) {
			workflowTasks = workflowTasks.filter(
				(task) => task.workflowTaskId !== currentTask.workflowTaskId
			);
			setDefaultValue('');
		}
		else if (!currentTask && newAssignee) {
			workflowTasks.push({
				assigneeId: newAssignee.id,
				workflowTaskId: id,
			});
			setDefaultValue(newAssignee.name);
		}

		setBulkReassign((prevBulkReassign) => ({
			...prevBulkReassign,
			reassignedTasks: workflowTasks,
		}));
	};

	useEffect(() => {
		const {assigneeId} =
			reassignedTasks.find(({workflowTaskId}) => workflowTaskId === id) ||
			{};

		const {name: assigneeName} =
			assignableUsers.find((assignee) => assignee.id === assigneeId) ||
			{};

		setDefaultValue(assigneeName);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [reassignedTasks]);

	return (
		<ClayTable.Row>
			<ClayTable.Cell className="font-weight-bold">
				{instanceId}
			</ClayTable.Cell>

			<ClayTable.Cell>{`${assetType}: ${assetTitle}`} </ClayTable.Cell>

			<ClayTable.Cell>{label}</ClayTable.Cell>

			<ClayTable.Cell>
				{currentAssignee
					? currentAssignee.name
					: Liferay.Language.get('unassigned')}
			</ClayTable.Cell>

			<ClayTable.Cell>
				<Autocomplete
					defaultValue={defaultValue}
					disabled={reassigning || useSameAssignee}
					items={assignableUsers}
					onSelect={handleSelect}
				/>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

function Table({data, items}) {
	return (
		<ClayTable>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '10%',
						}}
					>
						{Liferay.Language.get('id')}
					</ClayTable.Cell>

					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '25%',
						}}
					>
						{Liferay.Language.get('item-subject')}
					</ClayTable.Cell>

					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '20%',
						}}
					>
						{Liferay.Language.get('process-step')}
					</ClayTable.Cell>

					<ClayTable.Cell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '20%',
						}}
					>
						{Liferay.Language.get('current-assignee')}
					</ClayTable.Cell>

					<ClayTable.Cell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '25%',
						}}
					>
						{`${Liferay.Language.get('new-assignee')}`}

						<span
							className="ml-1 workflow-tooltip"
							data-tooltip-align="top"
							data-tooltip-delay="0"
							title={Liferay.Language.get(
								'possible-assignees-must-have-permissions-to-be-assigned-to-the-corresponding-step'
							)}
						>
							<ClayIcon symbol="question-circle-full" />
						</span>
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items &&
					!!items.length &&
					items.map((item) => (
						<Table.Item data={data} {...item} key={item.id} />
					))}
			</ClayTable.Body>
		</ClayTable>
	);
}

Table.Item = Item;
export default Table;
