/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';
import {
	CartesianGrid,
	Line,
	LineChart,
	ResponsiveContainer,
	Tooltip,
	XAxis,
	YAxis,
} from 'recharts';

import moment from '../../../shared/util/moment.es';
import {AppContext} from '../../AppContext.es';
import {
	formatMonthDate,
	formatWeekDateWithYear,
	formatXAxisDate,
	formatYearDate,
	getAxisMeasuresFromData,
} from './util/chart.es';
import {HOURS, MONTHS, WEEKS, YEARS} from './util/chartConstants.es';

function VelocityChart({timeRange, velocityData = {}, velocityUnit}) {
	const {isAmPm} = useContext(AppContext);

	const {key: unitKey, name: unitName} = velocityUnit;

	const {histograms = []} = velocityData;

	const dataValues = [[...histograms.map((item) => item.value)]];

	const {intervals, maxValue} = getAxisMeasuresFromData(dataValues);

	const dataChart = histograms.map((data) => {
		const date = moment(data.key).toDate();
		data.name = formatXAxisDate(date, isAmPm, unitKey, timeRange);

		return data;
	});

	return (
		<div className="velocity-chart">
			{!!histograms.length && (
				<ResponsiveContainer height="100%" width="100%">
					<LineChart
						data={dataChart}
						height={300}
						margin={{
							bottom: 5,
							left: 20,
							right: 20,
							top: 5,
						}}
						width={1180}
					>
						<CartesianGrid strokeDasharray="3 3" />

						<XAxis dataKey="name" interval="preserveStartEnd" />

						<YAxis
							domain={[intervals, maxValue]}
							interval="preserveStartEnd"
						/>

						<Tooltip
							content={
								<CustomTooltip
									isAmPm={isAmPm}
									timeRange={timeRange}
									unit={unitName}
									unitKey={unitKey}
								/>
							}
						/>

						<Line
							activeDot={{r: 8}}
							dataKey="value"
							type="monotone"
						/>
					</LineChart>
				</ResponsiveContainer>
			)}
		</div>
	);
}

const CLASSNAME = 'workflow-tooltip-chart';

const CustomTooltip = ({active, isAmPm, payload, timeRange, unit, unitKey}) => {
	const formatTooltipDate = (date, isAmPm, timeRange, unitKey) => {
		let datePattern = Liferay.Language.get('ddd-mmm-d');

		const dateMoment = moment(date);

		if (unitKey === HOURS) {
			if (isAmPm) {
				datePattern = Liferay.Language.get('mmm-dd-hh-mm-a');
			}
			else {
				datePattern = Liferay.Language.get('mmm-dd-hh-mm');
			}

			return dateMoment.format(datePattern);
		}
		else if (unitKey === WEEKS) {
			return formatWeekDateWithYear(date, timeRange);
		}
		else if (unitKey === MONTHS) {
			return formatMonthDate(date, timeRange);
		}
		else if (unitKey === YEARS) {
			return formatYearDate(date, timeRange);
		}

		return dateMoment.format(datePattern);
	};

	const getDateTitle = (date, isAmPm, timeRange, unitKey) => {
		if (!(date instanceof Date && !isNaN(date.getTime()))) {

			// is valid date

			return '';
		}

		return formatTooltipDate(date, isAmPm, timeRange, unitKey);
	};

	if (active && payload?.length) {
		const date = new Date(payload[0].payload.key);
		const label = getDateTitle(date, isAmPm, timeRange, unitKey);

		return (
			<div className={CLASSNAME}>
				<div className={`${CLASSNAME}-header`}>
					<div
						className={`${CLASSNAME}-content ${CLASSNAME}-column font-weight-semibold`}
					>
						{label}
					</div>
				</div>

				<div className={`${CLASSNAME}-body`}>
					<div className={`${CLASSNAME}-row`}>
						<div
							className={`${CLASSNAME}-content ${CLASSNAME}-column font-weight-normal`}
						>
							{payload[0].value} {unit}
						</div>
					</div>
				</div>
			</div>
		);
	}

	return null;
};

VelocityChart.Tooltip = CustomTooltip;

export default VelocityChart;
