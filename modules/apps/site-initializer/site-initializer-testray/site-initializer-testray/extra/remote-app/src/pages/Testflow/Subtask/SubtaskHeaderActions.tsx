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
import {KeyedMutator} from 'swr';

import AssignModal from '../../../components/AssignModal';
import useFormModal from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {TestraySubTask, UserAccount} from '../../../services/rest';
import {testraySubtaskImpl} from '../../../services/rest/TestraySubtask';
import {SUB_TASK_STATUS} from '../../../util/constants';
import SubtaskCompleteModal from './SubtaskCompleteModal';

const SubtaskHeaderActions: React.FC<{
	caseResultIds: number[];
	dueStatus: number;
	mutateCaseResult: KeyedMutator<any>;
	mutateSubtask: KeyedMutator<any>;
	subtask: TestraySubTask;
}> = ({caseResultIds, dueStatus, mutateCaseResult, mutateSubtask, subtask}) => {
	const {modal: assignUserModal} = useFormModal({
		onSave: (user: UserAccount) =>
			testraySubtaskImpl.assignTo(subtask, user.id).then(mutateSubtask),
	});

	const {modal: completeModal} = useFormModal({
		onSave: (dueStatus) => {
			testraySubtaskImpl
				.complete(subtask.id, caseResultIds, dueStatus)
				.then(mutateSubtask)
				.then(mutateCaseResult);
		},
	});

	const ButtonDisabled =
		subtask.dueStatus === SUB_TASK_STATUS.OPEN ||
		subtask.dueStatus === SUB_TASK_STATUS.COMPLETE;

	return (
		<>
			<AssignModal modal={assignUserModal} />
			<SubtaskCompleteModal
				modal={completeModal}
				status={Number(dueStatus)}
			/>

			{ButtonDisabled && (
				<>
					<ClayButton
						className="mb-3 ml-3"
						displayType="secondary"
						onClick={() => assignUserModal.open()}
					>
						{i18n.translate(
							subtask.dueStatus === SUB_TASK_STATUS.OPEN
								? 'assign-and-begin-analysis'
								: 'assign-and-reanalyze'
						)}
					</ClayButton>
				</>
			)}

			{!ButtonDisabled && (
				<>
					<ClayButton.Group className="mb-3 ml-3" spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => assignUserModal.open()}
						>
							{i18n.translate('assign')}
						</ClayButton>

						<ClayButton onClick={() => completeModal.open()}>
							{i18n.translate('complete')}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() =>
								testraySubtaskImpl
									.returnToOpen(subtask)
									.then(mutateSubtask)
							}
						>
							{i18n.translate('return-to-open')}
						</ClayButton>
					</ClayButton.Group>
				</>
			)}
		</>
	);
};

export default SubtaskHeaderActions;
