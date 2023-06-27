// @ts-nocheck - Fix it at this LRAC-13388

import Checkbox from 'shared/components/Checkbox';
import ComposedChartWithEmptyState from 'shared/components/ComposedChartWithEmptyState';
import MetricStateRenderer from './MetricStateRenderer';
import MetricTooltip from './MetricTooltipt';
import React, {useMemo, useState} from 'react';
import URLConstants from 'shared/util/url-constants';
import {
	ANIMATION_DURATION,
	AXIS,
	getAxisTickText,
	getTextWidth,
	getYAxisLabel
} from 'shared/util/recharts';
import {
	Bar,
	CartesianGrid,
	Cell,
	ComposedChart,
	Legend,
	Line,
	ResponsiveContainer,
	Tooltip,
	XAxis,
	YAxis
} from 'recharts';
import {formatXAxisDate} from 'shared/util/charts';
import {getActiveItem, getMetricData, getMetricName} from './util';
import {ICommonMetricProps, useActions, useData} from './MetricBaseCard';
import {useMetricQuery} from './hooks';

export const CHART_DATA_PREVIOUS = 'data_previous';
export const METRIC_TOOLTIP_LABEL_MAP = {
	bounceRateMetric: Liferay.Language.get('avg-bounce')
};

interface IMetricChartProps extends Partial<ICommonMetricProps> {
	// @deprecated. It is used only on CustomAsset
	onCompareToPreviousChange?: (compareToPrevious: boolean) => void;
	chartHeight?: number;
	compareToPrevious: boolean;
	data: any;
}

export const MetricChart: React.FC<IMetricChartProps> = ({
	chartHeight: height = 350,
	compareToPrevious,
	data,
	interval,
	onCompareToPreviousChange,
	rangeSelectors
}) => {
	const {changeCompareToPrevious} = useActions();

	const [hoveredLegendItem, setHoveredLegendItem] = useState(null);
	const [hoverIndex, setHoverIndex] = useState(-1);

	const {
		chartData,
		compositeContent,
		content: {name, title},
		dateKeysIMap,
		format,
		intervals,
		timeline
	} = useMemo(() => getActiveItem(data, compareToPrevious), [
		compareToPrevious,
		data
	]);

	const dataIds = chartData.map(item => item.id);

	let yAxisWidth = 40;

	const combinedData = useMemo(
		() =>
			timeline.data.map((date, i) =>
				dataIds.reduce(
					(acc, item, j) => {
						acc[item] = chartData[j].data[i];

						const textWidth = getTextWidth(
							format(chartData[j].data[i])
						);

						if (yAxisWidth < textWidth) {
							yAxisWidth = textWidth;
						}

						return acc;
					},
					{
						date,
						dateString: formatXAxisDate(
							date,
							rangeSelectors.rangeKey,
							interval,
							dateKeysIMap
						)
					}
				)
			),
		[timeline, dataIds, chartData]
	);

	const barData = chartData.filter(item => item.type === 'bar');

	const lineData = chartData.filter(item => {
		if (!compareToPrevious && item.id === CHART_DATA_PREVIOUS) {
			return;
		}

		return item.type !== 'bar';
	});

	return (
		<>
			<ResponsiveContainer height={height}>
				<ComposedChart data={combinedData}>
					<CartesianGrid
						stroke={AXIS.gridStroke}
						strokeDasharray='3 3'
						vertical={false}
					/>

					<XAxis
						axisLine={{
							stroke: AXIS.borderStroke
						}}
						dataKey='date'
						interval='preserveStart'
						stroke={AXIS.gridStroke}
						tick={getAxisTickText('x', int =>
							formatXAxisDate(
								int,
								rangeSelectors.rangeKey,
								interval,
								dateKeysIMap
							)
						)}
						tickLine={false}
						tickMargin={12}
						ticks={intervals}
					/>

					<XAxis
						axisLine={{
							stroke: AXIS.borderStroke
						}}
						dataKey='date'
						orientation='top'
						stroke={AXIS.gridStroke}
						tick={false}
						tickLine={false}
						xAxisId='top'
					/>

					<YAxis
						axisLine={{
							stroke: AXIS.borderStroke
						}}
						label={getYAxisLabel(
							METRIC_TOOLTIP_LABEL_MAP[name] || title,
							'left',
							yAxisWidth
						)}
						stroke={AXIS.gridStroke}
						tick={getAxisTickText('y', format)}
						tickLine={false}
						width={yAxisWidth}
					/>

					<YAxis
						axisLine={{
							stroke: AXIS.borderStroke
						}}
						orientation='right'
						stroke={AXIS.gridStroke}
						tick={false}
						tickLine={false}
						width={12}
						yAxisId='right'
					/>

					<Tooltip
						content={props => (
							<MetricTooltip
								data={data}
								interval={interval}
								rangeSelectors={rangeSelectors}
								{...props}
							/>
						)}
						cursor={!intervals.length ? false : true}
					/>

					<Legend
						align='right'
						formatter={(label, {dataKey}) => {
							if (
								compositeContent &&
								dataKey !== 'data_previous'
							) {
								const {dataName} = barData.find(
									({id}) => id === dataKey
								);

								const {value} = compositeContent[dataName];

								return (
									<>
										{`${label}:`}

										<b className='ml-1'>{value}</b>
									</>
								);
							}

							return label;
						}}
						iconSize={8}
						onMouseEnter={({dataKey}) =>
							setHoveredLegendItem(dataKey)
						}
						onMouseLeave={() => setHoveredLegendItem(null)}
						verticalAlign='bottom'
						wrapperStyle={{
							bottom: 'auto',
							color: AXIS.textColor,
							fontSize: '14px',
							lineHeight: '21px'
						}}
					/>

					{barData.map(item => (
						<Bar
							animationDuration={ANIMATION_DURATION.bar}
							dataKey={item.id}
							fill={item.color}
							fillOpacity={
								hoveredLegendItem === item.id ||
								!hoveredLegendItem
									? 1
									: 0.2
							}
							key={item.id}
							legendType='circle'
							name={item.name}
							onMouseEnter={(e, index) => setHoverIndex(index)}
							onMouseLeave={() => setHoverIndex(-1)}
							stackId='a'
						>
							{item.data.map((entry, index) => (
								<Cell
									fill={item.color}
									key={`cell-${index}`}
									opacity={index === hoverIndex ? 0.75 : 1}
								/>
							))}
						</Bar>
					))}

					{lineData.map(item => (
						<Line
							animationDuration={ANIMATION_DURATION.line}
							dataKey={item.id}
							dot={false}
							fill={item.color}
							key={item.id}
							legendType='plainline'
							name={item.name}
							onMouseEnter={(e, index) => setHoverIndex(index)}
							onMouseLeave={() => setHoverIndex(-1)}
							stroke={item.color}
							strokeDasharray={
								item.id === CHART_DATA_PREVIOUS
									? '5 5'
									: undefined
							}
							strokeOpacity={
								hoveredLegendItem === item.id ||
								!hoveredLegendItem
									? 1
									: 0.2
							}
							strokeWidth={2}
							type='linear'
						/>
					))}
				</ComposedChart>
			</ResponsiveContainer>

			<div className='analytics-metrics-chart-sub-content-wrapper'>
				<Checkbox
					checked={compareToPrevious}
					label={Liferay.Language.get('compare-to-previous')}
					onChange={() => {
						changeCompareToPrevious(!compareToPrevious);

						onCompareToPreviousChange &&
							onCompareToPreviousChange(!compareToPrevious);
					}}
				/>
			</div>

			<div
				data-qa-is-chart-populated={data.data[0].data.some(
					value => value
				)}
			/>
		</>
	);
};

interface IMetricChartRendererProps extends ICommonMetricProps {}

const MetricChartRenderer: React.FC<IMetricChartRendererProps> = ({
	filters,
	interval,
	rangeSelectors
}) => {
	const {activeItemIndex, metrics, queries, variables} = useData();

	const metricName = getMetricName(activeItemIndex, metrics);

	const {data, error, loading} = useMetricQuery({
		filters,
		interval,
		Query: queries.MetricQuery(metricName),
		rangeSelectors,
		variables
	});

	return (
		<MetricStateRenderer error={error} loading={loading}>
			<MetricChartWrapper
				data={data}
				interval={interval}
				metricName={metricName}
				rangeSelectors={rangeSelectors}
			/>
		</MetricStateRenderer>
	);
};

interface IMetricChartWrapperProps extends Partial<IMetricChartRendererProps> {
	data: any;
	metricName: string;
}

const MetricChartWrapper: React.FC<IMetricChartWrapperProps> = ({
	data,
	interval,
	metricName,
	rangeSelectors
}) => {
	const {chartDataMapFn, compareToPrevious, metrics, queries} = useData();

	const {compositeMetrics, name, title, tooltipTitle, type} = metrics.find(
		({name}) => metricName === name
	);

	const formattedData = useMemo(
		() =>
			getMetricData({
				chartDataMapFn,
				compositeMetrics,
				interval,
				name,
				rangeSelectors,
				result: data[queries.name],
				title,
				tooltipTitle,
				type
			}),
		[data, interval, metricName, rangeSelectors]
	);

	const {intervals} = useMemo(
		() => getActiveItem(formattedData, compareToPrevious),
		[formattedData, compareToPrevious]
	);

	return (
		<div className='analytics-metrics-chart'>
			<ComposedChartWithEmptyState
				emptyDescription={
					<>
						<span className='mr-1'>
							{Liferay.Language.get(
								'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
							)}
						</span>

						<a
							href={URLConstants.SitesDashboardSitesActivities}
							key='DOCUMENTATION'
							target='_blank'
						>
							{Liferay.Language.get(
								'learn-more-about-site-activity'
							)}
						</a>
					</>
				}
				emptyTitle={Liferay.Language.get(
					'there-is-no-data-for-site-activity'
				)}
				showEmptyState={!intervals.length}
			>
				<MetricChart
					compareToPrevious={compareToPrevious}
					data={formattedData}
					interval={interval}
					rangeSelectors={rangeSelectors}
				/>
			</ComposedChartWithEmptyState>
		</div>
	);
};

export default MetricChartRenderer;
