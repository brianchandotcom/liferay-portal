/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			search: {
				base: MODULE_PATH + '/js/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-search-custom-filter': {
						path: 'custom_filter.js',
						requires: [],
					},
					'liferay-search-facet-util': {
						path: 'facet_util.js',
						requires: [],
					},
					'liferay-search-modified-facet': {
						path: 'modified_facet.js',
						requires: [
							'aui-form-validator',
							'liferay-search-facet-util',
						],
					},
					'liferay-search-modified-facet-configuration': {
						path: 'modified_facet_configuration.js',
						requires: ['aui-node'],
					},
					'liferay-search-sort-configuration': {
						path: 'sort_configuration.js',
						requires: ['aui-node'],
					},
					'liferay-search-sort-util': {
						path: 'sort_util.js',
						requires: [],
					},
				},
				root: MODULE_PATH + '/js/',
			},
		},
	});
})();
