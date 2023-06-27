// @ts-nocheck - Fix it at this LRAC-13388

import ComposedChartWithEmptyState from 'shared/components/ComposedChartWithEmptyState';
import Loading from 'shared/components/Loading';
import React, {useState} from 'react';
import URLConstants from 'shared/util/url-constants';
import {
	ANIMATION_DURATION,
	AXIS,
	getAxisTickText,
	getYAxisLabel,
	getYAxisWidth,
	RechartsTooltip
} from 'shared/util/recharts';
import {
	Bar,
	CartesianGrid,
	Cell,
	ComposedChart,
	Legend,
	ResponsiveContainer,
	Tooltip,
	XAxis,
	YAxis
} from 'recharts';
import {CHART_COLOR_NAMES} from 'shared/components/Chart';
import {
	formatXAxisDate,
	getBarColor,
	getDateTitle,
	getIntervals
} from 'shared/util/charts';
import {get} from 'lodash';
import {Interval} from 'shared/types';
import {Map} from 'immutable';
import {RangeSelectors} from 'shared/types';
import {toThousands} from 'shared/util/numbers';

const {mormont: CHART_ORANGE, stark: CHART_BLUE} = CHART_COLOR_NAMES;

interface IActiveIndividualsChartProps {
	data: {
		anonymousVisitors: number;
		intervalInitDate: number;
		knownVisitors: number;
		visitors: number;
	}[];
	dateKeysIMap: Map<number, [number, number?]>;
	height: number;
	interval: Interval;
	loading: Boolean;
	rangeSelectors: RangeSelectors;
}

const ActiveIndividualsChart: React.FC<IActiveIndividualsChartProps> = ({
	data = [],
	dateKeysIMap,
	height = 360,
	interval,
	loading,
	rangeSelectors
}) => {
	const [hoverIndex, setHoverIndex] = useState(-1);
	const [legendHoverItem, setLegendHoverItem] = useState(null);

	const renderTooltip = ({active, payload}) => {
		if (active) {
			const {
				anonymousVisitors,
				intervalInitDate,
				knownVisitors,
				visitors
			} = get(payload, [0, 'payload'], {});

			return (
				<RechartsTooltip
					dateTitle={getDateTitle(
						dateKeysIMap.get(intervalInitDate),
						rangeSelectors.rangeKey,
						interval
					)}
					rows={[
						{
							label: Liferay.Language.get('anonymous'),
							value: toThousands(anonymousVisitors)
						},
						{
							label: Liferay.Language.get('known'),
							value: toThousands(knownVisitors)
						},
						{
							label: Liferay.Language.get('total'),
							value: toThousands(visitors)
						}
					]}
					title={Liferay.Language.get('active-individuals')}
				/>
			);
		}
	};

	const yAxisWidth = getYAxisWidth(data, 'knownVisitors');

	const intervals = getIntervals(
		rangeSelectors.rangeKey,
		data.map(({intervalInitDate}) => intervalInitDate),
		interval,
		dateKeysIMap
	);

	if (loading) {
		return <Loading key='LOADING' />;
	}

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
						href={
							URLConstants.IndividualDashboardActiveIndividualsDocumentation
						}
						key='DOCUMENTATION'
						target='_blank'
					>
						{Liferay.Language.get(
							'learn-more-about-active-individuals'
						)}
					</a>
				</>
			}
			emptyTitle={Liferay.Language.get(
				'there-is-no-data-for-active-individuals'
			)}
			showEmptyState={!intervals.length}
		>
			<ResponsiveContainer height={height}>
				<ComposedChart data={data}>
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
						label={getYAxisLabel(
							Liferay.Language.get('individuals'),
							'left',
							yAxisWidth
						)}
						name={Liferay.Language.get('individuals')}
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

					<Legend
						align='right'
						iconSize={8}
						onMouseEnter={({dataKey}) =>
							setLegendHoverItem(dataKey)
						}
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

					<Tooltip
						content={renderTooltip}
						cursor={!intervals.length ? false : true}
					/>

					<Bar
						animationDuration={ANIMATION_DURATION.bar}
						dataKey='knownVisitors'
						fill={CHART_BLUE}
						fillOpacity={
							legendHoverItem === 'anonymousVisitors' ? 0.2 : 1
						}
						legendType='circle'
						name={Liferay.Language.get('known-visitors')}
						onMouseEnter={(e, index) => setHoverIndex(index)}
						onMouseLeave={() => setHoverIndex(-1)}
						stackId='count'
					>
						{data.map((entry, index) => (
							<Cell
								fill={getBarColor(
									index,
									hoverIndex,
									null,
									'blue'
								)}
								key={`cell-${index}`}
							/>
						))}
					</Bar>

					<Bar
						animationDuration={ANIMATION_DURATION.bar}
						dataKey='anonymousVisitors'
						fill={CHART_ORANGE}
						fillOpacity={
							legendHoverItem === 'knownVisitors' ? 0.2 : 1
						}
						legendType='circle'
						name={Liferay.Language.get('anonymous-visitors')}
						onMouseEnter={(e, index) => setHoverIndex(index)}
						onMouseLeave={() => setHoverIndex(-1)}
						stackId='count'
					>
						{data.map((entry, index) => (
							<Cell
								fill={getBarColor(
									index,
									hoverIndex,
									null,
									'orange'
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

export default ActiveIndividualsChart;
