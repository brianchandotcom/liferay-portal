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

import React from 'react';
import {Cell, Pie, PieChart as RechartsPieChart} from 'recharts';

import {numberFormat} from '../utils/numberFormat';
import Hint from './Hint';

const {useEffect, useState} = React;

const COLORS_MAP = {
	organic: '#7785FF',
	paid: '#FFB46E',
};

/**
 * Used when the traffic source name is not within the COLORS_MAP
 */
const FALLBACK_COLOR = '#e92563';

const getColorByName = name => COLORS_MAP[name] || FALLBACK_COLOR;

export default function PieChart({dataProvider, languageTag}) {
	const [trafficSources, setTrafficSources] = useState([]);

	useEffect(() => {
		dataProvider().then(response =>
			setTrafficSources(response.analyticsReportsTrafficSources)
		);
	}, [dataProvider]);

	return (
		<div className="pie-chart-wrapper">
			<div className="pie-chart-wrapper--legend">
				<table>
					<tbody>
						{trafficSources.map(entry => {
							return (
								<tr key={entry.name}>
									<td>
										<span
											className="pie-chart-wrapper--legend--dot"
											style={{
												backgroundColor: getColorByName(
													entry.name
												),
											}}
										></span>
									</td>
									<td className="font-weight-bold pr-1 text-secondary">
										{entry.title}
										<Hint
											message={entry.helpMessage}
											title={entry.title}
										/>
									</td>
									<td className="font-weight-bold">
										{numberFormat(languageTag, entry.value)}
									</td>
								</tr>
							);
						})}
					</tbody>
				</table>
			</div>

			<div className="pie-chart-wrapper--chart">
				<RechartsPieChart height={80} width={80}>
					<Pie
						cx="50%"
						cy="50%"
						data={trafficSources}
						innerRadius={25}
						nameKey={'name'}
						outerRadius={40}
						paddingAngle={5}
					>
						{trafficSources.map((entry, i) => {
							const fillColor = getColorByName(entry.name);

							return <Cell fill={fillColor} key={i} />;
						})}
					</Pie>
				</RechartsPieChart>
			</div>
		</div>
	);
}
