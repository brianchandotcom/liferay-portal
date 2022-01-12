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

import {useContext} from 'react';
import {AppContext} from '../context';
import {steps} from '../utils/constants';
import Invites from './Invites';
import SetupDXPCloud from './SetupDXPCloud';
import SuccessDXPCloud from './SuccessDXPCloud';
import Welcome from './Welcome';

const Pages = () => {
	const [{project, step}] = useContext(AppContext);

	const StepsLayout = {
		[steps.invites]: {
			Component: <Invites project={project} />,
		},
		[steps.dxpCloud]: {
			Component: <SetupDXPCloud project={project} />,
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
