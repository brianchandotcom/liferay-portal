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

import PropTypes from 'prop-types';
import React from 'react';
import {
	Bar,
	BarChart,
	CartesianGrid,
	Legend,
	ResponsiveContainer,
	Text,
	XAxis,
	YAxis,
} from 'recharts';

const BAR_CHART = {
	barHeight: 16,
	dotRadiusMax: 35,
	dotRadiusMin: 5,
	fillXAxis: '#666',
	height: 400,
	legendHeight: 70,
	stroke: '#E7E7ED',
	width: 1150,
};

export default function AuditBarChart({bars, data, rtl}) {
	return (
		<>
			<ResponsiveContainer height={BAR_CHART.height}>
				<BarChart
					data={data}
					height={BAR_CHART.height}
					width={BAR_CHART.width}
				>
					<Legend
						align={rtl ? 'right' : 'left'}
						height={BAR_CHART.legendHeight}
						verticalAlign="top"
					/>
					<CartesianGrid
						horizontalPoints={[
							BAR_CHART.legendHeight + BAR_CHART.dotRadiusMin,
						]}
						stroke={BAR_CHART.stroke}
					/>
					<XAxis
						axisLine={{
							stroke: BAR_CHART.stroke,
						}}
						dataKey="name"
						height={70}
						interval={0}
						reversed={rtl}
						tick={<CustomXAxisTick />}
						tickLine={false}
					/>
					<YAxis
						axisLine={{
							stroke: BAR_CHART.stroke,
						}}
						orientation={rtl ? 'right' : 'left'}
						tick={<CustomYAxisTick rtl={rtl} />}
						tickLine={false}
					/>

					{bars.map((bar, index) => {
						return (
							<Bar
								barSize={BAR_CHART.barHeight}
								dataKey={bar.dataKey}
								fill={bar.fill}
								key={index}
								legendType="square"
								name={bar.name}
							/>
						);
					})}
				</BarChart>
			</ResponsiveContainer>
		</>
	);
}

function CustomXAxisTick(props) {
	const {payload, x, y} = props;

	return (
		<Text
			fill={BAR_CHART.fillXAxis}
			textAnchor="middle"
			verticalAnchor="start"
			width={120}
			x={x}
			y={y + 16}
		>
			{payload.value}
		</Text>
	);
}

function CustomYAxisTick(props) {
	const {payload, rtl, x, y} = props;

	return (
		<Text
			fill={BAR_CHART.fillXAxis}
			textAnchor="end"
			verticalAnchor="end"
			x={rtl ? x + 16 : x - 16}
			y={y}
		>
			{payload.value}
		</Text>
	);
}

AuditBarChart.propTypes = {
	bars: PropTypes.array.isRequired,
	data: PropTypes.array.isRequired,
	rtl: PropTypes.bool.isRequired,
};
