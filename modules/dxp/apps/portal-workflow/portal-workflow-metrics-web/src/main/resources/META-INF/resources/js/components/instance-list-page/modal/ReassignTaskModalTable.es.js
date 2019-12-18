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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState, useEffect, useCallback, useMemo} from 'react';

import {useFetch} from '../../../shared/hooks/useFetch.es';

const AssigneeInput = ({reassignedTasks, setReassignedTasks, taskId}) => {
	const [dropDownVisible, setDropDownVisible] = useState(() => false);
	const [error, setError] = useState();
	const [loading, setLoading] = useState();
	const [newAssignee, setNewAssignee] = useState(() => ({}));
	const [value, setValue] = useState(() => '');

	const {data, fetchData} = useFetch({
		headless: true,
		params: {page: 1, pageSize: 10000},
		url: `/workflow-tasks/${taskId}/assignable-users`
	});

	const handleNewAssignee = useCallback(item => {
		setValue(() => item.name);
		setNewAssignee(() => item);
		setDropDownVisible(() => false);
	}, []);

	const autocompleteChangeHandler = useCallback(value => {
		setValue(() => value);
		setDropDownVisible(() => !!value && !!data);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const tasks = useMemo(() => reassignedTasks.tasks, [reassignedTasks.tasks]);

	useEffect(() => {
		if (newAssignee && newAssignee.id !== undefined) {
			const selectedTaskIndex = tasks.findIndex(
				task => task.id === taskId
			);
			if (selectedTaskIndex === -1) {
				tasks.push({assigneeId: newAssignee.id, id: taskId});

				setReassignedTasks(() => ({
					tasks
				}));
			} else {
				tasks[selectedTaskIndex].assigneeId = newAssignee.id;
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [newAssignee]);

	useEffect(() => {
		setLoading(true);
		setError(false);
		if (fetchData !== undefined) {
			fetchData()
				.then(() => {
					setLoading(() => false);
					setError(() => false);
				})
				.catch(() => {
					setLoading(() => false);
					setError(() => true);
				});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchData]);

	return (
		<ClayAutocomplete>
			<ClayAutocomplete.Input
				inputProps={{'data-testid': 'autocompleteInput'}}
				onChange={event =>
					autocompleteChangeHandler(event.target.value)
				}
				value={value}
			/>
			<ClayAutocomplete.DropDown active={dropDownVisible || loading}>
				<ClayDropDown.ItemList>
					{(error || (data && data.error)) && (
						<ClayDropDown.Item className="disabled">
							{Liferay.Language.get('no-results-found')}
						</ClayDropDown.Item>
					)}
					{!error &&
						data.items &&
						data.items.length > 0 &&
						data.items.map((item, index) => (
							<ClayAutocomplete.Item
								key={index}
								match={value}
								onClick={() => handleNewAssignee(item)}
								value={item.name}
							/>
						))}
				</ClayDropDown.ItemList>
			</ClayAutocomplete.DropDown>
			{loading && <ClayAutocomplete.LoadingIndicator />}
		</ClayAutocomplete>
	);
};

const Table = ({
	assetTitle,
	assetType,
	data,
	reassignedTasks,
	setReassignedTasks,
	status,
	taskNames = []
}) => {
	const completed = status === 'Completed';
	const {items} = data;
	const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`;

	return (
		<ClayTable borderless={true} data-testid="reassignTaskModalTable">
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '8%'
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
							width: '20%'
						}}
					>
						{`${Liferay.Language.get('new-assignee')} `}

						<ClayTooltipProvider>
							<ClayIcon
								data-tooltip-align="top"
								spritemap={spritemap}
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
						<ClayTable.Row {...item} key={index}>
							<ClayTable.Cell style={{fontWeight: 'bold'}}>
								{item.id}
							</ClayTable.Cell>

							<ClayTable.Cell>
								{`${assetType}: ${assetTitle}`}{' '}
							</ClayTable.Cell>

							<ClayTable.Cell>
								{!completed
									? taskNames.join(', ')
									: Liferay.Language.get('completed')}
							</ClayTable.Cell>

							<ClayTable.Cell>
								{item.assigneePerson &&
									item.assigneePerson.name}
							</ClayTable.Cell>

							<ClayTable.Cell>
								<Table.AssigneeInput
									reassignedTasks={reassignedTasks}
									setReassignedTasks={setReassignedTasks}
									taskId={items[index].id}
								/>
							</ClayTable.Cell>
						</ClayTable.Row>
					))}
			</ClayTable.Body>
		</ClayTable>
	);
};

Table.AssigneeInput = AssigneeInput;

export {Table};
