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
				'billboard.js': ['src/**/*', 'dist/*.min.js', 'dist/*.pkgd.js'],
				'clay-charts': ['src/jsx/**/*', 'src/__tests__/**/*'],
				'commander': true,
				'd3': ['rollup.config.js', 'dist/d3.js'],
				'd3-array': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-axis': ['rollup.config.js', 'dist/**/*.min.js', 'src/**/*'],
				'd3-brush': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-chord': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-collection': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-color': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-contour': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-dispatch': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-drag': ['rollup.config.js', 'dist/**/*.min.js', 'src/**/*'],
				'd3-dsv': ['rollup.config.js', 'dist/**/*.min.js', 'src/**/*'],
				'd3-ease': ['rollup.config.js', 'dist/**/*.min.js', 'src/**/*'],
				'd3-fetch': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-force': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-format': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-geo': ['rollup.config.js', 'dist/**/*.min.js', 'src/**/*'],
				'd3-hierarchy': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-interpolate': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-path': ['rollup.config.js', 'dist/**/*.min.js', 'src/**/*'],
				'd3-polygon': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-quadtree': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-random': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-scale': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-scale-chromatic': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-selection': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-shape': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-time': ['rollup.config.js', 'dist/**/*.min.js', 'src/**/*'],
				'd3-time-format': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-timer': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-transition': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-voronoi': [
					'rollup.config.js',
					'dist/**/*.min.js',
					'src/**/*',
				],
				'd3-zoom': ['rollup.config.js', 'dist/**/*.min.js', 'src/**/*'],
				'iconv-lite': true,
				'rw': true,
				'safer-buffer': ['tests.js'],
				'xss-filters': true,
			},
		},
	},
	check: false,
	fix: false,
};
