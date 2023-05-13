import moment from 'moment';
import React, {useRef, useState} from 'react';
import {
	ANIMATION_DURATION,
	AXIS,
	ChartTooltipRow,
	getAxisTickText,
	getYAxisWidth,
	RechartsTooltip
} from 'shared/util/recharts';
import {
	Bar,
	CartesianGrid,
	Cell,
	ComposedChart,
	ReferenceLine,
	ResponsiveContainer,
	Tooltip,
	XAxis,
	YAxis
} from 'recharts';
import {CHART_COLOR_NAMES} from 'shared/components/Chart';
import {createDateKeysIMap} from 'shared/util/intervals';
import {
	formatXAxisDate,
	getBarColor,
	getDateTitle,
	getIntervals
} from 'shared/util/charts';
import {get} from 'lodash';
import {Interval, RangeSelectors} from 'shared/types';

const {stark: CHART_BLUE} = CHART_COLOR_NAMES;

interface IChartProps<T> extends React.HTMLAttributes<HTMLElement> {
	alwaysShowSelectedTooltip: boolean;
	hasSelectedPoint?: boolean;
	height?: number;
	history: Array<T>;
	interval?: Interval;
	onAfterInit?: () => void;
	onPointSelect: (index: number) => void;
	rangeSelectors?: RangeSelectors;
	selectedPoint: number;
	tooltipRenderRows?: (
		data: T
	) => Array<{
		label: string;
		value: any;
	}>;
}

interface IActivitiesHistoryProps<initDateType = number> {
	intervalInitDate: initDateType;
	totalEvents: number;
}

const ActivitiesChart: React.FC<
	IChartProps<IActivitiesHistoryProps<number>>
> = ({
	alwaysShowSelectedTooltip = false,
	hasSelectedPoint,
	height = 340,
	history,
	interval,
	onPointSelect,
	rangeSelectors,
	selectedPoint
}) => {
	const _tooltipRef = useRef<any>();

	const [hoverIndex, setHoverIndex] = useState(-1);
	const [mouseOutside, setMouseOutside] = useState(false);
	const [selectedTooltipX, setSelectedTooltipX] = useState(null);

	const dateKeysIMap = createDateKeysIMap(
		interval,
		history,
		'intervalInitDate'
	);

	const renderTooltip = ({active, payload}) => {
		if ((active && !!payload.length) || hasSelectedPoint) {
			const {intervalInitDate, totalEvents, totalSessions} = get(
				payload,
				[0, 'payload'],
				history[selectedPoint]
			);

			const rows: ChartTooltipRow[] = [
				{
					label: Liferay.Language.get('events'),
					value: totalEvents.toLocaleString()
				},
				{
					label: Liferay.Language.get('sessions'),
					value: totalSessions.toLocaleString()
				}
			];

			if (moment.utc(intervalInitDate).isSame(moment(), 'day')) {
				rows.push({
					className: 'informative-text',
					label: Liferay.Language.get(
						'data-for-todays-events-may-vary-or-be-incomplete'
					).toUpperCase()
				});
			}

			return (
				<RechartsTooltip
					dateTitle=''
					rows={rows}
					title={getDateTitle(
						dateKeysIMap.get(intervalInitDate),
						rangeSelectors.rangeKey,
						interval
					)}
				/>
			);
		}
	};

	const intervals = getIntervals(
		rangeSelectors.rangeKey,
		history.map(({intervalInitDate}) => intervalInitDate),
		interval,
		dateKeysIMap
	);

	const showFixedTooltip = hasSelectedPoint && mouseOutside;

	const yAxisWidth = getYAxisWidth(history, 'totalEvents');

	return (
		<ResponsiveContainer height={height}>
			<ComposedChart
				data={history}
				onClick={pointData => {
					if (alwaysShowSelectedTooltip && pointData) {
						if (_tooltipRef) {
							const {
								getTranslate,
								props: {viewBox},
								state: {boxWidth}
							} = _tooltipRef.current;

							setSelectedTooltipX(
								getTranslate({
									key: 'x',
									tooltipDimension: boxWidth,
									viewBoxDimension: viewBox.width
								})
							);
						}

						onPointSelect(pointData.activeTooltipIndex);
					}
				}}
				onMouseLeave={() => setMouseOutside(true)}
				onMouseMove={() => setMouseOutside(false)}
			>
				<CartesianGrid
					stroke={AXIS.gridStroke}
					strokeDasharray='3 3'
					vertical={false}
				/>

				<XAxis
					axisLine={{stroke: AXIS.borderStroke}}
					dataKey='intervalInitDate'
					domain={['dataMin', 'dataMax']}
					interval='preserveStart'
					padding={{left: 20, right: 20}}
					tick={getAxisTickText('x', value =>
						formatXAxisDate(
							value,
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
					axisLine={{stroke: AXIS.borderStroke}}
					dataKey='intervalInitDate'
					orientation='top'
					stroke={AXIS.gridStroke}
					tick={false}
					tickLine={false}
					xAxisId='top'
				/>

				<YAxis
					allowDecimals={false}
					axisLine={{stroke: AXIS.borderStroke}}
					name={Liferay.Language.get('events')}
					stroke={AXIS.gridStroke}
					tick={getAxisTickText('y')}
					tickCount={6}
					tickLine={false}
					type='number'
					width={yAxisWidth}
				/>

				<YAxis
					axisLine={{stroke: AXIS.borderStroke}}
					orientation='right'
					stroke={AXIS.gridStroke}
					tick={false}
					tickLine={false}
					type='number'
					width={1}
					yAxisId='right'
				/>

				<Tooltip
					content={renderTooltip}
					cursor={{stroke: CHART_BLUE}}
					position={
						showFixedTooltip
							? {
									x: selectedTooltipX
							  }
							: null
					}
					ref={_tooltipRef}
					wrapperStyle={
						showFixedTooltip
							? {
									visibility: 'visible'
							  }
							: null
					}
				/>

				<ReferenceLine
					strokeWidth={1}
					x={
						showFixedTooltip
							? history[selectedPoint].intervalInitDate
							: null
					}
				/>

				<Bar
					animationDuration={ANIMATION_DURATION.bar}
					dataKey='totalEvents'
					fill={CHART_BLUE}
					onMouseEnter={(e, index) => setHoverIndex(index)}
					onMouseLeave={() => setHoverIndex(-1)}
				>
					{history.map((entry, index) => (
						<Cell
							fill={getBarColor(index, hoverIndex, selectedPoint)}
							key={`cell-${index}`}
						/>
					))}
				</Bar>
			</ComposedChart>
		</ResponsiveContainer>
	);
};

export default ActivitiesChart;
