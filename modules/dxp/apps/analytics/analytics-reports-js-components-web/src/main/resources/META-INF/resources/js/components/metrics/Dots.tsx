/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export type DotProps = {
	cx?: number;
	cy?: number;
	displayOutsideOfRecharts?: boolean;
	size?: number;
	stroke: string;
	strokeOpacity?: string;
	value?: number | null;
};

function DotWrapper({
	children,
	size,
}: {size: number} & React.HTMLAttributes<HTMLElement>) {
	return (
		<svg height={size} width={size}>
			{children}
		</svg>
	);
}

export function CircleDot({
	cx,
	cy,
	displayOutsideOfRecharts = false,
	stroke,
	strokeOpacity,
}: DotProps) {
	const Circle = ({size}: {size: number}) => {
		const halfSize = size / 2;

		return (
			<circle
				cx={cx || halfSize}
				cy={cy || halfSize}
				fill={stroke}
				fillOpacity={strokeOpacity}
				r={halfSize}
				strokeOpacity={strokeOpacity}
			/>
		);
	};

	if (displayOutsideOfRecharts) {
		return (
			<DotWrapper size={7}>
				<Circle size={7} />
			</DotWrapper>
		);
	}

	return <Circle size={7} />;
}

export function SquareDot({
	cx,
	cy,
	displayOutsideOfRecharts = false,
	stroke,
	strokeOpacity,
}: DotProps) {
	const Rect = ({size}: {size: number}) => {
		const halfSize = size / 2;

		return (
			<rect
				fill={stroke}
				fillOpacity={strokeOpacity}
				height={size}
				strokeOpacity={strokeOpacity}
				width={size}
				x={cx ? cx - halfSize : 0}
				y={cy ? cy - halfSize : 0}
			/>
		);
	};

	if (displayOutsideOfRecharts) {
		return (
			<DotWrapper size={7}>
				<Rect size={7} />
			</DotWrapper>
		);
	}

	return <Rect size={6} />;
}

export function DiamondDot({
	cx = 0,
	cy = 0,
	displayOutsideOfRecharts = false,
	stroke,
	strokeOpacity,
}: DotProps) {
	const Diamond = ({size}: {size: number}) => {
		const halfSize = size / 2;

		return (
			<svg
				fill={stroke}
				fillOpacity={strokeOpacity}
				height={size}
				strokeOpacity={strokeOpacity}
				viewBox={`0 0 ${size} ${size}`}
				width={size}
				x={cx ? cx - halfSize : 0}
				y={cy ? cy - halfSize : 0}
			>
				<polygon
					points={`${halfSize},0 ${size},${halfSize} ${halfSize},${size} 0,${halfSize}`}
				/>
			</svg>
		);
	};

	if (displayOutsideOfRecharts) {
		return (
			<DotWrapper size={8}>
				<Diamond size={8} />
			</DotWrapper>
		);
	}

	return <Diamond size={8} />;
}

export function PublishedVersionDot({
	cx,
	cy,
	displayOutsideOfRecharts = false,
	stroke,
	strokeOpacity,
	value,
}: DotProps) {
	const PublishedVersionCircle = ({size}: {size: number}) => {
		const halfSize = size / 2;

		return (
			<circle
				cx={cx || size}
				cy={cy || size}
				fill="white"
				fillOpacity={strokeOpacity}
				r={displayOutsideOfRecharts ? halfSize - 1 : halfSize}
				stroke={stroke}
				strokeOpacity={strokeOpacity}
				strokeWidth={
					displayOutsideOfRecharts ? halfSize - 2 : halfSize - 1
				}
			/>
		);
	};

	if (value === null) {
		return null;
	}

	if (displayOutsideOfRecharts) {
		return (
			<DotWrapper size={14}>
				<PublishedVersionCircle size={8} />
			</DotWrapper>
		);
	}

	return <PublishedVersionCircle size={6} />;
}
