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
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useContext, useMemo} from 'react';

import {Autocomplete} from '../../../../../shared/components/autocomplete/Autocomplete.es';
import {ModalContext} from '../../ModalContext.es';

const Item = ({
	assigneePerson,
	data,
	id,
	name,
	objectReviewed: {assetTitle, assetType},
	workflowInstanceId
}) => {
	const {bulkModal, setBulkModal} = useContext(ModalContext);
	const {
		reassignedTasks,
		reassigning,
		selectedAssignee,
		useSameAssignee
	} = bulkModal;

	const defaultValue = useMemo(
		() => (selectedAssignee ? selectedAssignee.name : ''),
		[selectedAssignee]
	);

	const {assigneeId} = useMemo(
		() =>
			!useSameAssignee
				? reassignedTasks.find(task => task.workflowTaskId === id) || {}
				: {},
		[id, reassignedTasks, useSameAssignee]
	);

	const assignees = useMemo(() => {
		const {workflowTaskAssignableUsers: users} = data || {};

		if (users && users.length) {
			const {assignableUsers = []} =
				users.find(item => item.workflowTaskId === id) || {};

			return assignableUsers;
		}

		return [];
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data]);

	const assignee = useMemo(
		() => assignees.find(assignee => assignee.id === assigneeId) || {},
		[assigneeId, assignees]
	);

	const handleSelect = newAssignee => {
		const filteredTasks = reassignedTasks.filter(
			task => task.workflowTaskId !== id
		);

		if (newAssignee) {
			filteredTasks.push({
				assigneeId: newAssignee.id,
				workflowTaskId: id
			});
		}

		setBulkModal({
			...bulkModal,
			reassignedTasks: filteredTasks
		});
	};

	return (
		<ClayTable.Row>
			<ClayTable.Cell className="font-weight-bold">
				{workflowInstanceId}
			</ClayTable.Cell>

			<ClayTable.Cell>{`${assetType}: ${assetTitle}`} </ClayTable.Cell>

			<ClayTable.Cell>{name}</ClayTable.Cell>

			<ClayTable.Cell>
				{assigneePerson
					? assigneePerson.name
					: Liferay.Language.get('unassigned')}
			</ClayTable.Cell>

			<ClayTable.Cell>
				<Autocomplete
					defaultValue={defaultValue || assignee.name}
					disabled={reassigning || useSameAssignee}
					items={assignees}
					onSelect={handleSelect}
					promises={[]}
				/>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
};

const Table = ({data, items}) => {
	return (
		<ClayTable data-testid="bulkReassignModalTable">
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '10%'
						}}
					>
						{Liferay.Language.get('id')}
					</ClayTable.Cell>

					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '25%'
						}}
					>
						{Liferay.Language.get('item-subject')}
					</ClayTable.Cell>

					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '20%'
						}}
					>
						{Liferay.Language.get('process-step')}
					</ClayTable.Cell>

					<ClayTable.Cell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '20%'
						}}
					>
						{Liferay.Language.get('current-assignee')}
					</ClayTable.Cell>

					<ClayTable.Cell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '25%'
						}}
					>
						{`${Liferay.Language.get('new-assignee')} `}

						<ClayTooltipProvider>
							<ClayIcon
								data-tooltip-align="top"
								style={{color: '#6B6C7E'}}
								symbol="question-circle-full"
								title={Liferay.Language.get(
									'possible-assignees-must-have-permissions-to-be-assigned-to-the-corresponding-step'
								)}
							/>
						</ClayTooltipProvider>
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items &&
					items.length > 0 &&
					items.map((item, index) => (
						<Table.Item data={data} {...item} key={index} />
					))}
			</ClayTable.Body>
		</ClayTable>
	);
};

Table.Item = Item;
export {Table};
