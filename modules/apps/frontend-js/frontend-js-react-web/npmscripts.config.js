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
				lodash: false,
			},
			ignore: ['**/legacy/config.js'],
		},
		exports: {
			'classnames': 'classnames/index',
			'formik': 'formik/dist/index',
			'prop-types': 'prop-types/index',
			'react': 'react/index',
			'react-dnd': 'react-dnd/dist/cjs/index',
			'react-dnd-html5-backend': 'react-dnd-html5-backend/dist/cjs/index',
			'react-dom': 'react-dom/index',
		},
	},
};
