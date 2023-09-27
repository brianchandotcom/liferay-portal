/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		revalidateSubtask: () => void;
	};
};

const SubtaskHeaderActions = () => {
	const {
		data: {testraySubtask},
		mutate: {mutateSubtask},
		revalidate: {revalidateSubtask},
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
				revalidateSubtask={revalidateSubtask}
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
