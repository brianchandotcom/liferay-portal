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
import React, {useMemo} from 'react';
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

import {BAR_CHART, COLORS} from '../utils/constants';

export default function AuditBarChart({categories, rtl}) {
	const auditBarChartData = useMemo(() => {
		const dataKeys = new Set();

		const bars = categories.reduce((acc, category) => {
			if (!category.children) {
				return undefined;
			}

			const newBar = category.children.reduce(
				(childAcc, {key: dataKey, name}) => {
					if (dataKeys.has(dataKey)) {
						return childAcc;
					}

					dataKeys.add(dataKey);

					return childAcc.concat({dataKey, name});
				},
				[]
			);

			return acc.concat(newBar);
		}, []);

		const data = categories.map((category) => {
			if (!category.children) {
				return category;
			}

			return category.children.reduce(
				(acc, {key, value}) => {
					return {
						...acc,
						[key]: value,
					};
				},
				{name: category.name}
			);
		});

		return {bars, data};
	}, [categories]);

	const {bars, data} = auditBarChartData;

	const height =
		bars === undefined
			? BAR_CHART.height - BAR_CHART.legendHeight
			: BAR_CHART.height;

	const horizontalPoints =
		bars === undefined
			? [BAR_CHART.dotRadiusMin]
			: [BAR_CHART.legendHeight + BAR_CHART.dotRadiusMin];

	return (
		<>
			<ResponsiveContainer height={height}>
				<BarChart data={data} height={height} width={BAR_CHART.width}>
					{bars && (
						<Legend
							align={rtl ? 'right' : 'left'}
							height={BAR_CHART.legendHeight}
							verticalAlign="top"
						/>
					)}
					<CartesianGrid
						horizontalPoints={horizontalPoints}
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
					{bars &&
						bars.map((bar, index) => {
							return (
								<Bar
									barSize={BAR_CHART.barHeight}
									dataKey={bar.dataKey}
									fill={COLORS[index % COLORS.length]}
									key={index}
									legendType="square"
									name={bar.name}
								/>
							);
						})}
					{bars === undefined && (
						<Bar
							barSize={BAR_CHART.barHeight}
							dataKey="value"
							fill={COLORS[0]}
						/>
					)}
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
			y={y + BAR_CHART.axisMargin}
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
			x={rtl ? x + BAR_CHART.axisMargin : x - BAR_CHART.axisMargin}
			y={y}
		>
			{payload.value}
		</Text>
	);
}

AuditBarChart.propTypes = {
	categories: PropTypes.array.isRequired,
	rtl: PropTypes.bool.isRequired,
};
