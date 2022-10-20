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
			'incremental-dom',
			{name: 'incremental-dom-string', symbols: 'auto'},
			{name: 'metal', symbols: 'auto'},
			'metal-affix',
			'metal-ajax',
			'metal-anim',
			'metal-aop',
			'metal-assertions',
			'metal-clipboard',
			{name: 'metal-component', symbols: 'auto'},
			'metal-debounce',
			{name: 'metal-dom', symbols: 'auto'},
			'metal-drag-drop',
			'metal-events',
			'metal-incremental-dom',
			'metal-jsx',
			'metal-key',
			'metal-keyboard-focus',
			'metal-multimap',
			'metal-pagination',
			'metal-path-parser',
			'metal-position',
			'metal-promise',
			'metal-router',
			'metal-scrollspy',
			'metal-soy',
			{name: 'metal-soy-bundle', symbols: 'auto'},
			'metal-state',
			'metal-storage',
			'metal-structs',
			'metal-throttle',
			'metal-toggler',
			'metal-uri',
			'metal-useragent',
			'metal-web-component',
			'querystring',
			'xss-filters',
		],
	}
};
