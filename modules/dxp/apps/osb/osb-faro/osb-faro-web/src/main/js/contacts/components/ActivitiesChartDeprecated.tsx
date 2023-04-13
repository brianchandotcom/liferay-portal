import ComposedChartWithEmptyState from 'shared/components/ComposedChartWithEmptyState';
import React, {useRef, useState} from 'react';
import URLConstants from 'shared/util/url-constants';
import {
	ANIMATION_DURATION,
	AXIS,
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
	onPointSelect: ({index}) => void;
	rangeSelectors?: RangeSelectors;
	selectedPoint: number;
	tooltipRenderRows?: (
		data: T
	) => Array<{
		label: string;
		value: any;
	}>;
}

interface IActivitiesHistory<initDateType = number> {
	intervalInitDate: initDateType;
	totalElements: number;
}

const ActivitiesChart: React.FC<IChartProps<IActivitiesHistory<number>>> = ({
	alwaysShowSelectedTooltip = false,
	hasSelectedPoint,
	height = 340,
	history = [],
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
		if ((active || hasSelectedPoint) && !!payload.length) {
			const {intervalInitDate, totalElements} = get(
				payload,
				[0, 'payload'],
				history[selectedPoint]
			);

			return (
				<RechartsTooltip
					dateTitle={getDateTitle(
						dateKeysIMap.get(intervalInitDate),
						rangeSelectors.rangeKey,
						interval
					)}
					rows={[
						{
							label: Liferay.Language.get('activities'),
							value: totalElements.toLocaleString()
						}
					]}
					title={Liferay.Language.get('activities')}
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

	const yAxisWidth = getYAxisWidth(history, 'totalElements');

	return (
		<ComposedChartWithEmptyState
			emptyDescription={
				<>
					<span className='mr-1'>
						{Liferay.Language.get(
							'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
						)}
					</span>

					<a
						href={URLConstants.AccountActivitiesDocumentationLink}
						key='DOCUMENTATION'
						target='_blank'
					>
						{Liferay.Language.get(
							'learn-more-about-account-activities'
						)}
					</a>
				</>
			}
			emptyTitle={Liferay.Language.get(
				'there-is-no-data-for-account-activities'
			)}
			showEmptyState={!intervals.length}
		>
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

							onPointSelect({
								index: pointData.activeTooltipIndex
							});
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
						name={Liferay.Language.get('activities')}
						stroke={AXIS.gridStroke}
						tick={getAxisTickText('y')}
						tickCount={6}
						tickLine={false}
						type='number'
						width={yAxisWidth}
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
								? history[selectedPoint]?.intervalInitDate
								: null
						}
					/>

					<Bar
						animationDuration={ANIMATION_DURATION.bar}
						dataKey='totalElements'
						fill={CHART_BLUE}
						onMouseEnter={(e, index) => setHoverIndex(index)}
						onMouseLeave={() => setHoverIndex(-1)}
					>
						{history.map((entry, index) => (
							<Cell
								fill={getBarColor(
									index,
									hoverIndex,
									selectedPoint
								)}
								key={`cell-${index}`}
							/>
						))}
					</Bar>
				</ComposedChart>
			</ResponsiveContainer>
		</ComposedChartWithEmptyState>
	);
};

export default ActivitiesChart;
