/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export const oneMarkerId = 'oneMarker';

export default function OneMarkerEnd() {
	return (
		<defs>
			<marker
				fill="none"
				id={oneMarkerId}
				markerHeight="8"
				markerWidth="20"
				orient="auto"
				refX="20"
				refY="9"
				viewBox="0 0 9 19"
			>
				<path
					d="M1.17969 9H241.18"
					stroke="#0B5FFF"
					strokeLinecap="round"
					strokeWidth="2"
				/>

				<rect fill="#0B5FFF" height="18" rx="1" width="2" x="5.17969" />

				<rect fill="#0B5FFF" height="18" rx="1" width="2" x="10.1797" />

				<path
					clipRule="evenodd"
					d="M241.838 17.7526C241.423 18.1162 240.791 18.0741 240.427 17.6585L233.427 9.65849C233.097 9.28146 233.097 8.71851 233.427 8.34148L240.427 0.341482C240.791 -0.074154 241.423 -0.116272 241.838 0.24741C242.254 0.611092 242.296 1.24286 241.932 1.65849L235.508 8.99999L241.932 16.3415C242.296 16.7571 242.254 17.3889 241.838 17.7526Z"
					fill="#0B5FFF"
					fillRule="evenodd"
				/>

				<rect fill="#0B5FFF" height="18" rx="1" width="2" x="230.18" />
			</marker>
		</defs>
	);
}
