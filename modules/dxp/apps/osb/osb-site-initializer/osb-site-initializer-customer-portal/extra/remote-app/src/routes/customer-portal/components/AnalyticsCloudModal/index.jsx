/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	const currentModalForm = useMemo(
		() => ({
			[ANALYTICS_STEPS_TYPES.confirmationForm]: (
				<ConfirmationMessageModal handlePage={onClose} />
			),
			[ANALYTICS_STEPS_TYPES.setupForm]: (
				<SetupAnalyticsCloudForm
					client={client}
					handlePage={(isSuccess) => {
						if (isSuccess) {
							return setCurrentProcess(
								ANALYTICS_STEPS_TYPES.confirmationForm
							);
						}

						onClose();
					}}
					leftButton={i18n.translate('cancel')}
					project={project}
					setFormAlreadySubmitted={setFormAlreadySubmitted}
					subscriptionGroupId={subscriptionGroupId}
				/>
			),
		}),
		[client, onClose, project, subscriptionGroupId]
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
