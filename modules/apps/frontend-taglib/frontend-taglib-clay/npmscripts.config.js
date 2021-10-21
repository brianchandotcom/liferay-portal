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
		bundler: {
			exclude: {
				'*': ['src/__tests__/**/*'],
				'billboard.js': true,
				'commander': true,
				'core-js': true,
				'd3': true,
				'd3-array': true,
				'd3-axis': true,
				'd3-brush': true,
				'd3-chord': true,
				'd3-collection': true,
				'd3-color': true,
				'd3-contour': true,
				'd3-dispatch': true,
				'd3-drag': true,
				'd3-dsv': true,
				'd3-ease': true,
				'd3-fetch': true,
				'd3-force': true,
				'd3-format': true,
				'd3-geo': true,
				'd3-hierarchy': true,
				'd3-interpolate': true,
				'd3-path': true,
				'd3-polygon': true,
				'd3-quadtree': true,
				'd3-random': true,
				'd3-scale': true,
				'd3-scale-chromatic': true,
				'd3-selection': true,
				'd3-shape': true,
				'd3-time': true,
				'd3-time-format': true,
				'd3-timer': true,
				'd3-transition': true,
				'd3-voronoi': true,
				'd3-zoom': true,
				'incremental-dom': true,
				'incremental-dom-string': true,
				'metal': true,
				'metal-anim': true,
				'metal-component': true,
				'metal-events': true,
				'metal-incremental-dom': true,
				'metal-position': true,
				'metal-soy': true,
				'metal-soy-bundle': true,
				'metal-state': true,
				'metal-web-component': true,
				'xss-filters': true,
			},
		},
	},
};
