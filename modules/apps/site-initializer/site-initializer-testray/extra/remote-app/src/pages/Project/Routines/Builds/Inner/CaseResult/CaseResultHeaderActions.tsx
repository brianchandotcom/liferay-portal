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

import {
	TestrayCaseResult,
	UserAccount,
} from '../../../../../../graphql/queries';
import useAssignCaseResult from '../../../../../../hooks/useAssignCaseResult';
import useFormModal from '../../../../../../hooks/useFormModal';
import i18n from '../../../../../../i18n';
import {Liferay} from '../../../../../../services/liferay';
import CaseResultAssignModal from './CaseResultAssignModal';

const CaseResultHeaderActions: React.FC<{
	caseResult: TestrayCaseResult;
	refetch: () => void;
}> = ({caseResult, refetch}) => {
	const {onAssignTo, onAssignToMe, onRemoveAssign} = useAssignCaseResult();

	const {modal} = useFormModal({
		onSave: (user: UserAccount) =>
			onAssignTo(caseResult, user.id).then(refetch),
	});

	return (
		<>
			<CaseResultAssignModal modal={modal} />

			<ClayButton.Group className="mb-3 ml-3" spaced>
				<ClayButton onClick={() => modal.open()}>
					{i18n.translate('assign')}
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => onAssignToMe(caseResult).then(refetch)}
				>
					{i18n.translate('assign-to-me')}
				</ClayButton>

				<ClayButton disabled displayType="unstyled">
					{i18n.translate('start-test')}
				</ClayButton>

				<ClayButton disabled displayType="unstyled">
					{i18n.translate('complete-test')}
				</ClayButton>

				<ClayButton disabled displayType="unstyled">
					{i18n.translate('reopen-test')}
				</ClayButton>

				<ClayButton displayType="secondary">
					{i18n.translate('edit')}
				</ClayButton>

				{caseResult.user?.id?.toString() ===
					Liferay.ThemeDisplay.getUserId() && (
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
