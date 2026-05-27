/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import React from 'react';

const VisibleRenderer = ({value}: {value: boolean}) => {
	if (value) {
		return null;
	}

	return (
		<span className="align-items-center d-flex">
			<ClayLabel displayType="info" inverse>
				{Liferay.Language.get('not-visible')}
			</ClayLabel>
		</span>
	);
};

export default VisibleRenderer;
