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
import {useModal} from '@clayui/modal';
import {useAppPropertiesContext} from '../../../../../common/contexts/AppPropertiesContext';
import {getAccountSubscriptionGroups} from '../../../../../common/services/liferay/graphql/queries';
import {useCustomerPortal} from '../../../context';
import {actionTypes} from '../../../context/reducer';
import {STATUS_TAG_TYPE_NAMES} from '../../../utils/constants';
import ActivationStatusLayout from '../Layout';
import SetupLiferayExperienceCloudModal from './components/SetupLXCModal';
import useActivationStatusDate from './hooks/useActivationStatusDate';
import useGetActivationStatusCardLayout from './hooks/useGetActivationStatusCardLayout';

const ActivationStatusLiferayExperienceCloud = ({
	lxcEnvironment,
	project,
	subscriptionGroupLxcEnvironment,
	userAccount,
}) => {
	const {client, liferayWebDAV} = useAppPropertiesContext();

	const {observer, onClose} = useModal({
		onClose: () => setIsVisibleSetupLxcModal(false),
	});

	const [, dispatch] = useCustomerPortal();

	const {activationStatusDate} = useActivationStatusDate(project);
	const {
		activationStatus,
		isVisibleSetupLxcModal,
		setIsVisibleSetupLxcModal,
		setStatusActivation,
	} = useGetActivationStatusCardLayout(
		lxcEnvironment,
		project,
		userAccount,
		subscriptionGroupLxcEnvironment
	);

	const onCloseSetupModal = async (isSuccess) => {
		onClose();

		if (isSuccess) {
			const getSubscriptionGroups = async (accountKey) => {
				const {data: dataSubscriptionGroups} = await client.query({
					query: getAccountSubscriptionGroups,
					variables: {
						filter: `accountKey eq '${accountKey}'`,
					},
				});
				if (dataSubscriptionGroups) {
					const items =
						dataSubscriptionGroups?.c?.accountSubscriptionGroups
							?.items;
					dispatch({
						payload: items,
						type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
					});

					setStatusActivation(STATUS_TAG_TYPE_NAMES.inProgress);
				}
			};
			getSubscriptionGroups(project.accountKey);
		}
	};

	return (
		<div>
			{isVisibleSetupLxcModal && (
				<SetupLiferayExperienceCloudModal
					observer={observer}
					onClose={onCloseSetupModal}
					project={project}
					setIsVisibleSetupLxcModal={setIsVisibleSetupLxcModal}
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
