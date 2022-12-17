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
	APIResponse,
	TestraySubTask,
	TestrayTask,
	TestrayTaskUser,
	testrayTaskImpl,
} from '../../services/rest';
import TestflowAssignUserModal, {TestflowAssigUserType} from './modal';

type OutletContext = {
	data: {
		testraySubtasks: APIResponse<TestraySubTask>;
		testrayTask: TestrayTask;
		testrayTaskUser: TestrayTaskUser[];
	};
	mutate: {
		mutateTask: KeyedMutator<TestrayTask>;
		mutateTaskUser: KeyedMutator<APIResponse<TestrayTaskUser>>;
	};
	revalidate: {
		revalidateTaskUser: () => void;
	};
};

const TaskHeaderActions = () => {
	const {
		data: {testraySubtasks, testrayTask, testrayTaskUser},
		mutate: {mutateTask},
		revalidate: {revalidateTaskUser},
	} = useOutletContext<OutletContext>();

	const subTaskAllCompleted = testraySubtasks?.totalCount === 0;

	const [modalType, setModalType] = useState<TestflowAssigUserType>(
		'select-users'
	);
	const [userIds, setUsersId] = useState<number[]>([]);
	const {modal} = useFormModal({
		onSave: (newUserIds: number[]) =>
			testrayTaskImpl
				.assignTo(testrayTask, newUserIds)
				.then((response) => {
					mutateTask(response);

					revalidateTaskUser();
				}),
	});

	const navigate = useNavigate();

	const onOpenModal = (type: TestflowAssigUserType) => {
		setModalType(type);

		modal.open(userIds);
	};

	useEffect(() => {
		if (testrayTaskUser) {
			setUsersId(testrayTaskUser.map(({user}) => user?.id as number));
		}
	}, [setUsersId, testrayTaskUser]);

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
					onClick={() => {
						const fn = subTaskAllCompleted
							? (task: TestrayTask) =>
									testrayTaskImpl.complete(task)
							: (task: TestrayTask) =>
									testrayTaskImpl.abandon(task);

						fn(testrayTask).then(mutateTask);
					}}
				>
					{i18n.translate(
						subTaskAllCompleted ? 'complete' : 'abandon'
					)}
				</ClayButton>
			</ClayButton.Group>

			<TestflowAssignUserModal modal={modal} type={modalType} />
		</>
	);
};

export default TaskHeaderActions;
