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
			ignore: [
				'**/config.js',
				'**/custom_filter.js',
				'**/facet_util.js',
				'**/modified_facet_configuration.js',
				'**/modified_facet.js',
				'**/search_bar.js',
				'**/sort_configuration.js',
				'**/sort_util.js',
			],
		},
	},
};
