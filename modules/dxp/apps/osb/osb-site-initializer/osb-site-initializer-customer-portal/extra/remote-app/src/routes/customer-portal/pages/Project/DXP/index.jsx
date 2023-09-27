/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
