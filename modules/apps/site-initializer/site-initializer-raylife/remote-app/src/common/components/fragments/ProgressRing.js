import React from 'react';
import {calculateCircumference, calculateOffset} from '~/common/utils';

export const ProgressRing = ({
	className,
	diameter = 24,
	percent = 0,
	strokeColor = '#4C85FF',
	strokeWidth = 2,
}) => {
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
};
