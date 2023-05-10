/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */
import ClayModal from '@clayui/modal';
import {useMemo, useState} from 'react';
import i18n from '../../../../../../../common/I18n';
import {LXC_STEPS_TYPES} from '../../../../../utils/constants/';
import AlreadySubmittedFormModal from '../../../AlreadySubmittedModal';
import ConfirmationMessageModal from '../ConfirmationMessageModal';
import SetupLiferayExperienceCloudForm from './components/SetupLXCForm';
import {submittedModalTexts} from './utils/submittedModalTexts';

const SetupLiferayExperienceCloudModal = ({
	handleOnLeftButtonClick,
	observer,
	onClose,
	project,
	subscriptionGroupLxcId,
}) => {
	const [currentProcess, setCurrentProcess] = useState(
		LXC_STEPS_TYPES.setupForm
	);
	const [formAlreadySubmitted, setFormAlreadySubmitted] = useState(false);

	const currentModalForm = useMemo(() => {
		const handleChangeForm = (isSuccess) => {
			if (isSuccess) {
				return setCurrentProcess(LXC_STEPS_TYPES.confirmationForm);
			}
			onClose();
		};

		return {
			[LXC_STEPS_TYPES.confirmationForm]: (
				<ConfirmationMessageModal onClose={onClose} />
			),
			[LXC_STEPS_TYPES.setupForm]: (
				<SetupLiferayExperienceCloudForm
					handleChangeForm={handleChangeForm}
					handleOnLeftButtonClick={handleOnLeftButtonClick}
					leftButton={i18n.translate('cancel')}
					project={project}
					setFormAlreadySubmitted={setFormAlreadySubmitted}
					subscriptionGroupLxcId={subscriptionGroupLxcId}
				/>
			),
		};
	}, [handleOnLeftButtonClick, onClose, project, subscriptionGroupLxcId]);

	return (
		<ClayModal center observer={observer}>
			{formAlreadySubmitted ? (
				<AlreadySubmittedFormModal
					onClose={onClose}
					submittedModalTexts={submittedModalTexts}
				/>
			) : (
				currentModalForm[currentProcess]
			)}
		</ClayModal>
	);
};

export default SetupLiferayExperienceCloudModal;
