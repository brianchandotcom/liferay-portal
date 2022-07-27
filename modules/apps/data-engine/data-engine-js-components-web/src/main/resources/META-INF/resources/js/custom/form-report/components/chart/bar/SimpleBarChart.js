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

import React, {useState} from 'react';
import {
	Bar,
	BarChart,
	Cell,
	LabelList,
	ResponsiveContainer,
	Tooltip,
	XAxis,
	YAxis,
} from 'recharts';

import {NAMED_COLORS} from '../../../utils/colors';
import ellipsize from '../../../utils/ellipsize';
import TooltipContent from '../TooltipContent';

const {blueDark, gray, lightBlue, white} = NAMED_COLORS;

export default function SimpleBarChart({data, height, totalEntries, width}) {
	const [activeIndex, setActiveIndex] = useState(null);

	const handleOnMouseOut = () => {
		setActiveIndex(null);
	};

	const handleOnMouseOver = (index) => {
		setActiveIndex(index);
	};

	const CustomizedYAxisTick = ({payload, x, y}) => {
		const minX = -162;
		const SPACES_REGEX = /\s/g;
		const maxTextSize = 24;

		return (
			<g transform={`translate(${x},${y})`}>
				<text
					className={`${
						activeIndex !== null && activeIndex !== payload.index
							? 'dim'
							: ''
					}`}
					x={minX}
				>
					{`${
						payload.value.replace(SPACES_REGEX, '').length >
						maxTextSize
							? ellipsize(payload.value, maxTextSize)
							: payload.value
					}`}
				</text>
			</g>
		);
	};

	return (
		<div
			className="lfr-de-recharts"
			style={{
				display: 'flex',
				height: '100%',
				minHeight: '500px',
				width: '100%',
			}}
		>
			<ResponsiveContainer
				height={height || '100%'}
				width={width || '100%'}
			>
				<BarChart
					data={data}
					layout="vertical"
					margin={{
						bottom: 20,
						left: 20,
						right: 20,
						top: 20,
					}}
				>
					<XAxis
						axisLine={{stroke: gray}}
						tick={{fontSize: 14}}
						tickLine={false}
						type="number"
					/>

					<YAxis
						dataKey="label"
						stroke={blueDark}
						tick={CustomizedYAxisTick}
						tickLine={false}
						tickMargin={16}
						type="category"
						width={214}
					/>

					<Tooltip
						content={
							<TooltipContent
								showBullet={false}
								showHeader={false}
								totalEntries={totalEntries}
							/>
						}
						cursor={{fill: 'transparent'}}
					/>

					<Bar
						barCategoryGap={30}
						barGap={5}
						dataKey="count"
						fill={lightBlue}
						onMouseOut={handleOnMouseOut}
						onMouseOver={(_, index) => handleOnMouseOver(index)}
					>
						{data.map((_, index) => (
							<Cell
								fillOpacity={
									activeIndex !== null &&
									activeIndex !== undefined &&
									activeIndex !== index
										? '.5'
										: 1
								}
								key={`cell-${index}`}
							/>
						))}

						<LabelList
							dataKey="count"
							fill={white}
							fontSize={14}
							offset={16}
							position="insideRight"
						/>
					</Bar>
				</BarChart>
			</ResponsiveContainer>
		</div>
	);
}
