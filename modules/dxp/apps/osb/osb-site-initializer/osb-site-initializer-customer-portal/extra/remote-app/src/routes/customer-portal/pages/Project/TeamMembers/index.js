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

import ClayModal, {useModal} from '@clayui/modal';
import {useEffect, useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import client from '../../../../../apolloClient';
import {Button} from '../../../../../common/components';
import InviteTeamMembersForm from '../../../../../common/containers/setup-forms/InviteTeamMembersForm';
import {getDXPCloudEnvironment} from '../../../../../common/services/liferay/graphql/queries';
import ManageProductUser from '../../../components/ManageProductUsers';
import {PRODUCT_TYPES} from '../../../utils/constants/';
import {STATUS_TAG_TYPE_NAMES} from '../../../utils/constants/statusTag';

const InvitesModal = ({observer, onClose, project}) => {
	return (
		<ClayModal center observer={observer}>
			<InviteTeamMembersForm
				handlePage={onClose}
				leftButton="Cancel"
				project={project}
			/>
		</ClayModal>
	);
};

const TeamMembers = () => {
	const [dxpCloudEnvironment, setDxpCloudEnvironment] = useState();
	const activationStatusAC = 'Active';
	const groupId = 'groupid';
	const {project, subscriptionGroups} = useOutletContext();
	const [visible, setVisible] = useState(false);
	const modalProps = useModal({
		onClose: () => setVisible(false),
	});
	const [activatedStatusDXPC, setActivatedStatusDXPC] = useState();
	const [activatedStatusAC, setActivatedStatusAC] = useState();
	const [activatedLinkDXPC, setActivatedLinkDXPC] = useState();
	const [activatedLinkAC, setActivatedLinkAC] = useState();

	useEffect(() => {
		const getOnboardingFormData = async () => {
			const {data} = await client.query({
				query: getDXPCloudEnvironment,
				variables: {
					filter: `accountKey eq '${project.accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const dxpProjectId =
					data.c?.dXPCloudEnvironments?.items[0]?.projectId;

				setDxpCloudEnvironment(dxpProjectId);
			}
		};
		getOnboardingFormData();

		const accountSubscriptionFromDXPC = subscriptionGroups.find(
			(subscriptionGroup) =>
				subscriptionGroup.name === PRODUCT_TYPES.dxpCloud
		);
		const activationStatusDXPC =
			accountSubscriptionFromDXPC?.activationStatus;

		if (activationStatusDXPC === STATUS_TAG_TYPE_NAMES.active) {
			setActivatedStatusDXPC(activationStatusDXPC);
		}
		if (activationStatusAC === STATUS_TAG_TYPE_NAMES.active) {
			setActivatedStatusAC(activationStatusAC);
		}

		setActivatedLinkDXPC(
			`https://console.liferay.cloud/projects/${dxpCloudEnvironment}/overview`
		);
		setActivatedLinkAC(
			`https://analytics.liferay.com/workspace/${groupId}/sites`
		);
	}, [subscriptionGroups, project, dxpCloudEnvironment]);

	return (
		<>
			{visible && <InvitesModal {...modalProps} project={project} />}
			<div className="cp-team-members-overview mr-8">
				<div className="align-items-center d-flex justify-content-between">
					<div>
						<h1 className="m-0">Team Members</h1>

						<p className="mb-0 mt-1 text-neutral-7 text-paragraph-sm">
							Team members have access to this project in Customer
							Portal.
						</p>
					</div>

					<Button
						className="btn-outline-primary invite-button"
						onClick={() => setVisible(true)}
						prependIcon="plus"
					>
						Invite
					</Button>
				</div>
			</div>
			{(activatedStatusDXPC || activationStatusAC) && (
				<ManageProductUser
					activationStatusAC={activatedStatusAC}
					activationStatusDXPC={activatedStatusDXPC}
					refLinkAC={activatedLinkAC}
					refLinkDXPC={activatedLinkDXPC}
				/>
			)}
		</>
	);
};

export default TeamMembers;
