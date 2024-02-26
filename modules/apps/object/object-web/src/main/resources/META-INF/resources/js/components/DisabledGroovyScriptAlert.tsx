/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React from 'react';

export function DisabledGroovyScriptAlert() {
	return (
		<ClayAlert
			className="lfr-objects__side-panel-content-container"
			displayType="warning"
			title="Warning"
		>
			{Liferay.Language.get(
				'groovy-script-validations-are-deactivated-for-your-instance'
			)}
			&nbsp;
			<a>
				{Liferay.Language.get(
					'go-to-system-settings-security-tools-script-management-to-allow-administrators-to-execute-groovy-code'
				)}
			</a>
		</ClayAlert>
	);
}
