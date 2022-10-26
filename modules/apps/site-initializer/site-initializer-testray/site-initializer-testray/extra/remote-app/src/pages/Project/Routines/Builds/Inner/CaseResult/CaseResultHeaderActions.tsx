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

import AssignModal from '../../../../../../components/AssignModal';
import useFormModal from '../../../../../../hooks/useFormModal';
import i18n from '../../../../../../i18n';
import {Liferay} from '../../../../../../services/liferay';
import {
	TestrayCaseResult,
	UserAccount,
	testrayCaseResultImpl,
} from '../../../../../../services/rest';
import {CaseResultStatuses} from '../../../../../../util/statuses';

const userId = Number(Liferay.ThemeDisplay.getUserId());

const CaseResultHeaderActions: React.FC<{
	caseResult: TestrayCaseResult;
	mutateCaseResult: KeyedMutator<any>;
}> = ({caseResult, mutateCaseResult}) => {
	const {modal} = useFormModal({
		onSave: (user: UserAccount) =>
			testrayCaseResultImpl
				.assignTo(caseResult, user.id)
				.then(mutateCaseResult),
	});

	const navigate = useNavigate();

	const assignedUserId = caseResult.user?.id || 0;
	const isCaseResultAssignedToMe = caseResult.user?.id === userId;

	const isReopened = ![
		CaseResultStatuses.BLOCKED,
		CaseResultStatuses.FAILED,
		CaseResultStatuses.PASSED,
		CaseResultStatuses.TEST_FIX,
	].includes(caseResult.dueStatus.key as CaseResultStatuses);

	const workflowDisabled = assignedUserId <= 0 || assignedUserId !== userId;

	const buttonValidations = {
		completeTest:
			workflowDisabled ||
			caseResult.dueStatus.key !== CaseResultStatuses.IN_PROGRESS,
		editValidation: assignedUserId > 0 && assignedUserId !== userId,
		reopenTest: workflowDisabled || isReopened,
		startTest:
			workflowDisabled ||
			caseResult.dueStatus.key !== CaseResultStatuses.UNTESTED,
	};

	return (
		<>
			<AssignModal modal={modal} />

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
					onClick={() =>
						(isCaseResultAssignedToMe
							? testrayCaseResultImpl.removeAssign(caseResult)
							: testrayCaseResultImpl.assignToMe(caseResult)
						).then(mutateCaseResult)
					}
				>
					{i18n.translate(
						isCaseResultAssignedToMe
							? 'unassign-myself'
							: 'assign-to-me'
					)}
				</ClayButton>

				<ClayButton
					disabled={buttonValidations.startTest}
					displayType={
						buttonValidations.startTest ? 'unstyled' : 'primary'
					}
				>
					{i18n.translate('start-test')}
				</ClayButton>

				<ClayButton
					disabled={buttonValidations.completeTest}
					displayType={
						buttonValidations.completeTest ? 'unstyled' : undefined
					}
					onClick={() => navigate(`edit/${caseResult.dueStatus.key}`)}
				>
					{i18n.translate('complete-test')}
				</ClayButton>

				<ClayButton
					disabled={buttonValidations.reopenTest}
					displayType={
						buttonValidations.reopenTest ? 'unstyled' : 'primary'
					}
					onClick={() =>
						testrayCaseResultImpl
							.assignToMe(caseResult)
							.then(mutateCaseResult)
					}
				>
					{i18n.translate('reopen-test')}
				</ClayButton>

				<ClayButton
					disabled={buttonValidations.editValidation}
					displayType={
						buttonValidations.editValidation
							? 'unstyled'
							: 'secondary'
					}
					onClick={() => navigate(`edit/${caseResult.dueStatus.key}`)}
				>
					{i18n.translate('edit')}
				</ClayButton>

				{caseResult.dueStatus.key ===
					CaseResultStatuses.IN_PROGRESS && (
					<ClayButton
						displayType="secondary"
						onClick={() =>
							testrayCaseResultImpl
								.removeAssign(caseResult)
								.then(mutateCaseResult)
						}
					>
						{i18n.translate('reset-test')}
					</ClayButton>
				)}
			</ClayButton.Group>
		</>
	);
};

export default CaseResultHeaderActions;
