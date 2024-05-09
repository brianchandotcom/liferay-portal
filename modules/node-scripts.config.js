/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const npmscriptsConfig = require('./npmscripts.config');

const {imports} = npmscriptsConfig.build;

module.exports = {
	imports,
	symbols: {
		'@clayui/charts': ['bb', 'default'],
		'@clayui/css': [

			// Need to explicitly disable exports because the package differs in browser and server

		],
		'axe-core': ['*', 'default'],
		'clipboard': ['*', 'default'],
		'cropperjs': ['*', 'default'],
		'dagre': ['*', 'default'],
		'fuzzy': ['*', 'default'],
		'graphql-hooks-memcache': ['*', 'default'],
		'highlight.js': ['*', 'default'],
		'highlight.js/lib/core': ['*', 'default'],
		'highlight.js/lib/languages/java': ['*', 'default'],
		'highlight.js/lib/languages/javascript': ['*', 'default'],
		'highlight.js/lib/languages/plaintext': ['*', 'default'],
		'image-promise': ['*', 'default'],
		'lodash.groupby': ['*', 'default'],
		'lodash.isequal': ['*', 'default'],
		'moment': ['*', 'default'],
		'moment/min/moment-with-locales': ['*', 'default'],
		'numeral': ['*', 'default'],
		'object-hash': ['*', 'default'],
		'prop-types': ['*', 'default'],
		'qrcode': [

			// Need to explicitly list exports because the package differs in browser and server

			'create',
			'toCanvas',
			'toString',
			'toDataURL',
		],
		'qs': ['*', 'default'],
		'react': ['*', 'default'],
		'react-dnd': ['*', 'default'],
		'react-dom': ['*', 'default'],
		'text-mask-addons': ['*', 'default'],
		'text-mask-core': ['*', 'default'],
	}
};
