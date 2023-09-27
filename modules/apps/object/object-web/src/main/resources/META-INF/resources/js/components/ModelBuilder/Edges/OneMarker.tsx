/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export const oneMarkerId = 'oneMarker';

export default function OneMarker() {
	return (
		<defs>
			<marker
				id={oneMarkerId}
				markerHeight="10"
				markerWidth="22"
				orient="auto"
				refX="18"
				refY="9"
				viewBox="0 0 9 19"
			>
				<rect height="18" rx="1" width="2" x="5.17969" />

				<rect height="18" rx="1" width="2" x="10.1797" />
			</marker>
		</defs>
	);
}
