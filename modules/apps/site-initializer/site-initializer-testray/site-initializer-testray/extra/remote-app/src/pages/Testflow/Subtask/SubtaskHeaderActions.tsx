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
import {testraySubTaskImpl} from '../../../services/rest/TestraySubtask';
import {SubTaskStatuses} from '../../../util/statuses';
import SubtaskCompleteModal from './SubtaskCompleteModal';

type SubTaskHeaderActionsProps = {
	mutateSubtask: KeyedMutator<any>;
	subtask: TestraySubTask;
};

const SubtaskHeaderActions: React.FC<SubTaskHeaderActionsProps> = ({
	mutateSubtask,
	subtask,
}) => {
	const {modal: assignUserModal} = useFormModal({
		onSave: (user: UserAccount) =>
			testraySubTaskImpl.assignTo(subtask, user.id).then(mutateSubtask),
	});

	const {modal: completeModal} = useFormModal();

	const buttonDisabled = [
		SubTaskStatuses.OPEN,
		SubTaskStatuses.COMPLETE,
	].includes(subtask.dueStatus.key as SubTaskStatuses);

	return (
		<>
			<AssignModal modal={assignUserModal} />

			<SubtaskCompleteModal
				modal={completeModal}
				mutate={mutateSubtask}
				subtask={subtask}
			/>

			{buttonDisabled && (
				<>
					<ClayButton
						className="mb-3 ml-3"
						displayType="secondary"
						onClick={() => assignUserModal.open()}
					>
						{i18n.translate(
							subtask.dueStatus.key === SubTaskStatuses.OPEN
								? 'assign-and-begin-analysis'
								: 'assign-and-reanalyze'
						)}
					</ClayButton>
				</>
			)}

			{!buttonDisabled && (
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
								testraySubTaskImpl
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
