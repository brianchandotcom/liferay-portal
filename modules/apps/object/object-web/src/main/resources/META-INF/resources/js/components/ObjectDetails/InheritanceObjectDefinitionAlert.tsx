/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {LearnMessage} from 'frontend-js-components-web';
import React from 'react';

export function InheritanceObjectDefinitionAlert() {
	return (
		<ClayAlert
			displayType="info"
			title={`${Liferay.Language.get('info')}:`}
		>
			{Liferay.Language.get(
				'inheritance-is-enabled-for-at-least-one-relationship-in-this-definition'
			)}
			&nbsp;
			<LearnMessage
				className="alert-link"
				resource="object-web"
				resourceKey="inheritance-relationships"
			/>
		</ClayAlert>
	);
}
