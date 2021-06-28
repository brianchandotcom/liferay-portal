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

import React, {useContext} from 'react';
import {CartesianGrid, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis} from 'recharts';

import moment from '../../../shared/util/moment.es';
import {AppContext} from '../../AppContext.es';
import {
	formatXAxisDate,
	getAxisMeasuresFromData,
} from './util/chart.es';

function VelocityChart({timeRange, velocityData = {}, velocityUnit}) {
	const {isAmPm} = useContext(AppContext);

	const {key: unitKey} = velocityUnit;

	const {histograms = []} = velocityData;

	const dataValues = [[...histograms.map((item) => item.value)]];

	const {intervals, maxValue} = getAxisMeasuresFromData(dataValues);

	const dataChart = histograms.map((data) => {
		const date = moment.utc(data.key).toDate();
		data.name = formatXAxisDate(
			date,
			isAmPm,
			unitKey,
			timeRange
		);

		return data
	})

	return (
		<div className="velocity-chart">
			{histograms.length > 0 && (
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
						<YAxis domain={[intervals, maxValue]} interval="preserveStartEnd"/>
						<Tooltip />
						<Line activeDot={{ r: 8 }} dataKey="value" type="monotone" />
					</LineChart>
				</ResponsiveContainer>
			)}
		</div>
	);
}

export default VelocityChart;
