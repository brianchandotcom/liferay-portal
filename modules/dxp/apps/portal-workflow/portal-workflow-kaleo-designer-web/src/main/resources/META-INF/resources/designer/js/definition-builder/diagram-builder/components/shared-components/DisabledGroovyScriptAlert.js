/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React from 'react';

export function DisabledGroovyScriptAlert({
	scriptManagementConfigurationPortletURL,
}) {
	return (
		<ClayAlert displayType="warning" title="Warning">
			{Liferay.Language.get('scripts-are-deactivated-in-your-instance')}
			&nbsp;
			<a href={scriptManagementConfigurationPortletURL} target="_blank">
				{Liferay.Language.get(
					'go-to-system-settings-security-tools-script-management-to-enable-script-execution-for-administrators'
				)}
			</a>
		</ClayAlert>
	);
}
