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
import React, {useContext, useEffect, useMemo, useState} from 'react';

import {Autocomplete} from '../../../../../shared/components/autocomplete/Autocomplete.es';
import PromisesResolver from '../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {ModalContext} from '../../ModalContext.es';

const Header = ({data}) => {
	const {bulkModal, setBulkModal} = useContext(ModalContext);
	const {
		reassigning,
		selectedAssignee,
		selectedTasks = [],
		useSameAssignee
	} = bulkModal;

	const [assignees, setAssignees] = useState([]);

	useEffect(() => {
		const {workflowTaskAssignableUsers: users} = data || {};

		if (users && users.length) {
			const {assignableUsers = []} =
				users.find(item => item.workflowTaskId === 0) || {};

			setAssignees(assignableUsers);
		}
	}, [data]);

	const defaultValue = useMemo(
		() => (selectedAssignee ? selectedAssignee.name : ''),
		[selectedAssignee]
	);

	const disableBulk = useMemo(() => reassigning || assignees.length === 0, [
		assignees,
		reassigning
	]);

	const handleCheck = ({target}) => {
		setBulkModal({
			...bulkModal,
			reassignedTasks: [],
			selectedAssignee: null,
			useSameAssignee: target.checked
		});
	};

	const handleSelect = newAssignee => {
		const reassignedTasks = [];

		if (newAssignee) {
			selectedTasks.forEach(task => {
				reassignedTasks.push({
					assigneeId: newAssignee.id,
					workflowTaskId: task.id
				});
			});

			setBulkModal({
				...bulkModal,
				reassignedTasks,
				selectedAssignee: newAssignee,
				useSameAssignee: true
			});
		}
	};

	return (
		<PromisesResolver.Resolved>
			<ClayManagementToolbar className="border-bottom mb-0 px-3">
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

				<ClayManagementToolbar.Item />

				<div className="navbar-form">
					<Autocomplete
						defaultValue={defaultValue}
						disabled={disableBulk || !useSameAssignee}
						items={assignees}
						onSelect={handleSelect}
						placeholder={Liferay.Language.get(
							'search-for-an-assignee'
						)}
						promises={[]}
					>
						<ClayInput.GroupInsetItem after tag="span">
							<ClayIcon
								className="m-2"
								displayType="unstyled"
								symbol="search"
							/>
						</ClayInput.GroupInsetItem>
					</Autocomplete>
				</div>
			</ClayManagementToolbar>
		</PromisesResolver.Resolved>
	);
};

export {Header};
