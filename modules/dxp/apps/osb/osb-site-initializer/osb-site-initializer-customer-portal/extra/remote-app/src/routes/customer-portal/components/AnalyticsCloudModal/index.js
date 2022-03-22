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
import SetupAnalyticsCloud from '../../../../common/containers/setup-forms/SetupAnalyticsCloudForm';
import ConfirmationMessageModal from '../../../../common/containers/setup-forms/SetupAnalyticsCloudForm/ConfirmationMessageModal';
import {ANALYTICS_STEPS_TYPES} from '../../utils/constants';
import AlreadySubmittedFormModal from '../ActivationStatus/AlreadySubmittedModal';

const AnalyticsCloudModal = ({
	observer,
	onClose,
	project,
	subscriptionGroupId,
}) => {
	const [currentProcess, setCurrentProcess] = useState(
		ANALYTICS_STEPS_TYPES.setupForm
	);
	const [formAlreadySubmitted, setFormAlreadySubmitted] = useState(false);

	const handleChangeForm = () => {
		setCurrentProcess(ANALYTICS_STEPS_TYPES.confirmationForm);
	};

	const currentModalForm = useMemo(
		() => ({
			[ANALYTICS_STEPS_TYPES.confirmationForm]: (
				<ConfirmationMessageModal handlePage={onClose} />
			),
			[ANALYTICS_STEPS_TYPES.setupForm]: (
				<SetupAnalyticsCloud
					handlePage={handleChangeForm}
					leftButton="Cancel"
					onClose={onClose}
					project={project}
					setFormAlreadySubmitted={setFormAlreadySubmitted}
					subscriptionGroupId={subscriptionGroupId}
				/>
			),
		}),
		[onClose, project, setFormAlreadySubmitted, subscriptionGroupId]
	);

	const submittedModalTexts = {
		paragraph:
			'Return to the product activation page to view the current Activation Status',
		subtitle: `We'll need a few details to finish building your Analytics Cloud workspace(s).`,
		text:
			'Another user already submitted the Analytics Cloud activation request.',
		title: 'Set up Analytics Cloud',
	};

	return (
		<ClayModal center observer={observer}>
			{formAlreadySubmitted ? (
				<AlreadySubmittedFormModal
					setVisibleModal={onClose}
					submittedModalTexts={submittedModalTexts}
				/>
			) : (
				currentModalForm[currentProcess]
			)}
		</ClayModal>
	);
};
export default AnalyticsCloudModal;
