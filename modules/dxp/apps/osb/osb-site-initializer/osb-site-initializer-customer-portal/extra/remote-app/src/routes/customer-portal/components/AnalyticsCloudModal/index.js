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
import i18n from '../../../../common/I18n';
import SetupAnalyticsCloudForm from '../../../../common/containers/setup-forms/SetupAnalyticsCloudForm';
import ConfirmationMessageModal from '../../../../common/containers/setup-forms/SetupAnalyticsCloudForm/ConfirmationMessageModal';
import {useAppPropertiesContext} from '../../../../common/contexts/AppPropertiesContext';
import {ANALYTICS_STEPS_TYPES} from '../../utils/constants';
import AlreadySubmittedFormModal from '../ActivationStatus/AlreadySubmittedModal';

const submittedModalTexts = {
	paragraph: i18n.translate(
		'return-to-the-product-activation-page-to-view-the-current-activation-status'
	),
	subtitle: i18n.translate(
		'we-ll-need-a-few-details-to-finish-building-your-analytics-cloud-workspace'
	),
	text: i18n.translate(
		'another-user-already-submitted-the-analytics-cloud-activation-request'
	),
	title: i18n.translate('set-up-analytics-cloud'),
};

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
	const {client} = useAppPropertiesContext();

	const handleChangeForm = (isSuccess) => {
		if (isSuccess) {
			return setCurrentProcess(ANALYTICS_STEPS_TYPES.confirmationForm);
		}
		onClose();
	};

	const currentModalForm = useMemo(
		() => ({
			[ANALYTICS_STEPS_TYPES.confirmationForm]: (
				<ConfirmationMessageModal handlePage={onClose} />
			),
			[ANALYTICS_STEPS_TYPES.setupForm]: (
				<SetupAnalyticsCloudForm
					client={client}
					handlePage={handleChangeForm}
					leftButton={i18n.translate('cancel')}
					project={project}
					setFormAlreadySubmitted={setFormAlreadySubmitted}
					subscriptionGroupId={subscriptionGroupId}
				/>
			),
		}),

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[onClose, project, subscriptionGroupId]
	);

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
export default AnalyticsCloudModal;
