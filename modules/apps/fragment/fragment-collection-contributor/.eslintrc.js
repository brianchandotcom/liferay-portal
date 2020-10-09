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
	globals: {
		YT: true,
		configuration: true,
		fragmentElement: true,
		fragmentNamespace: true,
	},
	overrides: [
		{
			files: '**/src/**/*.js',
			rules: {

				// For IE compatibility because JS here doesn't get transpiled.

				'notice/notice': 'off',
				'object-shorthand': 'off',
				'prefer-arrow-callback': 'off',
				'prefer-object-spread': 'off',
			},
		},
	],
};
