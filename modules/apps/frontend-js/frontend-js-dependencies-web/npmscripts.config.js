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
			'date-fns',
			'graphql-hooks-memcache',
			'graphql-hooks',
			'graphql',
			'highlight.js/styles/monokai-sublime.css',
			'qrcode',
			'react-dropzone',
			'react-transition-group',
			'uuid',
			'react-flow-renderer',
			'react-helmet',
			'react-router-dom',
			'graphiql',
			'graphiql/graphiql.css',
			{name: 'axe-core', symbols: 'auto'},
			{name: 'clipboard', symbols: 'auto'},
			{name: 'dagre', symbols: 'auto'},
			{
				format: 'esm',
				name: 'dom-align',
				symbols: ['alignElement', 'alignPoint'],
			},
			{name: 'fuzzy', symbols: 'auto'},
			{name: 'highlight.js', symbols: 'auto'},
			{name: 'highlight.js/lib/core', symbols: 'auto'},
			{name: 'highlight.js/lib/languages/java', symbols: 'auto'},
			{name: 'highlight.js/lib/languages/javascript', symbols: 'auto'},
			{name: 'highlight.js/lib/languages/plaintext', symbols: 'auto'},
			{name: 'image-promise', symbols: 'auto'},
			{name: 'lodash.groupby', symbols: 'auto'},
			{name: 'lodash.isequal', symbols: 'auto'},
			{name: 'moment', symbols: 'auto'},
			{name: 'moment/min/moment-with-locales', symbols: 'auto'},
			{name: 'numeral', symbols: 'auto'},
			{name: 'object-hash', symbols: 'auto'},
			{name: 'pkce-challenge', symbols: 'auto'},
			{name: 'qs', symbols: 'auto'},
			{name: 'react-text-mask', symbols: 'auto'},
			{name: 'text-mask-addons', symbols: 'auto'},
			{name: 'text-mask-core', symbols: 'auto'},
		],
	},
};
