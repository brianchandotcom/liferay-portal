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
import className from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
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

import ConnectionContext from '../state/context';
import {useChartState} from '../utils/chartState';
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
const LAST_7_DAYS = 'last-7-days';

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
	const metricValues = METRICS_STATIC_VALUES[key];

	return metricValues ? metricValues.langKey : key;
}

function keyToHexColor(key) {
	const metricValues = METRICS_STATIC_VALUES[key];

	return metricValues ? metricValues.color : '#666666';
}

function keyToIconType(key) {
	const metricValues = METRICS_STATIC_VALUES[key];

	return metricValues ? metricValues.iconType : 'line';
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

/*
 * It generates a set of functions used to produce
 * internationalized date related content.
 */
const generateDateFormatters = key => {
	/*
	 * Given 2 date objects it produces a user friendly date interval
	 *
	 * For 'en-US'
	 * [Date, Date] => '16 - Jun 21, 2020'
	 */
	function formatChartTitle([initialDate, finalDate]) {
		const singleDayDateRange =
			finalDate - initialDate <= 1000 * 60 * 60 * 24;

		const dateFormatter = (
			date,
			options = {
				day: 'numeric',
				month: 'short',
				year: 'numeric',
			}
		) => Intl.DateTimeFormat([key], options).format(date);

		const equalMonth = initialDate.getMonth() === finalDate.getMonth();
		const equalYear = initialDate.getYear() === finalDate.getYear();

		const initialDateOptions = {
			day: 'numeric',
			month: equalMonth && equalYear ? undefined : 'short',
			year: equalYear ? undefined : 'numeric',
		};

		if (singleDayDateRange) {
			return dateFormatter(finalDate);
		}

		return `${dateFormatter(
			initialDate,
			initialDateOptions
		)} - ${dateFormatter(finalDate)}`;
	}

	/*
	 * Given a date like string it produces a internationalized long date
	 *
	 * For 'en-US'
	 * String => '06/17/2020'
	 */
	function formatLongDate(value) {
		return Intl.DateTimeFormat([key]).format(new Date(value));
	}

	/*
	 * Given a date like string produces the day of the month
	 *
	 * For 'en-US'
	 * String => '16'
	 */
	function formatNumericDay(value) {
		return Intl.DateTimeFormat([key], {
			day: 'numeric',
		}).format(new Date(value));
	}

	/*
	 * Given a date like string produces the hour of the day
	 *
	 * For 'en-US'
	 * String => '04 AM'
	 */
	function formatNumericHour(value) {
		return Intl.DateTimeFormat([key], {
			hour: 'numeric',
		}).format(new Date(value));
	}

	return {
		formatChartTitle,
		formatLongDate,
		formatNumericDay,
		formatNumericHour,
	};
};

function legendFormatterGenerator(
	totals,
	languageTag,
	validAnalyticsConnection
) {
	return value => {
		const preformattedNumber = totals[value];

		return (
			<span>
				<span
					className={`custom-${keyToIconType(value)} mr-2`}
					style={{
						backgroundColor: keyToHexColor(value),
					}}
				></span>
				<span className="mr-2 text-secondary">
					{keyToTranslatedLabelValue(value)}
				</span>
				{preformattedNumber !== null && (
					<span className="font-weight-bold">
						{validAnalyticsConnection
							? numberFormat(languageTag, preformattedNumber)
							: '-'}
					</span>
				)}
			</span>
		);
	};
}

export default function Chart({
	dataProviders = [],
	defaultTimeSpanOption,
	languageTag,
	publishDate,
	timeSpanOptions,
}) {
	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const {actions, state: chartState} = useChartState({
		defaultTimeSpanOption,
		publishDate,
	});
	const isMounted = useIsMounted();

	useEffect(() => {
		let gone = false;

		actions.setLoading();

		dataProviders.map(getter => {
			getter({
				timeSpanKey: chartState.timeSpanOption,
				timeSpanOffset: chartState.timeSpanOffset,
			}).then(data => {
				if (!gone) {
					if (isMounted()) {
						const timeSpanComparator =
							chartState.timeSpanOption === LAST_24_HOURS
								? HOUR_IN_MILLISECONDS
								: DAY_IN_MILLISECONDS;

						Object.keys(data).map(key => {
							actions.addDataSetItem({
								dataSetItem: data[key],
								key,
								timeSpanComparator,
							});
						});
					}
				}
			});
		});

		return () => {
			gone = true;
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [chartState.timeSpanOption, chartState.timeSpanOffset]);

	const dateFormatters = useMemo(() => generateDateFormatters(languageTag), [
		languageTag,
	]);

	const {dataSet} = chartState;
	const {histogram, keyList} = dataSet;

	const disabledPreviousPeriodButton = useMemo(() => {
		if (histogram.length) {
			const firstDateLabel = histogram[0].label;

			const firstDate = new Date(firstDateLabel);
			const publishedDate = new Date(publishDate);

			return firstDate < publishedDate;
		}

		return true;
	}, [histogram, publishDate]);

	const referenceDotPosition = useMemo(() => {
		const publishDateISOString = new Date(publishDate).toISOString();

		return chartState.timeSpanOption === LAST_24_HOURS
			? publishDateISOString.split(':')[0].concat(':00:00')
			: publishDateISOString.split('T')[0].concat('T00:00:00');
	}, [chartState.timeSpanOption, publishDate]);

	const title = useMemo(() => {
		if (histogram.length) {
			const firstDateLabel = histogram[0].label;
			const lastDateLabel = histogram[histogram.length - 1].label;

			return dateFormatters.formatChartTitle([
				new Date(firstDateLabel),
				new Date(lastDateLabel),
			]);
		}
	}, [histogram, dateFormatters]);

	const handleTimeSpanChange = event => {
		const {value} = event.target;

		actions.changeTimeSpanOption({key: value});
	};
	const handlePreviousTimeSpanClick = () => {
		actions.previousTimeSpan();
	};
	const handleNextTimeSpanClick = () => {
		actions.nextTimeSpan();
	};

	const legendFormatter =
		dataSet &&
		legendFormatterGenerator(
			dataSet.totals,
			languageTag,
			validAnalyticsConnection
		);

	const disabledNextTimeSpan = chartState.timeSpanOffset === 0;

	const xAxisFormatter =
		chartState.timeSpanOption === LAST_24_HOURS
			? dateFormatters.formatNumericHour
			: dateFormatters.formatNumericDay;

	const lineChartWrapperClasses = className('line-chart-wrapper', {
		'line-chart-wrapper--loading': chartState.loading,
	});

	return (
		<>
			{timeSpanOptions.length && (
				<TimeSpanSelector
					disabledNextTimeSpan={disabledNextTimeSpan}
					disabledPreviousPeriodButton={disabledPreviousPeriodButton}
					onNextTimeSpanClick={handleNextTimeSpanClick}
					onPreviousTimeSpanClick={handlePreviousTimeSpanClick}
					onTimeSpanChange={handleTimeSpanChange}
					timeSpanOption={chartState.timeSpanOption}
					timeSpanOptions={timeSpanOptions}
				/>
			)}

			{dataSet ? (
				<div className={lineChartWrapperClasses}>
					{chartState.loading && (
						<ClayLoadingIndicator
							style={{
								left: `${CHART_SIZES.yAxisWidth}px`,
							}}
						/>
					)}

					{title && <h5>{title}</h5>}

					<div className="line-chart mt-3">
						<LineChart
							data={histogram}
							height={CHART_SIZES.height}
							width={CHART_SIZES.width}
						>
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
								interval={
									chartState.timeSpanOption === LAST_7_DAYS
										? 'preserveStartEnd'
										: 4
								}
								tickFormatter={xAxisFormatter}
								tickLine={false}
							/>

							<YAxis
								allowDecimals={false}
								axisLine={{
									stroke: CHART_COLORS.cartesianGrid,
								}}
								minTickGap={3}
								tickFormatter={thousandsToKilosFormater}
								tickLine={false}
								ticks={
									!validAnalyticsConnection && [0, 50, 100]
								}
								width={CHART_SIZES.yAxisWidth}
							/>

							<Tooltip
								content={
									<CustomTooltip
										publishDateFill={
											CHART_COLORS.publishDate
										}
										showPublishedDateLabel={
											disabledPreviousPeriodButton
										}
									/>
								}
								formatter={(value, name) => {
									return [
										numberFormat(languageTag, value),
										keyToTranslatedLabelValue(name),
										keyToIconType(name),
									];
								}}
								labelFormatter={dateFormatters.formatLongDate}
								separator={': '}
							/>

							<ReferenceDot
								fill={CHART_SIZES.referenceDotFill}
								r={3}
								stroke={CHART_COLORS.publishDate}
								strokeWidth={CHART_SIZES.lineWidth}
								x={referenceDotPosition}
								y={0}
							/>

							<Legend
								formatter={legendFormatter}
								iconSize={0}
								layout="vertical"
								wrapperStyle={{
									left: 0,
									paddingBottom: 0,
									paddingTop: '8px',
								}}
							/>

							{keyList.map(keyName => {
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
										key={keyName}
										stroke={color}
										strokeWidth={CHART_SIZES.lineWidth}
										type="monotone"
									/>
								);
							})}
						</LineChart>
					</div>
				</div>
			) : null}
		</>
	);
}

Chart.propTypes = {
	dataProviders: PropTypes.arrayOf(PropTypes.func).isRequired,
	defaultTimeSpanOption: PropTypes.string.isRequired,
	languageTag: PropTypes.string.isRequired,
	publishDate: PropTypes.number.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		})
	).isRequired,
};
