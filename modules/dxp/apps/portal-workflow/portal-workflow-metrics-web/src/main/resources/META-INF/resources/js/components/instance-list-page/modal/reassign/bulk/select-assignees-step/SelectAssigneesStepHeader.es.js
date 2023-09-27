/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {Autocomplete} from '../../../../../../shared/components/autocomplete/Autocomplete.es';
import PromisesResolver from '../../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {ModalContext} from '../../../ModalProvider.es';

export default function Header({data}) {
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

	const disableBulk = reassigning || !assignees.length;

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

	const handleSubmit = (event) => {
		event.preventDefault();
	};

	return (
		<PromisesResolver.Resolved>
			<ManagementToolbar.Container className="border-bottom mb-0 px-3">
				<ManagementToolbar.ItemList>
					<ManagementToolbar.Item className="pt-2">
						<ClayCheckbox
							checked={useSameAssignee}
							disabled={disableBulk}
							label={Liferay.Language.get(
								'use-the-same-assignee-for-all-tasks'
							)}
							onChange={handleCheck}
						/>
					</ManagementToolbar.Item>
				</ManagementToolbar.ItemList>

				<ManagementToolbar.Search onSubmit={handleSubmit}>
					<Autocomplete
						defaultValue={selectedAssignee?.name || ''}
						disabled={disableBulk || !useSameAssignee}
						items={assignees}
						onSelect={handleSelect}
						placeholder={Liferay.Language.get(
							'search-for-an-assignee'
						)}
					>
						<ClayInput.GroupInsetItem after tag="span">
							<ClayIcon className="m-2" symbol="search" />
						</ClayInput.GroupInsetItem>
					</Autocomplete>
				</ManagementToolbar.Search>
			</ManagementToolbar.Container>
		</PromisesResolver.Resolved>
	);
}
