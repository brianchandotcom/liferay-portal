/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useEffect, useState} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import useMutate from '~/hooks/useMutate';

import useFormModal from '../../hooks/useFormModal';
import i18n from '../../i18n';
import {Liferay} from '../../services/liferay';
import {
	APIResponse,
	TestraySubTask,
	TestrayTask,
	TestrayTaskUser,
	testrayTaskImpl,
	testrayTaskUsersImpl,
} from '../../services/rest';
import {TaskStatuses} from '../../util/statuses';
import TestflowAssignUserModal, {TestflowAssigUserType} from './modal';

type OutletContext = {
	data: {
		testraySubtasks: APIResponse<TestraySubTask>;
		testrayTask: TestrayTask & {
			actions: {
				[key: string]: string;
			};
		};
		testrayTaskUser: TestrayTaskUser[];
	};
	mutate: {
		mutateTask: KeyedMutator<TestrayTask>;
		mutateTaskUser: KeyedMutator<APIResponse<TestrayTaskUser>>;
	};
	revalidate: {revalidateTaskUser: () => void};
};

const TaskHeaderActions = () => {
	const {
		data: {testraySubtasks, testrayTask, testrayTaskUser},
		mutate: {mutateTask},
		revalidate: {revalidateTaskUser},
	} = useOutletContext<OutletContext>();

	const {mutatePartial: mutateTaskPartial} = useMutate(mutateTask);

	const subTaskAllCompleted = testraySubtasks?.totalCount === 0;

	const [modalType, setModalType] = useState<TestflowAssigUserType>(
		'select-users'
	);

	const [userIds, setUsersId] = useState<number[]>([]);
	const {modal} = useFormModal<number[]>({
		onBeforeSave: (newUserIds, act) => {
			if (!newUserIds.length) {
				return Liferay.Util.openToast({
					message: i18n.translate(
						'mark-at-least-one-user-for-assignment'
					),
					type: 'danger',
				});
			}

			act();
		},
		onSave: async (newUserIds) => {
			await testrayTaskUsersImpl.assign(testrayTask.id, newUserIds);

			revalidateTaskUser();
		},
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

				{[TaskStatuses.ABANDONED, TaskStatuses.COMPLETE].includes(
					testrayTask.dueStatus.key as TaskStatuses
				) ? (
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={async () => {
								const response = await testrayTaskImpl.reanalyze(
									testrayTask
								);

								mutateTaskPartial({
									dueStatus: response.dueStatus,
								});

								await testrayTaskUsersImpl.assign(
									response.id,
									Number(Liferay.ThemeDisplay.getUserId())
								);

								revalidateTaskUser();
							}}
						>
							{i18n.translate('reanalyze')}
						</ClayButton>

						{testrayTask.actions?.delete && (
							<ClayButton
								displayType="secondary"
								onClick={async () => {
									if (
										!confirm(
											i18n.translate(
												'are-you-sure-you-want-to-delete-this-item'
											)
										)
									) {
										return;
									}

									await testrayTaskImpl.remove(
										testrayTask.id
									);

									navigate('/testflow');

									Liferay.Util.openToast({
										message: i18n.translate(
											'your-request-completed-successfully'
										),
										type: 'success',
									});
								}}
							>
								{i18n.translate('delete')}
							</ClayButton>
						)}
					</ClayButton.Group>
				) : (
					<ClayButton
						displayType="secondary"
						onClick={() => {
							const fn = subTaskAllCompleted
								? (task: TestrayTask) =>
										testrayTaskImpl.complete(task)
								: (task: TestrayTask) =>
										testrayTaskImpl.abandon(task);

							fn(testrayTask).then(({dueStatus}) =>
								mutateTaskPartial({dueStatus})
							);
						}}
					>
						{i18n.translate(
							subTaskAllCompleted ? 'complete' : 'abandon'
						)}
					</ClayButton>
				)}
			</ClayButton.Group>

			<TestflowAssignUserModal modal={modal} type={modalType} />
		</>
	);
};

export default TaskHeaderActions;
