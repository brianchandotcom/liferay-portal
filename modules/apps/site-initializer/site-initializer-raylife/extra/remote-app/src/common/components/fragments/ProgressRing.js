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
import {calculateCircumference, calculateOffset} from '../../utils';

export function ProgressRing({
	className,
	diameter = 24,
	percent = 0,
	strokeColor = '#4C85FF',
	strokeWidth = 2,
}) {
	const radius = diameter / 2;
	const normalizedRadius = radius - strokeWidth * 2;
	const center = (radius - strokeWidth) / 2;

	return (
		<svg className={className} height={diameter} width={diameter}>
			<circle
				className="progress"
				cx={center}
				cy={center}
				fill="transparent"
				r={normalizedRadius}
				stroke={strokeColor}
				strokeLinecap="round"
				strokeWidth={strokeWidth}
				style={{
					strokeDasharray: `${calculateCircumference(
						normalizedRadius
					)} ${calculateCircumference(normalizedRadius)}`,
					strokeDashoffset: calculateOffset(
						percent,
						calculateCircumference(normalizedRadius)
					),
				}}
			/>
		</svg>
	);
}
