/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React from 'react';

import './TranslationsContainer.scss';

export function TranslationsContainer() {
	return (
		<ClayAlert
			displayType="info"
			title={`${Liferay.Language.get('info')}:`}
		>
			{`${Liferay.Language.get(
				'enable-or-disable-translation-for-fields-individually'
			)} `}
		</ClayAlert>
	);
}
