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

import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {Autocomplete} from '../../../../../../shared/components/autocomplete/Autocomplete.es';
import PromisesResolver from '../../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {ModalContext} from '../../../ModalProvider.es';

const Header = ({data}) => {
	const {
		bulkReassign,
		selectTasks: {tasks},
		setBulkReassign,
	} = useContext(ModalContext);
	const {reassigning, selectedAssignee, useSameAssignee} = bulkReassign;

	const [assignees, setAssignees] = useState([]);

	useEffect(() => {
		const {workflowTaskAssignableUsers: users} = data || {};

		if (users && users.length) {
			const {assignableUsers = []} =
				users.find((item) => item.workflowTaskId === 0) || {};

			setAssignees(assignableUsers);
		}
	}, [data]);

	const defaultValue = useMemo(
		() => (selectedAssignee ? selectedAssignee.name : ''),
		[selectedAssignee]
	);

	const disableBulk = useMemo(() => reassigning || assignees.length === 0, [
		assignees,
		reassigning,
	]);

	const handleCheck = ({target}) => {
		setBulkReassign({
			...bulkReassign,
			reassignedTasks: [],
			selectedAssignee: null,
			useSameAssignee: target.checked,
		});
	};

	const handleSelect = useCallback(
		(newAssignee) => {
			const reassignedTasks = [];

			if (newAssignee) {
				tasks.forEach((task) => {
					reassignedTasks.push({
						assigneeId: newAssignee.id,
						workflowTaskId: task.id,
					});
				});
			}

			setBulkReassign({
				...bulkReassign,
				reassignedTasks,
				selectedAssignee: newAssignee,
			});
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[bulkReassign, tasks, setBulkReassign]
	);

	return (
		<PromisesResolver.Resolved>
			<ClayManagementToolbar className="border-bottom mb-0 px-3">
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item className="pt-2">
						<ClayCheckbox
							checked={useSameAssignee}
							data-testid="useSameAssignee"
							disabled={disableBulk}
							label={Liferay.Language.get(
								'use-the-same-assignee-for-all-tasks'
							)}
							onChange={handleCheck}
						/>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>
				<ClayManagementToolbar.Search>
					<Autocomplete
						defaultValue={defaultValue}
						disabled={disableBulk || !useSameAssignee}
						items={assignees}
						onSelect={handleSelect}
						placeholder={Liferay.Language.get(
							'search-for-an-assignee'
						)}
					>
						<ClayInput.GroupInsetItem after tag="span">
							<ClayIcon
								className="m-2"
								displayType="unstyled"
								symbol="search"
							/>
						</ClayInput.GroupInsetItem>
					</Autocomplete>
				</ClayManagementToolbar.Search>
			</ClayManagementToolbar>
		</PromisesResolver.Resolved>
	);
};

export {Header};
