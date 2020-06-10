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
	height: 400,
	legendHeight: 70,
	stroke: '#E7E7ED',
	width: 1150,
};

const BAR_CHART_BARS = {
	education: {fill: '#4B9BFF', name: Liferay.Language.get('education')},
	selection: {fill: '#50D2A0', name: Liferay.Language.get('selection')},
	solution: {fill: '#FFB46E', name: Liferay.Language.get('solution')},
};

export default function AuditBarChart({data}) {
	return (
		<>
			<ResponsiveContainer height={BAR_CHART.height}>
				<BarChart
					data={data}
					height={BAR_CHART.height}
					width={BAR_CHART.width}
				>
					<Legend
						align="left"
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
						tick={<CustomTick />}
						tickLine={false}
					/>
					<YAxis
						axisLine={{
							stroke: BAR_CHART.stroke,
						}}
						tickLine={false}
					/>
					<Bar
						barSize={BAR_CHART.barHeight}
						dataKey="education"
						fill={BAR_CHART_BARS.education.fill}
						legendType="square"
						name={BAR_CHART_BARS.education.name}
					/>
					<Bar
						barSize={BAR_CHART.barHeight}
						dataKey="selection"
						fill={BAR_CHART_BARS.selection.fill}
						legendType="square"
						name={BAR_CHART_BARS.selection.name}
					/>
					<Bar
						barSize={BAR_CHART.barHeight}
						dataKey="solution"
						fill={BAR_CHART_BARS.solution.fill}
						legendType="square"
						name={BAR_CHART_BARS.solution.name}
					/>
				</BarChart>
			</ResponsiveContainer>
		</>
	);
}

function CustomTick(props) {
	const {payload, x, y} = props;

	return (
		<Text
			fill="#666"
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

AuditBarChart.propTypes = {
	data: PropTypes.array.isRequired,
};
