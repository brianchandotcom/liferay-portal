/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
