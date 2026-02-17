/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

module.exports = {
	'portal/deprecation': require('./lib/rules/deprecation'),
	'portal/empty-line-after-copyright': require('./lib/rules/empty-line-after-copyright'),
	'portal/no-api-submodule-import': require('./lib/rules/no-api-submodule-import'),
	'portal/no-cross-module-deep-import': require('./lib/rules/no-cross-module-deep-import'),
	'portal/no-default-export-from-frontend-js-web': require('./lib/rules/no-default-export-from-frontend-js-web'),
	'portal/no-document-cookie': require('./lib/rules/no-document-cookie'),
	'portal/no-explicit-extend': require('./lib/rules/no-explicit-extend'),
	'portal/no-global-fetch': require('./lib/rules/no-global-fetch'),
	'portal/no-global-storage': require('./lib/rules/no-global-storage'),
	'portal/no-loader-import-specifier': require('./lib/rules/no-loader-import-specifier'),
	'portal/no-localhost-reference': require('./lib/rules/no-localhost-reference'),
	'portal/no-metal-plugins': require('./lib/rules/no-metal-plugins'),
	'portal/no-react-dom-create-portal': require('./lib/rules/no-react-dom-create-portal'),
	'portal/no-react-dom-render': require('./lib/rules/no-react-dom-render'),
	'portal/no-side-navigation': require('./lib/rules/no-side-navigation'),
	'portal/unexecuted-ismounted': require('./lib/rules/unexecuted-ismounted'),
};
