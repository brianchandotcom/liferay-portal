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
import i18n from '../../../../../common/I18n';
import ActivationKeysTable from '../../../containers/ActivationKeysTable';
import {useCustomerPortal} from '../../../context';
import DeveloperKeysLayouts from '../../../layouts/DeveloperKeysLayout';
import {LIST_TYPES} from '../../../utils/constants';

const DXP = () => {
	const [{project, sessionId}] = useCustomerPortal();

	return (
		<div className="mr-4">
			<ActivationKeysTable
				initialFilter="(startswith(productName,'DXP') or startswith(productName,'Digital'))"
				productName="DXP"
				project={project}
				sessionId={sessionId}
			/>

			<DeveloperKeysLayouts>
				<DeveloperKeysLayouts.Inputs
					accountKey={project.accountKey}
					downloadTextHelper={i18n.translate(
						'select-the-liferay-dxp-version-for-your-developer-key-to-download'
					)}
					dxpVersion={project.dxpVersion}
					listType={LIST_TYPES.dxpVersion}
					productName="DXP"
					projectName={project.name}
					sessionId={sessionId}
				></DeveloperKeysLayouts.Inputs>
			</DeveloperKeysLayouts>
		</div>
	);
};

export default DXP;
