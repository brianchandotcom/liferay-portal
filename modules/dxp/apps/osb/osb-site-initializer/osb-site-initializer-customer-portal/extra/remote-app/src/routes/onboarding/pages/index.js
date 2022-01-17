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

import Invites from '../../../common/components/onboarding/Invites';
import SetupDXPCloud from '../../../common/components/onboarding/SetupDXPCloud';
import {PARAMS_KEYS} from '../../../common/services/liferay/search-params';
import {getLiferaySiteName} from '../../../common/services/liferay/utils';
import {API_BASE_URL} from '../../../common/utils';
import {useOnboarding} from '../context';
import {actionTypes} from '../context/reducer';
import {steps} from '../utils/constants';
import SuccessDXPCloud from './SuccessDXPCloud';
import Welcome from './Welcome';

const Pages = () => {
	const [{project, step, subscriptionGroups}, dispatch] = useOnboarding();

	const invitesPageHandle = () => {
		const hasSubscriptionsDXPCloud = !!subscriptionGroups?.length;

		if (hasSubscriptionsDXPCloud) {
			dispatch({
				payload: steps.dxpCloud,
				type: actionTypes.CHANGE_STEP,
			});
		} else {
			window.location.href = `${API_BASE_URL}/${getLiferaySiteName()}/overview?${
				PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
			}=${project.accountKey}`;
		}
	};

	const StepsLayout = {
		[steps.invites]: {
			Component: (
				<Invites handlePage={invitesPageHandle} project={project} />
			),
		},
		[steps.dxpCloud]: {
			Component: (
				<SetupDXPCloud
					handlePage={() =>
						dispatch({
							payload: steps.successDxpCloud,
							type: actionTypes.CHANGE_STEP,
						})
					}
					project={project}
				/>
			),
		},
		[steps.successDxpCloud]: {
			Component: <SuccessDXPCloud project={project} />,
		},
		[steps.welcome]: {
			Component: <Welcome />,
			Skeleton: <Welcome.Skeleton />,
		},
	};

	if (project) {
		return StepsLayout[step].Component;
	}

	return StepsLayout[steps.welcome].Skeleton;
};

export default Pages;
