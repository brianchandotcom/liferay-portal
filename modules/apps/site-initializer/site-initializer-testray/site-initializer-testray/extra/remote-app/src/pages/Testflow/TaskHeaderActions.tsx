/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import {useEffect, useState} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import useFormModal from '../../hooks/useFormModal';
import i18n from '../../i18n';
import {
	TestrayTask,
	TestrayTaskUser,
	testrayTaskImpl,
} from '../../services/rest';
import TestflowAssignUserModal from './modal';

type OutletContext = {
	mutateTask: KeyedMutator<any>;
	mutateTaskUsers: KeyedMutator<TestrayTaskUser>;
	taskUser: number[];
	testrayTask: TestrayTask;
};

const TaskHeaderActions = () => {
	const {
		mutateTask,
		mutateTaskUsers,
		taskUser,
		testrayTask,
	} = useOutletContext<OutletContext>();

	const [modalType, setModalType] = useState('assign-users');
	const [users, setUsers] = useState<number[]>([]);
	const {modal} = useFormModal({
		onSave: (userIds: number[]) =>
			testrayTaskImpl
				.assignTo(testrayTask, userIds)
				.then(mutateTask)
				.then(mutateTaskUsers),
	});

	const navigate = useNavigate();

	const onOpenModal = (option: 'select-users') => {
		setModalType(option);

		modal.open(users);
	};

	useEffect(() => {
		setUsers(taskUser);
	}, [setUsers, taskUser]);

	return (
		<>
			<ClayButton
				className="mb-3 ml-3"
				displayType="secondary"
				onClick={() => onOpenModal('select-users')}
			>
				{i18n.translate('assign-users')}
			</ClayButton>

			<ClayButton.Group className="mb-3 ml-3" spaced>
				<ClayButton
					displayType="secondary"
					onClick={() => navigate('update')}
				>
					{i18n.sub('edit-x', 'task')}
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() =>
						testrayTaskImpl.abandon(testrayTask).then(mutateTask)
					}
				>
					{i18n.translate('abandon')}
				</ClayButton>
			</ClayButton.Group>

			<TestflowAssignUserModal modal={modal} type={modalType as any} />
		</>
	);
};

export default TaskHeaderActions;
