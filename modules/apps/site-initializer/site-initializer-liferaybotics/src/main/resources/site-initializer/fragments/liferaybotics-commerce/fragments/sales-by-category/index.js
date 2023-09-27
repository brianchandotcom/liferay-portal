/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const div = fragmentElement.querySelector('div');
const chart = bb.generate({
	axis: {
		x: {
			tick: {
				format: '%b',
			},
			type: 'timeseries',
		},
	},
	bindto: div,
	data: {
		columns: JSON.parse(configuration.data),
		x: 'x',
	},
	grid: {
		x: {
			show: true,
		},
		y: {
			show: true,
		},
	},
	regions: [
		{
			axis: 'x',
			class: 'regionX',
			start: '2020-09-1',
		},
	],
});

chart.data.colors({
	'Category 1': '#FFB68D',
	'Category 2': '#B0DEFF',
	'Category 3': '#AD93EF',
	'Category 4': '#4BC286',
	'Category 5': '#F5B3EF',
});
