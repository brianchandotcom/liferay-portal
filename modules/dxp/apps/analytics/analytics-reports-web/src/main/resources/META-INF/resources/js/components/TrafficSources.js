/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import className from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';
import {Cell, Pie, PieChart, Tooltip} from 'recharts';

import {useWarning} from '../context/store';
import {numberFormat} from '../utils/numberFormat';
import EmptyPieChart from './EmptyPieChart';
import Hint from './Hint';

const COLORS_MAP = {
	organic: '#7785FF',
	paid: '#FFB46E',
};

const PIE_CHART_SIZES = {
	height: 80,
	innerRadius: 25,
	paddingAngle: 1,
	radius: 40,
	width: 100,
};

/**
 * Used when the traffic source name is not within the COLORS_MAP
 */
const FALLBACK_COLOR = '#e92563';

const getColorByName = (name) => COLORS_MAP[name] || FALLBACK_COLOR;

export default function TrafficSources({
	languageTag,
	onTrafficSourceClick,
	trafficSources,
}) {
	const [highlighted, setHighlighted] = useState(null);

	const [, addWarning] = useWarning();

	const fullPieChart = trafficSources.some((source) => !!source.value);

	const missingTrafficSourceValue = trafficSources.some(
		(trafficSource) => trafficSource.value === undefined
	);

	useEffect(() => {
		if (missingTrafficSourceValue) {
			addWarning();
		}
	}, [addWarning, missingTrafficSourceValue]);

	function handleLegendMouseEnter(name) {
		setHighlighted(name);
	}

	function handleLegendMouseLeave() {
		setHighlighted(null);
	}

	return (
		<>
			<h5 className="mt-2 sheet-subtitle text-secondary">
				{Liferay.Language.get('search-engines-traffic')}
				<Hint
					message={Liferay.Language.get(
						'search-engines-traffic-help'
					)}
					title={Liferay.Language.get('search-engines-traffic')}
				/>
			</h5>

			{!fullPieChart && !missingTrafficSourceValue && (
				<div className="mb-2 text-secondary">
					{Liferay.Language.get(
						'your-page-has-no-incoming-traffic-from-search-engines-yet'
					)}
				</div>
			)}
			<div className="pie-chart-wrapper">
				<div className="pie-chart-wrapper--legend">
					<table>
						<tbody>
							{trafficSources.map((entry) => {
								return (
									<tr key={entry.name}>
										<td
											className="px-0"
											onMouseOut={handleLegendMouseLeave}
											onMouseOver={() =>
												handleLegendMouseEnter(
													entry.name
												)
											}
										>
											<span
												className="pie-chart-wrapper--legend--dot"
												style={{
													backgroundColor: getColorByName(
														entry.name
													),
												}}
											></span>
										</td>
										<td
											className="pie-chart-wrapper--legend--title text-secondary"
											onMouseOut={handleLegendMouseLeave}
											onMouseOver={() =>
												handleLegendMouseEnter(
													entry.name
												)
											}
										>
											<ClayButton
												className="font-weight-semi-bold px-0 py-1 text-secondary"
												displayType="link"
												onClick={() =>
													onTrafficSourceClick(
														entry.name
													)
												}
												small
											>
												{entry.title}
											</ClayButton>
										</td>
										<td className="text-secondary">
											<Hint
												message={entry.helpMessage}
												title={entry.title}
											/>
										</td>
										<td className="font-weight-bold">
											{entry.value !== undefined
												? numberFormat(
														languageTag,
														entry.value
												  )
												: '-'}
										</td>
									</tr>
								);
							})}
						</tbody>
					</table>
				</div>

				{!fullPieChart && (
					<EmptyPieChart
						height={PIE_CHART_SIZES.height}
						innerRadius={PIE_CHART_SIZES.innerRadius}
						radius={PIE_CHART_SIZES.radius}
						width={PIE_CHART_SIZES.width}
					/>
				)}

				{fullPieChart && (
					<div className="pie-chart-wrapper--chart">
						<PieChart
							height={PIE_CHART_SIZES.height}
							width={PIE_CHART_SIZES.width}
						>
							<Pie
								cx="50%"
								cy="50%"
								data={trafficSources}
								dataKey="value"
								innerRadius={PIE_CHART_SIZES.innerRadius}
								nameKey={'name'}
								outerRadius={PIE_CHART_SIZES.radius}
								paddingAngle={PIE_CHART_SIZES.paddingAngle}
							>
								{trafficSources.map((entry, i) => {
									const fillColor = getColorByName(
										entry.name
									);

									const cellClasses = className({
										dim:
											highlighted &&
											entry.name !== highlighted,
									});

									return (
										<Cell
											className={cellClasses}
											fill={fillColor}
											key={i}
											onMouseOut={handleLegendMouseLeave}
											onMouseOver={() =>
												handleLegendMouseEnter(
													entry.name
												)
											}
										/>
									);
								})}
							</Pie>

							<Tooltip
								content={<TrafficSourcesCustomTooltip />}
								formatter={(value, name, iconType) => {
									return [
										numberFormat(languageTag, value),
										name,
										iconType,
									];
								}}
								separator={': '}
							/>
						</PieChart>
					</div>
				)}
			</div>
		</>
	);
}

function TrafficSourcesCustomTooltip(props) {
	const {formatter, payload, separator = ''} = props;

	return (
		<div className="custom-tooltip">
			<p className="mb-1 mt-0">
				<b>{payload.length && payload[0].payload.title}</b>
			</p>

			<ul className="list-unstyled mb-0">
				<>
					{payload.map((item) => {
						// eslint-disable-next-line no-unused-vars
						const [value, _name, iconType] = formatter
							? formatter(item.value, item.name, item.iconType)
							: [item.value, item.name, item.iconType];

						const {payload} = item;

						return (
							<React.Fragment key={item.name}>
								<li>
									{Liferay.Language.get('visitors')}
									{separator}
									<b>{value}</b>
								</li>
								<li>
									{Liferay.Language.get('traffic-share')}
									{separator}
									<b>{`${payload.share}%`}</b>
								</li>
							</React.Fragment>
						);
					})}
				</>
			</ul>
		</div>
	);
}

TrafficSources.propTypes = {
	languageTag: PropTypes.string.isRequired,
	onTrafficSourceClick: PropTypes.func.isRequired,
	trafficSources: PropTypes.array.isRequired,
};
