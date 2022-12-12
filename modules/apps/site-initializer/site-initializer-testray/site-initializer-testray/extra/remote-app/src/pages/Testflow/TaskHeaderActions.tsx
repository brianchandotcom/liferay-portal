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
import {useNavigate} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import AssignModal from '../../components/AssignModal';
import useFormModal from '../../hooks/useFormModal';
import i18n from '../../i18n';
import {TestrayTask, UserAccount, testrayTaskImpl} from '../../services/rest';

type TaskHeaderActionsProps = {
	TestrayTask: TestrayTask;
	mutateTask: KeyedMutator<TestrayTask>;
};

const TaskHeaderActions: React.FC<TaskHeaderActionsProps> = ({
	TestrayTask,
	mutateTask,
}) => {
	const navigate = useNavigate();
	const {modal: assignUserModal} = useFormModal({
		onSave: (user: UserAccount) =>
			testrayTaskImpl.assignTo(TestrayTask, user.id).then(mutateTask),
	});

	return (
		<>
			<AssignModal modal={assignUserModal} />

			<ClayButton
				className="mb-3 ml-3"
				displayType="secondary"
				onClick={() => assignUserModal.open()}
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
						testrayTaskImpl.abandon(TestrayTask).then(mutateTask)
					}
				>
					{i18n.translate('abandon')}
				</ClayButton>
			</ClayButton.Group>
		</>
	);
};

export default TaskHeaderActions;
