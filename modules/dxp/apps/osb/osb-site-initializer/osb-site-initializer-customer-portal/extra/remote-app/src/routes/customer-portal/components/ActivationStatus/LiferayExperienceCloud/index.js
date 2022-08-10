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

import {useAppPropertiesContext} from '../../../../../common/contexts/AppPropertiesContext';
import ActivationStatusLayout from '../Layout';
import useActivationStatusDate from './hooks/useActivationStatusDate';
import useGetActivationStatusCardLayout from './hooks/useGetActivationStatusCardLayout';

const ActivationStatusLiferayExperienceCloud = ({
	lxcEnvironment,
	project,
	subscriptionGroupLxcEnvironment,
	userAccount,
}) => {
	const {liferayWebDAV} = useAppPropertiesContext();

	const {activationStatusDate} = useActivationStatusDate(project);
	const {activationStatus} = useGetActivationStatusCardLayout(
		lxcEnvironment,
		project,
		userAccount,
		subscriptionGroupLxcEnvironment
	);

	return (
		<ActivationStatusLayout
			activationStatus={activationStatus}
			activationStatusDate={activationStatusDate}
			iconPath={`${liferayWebDAV}/assets/navigation-menu/dxp_icon.svg`}
			project={project}
		/>
	);
};

export default ActivationStatusLiferayExperienceCloud;
