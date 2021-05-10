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

(function () {
	AUI().applyConfig({
		groups: {
			commercefrontend: {
				base: MODULE_PATH + '/js/',
				combine: Liferay.AUI.getCombine(),
				modules: {
					'liferay-commerce-frontend-asset-categories-selector': {
						path:
							'liferay_commerce_frontend_asset_categories_selector.js',
						requires: [
							'aui-tree',
							'liferay-commerce-frontend-asset-tag-selector',
						],
					},
					'liferay-commerce-frontend-asset-tag-selector': {
						path: 'liferay_commerce_frontend_asset_tag_selector.js',
						requires: [
							'aui-io-plugin-deprecated',
							'aui-live-search-deprecated',
							'aui-template-deprecated',
							'aui-textboxlist-deprecated',
							'datasource-cache',
							'liferay-service-datasource',
						],
					},
					'liferay-commerce-frontend-management-bar-state': {
						condition: {
							trigger: 'liferay-management-bar',
						},
						path: 'management_bar_state.js',
						requires: ['liferay-management-bar'],
					},
				},
				root: MODULE_PATH + '/js/',
			},
		},
	});
})();
