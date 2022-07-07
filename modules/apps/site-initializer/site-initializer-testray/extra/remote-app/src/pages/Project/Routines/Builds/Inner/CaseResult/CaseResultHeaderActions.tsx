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

import {
	TestrayCaseResult,
	UserAccount,
} from '../../../../../../graphql/queries';
import useAssignCaseResult from '../../../../../../hooks/useAssignCaseResult';
import useFormModal from '../../../../../../hooks/useFormModal';
import i18n from '../../../../../../i18n';
import {Liferay} from '../../../../../../services/liferay';
import {TEST_STATUS} from '../../../../../../util/constants';
import CaseResultAssignModal from './CaseResultAssignModal';

const CaseResultHeaderActions: React.FC<{
	caseResult: TestrayCaseResult;
	refetch: () => void;
}> = ({caseResult, refetch}) => {
	const {onAssignTo, onAssignToMe, onRemoveAssign} = useAssignCaseResult();
	const navigate = useNavigate();

	const userId = Number(Liferay.ThemeDisplay.getUserId());
	const assignedUserId = caseResult.user?.id || 0;
	const isReopened = ![
		TEST_STATUS.Blocked,
		TEST_STATUS.Failed,
		TEST_STATUS.Passed,
		TEST_STATUS['Test Fix'],
	].includes(caseResult.dueStatus);

	const workflowDisabled = assignedUserId <= 0 || assignedUserId !== userId;

	const {modal} = useFormModal({
		onSave: (user: UserAccount) =>
			onAssignTo(caseResult, user.id).then(refetch),
	});

	const isCaseResultAssignedToMe =
		caseResult.user?.id?.toString() === Liferay.ThemeDisplay.getUserId();

	return (
		<>
			<CaseResultAssignModal modal={modal} />

			<ClayButton.Group className="mb-3 ml-3" spaced>
				<ClayButton
					disabled={isCaseResultAssignedToMe}
					displayType={
						isCaseResultAssignedToMe ? 'unstyled' : undefined
					}
					onClick={() => modal.open()}
				>
					{i18n.translate('assign')}
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => {
						if (isCaseResultAssignedToMe) {
							onRemoveAssign(caseResult).then(refetch);
						}
						else {
							onAssignToMe(caseResult).then(refetch);
						}
					}}
				>
					{i18n.translate(
						isCaseResultAssignedToMe
							? 'unassign-myself'
							: 'assign-to-me'
					)}
				</ClayButton>

				<ClayButton
					disabled={
						workflowDisabled ||
						caseResult.dueStatus !== TEST_STATUS.Untested
					}
					displayType={
						workflowDisabled ||
						caseResult.dueStatus !== TEST_STATUS.Untested
							? 'unstyled'
							: 'primary'
					}
				>
					{i18n.translate('start-test')}
				</ClayButton>

				<ClayButton
					disabled={
						workflowDisabled ||
						caseResult.dueStatus !== TEST_STATUS['In Progress']
					}
					displayType={
						workflowDisabled ||
						caseResult.dueStatus !== TEST_STATUS['In Progress']
							? 'unstyled'
							: undefined
					}
					onClick={() => navigate('complete-test')}
				>
					{i18n.translate('complete-test')}
				</ClayButton>

				<ClayButton
					disabled={workflowDisabled || isReopened}
					displayType={
						workflowDisabled || isReopened ? 'unstyled' : 'primary'
					}
					onClick={() => onAssignToMe(caseResult).then(refetch)}
				>
					{i18n.translate('reopen-test')}
				</ClayButton>

				<ClayButton
					disabled={
						workflowDisabled ||
						caseResult.dueStatus !== TEST_STATUS['In Progress']
					}
					displayType={
						workflowDisabled ||
						caseResult.dueStatus !== TEST_STATUS['In Progress']
							? 'unstyled'
							: 'secondary'
					}
					onClick={() => navigate('complete-test')}
				>
					{i18n.translate('edit')}
				</ClayButton>

				{caseResult.dueStatus === TEST_STATUS['In Progress'] && (
					<ClayButton
						displayType="secondary"
						onClick={() => onRemoveAssign(caseResult).then(refetch)}
					>
						{i18n.translate('reset-test')}
					</ClayButton>
				)}
			</ClayButton.Group>
		</>
	);
};

export default CaseResultHeaderActions;
