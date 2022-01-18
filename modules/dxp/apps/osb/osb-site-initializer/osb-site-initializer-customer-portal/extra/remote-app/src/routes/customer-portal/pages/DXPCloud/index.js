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
import ActivationStatus from '../../components/ActivationStatus/index';
import DeveloperKeysLayouts from '../../components/DeveloperKeysLayout';

const DXPCloud = ({project, sessionId, subscriptionGroups, userAccount}) => {
	return (
		<div>
			<ActivationStatus
				project={project}
				subscriptionGroups={subscriptionGroups}
				userAccount={userAccount}
			/>

			<DeveloperKeysLayouts>
				<DeveloperKeysLayouts.Inputs
					accountKey={project.accountKey}
					dxpVersion={project.dxpVersion}
					productTitle="DXP Cloud"
					sessionId={sessionId}
				></DeveloperKeysLayouts.Inputs>
			</DeveloperKeysLayouts>
		</div>
	);
};

export default DXPCloud;
