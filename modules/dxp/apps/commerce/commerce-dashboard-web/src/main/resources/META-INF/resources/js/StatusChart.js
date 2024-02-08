/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ChartWrapper from './ChartWrapper';

export default function StatusChart() {
	const chartData = {
		axis: {
			x: {
				type: 'category',
			},
			y: {
				tick: {
					count: 5,
				},
			},
		},
		data: {
			columns: [
				['x', 'Draft', 'Pending', 'Approved', 'Placed', 'Delivered'],
				['quantity', 80000, 50000, 60000, 20000, 35000],
			],
			type: 'bar',
			x: 'x',
		},
		grid: {
			x: {
				show: false,
			},
		},
		legend: {
			show: false,
		},
	};

	return (
		<ChartWrapper
			data={chartData}
			noDataErrorMessage={Liferay.Language.get('no-data-available')}
		/>
	);
}
