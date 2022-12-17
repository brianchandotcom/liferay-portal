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
import {useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import AssignModal from '../../../components/AssignModal';
import useFormModal from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {Liferay} from '../../../services/liferay';
import {TestraySubTask, UserAccount} from '../../../services/rest';
import {testraySubTaskImpl} from '../../../services/rest/TestraySubtask';
import {SubTaskStatuses} from '../../../util/statuses';
import SubtaskCompleteModal from './SubtaskCompleteModal';

type OutletContext = {
	data: {
		testraySubtask: TestraySubTask;
	};
	mutate: {
		mutateSubtask: KeyedMutator<TestraySubTask>;
	};
	revalidate: {
		revalidateTaskUser: () => void;
	};
};

const SubtaskHeaderActions = () => {
	const {
		data: {testraySubtask},
		mutate: {mutateSubtask},
		revalidate: {revalidateTaskUser},
	} = useOutletContext<OutletContext>();
	const {modal: assignUserModal} = useFormModal({
		onSave: (user: UserAccount) =>
			testraySubTaskImpl
				.assignTo(testraySubtask, user.id)
				.then(mutateSubtask),
	});

	const {modal: completeModal} = useFormModal();

	return (
		<>
			<AssignModal modal={assignUserModal} />

			<SubtaskCompleteModal
				modal={completeModal}
				revalidateSubtask={revalidateTaskUser}
				subtask={testraySubtask}
			/>

			{[SubTaskStatuses.COMPLETE, SubTaskStatuses.OPEN].includes(
				testraySubtask.dueStatus.key as SubTaskStatuses
			) ? (
				<ClayButton
					className="mb-3 ml-3"
					displayType="secondary"
					onClick={() => assignUserModal.open()}
				>
					{i18n.translate(
						testraySubtask.dueStatus.key === SubTaskStatuses.OPEN
							? 'assign-and-begin-analysis'
							: 'assign-and-reanalyze'
					)}
				</ClayButton>
			) : (
				<ClayButton.Group className="mb-3 ml-3" spaced>
					<ClayButton
						displayType="secondary"
						onClick={() => assignUserModal.open()}
					>
						{i18n.translate('assign')}
					</ClayButton>

					<ClayButton
						onClick={() => {
							if (
								testraySubtask.user.id ===
								Number(Liferay.ThemeDisplay.getUserId())
							) {
								return completeModal.open();
							}

							Liferay.Util.openToast({
								message: i18n.translate(
									'you-are-not-the-assigned-user'
								),
								type: 'danger',
							});
						}}
					>
						{i18n.translate('complete')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={() =>
							testraySubTaskImpl
								.returnToOpen(testraySubtask)
								.then(mutateSubtask)
						}
					>
						{i18n.translate('return-to-open')}
					</ClayButton>
				</ClayButton.Group>
			)}
		</>
	);
};

export default SubtaskHeaderActions;
