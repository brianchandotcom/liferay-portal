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

const COLORS_MAP = {
	backlinks: '#AF78FF',
	email: '#FFB46E',
	paid: '#50D2A0',
	search: '#4B9BFF',
};

/**
 * Used when the traffic source name is not within the COLORS_MAP
 */
const FALLBACK_COLOR = '#e92563';

const getColorByName = name => COLORS_MAP[name] || FALLBACK_COLOR;

export default function PieChart({languageTag = 'es-ES'}) {
	const mockData = [
		{
			name: 'search',
			title: 'Search',
			value: 32178,
		},
		{
			name: 'paid',
			title: 'Paid',
			value: 278256,
		},
		{
			name: 'backlinks',
			title: 'Backlinks',
			value: 200901,
		},
		{
			name: 'email',
			title: 'Email',
			value: 223836,
		},
	];

	return (
		<div className="pie-chart-wrapper">
			<div className="pie-chart-wrapper--legend">
				<table>
					<tbody>
						{mockData.map(entry => {
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
				<RechartsPieChart height={100} width={100}>
					<Pie
						cx="50%"
						cy="50%"
						data={mockData}
						innerRadius={25}
						nameKey={'name'}
						outerRadius={50}
						paddingAngle={5}
					>
						{mockData.map((entry, i) => {
							const fillColor = getColorByName(entry.name);

							return <Cell fill={fillColor} key={i} />;
						})}
					</Pie>
				</RechartsPieChart>
			</div>
		</div>
	);
}
