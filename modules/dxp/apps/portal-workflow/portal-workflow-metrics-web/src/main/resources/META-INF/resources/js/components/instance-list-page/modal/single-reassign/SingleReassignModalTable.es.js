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
import React, {useCallback, useContext, useMemo} from 'react';

import {Autocomplete} from '../../../../shared/components/autocomplete/Autocomplete.es';
import {useFetch} from '../../../../shared/hooks/useFetch.es';
import {ModalContext} from '../ModalContext.es';

const AssigneeInput = ({setAssigneeId, taskId}) => {
	const {data, fetchData} = useFetch({
		admin: true,
		params: {page: -1, pageSize: -1},
		url: `/workflow-tasks/${taskId}/assignable-users`
	});

	const handleSelect = useCallback(
		newAssignee => {
			const assigneeId = newAssignee ? newAssignee.id : undefined;

			setAssigneeId(assigneeId);
		},
		[setAssigneeId]
	);

	const promises = useMemo(() => [fetchData()], [fetchData]);

	return (
		<Autocomplete
			items={data.items}
			onSelect={handleSelect}
			promises={promises}
		/>
	);
};

const Item = ({
	assetTitle,
	assetType,
	completed,
	index,
	item,
	setAssigneeId,
	taskNames
}) => {
	const {singleModal} = useContext(ModalContext);

	return (
		<ClayTable.Row {...item} key={index}>
			<ClayTable.Cell style={{fontWeight: 'bold'}}>
				{singleModal.selectedItem && singleModal.selectedItem.id}
			</ClayTable.Cell>

			<ClayTable.Cell>{`${assetType}: ${assetTitle}`} </ClayTable.Cell>

			<ClayTable.Cell>
				{!completed
					? taskNames.join(', ')
					: Liferay.Language.get('completed')}
			</ClayTable.Cell>

			<ClayTable.Cell>
				{item.assigneePerson
					? item.assigneePerson.name
					: Liferay.Language.get('unassigned')}
			</ClayTable.Cell>

			<ClayTable.Cell>
				<Table.AssigneeInput
					setAssigneeId={setAssigneeId}
					taskId={item.id}
				/>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
};

const Table = ({
	assetTitle,
	assetType,
	data,
	setAssigneeId,
	status,
	taskNames = []
}) => {
	const completed = status === 'Completed';
	const {items} = data;

	return (
		<ClayTable data-testid="singleReassignModalTable">
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
						<Table.Item
							assetTitle={assetTitle}
							assetType={assetType}
							completed={completed}
							index={index}
							item={item}
							key={index}
							setAssigneeId={setAssigneeId}
							taskNames={taskNames}
						/>
					))}
			</ClayTable.Body>
		</ClayTable>
	);
};

Table.AssigneeInput = AssigneeInput;
Table.Item = Item;

export {Table};
