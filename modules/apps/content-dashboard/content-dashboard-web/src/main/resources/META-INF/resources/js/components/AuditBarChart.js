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

import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';
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
import {shortenNumber} from '../utils/shortenNumber';

export default function AuditBarChart({rtl, vocabularies}) {
	const auditBarChartData = useMemo(() => {
		const dataKeys = new Set();

		const bars = vocabularies.reduce((acc, category) => {
			if (!category.categories) {
				return acc;
			}

			const newBar = category.categories.reduce(
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

		const data = vocabularies.map((category) => {
			if (!category.categories) {
				return category;
			}

			return category.categories.reduce(
				(acc, {key, value}) => {
					return {
						...acc,
						[key]: value,
					};
				},
				{name: category.name}
			);
		});

		const {colors, legendCheckboxes} = bars.reduce(
			(acc, {dataKey}, index) => ({
				colors: {
					...acc.colors,
					[dataKey]: COLORS[index % COLORS.length],
				},
				legendCheckboxes: {
					...acc.legendCheckboxes,
					[dataKey]: true,
				},
			}),
			{colors: {}, legendCheckboxes: {}}
		);

		return {bars, colors, data, legendCheckboxes};
	}, [vocabularies]);

	const {bars, colors, data, legendCheckboxes} = auditBarChartData;

	const height = !bars.length
		? BAR_CHART.height - BAR_CHART.legendHeight
		: BAR_CHART.height;

	const horizontalPoints = !bars.length
		? [BAR_CHART.dotRadiusMin]
		: [BAR_CHART.legendHeight + BAR_CHART.dotRadiusMin];

	const [checkboxes, setCheckbox] = useState(legendCheckboxes);

	useEffect(() => {
		let style;
		if (Object.keys(colors).length) {
			style = document.createElement('style');
			style.type = 'text/css';
			style.textContent = Object.entries(colors).reduce(
				(acc, [dataKey, color]) => {
					return acc.concat(`
						.custom-control-color-${dataKey}.custom-control-input:checked ~ 
							.custom-control-label::before {
								background-color: ${color};
								border-color: ${color};
						}
						.custom-control-color-${dataKey}.custom-control-input:not(:checked) ~ 
							.custom-control-label::before {
								border-color: ${color};
						}
					`);
				},
				''
			);

			document.head.appendChild(style);
		}

		return () => {
			if (style) {
				document.head.removeChild(style);
			}
		};
	}, [colors]);

	const renderLegend = (props) => {
		const {payload, yAxisName} = props;

		return (
			<>
				<span className="mr-4 small">{yAxisName}:</span>
				{payload.map((entry) => (
					<ClayCheckbox
						aria-labelledby={entry.value}
						checked={checkboxes[entry.dataKey]}
						className={`custom-control-color-${entry.dataKey}`}
						inline
						key={entry.dataKey}
						onChange={() =>
							setCheckbox({
								...checkboxes,
								[entry.dataKey]: !checkboxes[entry.dataKey],
							})
						}
					>
						<span className="inline-item inline-item-after small text-secondary">
							{entry.value}
						</span>
					</ClayCheckbox>
				))}
			</>
		);
	};

	const showLegend = !!bars.length;

	const axisNames = {
		x: vocabularies[0]?.vocabularyName,
		y: showLegend && vocabularies[0]?.categories?.[0]?.vocabularyName,
	};

	const noCheckboxesChecked = Object.keys(checkboxes).every(
		(i) => checkboxes[i] === false
	);

	return (
		<>
			{Object.keys(checkboxes).length > 0 && noCheckboxesChecked && (
				<ClayEmptyState
					className="empty-state no-categories-selected text-center"
					description={Liferay.Language.get(
						'select-categories-from-the-checkboxes-in-the-legend-above'
					)}
					title={Liferay.Language.get(
						'there-are-no-categories-selected'
					)}
				/>
			)}
			<ResponsiveContainer className="mb-3" height={height}>
				<BarChart data={data} height={height} width={BAR_CHART.width}>
					{showLegend && (
						<Legend
							align={rtl ? 'right' : 'left'}
							content={renderLegend}
							height={BAR_CHART.legendHeight}
							verticalAlign="top"
							yAxisName={axisNames.y}
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
						height={75}
						interval={0}
						label={{
							className: 'small',
							position: 'insideBottom',
							value: axisNames.x,
						}}
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
						width={45}
					/>
					{bars.length &&
						bars.map((bar, index) => {
							return (
								<Bar
									barSize={
										checkboxes[bar.dataKey] === true
											? BAR_CHART.barHeight
											: 0
									}
									dataKey={bar.dataKey}
									fill={colors[bar.dataKey]}
									key={index}
									legendType="square"
									name={bar.name}
								/>
							);
						})}
					{!bars.length && (
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
			fontSize={14}
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
			fontSize={14}
			textAnchor="end"
			verticalAnchor="end"
			x={rtl ? x + BAR_CHART.axisMargin : x - BAR_CHART.axisMargin}
			y={y}
		>
			{shortenNumber(payload.value)}
		</Text>
	);
}

AuditBarChart.propTypes = {
	rtl: PropTypes.bool.isRequired,
	vocabularies: PropTypes.array.isRequired,
};
