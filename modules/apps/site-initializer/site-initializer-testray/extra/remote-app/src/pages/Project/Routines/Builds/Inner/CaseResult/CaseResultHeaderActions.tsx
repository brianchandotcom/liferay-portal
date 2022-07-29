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

import useAssignCaseResult from '../../../../../../hooks/useAssignCaseResult';
import useFormModal from '../../../../../../hooks/useFormModal';
import i18n from '../../../../../../i18n';
import {Liferay} from '../../../../../../services/liferay';
import {TestrayCaseResult, UserAccount} from '../../../../../../services/rest';
import {TEST_STATUS} from '../../../../../../util/constants';
import CaseResultAssignModal from './CaseResultAssignModal';

const userId = Number(Liferay.ThemeDisplay.getUserId());

const CaseResultHeaderActions: React.FC<{
	caseResult: TestrayCaseResult;
	mutateCaseResult: KeyedMutator<any>;
}> = ({caseResult, mutateCaseResult}) => {
	const {
		onAssignToFetch,
		onAssignToMeFetch,
		onRemoveAssignFetch,
	} = useAssignCaseResult(mutateCaseResult);
	const {modal} = useFormModal({
		onSave: (user: UserAccount) => onAssignToFetch(caseResult, user.id),
	});

	const navigate = useNavigate();

	const assignedUserId = caseResult.user?.id || 0;
	const isCaseResultAssignedToMe = caseResult.user?.id === userId;
	const isReopened = ![
		TEST_STATUS.Blocked,
		TEST_STATUS.Failed,
		TEST_STATUS.Passed,
		TEST_STATUS['Test Fix'],
	].includes(caseResult.dueStatus);

	const workflowDisabled = assignedUserId <= 0 || assignedUserId !== userId;

	const buttonValidations = {
		completeTest:
			workflowDisabled ||
			caseResult.dueStatus !== TEST_STATUS['In Progress'],
		editValidation: assignedUserId > 0 && assignedUserId !== userId,
		reopenTest: workflowDisabled || isReopened,
		startTest:
			workflowDisabled || caseResult.dueStatus !== TEST_STATUS.Untested,
	};

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
						const assignFN = isCaseResultAssignedToMe
							? onRemoveAssignFetch
							: onAssignToMeFetch;

						assignFN(caseResult);
					}}
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
					onClick={() => navigate(`edit/${caseResult.dueStatus}`)}
				>
					{i18n.translate('complete-test')}
				</ClayButton>

				<ClayButton
					disabled={buttonValidations.reopenTest}
					displayType={
						buttonValidations.reopenTest ? 'unstyled' : 'primary'
					}
					onClick={() => onAssignToMeFetch(caseResult)}
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
					onClick={() => navigate(`edit/${caseResult.dueStatus}`)}
				>
					{i18n.translate('edit')}
				</ClayButton>

				{caseResult.dueStatus === TEST_STATUS['In Progress'] && (
					<ClayButton
						displayType="secondary"
						onClick={() => onRemoveAssignFetch(caseResult)}
					>
						{i18n.translate('reset-test')}
					</ClayButton>
				)}
			</ClayButton.Group>
		</>
	);
};

export default CaseResultHeaderActions;
