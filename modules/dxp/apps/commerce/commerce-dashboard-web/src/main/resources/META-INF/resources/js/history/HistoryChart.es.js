/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
