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
import {useStateSafe} from '@liferay/frontend-js-react-web';
import className from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useState} from 'react';
import {Cell, Pie, PieChart, Tooltip} from 'recharts';

import ConnectionContext from '../context/ConnectionContext';
import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
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
	height: 140,
	innerRadius: 40,
	outerRadius: 70,
	paddingAngle: 1,
	width: 140,
};

/**
 * Used when the traffic source name is not within the COLORS_MAP
 */
const FALLBACK_COLOR = '#e92563';

const getColorByName = (name) => COLORS_MAP[name] || FALLBACK_COLOR;

export default function TrafficSources({dataProvider, onTrafficSourceClick}) {
	const [highlighted, setHighlighted] = useState(null);

	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const dispatch = useContext(StoreDispatchContext);

	const {languageTag, publishedToday} = useContext(StoreStateContext);

	const [trafficSources, setTrafficSources] = useStateSafe([]);

	useEffect(() => {
		if (validAnalyticsConnection) {
			dataProvider()
				.then((trafficSources) => setTrafficSources(trafficSources))
				.catch(() => {
					setTrafficSources([]);
					dispatch({type: 'ADD_WARNING'});
				});
		}
	}, [dispatch, dataProvider, setTrafficSources, validAnalyticsConnection]);

	const fullPieChart = useMemo(
		() =>
			validAnalyticsConnection &&
			!publishedToday &&
			trafficSources?.some(({value}) => value),
		[publishedToday, trafficSources, validAnalyticsConnection]
	);

	const missingTrafficSourceValue = useMemo(
		() => trafficSources?.some(({value}) => value === undefined),
		[trafficSources]
	);

	useEffect(() => {
		if (missingTrafficSourceValue) {
			dispatch({type: 'ADD_WARNING'});
		}
	}, [dispatch, missingTrafficSourceValue]);

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
							{trafficSources?.map((entry) => {
								const hasDetails =
									entry?.countryKeywords ||
									(entry?.referringPages &&
										entry?.referringDomains) ||
									entry?.referringSocialMedia;

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
											className="c-py-1 text-secondary"
											onMouseOut={handleLegendMouseLeave}
											onMouseOver={() =>
												handleLegendMouseEnter(
													entry.name
												)
											}
										>
											{validAnalyticsConnection &&
											!publishedToday &&
											entry.value > 0 &&
											hasDetails ? (
												<ClayButton
													className="px-0 py-1 text-primary"
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
										<td className="font-weight-semi-bold">
											{validAnalyticsConnection &&
											!publishedToday &&
											entry.value !== undefined
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
						outerRadius={PIE_CHART_SIZES.outerRadius}
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
								nameKey="name"
								outerRadius={PIE_CHART_SIZES.outerRadius}
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
								separator=": "
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
	onTrafficSourceClick: PropTypes.func.isRequired,
};
