/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const markerEndId = 'arrowclosed';

export default function MarkerEndDefinition() {
	return (
		<defs>
			<marker
				className="react-flow__arrowhead"
				id={markerEndId}
				markerHeight="30"
				markerWidth="25"
				orient="auto"
				refX="0"
				refY="0"
				viewBox="-10 -10 20 20"
			>
				<polyline
					fill="#6b6c7e"
					points="-5,-4 0,0 -5,4 -5,-4"
					stroke="#6b6c7e"
					strokeLinecap="round"
					strokeLinejoin="round"
					strokeWidth="1"
				/>
			</marker>
		</defs>
	);
}

export {markerEndId};
