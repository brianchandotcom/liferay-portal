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

import ChartWrapper from '../ChartWrapper.es';

export default function HistoryChart({
	_APIBaseUrl,
	_accountIdParamName,
	_commerceAccountId,
	_noAccountErrorMessage,
	noDataErrorMessage,
}) {
	const chartData = {
		axis: {
			x: {
				categories: [
					'Jan',
					'Feb',
					'Mar',
					'Apr',
					'May',
					'Jun',
					'Jul',
					'Aug',
					'Sep',
				],
				type: 'category',
			},
		},
		color: {
			pattern: ['#4B9BFF'],
		},
		data: {
			columns: [
				['2019', 30, 200, 100, 400, 150, 250, 50, 100, 250],
				['2018', 100, 30, 200, 320, 50, 150, 230, 80, 150],
			],
			order: null,
			type: 'line',
		},
		grid: {
			x: {

				// lines: COLUMNS[0].map((c, i) => ({value: i}))

				show: false,
			},
		},
		legend: {
			show: false,
		},
		line: {
			classes: ['bb-line-past', 'bb-line-present'],
		},
		point: {
			show: false,
		},
	};

	return (
		<ChartWrapper
			data={chartData}
			noDataErrorMessage={noDataErrorMessage}
		/>
	);
}
