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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import className from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo} from 'react';
import {
	CartesianGrid,
	Legend,
	Line,
	LineChart,
	ReferenceDot,
	Tooltip,
	XAxis,
	YAxis,
} from 'recharts';

import {
	ChartDispatchContext,
	ChartStateContext,
	useDateTitle,
	useIsPreviousPeriodButtonDisabled,
} from '../context/ChartStateContext';
import ConnectionContext from '../context/ConnectionContext';
import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
import {generateDateFormatters as dateFormat} from '../utils/dateFormat';
import {numberFormat} from '../utils/numberFormat';
import {ActiveDot as CustomActiveDot, Dot as CustomDot} from './CustomDots';
import CustomTooltip from './CustomTooltip';
import TimeSpanSelector from './TimeSpanSelector';

const CHART_COLORS = {
	analyticsReportsHistoricalReads: '#50D2A0',
	analyticsReportsHistoricalViews: '#4B9BFF',
	cartesianGrid: '#E7E7ED',
	publishDate: '#2E5AAC',
};

const CHART_SIZES = {
	dotRadius: 4,
	fill: 'white',
	height: 220,
	lineWidth: 2,
	width: 280,
	yAxisWidth: 40,
};

const DAY_IN_MILLISECONDS = 24 * 60 * 60 * 1000;
const HOUR_IN_MILLISECONDS = 60 * 60 * 1000;

const LAST_24_HOURS = 'last-24-hours';

const METRICS_STATIC_VALUES = {
	analyticsReportsHistoricalReads: {
		color: CHART_COLORS.analyticsReportsHistoricalReads,
		iconType: 'square',
		langKey: Liferay.Language.get('reads-metric'),
	},
	analyticsReportsHistoricalViews: {
		color: CHART_COLORS.analyticsReportsHistoricalViews,
		iconType: 'circle',
		langKey: Liferay.Language.get('views-metric'),
	},
};

function keyToTranslatedLabelValue(key) {
	return METRICS_STATIC_VALUES[key]?.langKey ?? key;
}

function keyToHexColor(key) {
	return METRICS_STATIC_VALUES[key]?.color ?? '#666666';
}

function keyToIconType(key) {
	return METRICS_STATIC_VALUES[key]?.iconType ?? 'line';
}

/*
 * If a number is bigger than 1000 it will transform it to kilos
 *
 * 4 => 4
 * 4000 => '4K'
 */
function thousandsToKilosFormater(value) {
	if (value > 1000) {
		return value / 1000 + 'K';
	}

	return value;
}

function legendFormatterGenerator(
	totals,
	languageTag,
	publishedToday,
	validAnalyticsConnection
) {
	return (value) => {
		const preformattedNumber = totals[value];

		return (
			<span>
				<span
					className={`custom-${keyToIconType(value)} mr-2`}
					style={{
						backgroundColor: keyToHexColor(value),
					}}
				></span>
				<span className="text-secondary">
					{keyToTranslatedLabelValue(value)}
				</span>
				<span className="font-weight-bold inline-item-after">
					{validAnalyticsConnection &&
					preformattedNumber !== null &&
					!publishedToday
						? numberFormat(languageTag, preformattedNumber)
						: '-'}
				</span>
			</span>
		);
	};
}

export default function Chart({
	dataProviders = [],
	publishDate,
	timeSpanOptions,
}) {
	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const storeDispatch = useContext(StoreDispatchContext);

	const chartDispatch = useContext(ChartDispatchContext);

	const {languageTag, publishedToday} = useContext(StoreStateContext);

	const {
		dataSet,
		loading,
		timeRange,
		timeSpanKey,
		timeSpanOffset,
	} = useContext(ChartStateContext);

	const {firstDate, lastDate} = useDateTitle();

	const isPreviousPeriodButtonDisabled = useIsPreviousPeriodButtonDisabled();

	const dateFormatters = useMemo(() => dateFormat(languageTag), [
		languageTag,
	]);

	const title = dateFormatters.formatChartTitle([firstDate, lastDate]);

	const isMounted = useIsMounted();

	useEffect(() => {
		let gone = false;

		const timeSpanComparator =
			timeSpanKey === LAST_24_HOURS
				? HOUR_IN_MILLISECONDS
				: DAY_IN_MILLISECONDS;

		const keys = new Set(['analyticsReportsHistoricalViews']);

		if (dataProviders.length === 2) {
			keys.add('analyticsReportsHistoricalReads');
		}

		if (validAnalyticsConnection) {
			const promises = dataProviders.map((getter) => {
				return getter();
			});

			allSettled(promises).then((data) => {
				if (gone || !isMounted()) {
					return;
				}

				var dataSetItems = {};

				for (var i = 0; i < data.length; i++) {
					if (data[i].status === 'fulfilled') {
						dataSetItems = {
							...dataSetItems,
							...data[i].value,
						};
					}
					else {
						storeDispatch({type: 'ADD_WARNING'});
					}
				}

				chartDispatch({
					payload: {
						dataSetItems,
						keys,
						timeSpanComparator,
					},
					type: 'ADD_DATA_SET_ITEMS',
					validAnalyticsConnection,
				});
			});
		}
		else {
			chartDispatch({
				payload: {
					keys,
					timeSpanComparator,
				},
				type: 'ADD_DATA_SET_ITEMS',
				validAnalyticsConnection,
			});
		}

		return () => {
			gone = true;
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [timeSpanKey, timeSpanOffset]);

	const {histogram, keyList} = dataSet;

	const referenceDotPosition = useMemo(() => {
		const publishDateISOString = new Date(publishDate).toISOString();

		return timeSpanKey === LAST_24_HOURS
			? publishDateISOString.split(':')[0].concat(':00:00')
			: publishDateISOString.split('T')[0].concat('T00:00:00');
	}, [timeSpanKey, publishDate]);

	const legendFormatter =
		dataSet &&
		legendFormatterGenerator(
			dataSet.totals,
			languageTag,
			publishedToday,
			validAnalyticsConnection
		);

	const xAxisFormatter =
		timeSpanKey === LAST_24_HOURS
			? dateFormatters.formatNumericHour
			: dateFormatters.formatNumericDay;

	const lineChartWrapperClasses = className('line-chart-wrapper', {
		'line-chart-wrapper--loading': loading,
	});

	return (
		<>
			{!!timeSpanOptions.length && (
				<div className="c-mb-3 c-mt-4">
					<TimeSpanSelector
						disabledNextTimeSpan={timeSpanOffset === 0}
						disabledPreviousPeriodButton={
							isPreviousPeriodButtonDisabled
						}
						timeSpanKey={timeSpanKey}
						timeSpanOptions={timeSpanOptions}
					/>
				</div>
			)}

			{dataSet ? (
				<div className={lineChartWrapperClasses}>
					{loading && (
						<ClayLoadingIndicator
							className="chart-loading-indicator"
							small
						/>
					)}

					{title && <h5 className="mb-3">{title}</h5>}

					<div className="line-chart">
						<LineChart
							data={histogram}
							height={CHART_SIZES.height}
							width={CHART_SIZES.width}
						>
							<Legend
								formatter={legendFormatter}
								iconSize={0}
								layout="vertical"
								verticalAlign="top"
								wrapperStyle={{
									left: 0,
									top: 0,
								}}
							/>

							<CartesianGrid
								stroke={CHART_COLORS.cartesianGrid}
								strokeDasharray="0 0"
								vertical={true}
								verticalPoints={[
									CHART_SIZES.width - CHART_SIZES.dotRadius,
								]}
							/>

							<XAxis
								axisLine={{
									stroke: CHART_COLORS.cartesianGrid,
								}}
								dataKey="label"
								domain={
									!validAnalyticsConnection ||
									histogram.length === 0
										? [
												new Date(
													timeRange.startDate
												).getDate(),
												new Date(
													timeRange.endDate
												).getDate(),
										  ]
										: []
								}
								interval="preserveStartEnd"
								tickCount={7}
								tickFormatter={(value) => {
									return validAnalyticsConnection &&
										histogram.length !== 0
										? xAxisFormatter(value)
										: value;
								}}
								tickLine={false}
								type={
									validAnalyticsConnection &&
									histogram.length !== 0
										? 'category'
										: 'number'
								}
							/>

							{!validAnalyticsConnection ||
							publishedToday ||
							histogram.length === 0 ? (
								<YAxis
									axisLine={{
										stroke: CHART_COLORS.cartesianGrid,
									}}
									tickLine={false}
									ticks={[0, 50, 100]}
									width={CHART_SIZES.yAxisWidth}
								/>
							) : (
								<YAxis
									allowDecimals={false}
									axisLine={{
										stroke: CHART_COLORS.cartesianGrid,
									}}
									minTickGap={3}
									tickFormatter={thousandsToKilosFormater}
									tickLine={false}
									width={CHART_SIZES.yAxisWidth}
								/>
							)}

							{validAnalyticsConnection && !publishedToday && (
								<Tooltip
									animationDuration={0}
									content={
										<CustomTooltip
											publishDateFill={
												CHART_COLORS.publishDate
											}
											showPublishedDateLabel={
												isPreviousPeriodButtonDisabled
											}
										/>
									}
									cursor={
										validAnalyticsConnection &&
										histogram.length !== 0 &&
										!publishedToday
									}
									formatter={(value, name) => {
										return [
											numberFormat(languageTag, value),
											keyToTranslatedLabelValue(name),
											keyToIconType(name),
										];
									}}
									labelFormatter={
										dateFormatters.formatLongDate
									}
									separator=": "
								/>
							)}

							{keyList.map((keyName) => {
								const color = keyToHexColor(keyName);
								const shape = keyToIconType(keyName);

								return (
									<Line
										activeDot={
											<CustomActiveDot shape={shape} />
										}
										dataKey={keyName}
										dot={<CustomDot shape={shape} />}
										fill={color}
										isAnimationActive={false}
										key={keyName}
										stroke={color}
										strokeWidth={CHART_SIZES.lineWidth}
										type="monotone"
									/>
								);
							})}

							{validAnalyticsConnection && !publishedToday && (
								<ReferenceDot
									isFront={true}
									r={4}
									stroke={CHART_COLORS.publishDate}
									strokeWidth={CHART_SIZES.lineWidth}
									x={referenceDotPosition}
									y={0}
								/>
							)}
						</LineChart>
					</div>
				</div>
			) : null}
		</>
	);
}

function allSettled(promises) {
	return Promise.all(
		promises.map((promise) => {
			return promise
				.then((value) => {
					return {status: 'fulfilled', value};
				})
				.catch((reason) => {
					return {reason, status: 'rejected'};
				});
		})
	);
}

Chart.propTypes = {
	dataProviders: PropTypes.arrayOf(PropTypes.func).isRequired,
	publishDate: PropTypes.string.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		})
	).isRequired,
};
