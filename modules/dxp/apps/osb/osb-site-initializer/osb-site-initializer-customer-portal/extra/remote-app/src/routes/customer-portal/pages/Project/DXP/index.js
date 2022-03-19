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
import ActivationKeysTable from '../../../containers/ActivationKeysTable';
import DeveloperKeysLayouts from '../../../layouts/DeveloperKeysLayout';
const DXP = ({project, sessionId}) => {
	return (
		<div className="mr-4">
			<ActivationKeysTable
				productName="DXP"
				project={project}
				sessionId={sessionId}
			/>

			<DeveloperKeysLayouts>
				<DeveloperKeysLayouts.Inputs
					accountKey={project.accountKey}
					downloadTextHelper="Select the Liferay DXP version for which you want to download a developer key."
					dxpVersion={project.dxpVersion}
					projectName={project.name}
					sessionId={sessionId}
				></DeveloperKeysLayouts.Inputs>
			</DeveloperKeysLayouts>
		</div>
	);
};

export default DXP;
