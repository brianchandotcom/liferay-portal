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
			'@liferay/js-api',
			'@liferay/js-api/data-set',
			'cropperjs',
			'cropperjs/dist/cropper.css',
			{name: 'moment', symbols: 'auto'},
			{name: 'moment/min/moment-with-locales', symbols: 'auto'},
			'date-fns',
			'qrcode',
			'react-transition-group',
			'uuid',
			{name: 'clipboard', symbols: 'auto'},
			{name: 'dagre', symbols: 'auto'},
			{
				format: 'esm',
				name: 'dom-align',
				symbols: ['alignElement', 'alignPoint'],
			},
			{name: 'fuzzy', symbols: 'auto'},
			{name: 'image-promise', symbols: 'auto'},
			{name: 'qs', symbols: 'auto'},
			{name: 'react-text-mask', symbols: 'auto'},
			{name: 'text-mask-addons', symbols: 'auto'},
			{name: 'text-mask-core', symbols: 'auto'},
		],
	},
};
