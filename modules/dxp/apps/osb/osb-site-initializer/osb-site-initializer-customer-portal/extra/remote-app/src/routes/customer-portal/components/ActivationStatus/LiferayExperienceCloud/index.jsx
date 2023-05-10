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
import ClayAlert from '@clayui/alert';
import {useModal} from '@clayui/modal';
import {useState} from 'react';

import {DXPIcon} from '../../../../../common/icons';
import {useGetAccountSubscriptionGroups} from '../../../../../common/services/liferay/graphql/account-subscription-groups/queries/useGetAccountSubscriptionGroups';
import {ALERT_UPDATE_LIFERAY_EXPERIENCE_CLOUD_STATUS} from '../../../containers/ActivationKeysTable/utils/constants/alertUpdateLiferayExperienceCloud';
import {
	AUTO_CLOSE_ALERT_TIME,
	STATUS_TAG_TYPE_NAMES,
} from '../../../utils/constants';
import ActivationStatusLayout from '../Layout';
import LiferayExperienceCloudModal from './LiferayExperienceCloudModal';
import SetupLiferayExperienceCloudModal from './components/SetupLXCModal';
import useActivationStatusDate from './hooks/useActivationStatusDate';
import useOnCloseSetupModal from './hooks/useOnCloseSetupModal';
import getActivationStatusCardLayout from './utils/getActivationStatusCardLayout';

const ActivationStatusLiferayExperienceCloud = ({
	dispatch,
	lxcEnvironment,
	project,
	subscriptionGroupLxcEnvironment,
	subscriptionGroups,
	userAccount,
}) => {
	const [isVisibleSetupLxcModal, setIsVisibleSetupLxcModal] = useState(false);
	const [hasFinishedUpdate, setHasFinishedUpdate] = useState(false);
	const [visible, setVisible] = useState(false);
	const [lxcStatusActivation, setStatusLxcActivation] = useState(
		subscriptionGroupLxcEnvironment?.activationStatus
	);

	const {activationStatusDate} = useActivationStatusDate(project);

	const currentActivationStatus = getActivationStatusCardLayout(
		lxcEnvironment,
		project,
		() => setIsVisibleSetupLxcModal(true),
		() => setVisible(true),
		userAccount
	);

	const {
		observer: observerStatusModal,
		onClose: onCloseStatusModal,
	} = useModal({
		onClose: () => setVisible(false),
	});

	const {data: dataSubscriptionGroups} = useGetAccountSubscriptionGroups({
		fetchPolicy: 'network-only',
		filter: `accountKey eq '${project.accountKey}'`,
	});

	const {handleSubmitLxcEnvironment, observer} = useOnCloseSetupModal(
		dataSubscriptionGroups,
		() => setIsVisibleSetupLxcModal(false),
		setStatusLxcActivation
	);

	const activationStatus =
		currentActivationStatus[
			lxcStatusActivation || STATUS_TAG_TYPE_NAMES.notActivated
		];

	return (
		<div>
			{isVisibleSetupLxcModal && (
				<SetupLiferayExperienceCloudModal
					handleOnLeftButtonClick={() =>
						setIsVisibleSetupLxcModal(false)
					}
					observer={observer}
					onClose={handleSubmitLxcEnvironment}
					project={project}
					subscriptionGroupLxcId={
						subscriptionGroupLxcEnvironment.accountSubscriptionGroupId
					}
				/>
			)}

			{visible && (
				<LiferayExperienceCloudModal
					accountKey={project.accountKey}
					dispatch={dispatch}
					handleFinishUpdate={() => setHasFinishedUpdate(true)}
					handleStatusLxcActivation={() =>
						setStatusLxcActivation(STATUS_TAG_TYPE_NAMES.active)
					}
					lxcEnvironment={lxcEnvironment}
					observer={observerStatusModal}
					onClose={onCloseStatusModal}
					project={project}
					subscriptionGroupLxcEnvironment={
						subscriptionGroupLxcEnvironment
					}
					subscriptionGroups={subscriptionGroups}
				/>
			)}

			{hasFinishedUpdate && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={AUTO_CLOSE_ALERT_TIME.success}
						displayType="success"
						onClose={() => setHasFinishedUpdate(false)}
					>
						{ALERT_UPDATE_LIFERAY_EXPERIENCE_CLOUD_STATUS.success}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}

			<ActivationStatusLayout
				activationStatus={activationStatus}
				activationStatusDate={activationStatusDate}
				iconPath={DXPIcon}
				project={project}
				subscriptionGroupActivationStatus={lxcStatusActivation}
			/>
		</div>
	);
};

export default ActivationStatusLiferayExperienceCloud;
