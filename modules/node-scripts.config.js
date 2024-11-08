/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const npmscriptsConfig = require('./npmscripts.config');

const {imports} = npmscriptsConfig.build;

module.exports = {
	imports,

	// The following symbols override the actual symbols that listed packages export. This is
	// sometimes necessary when:
	//
	// 1. The build cannot infer the exported symbols because the package cannot be required from
	//    Node.js (eg: if it references `window` or any other browser API not available).
	// 2. The inferred exported symbols are wrong (eg: polymorphic packages).
	// 3. The package must re-export everything as `default` so that it can be directly imported
	//    using ES syntax (eg: `react` since it can be imported as `import React from 'react';` even
	//    though it is a CJS package that doesn't really export any `default` symbol).
	//
	// For number 3 note that tools like `webpack` sometimes rely on the `__esModule` symbol to
	// mimic that behavior. However we prefer to make it explicit in this file due to how much
	// headaches the `__esModule` inferences usually cause when they don't work correctly.
	//
	// The way to obtain these symbols is different for each package but it usually starts with a
	// runtime error in the browser and a following investigation on what the package is really
	// exporting when used from a browser.
	//

	symbols: {
		'@clayui/charts': ['bb', 'default'],
		'@clayui/css': [

			// Need to explicitly disable exports because the package differs in browser and server

		],
		'axe-core': ['*', 'default'],
		'ckeditor5': [
			'Bold',
			'ClassicEditor',
			'EditorConfig',
			'Essentials',
			'GeneralHtmlSupport',
			'Italic',
			'Paragraph',
			'Underline',
		],
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
		'liferay-ckeditor': [],
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
	},
};
