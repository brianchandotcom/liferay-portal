/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';

const markerEndId = 'arrowclosed';

export default function MarkerEndDefinition({color}) {
	return (
		<defs>
			<marker
				className="react-flow__arrowhead"
				id={markerEndId}
				markerHeight="8"
				markerWidth="20"
				orient="auto"
				refX="0"
				refY="0"
				viewBox="-5 -5 10 10"
			>
				<polyline
					fill={color}
					points="-4,-3 0,0 -4,3 -4,-3"
					stroke={color}
					strokeLinecap="round"
					strokeLinejoin="round"
					strokeWidth="1"
				/>
			</marker>
		</defs>
	);
}

export {markerEndId};
