import ChartTooltip from 'shared/components/chart-tooltip';
import HTMLBarChart, {Grid, Item} from 'shared/components/HTMLBarChart';
import InfoPopover from 'shared/components/InfoPopover';
import React, {useState} from 'react';
import {AXIS} from 'shared/util/recharts';
import {
	Cell,
	Label,
	Legend,
	Pie,
	PieChart,
	ResponsiveContainer,
	Sector,
	Text,
	Tooltip
} from 'recharts';
import {get} from 'lodash';
import {sub} from 'shared/util/lang';
import {toFixedPoint, toRounded} from 'shared/util/numbers';

const CLASSNAME = 'audience-report-chart';
const CLASSNAME_DONUT = `${CLASSNAME}-donut`;
const CLASSNAME_BAR_CHART = `${CLASSNAME}-bar`;
const EMPTY_CHART_COLOR = '#E7E7ED';

type Datapoint = {
	color: string;
	count: number;
	label: string;
};

type Dataset = {
	data: Datapoint[];
	empty: Empty;
	total: number;
};

type Empty = {
	message: string;
	show: boolean;
};

interface IDonutProps extends Dataset {
	height?: number;
}

const Donut: React.FC<IDonutProps> = ({
	data = [],
	empty: {message: emptyMessage, show: isEmpty = false},
	height = 360,
	total = 0
}) => {
	const [hoverIndex, setHoverIndex] = useState<number>(-1);

	const renderActiveShape = ({
		cx,
		cy,
		endAngle,
		fill,
		innerRadius,
		outerRadius,
		startAngle
	}) => (
		<g>
			<Sector
				cx={cx}
				cy={cy}
				endAngle={endAngle}
				fill={fill}
				innerRadius={innerRadius}
				outerRadius={outerRadius + 4}
				startAngle={startAngle}
			/>
		</g>
	);

	const renderBarLabel = ({
		cx,
		cy,
		innerRadius,
		midAngle,
		outerRadius,
		percent
	}) => {
		const RADIAN = Math.PI / 180;

		const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
		const x = cx + radius * Math.cos(-midAngle * RADIAN);
		const y = cy + radius * Math.sin(-midAngle * RADIAN);

		if (!isEmpty && percent) {
			return (
				<Text
					style={{
						fill: 'black',
						font: AXIS.font,
						fontSize: '1rem',
						fontWeight: 600
					}}
					textAnchor='middle'
					x={x}
					y={y}
				>
					{`${toRounded(percent * 100, 2)}%`}
				</Text>
			);
		}
	};

	const renderTooltip = ({active, payload}) => {
		if (!isEmpty && active && !!payload.length) {
			const {count, label} = get(payload, [0, 'payload'], {});

			return (
				<div
					className='bb-tooltip-container'
					style={{position: 'static'}}
				>
					<ChartTooltip
						rows={[
							{
								columns: [
									{
										className: 'pt-0',
										label: () => (
											<span
												style={{whiteSpace: 'nowrap'}}
											>
												<strong>
													{`${toFixedPoint(count)}`}
												</strong>

												{` ${label}`}
											</span>
										)
									}
								]
							}
						]}
					/>
				</div>
			);
		}
	};

	const handleSetHoverIndex = (e, index) => {
		!isEmpty && setHoverIndex(index);
	};

	const handleResetHoverIndex = () => {
		!isEmpty && setHoverIndex(-1);
	};

	return (
		<div className={CLASSNAME_DONUT}>
			<ResponsiveContainer height={height}>
				<PieChart>
					<Tooltip content={renderTooltip} />

					{/* eslint-disable jsx-a11y/mouse-events-have-key-events
					 */}
					<Legend
						formatter={(value, {payload: {label}}) => {
							if (isEmpty) {
								return (
									<div className='text-center pl-4 pr-4'>
										{emptyMessage}
									</div>
								);
							}

							return <span className='legend-item'>{label}</span>;
						}}
						iconSize={isEmpty ? 0 : 14}
						layout='vertical'
						onMouseMove={handleSetHoverIndex}
						onMouseOut={handleResetHoverIndex}
						verticalAlign='bottom'
					/>

					<Pie
						activeIndex={hoverIndex}
						activeShape={renderActiveShape}
						blendStroke
						cy={142}
						data={
							isEmpty
								? [
										{
											color: EMPTY_CHART_COLOR,
											count: 1,
											label: 'empty'
										}
								  ]
								: data
						}
						dataKey='count'
						endAngle={-270}
						innerRadius='50%'
						isAnimationActive={false}
						label={renderBarLabel}
						labelLine={false}
						legendType='circle'
						onMouseMove={handleSetHoverIndex}
						onMouseOut={handleResetHoverIndex}
						outerRadius='90%'
						startAngle={90}
					>
						<Label position='center' value={toFixedPoint(total)} />

						{isEmpty ? (
							<Cell
								fill={EMPTY_CHART_COLOR}
								fillOpacity={1}
								key='cell-empty'
								strokeOpacity={1}
							/>
						) : (
							data.map(({color}, index) => (
								<Cell
									fill={color}
									fillOpacity={
										hoverIndex >= 0 && hoverIndex !== index
											? 0.2
											: 1
									}
									key={`cell-${index}`}
									strokeOpacity={
										hoverIndex >= 0 && hoverIndex !== index
											? 0
											: 1
									}
								/>
							))
						)}
					</Pie>
					{/* eslint-enable jsx-a11y/mouse-events-have-key-events */}
				</PieChart>
			</ResponsiveContainer>
		</div>
	);
};

const Title: React.FC<React.ComponentProps<typeof InfoPopover>> = ({
	content,
	title
}) => (
	<div className='d-inline-flex gap'>
		<h4 className='mb-3 text-center text-secondary title'>{title}</h4>

		{content && <InfoPopover content={content} title={title} />}
	</div>
);

interface IAudienceReportProps {
	knownIndividuals: Dataset;
	knownIndividualsTitle: string;
	segments: {
		disableScroll: boolean;
		formatSpacement: boolean;
		grid: Grid;
		items: Item[];
	};
	segmentsTitle: string;
	uniqueVisitors: Dataset;
	uniqueVisitorsTitle: string;
	metricAction: React.ReactText;
}

const AudienceReport: React.FC<IAudienceReportProps> = ({
	knownIndividuals,
	knownIndividualsTitle,
	segments,
	segmentsTitle = Liferay.Language.get('viewer-segments'),
	uniqueVisitors,
	uniqueVisitorsTitle = Liferay.Language.get('visitors'),
	metricAction = Liferay.Language.get('view')
}) => (
	<div className={`${CLASSNAME} row w-100`}>
		<div className='col-sm-6'>
			<div className='row'>
				<div className='col-sm-6 text-center'>
					<Title title={uniqueVisitorsTitle} />

					<Donut {...uniqueVisitors} />
				</div>

				<div className='col-sm-6 text-center'>
					<Title
						content={
							sub(
								Liferay.Language.get(
									'only-known-individuals-that-interacted-with-the-current-asset-are-accounted-for-in-this-chart'
								),
								[metricAction]
							) as string
						}
						title={knownIndividualsTitle}
					/>

					<Donut {...knownIndividuals} />
				</div>
			</div>
		</div>

		<div className='col-sm-6 pl-5'>
			<Title
				content={
					sub(
						Liferay.Language.get(
							'only-segmented-known-individuals-that-interacted-with-the-current-asset-are-accounted-for-in-this-chart'
						),
						[metricAction]
					) as string
				}
				title={segmentsTitle}
			/>

			<div className={CLASSNAME_BAR_CHART}>
				<HTMLBarChart {...segments} />
			</div>
		</div>
	</div>
);

export default AudienceReport;
