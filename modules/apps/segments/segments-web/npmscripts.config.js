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
				'babel-runtime': true,
				'iconv-lite': true,
				'isomorphic-fetch': true,
				'lodash-es': true,
				'loose-envify': true,
				'node-fetch': true,
				'promise': true,
				'react': ['umd/**/*'],
				'react-dnd': ['dist/**/*'],
				'react-dnd-html5-backend': ['dist/**/*'],
				'react-dom': [
					'**/*profiling*',
					'**/*server*',
					'**/*test*',
					'**/*unstable*',
					'umd/**/*',
				],
				'react-is': ['umd/**/*'],
				'recompose': [
					'dist/Recompose.esm.js',
					'dist/Recompose.min.js',
					'dist/Recompose.umd.js',
				],
				'redux': ['lib/**/*', 'es/**/*', 'src/**/*'],
				'regenerator-runtime': true,
				'scheduler': ['umd/**/*'],
				'tiny-warning': ['src/**/*'],
				'whatwg-fetch': true,
			},
		},
	},
};
