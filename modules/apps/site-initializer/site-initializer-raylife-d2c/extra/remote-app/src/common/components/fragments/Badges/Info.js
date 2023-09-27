/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

export function InfoBadge({children, ...props}) {
	return (
		<div
			{...props}
			className="align-items-center badge badge-info d-flex rounded"
		>
			<ClayIcon
				className="c-ml-4 c-mr-2 flex-shrink-0"
				symbol="info-circle"
			/>

			{children}
		</div>
	);
}
