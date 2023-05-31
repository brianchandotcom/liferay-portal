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
