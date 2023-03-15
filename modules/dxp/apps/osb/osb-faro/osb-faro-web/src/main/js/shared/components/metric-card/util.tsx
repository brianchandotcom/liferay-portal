import MetricValue from './MetricValue';
import React, {Fragment} from 'react';
import Trend from 'shared/components/Trend';
import {CHART_COLOR_NAMES} from 'shared/components/Chart';
import {get, last} from 'lodash';
import {
	getAxisFormatter,
	getDataFormatter,
	getIntervals,
	getMetricFormatter
} from 'shared/util/charts';
import {getIcon, getStatsColor} from 'shared/util/metrics';
import {INTERVAL_KEY_MAP} from 'shared/util/time';
import {MetricType} from './metrics';
import {toRounded} from 'shared/util/numbers';
import {toUnix} from 'shared/util/date';

const CHART_DATA_ID_1 = 'data_1';
const CHART_DATA_ID_2 = 'data_2';
const CHART_DATA_PREVIOUS = 'data_previous';
const METRIC_TOOLTIP_LABEL_MAP = {
	bounceRateMetric: Liferay.Language.get('avg-bounce')
};

const {
	martell: CHART_GREEN,
	martellL2: CHART_GREEN_L2,
	mormont: CHART_ORANGE,
	stark: CHART_BLUE,
	starkL2: CHART_BLUE_L2
} = CHART_COLOR_NAMES;

export const Icons = {
	negative: 'caret-bottom-l',
	neutral: undefined,
	positive: 'caret-top-l'
};

const PREVIOUS_PERIOD_VISITORS_COLOR = '#393A4A';

type TBuildTabs = (props: {
	activeItemIndex: number;
	items: {
		content: {
			details: {
				color: string;
				icon: string;
				label: string;
			};
			title: string;
			type: MetricType;
			value: string;
		};
	}[];
	onActiveItemIndexChange: (index: number) => void;
}) => {
	onClick: () => void | ((index: number) => void);
	secondaryInfo: React.ReactElement;
}[];

export const buildTabs: TBuildTabs = ({
	activeItemIndex,
	items,
	onActiveItemIndexChange
}) =>
	items.map(({content}, index) => {
		const {details, title, type, value} = content;
		const {color, icon, label} = details;

		return {
			onClick: () => {
				if (activeItemIndex !== index && !onActiveItemIndexChange)
					return () => {};

				return onActiveItemIndexChange(index);
			},
			secondaryInfo: (
				<span>
					<span className='primary-content'>
						<MetricValue type={type} value={value} />
					</span>

					{label && <Trend color={color} icon={icon} label={label} />}
				</span>
			),
			tabId: index,
			title
		};
	});

export const getMetricName = (activeItemIndex, metrics) =>
	metrics.map(({name}) => name)[activeItemIndex];

export const getActiveItem = (retVal, compareToPrevious) => {
	if (!retVal) {
		return {
			chartData: [],
			intervals: [],
			timeline: []
		};
	}

	const chartData = retVal.data.slice(0, -1);
	const timeline = last(retVal.data);

	if (!compareToPrevious && retVal.asymmetricComparison) {
		retVal = {
			...retVal,
			chartData: chartData.map(dataSet => ({
				...dataSet,
				data: dataSet.data.slice(1)
			})),
			intervals: retVal.intervals.slice(1),

			// @ts-ignore

			timeline: {data: timeline.data.slice(1), id: timeline.id}
		};

		if (retVal.compositeData) {
			const compositeDataKeys = Object.keys(retVal.compositeData);

			retVal = {
				...retVal,
				compositeData: compositeDataKeys.reduce((acc, val) => {
					acc = {
						...acc,
						[val]: retVal.compositeData[val].slice(1)
					};

					return acc;
				}, {})
			};
		}
	} else if (compareToPrevious && retVal.asymmetricComparison) {
		retVal = {
			...retVal,
			chartData: chartData.map(dataSet => ({
				...dataSet,
				data:
					dataSet.id !== 'data_previous'
						? [null, ...dataSet.data.slice(1)]
						: dataSet.data
			})),
			timeline
		};
	} else {
		retVal = {
			...retVal,
			chartData,
			timeline
		};
	}

	return retVal;
};

export const getPreviousValueFromCompositeData = (
	compositeData,
	dataName,
	dateKey
) => {
	const data = get(compositeData, dataName);

	if (data) {
		return data.find(val => toUnix(val.key) === dateKey).previousValue;
	}
};

export const getRegexType = (type: MetricType): RegExp => {
	if (type === MetricType.Ratings) {
		return /([/][0-9]+)/g;
	} else {
		return /([a-zA-Z%])+/g;
	}
};

export const formatValue = (
	value: string,
	regex: RegExp
): React.ReactElement[] => {
	const items = value.split(' ');

	return items.map((item, i) => {
		const [value, unit] = item.split(regex);

		return (
			<Fragment key={i}>
				{value}

				<span className='metric-value-letter'>{unit}</span>
			</Fragment>
		);
	});
};

export const getMetricCardTabsData = (result, metrics) =>
	metrics.map(({name, title, type}) => {
		const metricFormatter = getMetricFormatter(type);

		return {
			content: {
				details: {
					color: getStatsColor(
						result[name].trend.trendClassification
					),
					icon: getIcon(result[name].trend.percentage),
					label: `${toRounded(
						Math.abs(result[name].trend.percentage)
					)}%`
				},
				name,
				title,
				type,
				value: metricFormatter(result[name].value)
			}
		};
	});

export const getMetricsData = (
	result,
	metrics,
	rangeSelectors = {},
	chartDataMapFn = getMetricsChartData,
	interval = INTERVAL_KEY_MAP.day
) =>
	metrics.map(({compositeMetrics, name, title, tooltipTitle, type}) =>
		getMetricData({
			chartDataMapFn,
			compositeMetrics,
			interval,
			name,
			rangeSelectors,
			result,
			title,
			tooltipTitle,
			type
		})
	);

export const getMetricsChartData = ({
	histogram,
	name,
	title,
	tooltipTitle,
	type
}) => [
	{
		color: CHART_BLUE,
		data: getDataFormatter(type)(histogram.map(({value}) => value)),
		id: CHART_DATA_ID_1,
		name: tooltipTitle || METRIC_TOOLTIP_LABEL_MAP[name] || title,
		tooltipTitle
	},
	{
		color: CHART_BLUE_L2,
		data: getDataFormatter(type)(
			histogram.map(({previousValue}) => previousValue)
		),
		id: CHART_DATA_PREVIOUS,
		name: Liferay.Language.get('previous-period')
	},
	{
		data: histogram.map(({key}) => key),
		id: 'x'
	}
];

export const convertHistogramKeysToDate = ({
	key,
	previousValueKey,
	valueKey,
	...otherParams
}) => ({
	key: toUnix(key),
	previousValueKey: previousValueKey.split('/').map(toUnix),
	valueKey: valueKey.split('/').map(toUnix),
	...otherParams
});

export const getMetricData = ({
	chartDataMapFn = getMetricsChartData,
	compositeMetrics,
	interval = INTERVAL_KEY_MAP.day,
	name,
	rangeSelectors,
	result,
	title,
	tooltipTitle,
	type
}) => {
	const metricFormatter = getMetricFormatter(type);

	const histogram = result[name].histogram.metrics.map(
		convertHistogramKeysToDate
	);

	const compositeData = compositeMetrics
		? {
				compositeContent: compositeMetrics.reduce(
					(
						acc,
						{
							name: compositeMetricName,
							title: compositeMetricTitle,
							type: compositeMetricType
						}
					) => {
						acc[compositeMetricName] = {
							details: {
								color: getStatsColor(
									result[compositeMetricName].trend
										.trendClassification
								),
								icon: getIcon(
									result[compositeMetricName].trend.percentage
								),
								label: `${toRounded(
									Math.abs(
										result[compositeMetricName].trend
											.percentage
									)
								)}%`
							},
							name: compositeMetricName,
							title: compositeMetricTitle,
							type: compositeMetricType,
							value: metricFormatter(
								result[compositeMetricName].value
							)
						};

						return acc;
					},
					{}
				),
				compositeData: compositeMetrics.reduce(
					(acc, {name: compositeMetricName}) => {
						acc[compositeMetricName] =
							result[compositeMetricName].histogram.metrics;

						return acc;
					},
					{}
				)
		  }
		: {};

	const dateKeysIMap = new Map(
		histogram.map(({key, valueKey}) => [key, valueKey])
	);

	return {
		...compositeData,
		asymmetricComparison: result[name].histogram.asymmetricComparison,
		content: {
			details: {
				color: getStatsColor(result[name].trend.trendClassification),
				icon: getIcon(result[name].trend.percentage),
				label: `${toRounded(Math.abs(result[name].trend.percentage))}%`
			},
			name,
			title,
			type,
			value: metricFormatter(result[name].value)
		},
		data: chartDataMapFn({
			...compositeData,
			histogram,
			name,
			title,
			tooltipTitle,
			type
		}),
		dateKeysIMap,
		format: getAxisFormatter(type),
		intervals: getIntervals(
			rangeSelectors.rangeKey,
			histogram.map(({key}) => key),
			interval,
			dateKeysIMap
		),
		prevDateKeysIMap: new Map(
			histogram.map(({key, previousValueKey}) => [key, previousValueKey])
		)
	};
};

export const getSiteMetricsChartData = ({
	compositeData,
	histogram,
	name,
	title,
	tooltipTitle,
	type
}) =>
	name === 'visitorsMetric'
		? [
				{
					color: CHART_BLUE,
					data: getDataFormatter(type)(
						compositeData.knownVisitorsMetric.map(
							({value}) => value
						)
					),
					dataName: 'knownVisitorsMetric',
					id: CHART_DATA_ID_1,
					name: Liferay.Language.get('known-visitors'),
					tooltipTitle: Liferay.Language.get('known'),
					type: 'bar'
				},
				{
					color: CHART_ORANGE,
					data: getDataFormatter(type)(
						compositeData.anonymousVisitorsMetric.map(
							({value}) => value
						)
					),
					dataName: 'anonymousVisitorsMetric',
					id: CHART_DATA_ID_2,
					name: Liferay.Language.get('anonymous-visitors'),
					tooltipTitle: Liferay.Language.get('anonymous'),
					type: 'bar'
				},
				{
					color: PREVIOUS_PERIOD_VISITORS_COLOR,
					data: getDataFormatter(type)(
						histogram.map(({previousValue}) => previousValue)
					),
					id: CHART_DATA_PREVIOUS,
					name: Liferay.Language.get('previous-period'),
					type: 'line'
				},
				{
					data: histogram.map(({key}) => key),
					id: 'x'
				}
		  ]
		: getMetricsChartData({histogram, name, title, tooltipTitle, type}).map(
				data =>
					[CHART_DATA_ID_1, CHART_DATA_PREVIOUS].includes(data.id)
						? {
								...data,
								color:
									data.id === CHART_DATA_PREVIOUS
										? CHART_GREEN_L2
										: CHART_GREEN
						  }
						: data
		  );
