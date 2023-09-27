/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import React, {useCallback, useMemo} from 'react';

import {Autocomplete} from '../../../../../shared/components/autocomplete/Autocomplete.es';
import {useFetch} from '../../../../../shared/hooks/useFetch.es';

function AssigneeInput({setAssigneeId, taskId}) {
	const {data, fetchData} = useFetch({
		admin: true,
		params: {page: -1, pageSize: -1},
		url: `/workflow-tasks/${taskId}/assignable-users`,
	});

	const handleSelect = useCallback(
		(newAssignee) => {
			const assigneeId = newAssignee ? newAssignee.id : undefined;

			setAssigneeId(assigneeId);
		},
		[setAssigneeId]
	);

	const promises = useMemo(
		() => [fetchData()],

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	return (
		<Autocomplete
			items={data?.items}
			onSelect={handleSelect}
			promises={promises}
		/>
	);
}

function Item({
	assigneePerson = {name: Liferay.Language.get('unassigned')},
	id,
	objectReviewed: {assetTitle, assetType},
	setAssigneeId,
	label,
	workflowInstanceId,
}) {
	return (
		<ClayTable.Row>
			<ClayTable.Cell style={{fontWeight: 'bold'}}>
				{workflowInstanceId}
			</ClayTable.Cell>

			<ClayTable.Cell>{`${assetType}: ${assetTitle}`} </ClayTable.Cell>

			<ClayTable.Cell>{label}</ClayTable.Cell>

			<ClayTable.Cell>{assigneePerson.name}</ClayTable.Cell>

			<ClayTable.Cell>
				<Table.AssigneeInput
					setAssigneeId={setAssigneeId}
					taskId={id}
				/>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

function Table({items, setAssigneeId}) {
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
					items.map((item, index) => (
						<Table.Item
							{...item}
							key={index}
							setAssigneeId={setAssigneeId}
						/>
					))}
			</ClayTable.Body>
		</ClayTable>
	);
}

Table.AssigneeInput = AssigneeInput;
Table.Item = Item;

export default Table;
