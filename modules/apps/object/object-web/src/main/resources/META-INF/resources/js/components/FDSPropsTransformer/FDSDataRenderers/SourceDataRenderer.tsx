/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

interface SourceDataRenderer {
	value: boolean;
}

export default function SourceDataRenderer({value}: SourceDataRenderer) {
	return (
		<strong
			className={classNames(
				value ? 'label-info' : 'label-warning',
				'label'
			)}
		>
			{value
				? Liferay.Language.get('system')
				: Liferay.Language.get('custom')}
		</strong>
	);
}
