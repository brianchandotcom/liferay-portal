import {
	formatMonthDate,
	formatWeekDateWithYear,
	formatXAxisDate,
	getAxisMeasuresFromData,
	getXAxisIntervals
} from '../../../shared/util/chart';
import {HOURS, MONTHS, WEEKS} from '../../../shared/util/chart-constants';
import React, {useContext} from 'react';
import LineChart from '@clayui/charts';
import moment from '../../../shared/util/moment';
import {TimeRangeContext} from '../filter/store/TimeRangeStore';
import TooltipChart from '../../../shared/components/chart/TooltipChart';
import {VelocityDataContext} from './store/VelocityDataStore';
import {VelocityUnitContext} from '../filter/store/VelocityUnitStore';

const VelocityChart = () => {
	const {getSelectedTimeRange} = useContext(TimeRangeContext);
	const {getSelectedVelocityUnit} = useContext(VelocityUnitContext);

	const {key: unitKey, name: unitName} = getSelectedVelocityUnit() || {};

	const {velocityData} = useContext(VelocityDataContext);

	const {histograms} = velocityData || {histograms: [], value: 0};

	const keys = histograms.map(item => moment.utc(item.key).toDate());

	const dataValues = [[...histograms.map(item => item.value)]];

	const {maxValue, intervals} = getAxisMeasuresFromData(dataValues);

	const CHART_DATA_ID_1 = 'data_1';

	const data = {
		x: 'x',
		columns: [
			['x', ...keys],
			[CHART_DATA_ID_1, ...histograms.map(item => item.value)]
		]
	};

	let dataX = [];

	if (keys.length) {
		dataX = getXAxisIntervals(getSelectedTimeRange(), keys, unitKey);
	}

	if (histograms.length === 0) {
		return null;
	}

	return (
		<div className="velocity-chart">
			<LineChart
				axis={{
					x: {
						padding: {
							left: 0,
							right: 0
						},
						tick: {
							centered: false,
							fit: true,
							values: dataX,
							format: date =>
								formatXAxisDate(
									date,
									unitKey,
									getSelectedTimeRange()
								),
							outer: false
						},
						type: 'timeseries'
					},
					y: {
						inner: false,
						inverted: false,
						max: maxValue,
						min: 0,
						padding: {
							bottom: 0,
							top: 0
						},
						show: true,
						tick: {
							values: intervals,
							outer: false
						}
					}
				}}
				data={data}
				grid={{
					lines: {
						front: false
					},
					x: {
						lines: dataX.map(key => ({value: key}))
					}
				}}
				height={190}
				legend={{show: false}}
				line={{
					classes: ['bb-line', 'bb-line-dashed-4-4']
				}}
				padding={{
					top: 0
				}}
				point={{
					focus: {expand: {enabled: true, r: 5}},
					pattern: ['circle'],
					r: 0.01,
					select: {r: 5}
				}}
				resize={{
					auto: true
				}}
				tooltip={{
					contents: VelocityChart.Tooltip(
						getSelectedTimeRange(),
						unitKey,
						unitName
					)
				}}
			></LineChart>
		</div>
	);
};

const Tooltip = (timeRange, unitKey, unitName) => dataPoints => {
	const isValidDate = date => {
		if (date instanceof Date && !isNaN(date.getTime())) {
			return true;
		}

		return false;
	};

	const formatTooltipDate = (date, unitKey, timeRange) => {
		const dateUTC = moment.utc(date);

		if (unitKey === HOURS) {
			return dateUTC.format('MMM D, h A');
		} else if (unitKey === WEEKS) {
			return formatWeekDateWithYear(date, timeRange);
		} else if (unitKey === MONTHS) {
			return formatMonthDate(date, timeRange);
		}
		return dateUTC.format('ddd, MMM D');
	};

	const getDateTitle = (date, dates, unitKey) => {
		if (!isValidDate(date)) {
			return '';
		}

		return formatTooltipDate(date, unitKey, dates);
	};

	const selectedDataPoint = dataPoints[0];

	const currentPeriodTitle = getDateTitle(
		selectedDataPoint.x,
		timeRange,
		unitKey
	);

	const header = [
		{label: currentPeriodTitle, weight: 'semibold', width: 160}
	].filter(Boolean);

	const rows = [
		{
			columns: [
				{
					label: `${selectedDataPoint.value} ${unitName}`,
					weight: 'normal'
				}
			].filter(Boolean)
		}
	].filter(Boolean);

	return TooltipChart({header, rows});
};

VelocityChart.Tooltip = Tooltip;

export default VelocityChart;
