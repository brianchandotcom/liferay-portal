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

import InviteTeamMembersForm from '../../../common/containers/setup-forms/InviteTeamMembersForm';
import SetupDXPCloud from '../../../common/containers/setup-forms/SetupDXPCloudForm';
import {
	API_BASE_URL,
	SEARCH_PARAMS_KEYS,
} from '../../../common/utils/constants';
import getLiferaySiteName from '../../../common/utils/getLiferaySiteName';
import {useOnboarding} from '../context';
import {actionTypes} from '../context/reducer';
import {ONBOARDING_STEP_TYPES} from '../utils/constants';
import SuccessDXPCloud from './SuccessDXPCloud';
import Welcome from './Welcome';

const Pages = () => {
	const [
		{project, sessionId, step, subscriptionGroups},
		dispatch,
	] = useOnboarding();

	const invitesPageHandle = () => {
		const hasSubscriptionsDXPCloud = !!subscriptionGroups?.length;

		if (hasSubscriptionsDXPCloud) {
			dispatch({
				payload: ONBOARDING_STEP_TYPES.dxpCloud,
				type: actionTypes.CHANGE_STEP,
			});
		}
		else {
			window.location.href = `${API_BASE_URL}/${getLiferaySiteName()}/overview?${
				SEARCH_PARAMS_KEYS.accountKey
			}=${project.accountKey}`;
		}
	};

	const StepsLayout = {
		[ONBOARDING_STEP_TYPES.invites]: {
			Component: (
				<InviteTeamMembersForm
					handlePage={invitesPageHandle}
					leftButton="Skip for now"
					project={project}
					sessionId={sessionId}
				/>
			),
		},
		[ONBOARDING_STEP_TYPES.dxpCloud]: {
			Component: (
				<SetupDXPCloud
					handlePage={(isSuccess) => {
						if (isSuccess) {
							dispatch({
								payload: ONBOARDING_STEP_TYPES.successDxpCloud,
								type: actionTypes.CHANGE_STEP,
							});
						}
						else {
							window.location.href = `${API_BASE_URL}/${getLiferaySiteName()}/overview?${
								SEARCH_PARAMS_KEYS.accountKey
							}=${project.accountKey}`;
						}
					}}
					leftButton="Skip for now"
					project={project}
					subscriptionGroupId={
						!!subscriptionGroups?.length &&
						subscriptionGroups[0].accountSubscriptionGroupId
					}
				/>
			),
		},
		[ONBOARDING_STEP_TYPES.successDxpCloud]: {
			Component: <SuccessDXPCloud project={project} />,
		},
		[ONBOARDING_STEP_TYPES.welcome]: {
			Component: <Welcome />,
			Skeleton: <Welcome.Skeleton />,
		},
	};

	if (project && subscriptionGroups) {
		return StepsLayout[step].Component;
	}

	return StepsLayout[ONBOARDING_STEP_TYPES.welcome].Skeleton;
};

export default Pages;
