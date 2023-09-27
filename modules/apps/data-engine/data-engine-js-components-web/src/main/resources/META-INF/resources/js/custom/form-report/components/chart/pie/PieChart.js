/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import {
	Cell,
	Pie,
	PieChart as RechartsPieChart,
	ResponsiveContainer,
	Sector,
	Tooltip,
} from 'recharts';

import colors, {NAMED_COLORS} from '../../../utils/colors';
import {roundPercentage} from '../../../utils/data';
import Legend from '../Legend';
import TooltipContent from '../TooltipContent';

const {black} = NAMED_COLORS;
const RADIAN = Math.PI / 180;

export default function PieChart({data, height, totalEntries, width}) {
	const [activeIndex, setActiveIndex] = useState(null);
	const [isAnimationActive, setAnimationActive] = useState(true);

	const handleOnMouseOut = () => {
		setActiveIndex(null);
	};

	const handleOnMouseOver = (index) => {
		setActiveIndex(index);
	};

	const ActiveShape = ({
		cx,
		cy,
		endAngle,
		innerRadius,
		outerRadius,
		startAngle,
	}) => {
		setAnimationActive(false);

		return (
			<g>
				<Sector
					cx={cx}
					cy={cy}
					endAngle={endAngle}
					fill={colors(activeIndex)}
					innerRadius={innerRadius}
					onMouseOut={handleOnMouseOut}
					outerRadius={outerRadius + 5}
					startAngle={startAngle}
				/>
			</g>
		);
	};

	const Label = ({cx, cy, innerRadius, midAngle, outerRadius, percent}) => {
		const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
		const x = cx + radius * Math.cos(-midAngle * RADIAN);
		const y = cy + radius * Math.sin(-midAngle * RADIAN);

		return (
			<text
				dominantBaseline="central"
				fill={black}
				textAnchor="middle"
				x={x}
				y={y}
			>
				{roundPercentage(percent)}
			</text>
		);
	};

	return (
		<div
			className="lfr-de-recharts pie-chart"
			style={{
				height: '100%',
				width: '100%',
			}}
		>
			<ResponsiveContainer
				height={height || '100%'}
				width={width || '70%'}
			>
				<RechartsPieChart>
					<Pie
						activeIndex={activeIndex}
						activeShape={ActiveShape}
						cx="50%"
						cy="50%"
						data={data}
						dataKey="count"
						endAngle={-270}
						innerRadius="60%"
						isAnimationActive={isAnimationActive}
						label={Label}
						labelLine={false}
						nameKey="label"
						onMouseOver={(_, index) => handleOnMouseOver(index)}
						outerRadius="100%"
						paddingAngle={0}
						startAngle={90}
					>
						{data.map((_, index) => (
							<Cell
								fill={colors(index)}
								fillOpacity={
									activeIndex !== null &&
									activeIndex !== index
										? 0.5
										: 1
								}
								key={index}
							/>
						))}
					</Pie>

					<Tooltip
						content={
							<TooltipContent
								showBullet={true}
								showHeader={false}
								totalEntries={totalEntries}
							/>
						}
					/>
				</RechartsPieChart>
			</ResponsiveContainer>

			<Legend
				activeIndex={activeIndex}
				data={data}
				onMouseOut={handleOnMouseOut}
				onMouseOver={handleOnMouseOver}
			/>
		</div>
	);
}
