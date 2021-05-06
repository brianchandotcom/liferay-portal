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
				promise: true,
				react: ['umd/**/*'],
				'react-dom': [
					'**/*profiling*',
					'**/*server*',
					'**/*test*',
					'**/*unstable*',
					'umd/**/*',
				],
				'react-is': ['umd/**/*'],
				recompose: [
					'dist/Recompose.esm.js',
					'dist/Recompose.min.js',
					'dist/Recompose.umd.js',
				],
				'regenerator-runtime': true,
				scheduler: ['umd/**/*'],
				'tiny-warning': ['src/**/*'],
				'whatwg-fetch': true,
			},
			ignore: ['**/dev-build'],
		},
	},
};
