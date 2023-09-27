/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	build: {
		exports: [
			{name: 'billboard.js', symbols: ['bb', 'default']},
			'clay-charts',
			'clay-charts/lib/css/main.css',
			'clay-charts/lib/svg/tiles.svg',
			{name: 'd3', symbols: 'auto'},
			'd3-array',
			'd3-axis',
			'd3-brush',
			'd3-chord',
			'd3-collection',
			'd3-color',
			'd3-contour',
			'd3-dispatch',
			'd3-drag',
			'd3-dsv',
			'd3-ease',
			'd3-fetch',
			'd3-force',
			'd3-format',
			'd3-geo',
			'd3-hierarchy',
			'd3-interpolate',
			'd3-path',
			'd3-polygon',
			'd3-quadtree',
			'd3-random',
			'd3-scale',
			'd3-scale-chromatic',
			'd3-selection',
			'd3-shape',
			'd3-time',
			'd3-time-format',
			'd3-timer',
			'd3-transition',
			'd3-voronoi',
			'd3-zoom',
		],
	},
	check: false,
	fix: false,
};
