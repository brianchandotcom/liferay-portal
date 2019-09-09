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

import {
	formatMonthDate,
	formatWeekDateWithYear,
	formatXAxisDate,
	formatYearDate,
	getAxisMeasuresFromData,
	getXAxisIntervals
} from '../../../shared/util/chart.es';
import {
	HOURS,
	MONTHS,
	WEEKS,
	YEARS
} from '../../../shared/util/chart-constants.es';
import React, {useContext} from 'react';
import {AppContext} from '../../AppContext.es';
import LineChart from '@clayui/charts';
import moment from '../../../shared/util/moment.es';
import {TimeRangeContext} from '../filter/store/TimeRangeStore.es';
import TooltipChart from '../../../shared/components/chart/TooltipChart.es';
import {VelocityDataContext} from './store/VelocityDataStore.es';
import {VelocityUnitContext} from '../filter/store/VelocityUnitStore.es';

const VelocityChart = () => {
	const {getSelectedTimeRange} = useContext(TimeRangeContext);
	const {getSelectedVelocityUnit} = useContext(VelocityUnitContext);

	const {key: unitKey, name: unitName} = getSelectedVelocityUnit() || {};

	const {isAmPm} = useContext(AppContext);

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
									isAmPm,
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
						isAmPm,
						getSelectedTimeRange(),
						unitKey,
						unitName
					)
				}}
			></LineChart>
		</div>
	);
};

const Tooltip = (isAmPm, timeRange, unitKey, unitName) => dataPoints => {
	const isValidDate = date => {
		if (date instanceof Date && !isNaN(date.getTime())) {
			return true;
		}

		return false;
	};

	const formatTooltipDate = (date, isAmPm, timeRange, unitKey) => {
		let datePattern = Liferay.Language.get('ddd-mmm-d');

		const dateUTC = moment.utc(date);

		if (unitKey === HOURS) {
			if (isAmPm) {
				datePattern = Liferay.Language.get('mmm-dd-hh-mm-a');
			} else {
				datePattern = Liferay.Language.get('mmm-dd-hh-mm');
			}

			return dateUTC.format(datePattern);
		} else if (unitKey === WEEKS) {
			return formatWeekDateWithYear(date, timeRange);
		} else if (unitKey === MONTHS) {
			return formatMonthDate(date, timeRange);
		} else if (unitKey === YEARS) {
			return formatYearDate(date, timeRange);
		}
		return dateUTC.format(datePattern);
	};

	const getDateTitle = (date, isAmPm, timeRange, unitKey) => {
		if (!isValidDate(date)) {
			return '';
		}

		return formatTooltipDate(date, isAmPm, timeRange, unitKey);
	};

	const selectedDataPoint = dataPoints[0];

	const currentPeriodTitle = getDateTitle(
		selectedDataPoint.x,
		isAmPm,
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
