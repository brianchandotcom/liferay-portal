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

(function() {
	AUI().applyConfig({
		groups: {
			search: {
				base: MODULE_PATH + '/js/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-search-bar': {
						path: 'search_bar.js',
						requires: []
					},
					'liferay-search-custom-filter': {
						path: 'custom_filter.js',
						requires: []
					},
					'liferay-search-facet-util': {
						path: 'facet_util.js',
						requires: []
					},
					'liferay-search-modified-facet': {
						path: 'modified_facet.js',
						requires: [
							'aui-form-validator',
							'liferay-search-facet-util'
						]
					},
					'liferay-search-modified-facet-configuration': {
						path: 'modified_facet_configuration.js',
						requires: ['aui-node']
					},
					'liferay-search-sort-configuration': {
						path: 'sort_configuration.js',
						requires: ['aui-node']
					},
					'liferay-search-sort-util': {
						path: 'sort_util.js',
						requires: []
					}
				},
				root: MODULE_PATH + '/js/'
			}
		}
	});
})();
