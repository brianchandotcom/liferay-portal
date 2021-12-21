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
import ClayLayout from '@clayui/layout';
import {useEventListener} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';
import {
	Bar,
	BarChart,
	CartesianGrid,
	Cell,
	Legend,
	ResponsiveContainer,
	Text,
	Tooltip,
	XAxis,
	YAxis,
} from 'recharts';

import {BAR_CHART, COLORS, DEFAULT_COLOR} from '../utils/constants';
import {shortenNumber} from '../utils/shortenNumber';

const handleKeydown = (event) => {
	const resetBarsCategoryFiltersURL = new URLSearchParams(
		window.location.href
	).get('resetBarsCategoryFiltersURL');

	if (event.key === 'Escape' && resetBarsCategoryFiltersURL) {
		Liferay.Util.navigate(decodeURIComponent(resetBarsCategoryFiltersURL));
	}
};

export default function AuditBarChart({namespace, rtl, vocabularies}) {
	const auditBarChartData = useMemo(() => {
		const dataKeys = new Set();
		var maxValue = 0;

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

		const noneBarIndex = bars.findIndex((bar) => bar.dataKey === 'none');

		if (noneBarIndex !== -1) {
			const noneBar = bars.splice(noneBarIndex, 1)[0];

			bars.push(noneBar);
		}

		const data = vocabularies.map((category) => {
			if (!category.categories) {
				if (Number(category.value) > maxValue) {
					maxValue = Number(category.value);
				}

				return category;
			}

			return category.categories.reduce(
				(acc, {key, value}) => {
					if (Number(value) > maxValue) {
						maxValue = Number(value);
					}

					return {
						...acc,
						[key]: value,
					};
				},
				{key: category.key, name: category.name}
			);
		});

		const {colors, legendCheckboxes} = bars.reduce(
			(acc, {dataKey}, index) => ({
				colors: {
					...acc.colors,
					[dataKey]:
						dataKey === 'none'
							? DEFAULT_COLOR
							: COLORS[index % COLORS.length],
				},
				legendCheckboxes: {
					...acc.legendCheckboxes,
					[dataKey]: true,
				},
			}),
			{colors: {}, legendCheckboxes: {}}
		);

		return {bars, colors, data, legendCheckboxes, maxValue};
	}, [vocabularies]);

	const {bars, colors, data, legendCheckboxes, maxValue} = auditBarChartData;

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
			<ClayLayout.ContainerFluid>
				<ClayLayout.Row>
					<ClayLayout.Col size={1}>
						<span className="small">{yAxisName}:</span>
					</ClayLayout.Col>

					<ClayLayout.Col>
						<ClayLayout.Row>
							{payload.map((entry) => (
								<ClayLayout.Col
									className="c-mb-2"
									key={entry.dataKey}
									size={2}
								>
									<ClayCheckbox
										aria-labelledby={entry.key}
										checked={checkboxes[entry.dataKey]}
										className={`custom-control-color-${entry.dataKey}`}
										inline
										onChange={() =>
											setCheckbox({
												...checkboxes,
												[entry.dataKey]: !checkboxes[
													entry.dataKey
												],
											})
										}
									>
										<span
											className="content-dashboard-checkbox-label pl-1 small text-secondary text-truncate"
											data-tooltip-align="bottom"
											id={entry.key}
											title={entry.value}
										>
											{entry.value}
										</span>
									</ClayCheckbox>
								</ClayLayout.Col>
							))}
						</ClayLayout.Row>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		);
	};

	const showLegend = !!bars.length;

	const axisNames = {
		x: vocabularies[0]?.vocabularyName,
		y:
			showLegend &&
			vocabularies.find(({categories}) => categories)?.categories[0]
				.vocabularyName,
	};

	const noCheckboxesChecked = Object.keys(checkboxes).every(
		(i) => checkboxes[i] === false
	);

	const [tooltip, setTooltip] = useState(null);

	const onBarClick = (assetCategoryIds) => {
		if (assetCategoryIds.length) {
			const params = new URLSearchParams(window.location.search);

			let uri = window.location.href;

			if (!params.get('resetBarsCategoryFiltersURL')) {
				uri = Liferay.Util.addParams(
					'resetBarsCategoryFiltersURL=' + encodeURIComponent(uri),
					uri
				);
			}

			params.getAll(namespace + 'assetCategoryId').forEach((category) => {
				uri = uri.replace(
					namespace + 'assetCategoryId=' + category,
					''
				);
			});

			assetCategoryIds.forEach((assetCategoryId) => {
				if (assetCategoryId !== 'none') {
					uri = Liferay.Util.addParams(
						namespace + 'assetCategoryId=' + assetCategoryId,
						uri
					);
				}
			});

			Liferay.Util.navigate(uri);
		}
	};

	useEventListener('keydown', handleKeydown, true, document);

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
			<div className="mb-3 overflow-auto">
				<ResponsiveContainer height={BAR_CHART.height} width="100%">
					<BarChart data={data}>
						{showLegend && (
							<Legend
								align={rtl ? 'right' : 'left'}
								content={renderLegend}
								verticalAlign="top"
								wrapperStyle={{paddingBottom: 24}}
								yAxisName={axisNames.y}
							/>
						)}

						<CartesianGrid stroke={BAR_CHART.stroke} />

						<XAxis
							axisLine={{
								stroke: BAR_CHART.stroke,
							}}
							dataKey="name"
							height={90}
							interval={0}
							label={{
								className: 'small',
								offset: 18,
								position: 'insideBottom',
								value: axisNames.x,
							}}
							reversed={rtl}
							tick={<CustomXAxisTick />}
							tickLine={false}
						/>

						<YAxis
							allowDataOverflow={true}
							allowDecimals={false}
							axisLine={{
								stroke: BAR_CHART.stroke,
							}}
							domain={[0, maxValue]}
							orientation={rtl ? 'right' : 'left'}
							tick={<CustomYAxisTick rtl={rtl} />}
							tickLine={false}
							width={45}
						/>

						<Tooltip
							animationDuration={0}
							content={<CustomTooltip />}
							cursor={{fill: 'transparent'}}
							tooltip={tooltip}
						/>

						{!!bars.length &&
							bars.map((bar, index) => {
								return (
									<Bar
										barSize={
											checkboxes[bar.dataKey] === true
												? BAR_CHART.barHeight
												: 0
										}
										dataKey={bar.dataKey}
										hide={checkboxes[bar.dataKey] !== true}
										key={index}
										legendType="square"
										name={bar.name}
										onClick={(props) =>
											onBarClick([
												props.payload.key,
												bar.dataKey,
											])
										}
										onMouseOut={() => {
											setTooltip(null);
										}}
										onMouseOver={(props) => {
											setTooltip({
												dataKey: bar.dataKey,
												name: props.name,
											});
										}}
										style={{cursor: 'pointer'}}
									>
										{data.map((entry, index) => (
											<Cell
												fill={colors[bar.dataKey]}
												key={`cell-${index}`}
												opacity={
													!tooltip
														? 1
														: tooltip.dataKey ===
																bar.dataKey &&
														  entry.name ===
																tooltip.name
														? 1
														: 0.4
												}
											/>
										))}
									</Bar>
								);
							})}

						{!bars.length && (
							<Bar
								barSize={BAR_CHART.barHeight}
								dataKey="value"
								onClick={(props) =>
									onBarClick([props.payload.key])
								}
								onMouseOut={() => {
									setTooltip(null);
								}}
								onMouseOver={(props) => {
									setTooltip({
										dataKey: 'value',
										name: props.name,
									});
								}}
								style={{cursor: 'pointer'}}
							>
								{data.map((entry, index) => (
									<Cell
										fill={COLORS[0]}
										key={`cell-${index}`}
										opacity={
											!tooltip
												? 1
												: tooltip.dataKey === 'value' &&
												  entry.name === tooltip.name
												? 1
												: 0.4
										}
									/>
								))}
							</Bar>
						)}
					</BarChart>
				</ResponsiveContainer>
			</div>
		</>
	);
}

function CustomTooltip(props) {
	const {active, label, payload, tooltip} = props;

	if (!active || !tooltip) {
		return null;
	}

	for (var i = 0; i < payload.length; i++) {
		if (payload[i].dataKey === tooltip.dataKey) {
			return (
				<ClayLayout.ContentRow
					className="bg-white custom-tooltip p-1 rounded small text-secondary"
					padded
					style={{width: 150}}
				>
					<ClayLayout.ContentCol expand>
						{payload[i].payload.vocabularyName
							? label
							: payload[i].name}
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol>
						{payload[i].value}
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			);
		}
	}

	return null;
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
	namespace: PropTypes.string.isRequired,
	rtl: PropTypes.bool.isRequired,
	vocabularies: PropTypes.array.isRequired,
};
