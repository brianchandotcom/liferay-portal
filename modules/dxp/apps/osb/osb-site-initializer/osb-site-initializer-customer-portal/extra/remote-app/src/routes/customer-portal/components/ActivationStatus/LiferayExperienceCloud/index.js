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
import {useState} from 'react';
import {useAppPropertiesContext} from '../../../../../common/contexts/AppPropertiesContext';
import {useGetAccountSubscriptionGroups} from '../../../../../common/services/liferay/graphql/account-subscription-groups/queries/useGetAccountSubscriptionGroups';
import {STATUS_TAG_TYPE_NAMES} from '../../../utils/constants';
import ActivationStatusLayout from '../Layout';
import SetupLiferayExperienceCloudModal from './components/SetupLXCModal';
import useActivationStatusDate from './hooks/useActivationStatusDate';
import useOnCloseSetupModal from './hooks/useOnCloseSetupModal';
import getActivationStatusCardLayout from './utils/getActivationStatusCardLayout';

const ActivationStatusLiferayExperienceCloud = ({
	lxcEnvironment,
	project,
	subscriptionGroupLxcEnvironment,
	userAccount,
}) => {
	const {liferayWebDAV} = useAppPropertiesContext();
	const [isVisibleSetupLxcModal, setIsVisibleSetupLxcModal] = useState(false);
	const [lxcStatusActivation, setStatusLxcActivation] = useState(
		subscriptionGroupLxcEnvironment?.activationStatus
	);

	const {activationStatusDate} = useActivationStatusDate(project);

	const currentActivationStatus = getActivationStatusCardLayout(
		lxcEnvironment,
		project,
		() => setIsVisibleSetupLxcModal(true),
		userAccount
	);

	const {data: dataSubscriptionGroups} = useGetAccountSubscriptionGroups({
		fetchPolicy: 'network-only',
		filter: `accountKey eq '${project.accountKey}' and hasActivation eq true`,
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

			<ActivationStatusLayout
				activationStatus={activationStatus}
				activationStatusDate={activationStatusDate}
				iconPath={`${liferayWebDAV}/assets/navigation-menu/dxp_icon.svg`}
				project={project}
			/>
		</div>
	);
};

export default ActivationStatusLiferayExperienceCloud;
