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
import {useStateSafe} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useState} from 'react';
import {Cell, Pie, PieChart, Tooltip} from 'recharts';

import ConnectionContext from '../context/ConnectionContext';
import {StoreContext, useWarning} from '../context/store';
import {numberFormat} from '../utils/numberFormat';
import EmptyPieChart from './EmptyPieChart';
import Hint from './Hint';

const COLORS_MAP = {
	direct: '#FF73C3',
	organic: '#4B9FFF',
	paid: '#FFB46E',
	referral: '#FF5F5F',
	social: '#50D2A0',
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
	dataProvider,
	languageTag,
	onTrafficSourceClick,
}) {
	const [highlighted, setHighlighted] = useState(null);

	const [, addWarning] = useWarning();

	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const [{publishedToday}] = useContext(StoreContext);

	const [trafficSources, setTrafficSources] = useStateSafe([]);

	useEffect(() => {
		if (validAnalyticsConnection) {
			dataProvider()
				.then((response) => setTrafficSources(response.trafficSources))
				.catch(() => {
					setTrafficSources([]);
					addWarning();
				});
		}
	}, [addWarning, dataProvider, setTrafficSources, validAnalyticsConnection]);

	const fullPieChart = useMemo(
		() => trafficSources.some(({value}) => value),
		[trafficSources]
	);

	const missingTrafficSourceValue = useMemo(
		() => trafficSources.some(({value}) => value === undefined),
		[trafficSources]
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
			<h5 className="mt-3 sheet-subtitle">
				{Liferay.Language.get('traffic-channels')}
				<Hint
					message={Liferay.Language.get('traffic-channels-help')}
					secondary={true}
					title={Liferay.Language.get('traffic-channels')}
				/>
			</h5>

			{!fullPieChart && !missingTrafficSourceValue && (
				<div className="mb-3 text-secondary">
					{Liferay.Language.get(
						'your-page-has-no-incoming-traffic-from-traffic-channels-yet'
					)}
				</div>
			)}
			<div className="pie-chart-wrapper">
				<div className="pie-chart-wrapper--legend">
					<table>
						<tbody>
							{trafficSources.map((entry) => {
								const hasDetails =
									entry?.countryKeywords !== undefined ||
									entry?.details !== undefined;

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
											className="c-py-1 pie-chart-wrapper--legend--title text-secondary"
											onMouseOut={handleLegendMouseLeave}
											onMouseOver={() =>
												handleLegendMouseEnter(
													entry.name
												)
											}
										>
											{entry.value > 0 && hasDetails ? (
												<ClayButton
													className="font-weight-semi-bold px-0 py-1 text-primary"
													displayType="link"
													onClick={() =>
														onTrafficSourceClick(
															trafficSources,
															entry.name
														)
													}
													small
												>
													{entry.title}
												</ClayButton>
											) : (
												<span>{entry.title}</span>
											)}
										</td>
										<td className="text-secondary">
											<Hint
												message={entry.helpMessage}
												title={entry.title}
											/>
										</td>
										<td className="font-weight-bold">
											{entry.value !== undefined &&
											!publishedToday
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
								isAnimationActive={false}
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
								animationDuration={0}
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
	dataProvider: PropTypes.func.isRequired,
	languageTag: PropTypes.string.isRequired,
	onTrafficSourceClick: PropTypes.func.isRequired,
};
