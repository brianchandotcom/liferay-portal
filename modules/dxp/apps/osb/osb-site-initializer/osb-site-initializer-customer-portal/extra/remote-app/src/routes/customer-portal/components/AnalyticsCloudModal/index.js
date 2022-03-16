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

const AnalyticsCloudModal = ({
	observer,
	onClose,
	project,
	subscriptionGroupId,
}) => {
	const [currentProcess, setCurrentProcess] = useState(
		ANALYTICS_STEPS_TYPES.setupForm
	);

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
					subscriptionGroupId={subscriptionGroupId}
				/>
			),
		}),
		[onClose, project, subscriptionGroupId]
	);

	return (
		<ClayModal center observer={observer}>
			{currentModalForm[currentProcess]}
		</ClayModal>
	);
};
export default AnalyticsCloudModal;
