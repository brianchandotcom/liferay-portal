/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React from 'react';

import ChartWrapper from '../ChartWrapper.es';

export default function HistoryChart({
	_APIBaseUrl,
	_accountIdParamName,
	_commerceAccountId,
	_noAccountErrorMessage,
	noDataErrorMessage
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
					'Sep'
				],
				type: 'category'
			}
		},
		color: {
			pattern: ['#4B9BFF']
		},
		data: {
			columns: [
				['2019', 30, 200, 100, 400, 150, 250, 50, 100, 250],
				['2018', 100, 30, 200, 320, 50, 150, 230, 80, 150]
			],
			order: null,
			type: 'line'
		},
		grid: {
			x: {
				// lines: COLUMNS[0].map((c, i) => ({value: i}))
				show: false
			}
		},
		legend: {
			show: false
		},
		line: {
			classes: ['bb-line-past', 'bb-line-present']
		},
		point: {
			show: false
		}
	};

	return (
		<ChartWrapper
			data={chartData}
			noDataErrorMessage={noDataErrorMessage}
		/>
	);
}
