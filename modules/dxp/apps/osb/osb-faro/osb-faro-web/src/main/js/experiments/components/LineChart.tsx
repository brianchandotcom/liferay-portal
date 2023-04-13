// @ts-nocheck - Fix it at this LRAC-13388

import * as d3 from 'd3';
import React, {useState} from 'react';
import {ANIMATION_DURATION, AXIS, getAxisTickText} from 'shared/util/recharts';
import {
	Area,
	CartesianGrid,
	ComposedChart,
	Legend,
	Line,
	ResponsiveContainer,
	Tooltip as TooltipRechart,
	XAxis,
	YAxis
} from 'recharts';
import {CHART_COLOR_NAMES} from 'shared/components/Chart';
import {Data, DataPoint} from 'experiments/util/types';
import {getAxisMeasuresFromData} from 'shared/util/charts';
import {getDate} from 'shared/util/date';
import {getShortIntervals} from 'experiments/util/experiments';

const {stark: CHART_BLUE} = CHART_COLOR_NAMES;

const CLASSNAME = 'analytics-experiments-line-chart';

const AREA_TYPE = 'area';
const LINE_TYPE = 'line';

type ChartData = {
	data: Array<Data>;
	name: string;
	color: string;
};

type Format = (value: Date) => Function | string;

type GetYTicks = (data: Array<ChartData>, format: Function) => YTick;

type IsEmptyData = (data: Array<number>) => boolean;

type YTick = {
	format: Format;
	values?: Array<Date>;
};
interface Tooltip extends React.HTMLAttributes<HTMLElement> {
	dataPoint: Array<DataPoint>;
}

interface ILineChartProps extends React.HTMLAttributes<HTMLElement> {
	chartType?: string;
	data: Array<ChartData>;
	format: Function;
	height?: number;
	intervals: any;
	Tooltip?: React.FC<Tooltip>;
}

const getYTicks: GetYTicks = (chartDataList, format) => {
	let yTick: YTick = {
		format: value => format(value)
	};

	const combinedData = chartDataList
		.map(({data}) => data.map(({value}) => Number(value)))
		.reduce(
			(prevDataArray, currDataArray) => [
				...prevDataArray,
				...currDataArray
			],
			[]
		);

	yTick = {
		...yTick,
		...(!isEmptyData(combinedData)
			? {values: getAxisMeasuresFromData(combinedData).intervals}
			: {format: () => format(0)})
	};

	return yTick;
};

const isEmptyData: IsEmptyData = data =>
	!data.filter(value => value > 0).length;

const LineChart: React.FC<ILineChartProps> = ({
	Tooltip,
	chartType = LINE_TYPE,
	data,
	format,
	height = 320,
	intervals
}) => {
	const [legendHoverItem, setLegendHoverItem] = useState();

	const yTick = getYTicks(data, format);

	// Shorten intervals of tickX
	if (intervals.length >= 12) {
		intervals = getShortIntervals(intervals);
	}

	return (
		<div className={CLASSNAME}>
			<ResponsiveContainer height={height}>
				<ComposedChart>
					<CartesianGrid
						stroke={AXIS.gridStroke}
						strokeDasharray='3 3'
						vertical={false}
					/>

					<XAxis
						allowDuplicatedCategory={false}
						axisLine={{stroke: AXIS.borderStroke}}
						dataKey='key'
						interval='preserveStart'
						tickFormatter={date =>
							d3.utcFormat('%b %-d')(getDate(date))
						}
						tickLine={false}
						ticks={intervals}
						type='category'
					/>

					<XAxis
						axisLine={{stroke: AXIS.borderStroke}}
						dataKey='key'
						interval='preserveStart'
						orientation='top'
						stroke={AXIS.gridStroke}
						tick={false}
						tickLine={false}
						ticks={intervals}
						xAxisId='top'
					/>

					<YAxis
						axisLine={{stroke: AXIS.borderStroke}}
						stroke={AXIS.gridStroke}
						tick={getAxisTickText('y', yTick.format)}
						tickLine={false}
						ticks={yTick.values}
						type='number'
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

					{chartType === AREA_TYPE &&
						data.map(area => (
							<Area
								animationDuration={ANIMATION_DURATION.line}
								data={area.data}
								dataKey='value'
								dot={false}
								fill={area.color || CHART_BLUE}
								fillOpacity={
									legendHoverItem === area.name ||
									!legendHoverItem
										? 0.1
										: 0.2
								}
								key={area.name}
								legendType='circle'
								name={area.name}
								stroke={area.color || CHART_BLUE}
								strokeOpacity={
									legendHoverItem === area.name ||
									!legendHoverItem
										? 1
										: 0.2
								}
								strokeWidth='2'
							/>
						))}

					{chartType === LINE_TYPE &&
						data.map(line => (
							<Line
								animationDuration={ANIMATION_DURATION.line}
								data={line.data}
								dataKey='value'
								dot={false}
								key={line.name}
								legendType='circle'
								name={line.name}
								stroke={line.color || CHART_BLUE}
								strokeOpacity={
									legendHoverItem === line.name ||
									!legendHoverItem
										? 1
										: 0.2
								}
								strokeWidth='2'
							/>
						))}

					<TooltipRechart
						content={({active, payload}) => {
							if (active && !!payload?.length) {
								return <Tooltip dataPoint={payload} />;
							}
						}}
					/>

					<Legend
						align='right'
						iconSize={8}
						onMouseEnter={({value}) => setLegendHoverItem(value)}
						onMouseLeave={() => setLegendHoverItem(null)}
						verticalAlign='bottom'
						wrapperStyle={{
							bottom: 0,
							color: AXIS.textColor,
							fontSize: '14px',
							lineHeight: '21px',
							right: 0
						}}
					/>
				</ComposedChart>
			</ResponsiveContainer>
		</div>
	);
};

export default LineChart;
