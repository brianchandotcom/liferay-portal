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

import {NAMED_COLORS} from '../../../utils/colors.es';
import TooltipContent from '../TooltipContent.es';

const {blueDark, gray, lightBlue, white} = NAMED_COLORS;

export default ({data, height, totalEntries, width}) => {
	const [activeIndex, setActiveIndex] = useState(null);

	const handleOnMouseOut = () => {
		setActiveIndex(null);
	};

	const handleOnMouseOver = (index) => {
		setActiveIndex(index);
	};

	const CustomizedYAxisTick = ({payload, x, y}) => {
		return (
			<g transform={`translate(${x},${y})`}>
				<text
					className={`${
						activeIndex !== null && activeIndex !== payload.index
							? 'dim'
							: ''
					}`}
					x={-22}
				>
					{`${payload.value}`}
				</text>
			</g>
		);
	};

	return (
		<div className="custom-chart-size simple-bar-chart">
			<ResponsiveContainer
				height={height || '99%'}
				width={width || '99%'}
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
									activeIndex != null && activeIndex != index
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
};
