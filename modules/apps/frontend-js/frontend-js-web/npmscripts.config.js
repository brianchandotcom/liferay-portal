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
	bridges: [
		'lodash.escape',
		'lodash.groupby',
		'lodash.isequal',
		'lodash.memoize',
		'lodash.unescape',
		'svg4everybody',
		'uuid',
	],
	build: {
		bundler: {
			exclude: {
				'*': true,
			},
			ignore: [
				'**/global.bundle.js',
				'**/liferay/dom_task_runner.js',
				'**/liferay/events.js',
				'**/liferay/lazy_load.js',
				'**/liferay/liferay.js',
				'**/liferay/portlet.js',
				'**/liferay/workflow.js',
				'**/loader/config.js',
			],
		},
	},
};
