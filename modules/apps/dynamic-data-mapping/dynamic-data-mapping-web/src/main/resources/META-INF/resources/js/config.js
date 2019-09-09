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
	var LiferayAUI = Liferay.AUI;

	AUI().applyConfig({
		groups: {
			ddm: {
				base: MODULE_PATH + '/js/',
				combine: Liferay.AUI.getCombine(),
				filter: LiferayAUI.getFilterConfig(),
				modules: {
					'liferay-ddm-form': {
						path: 'ddm_form.js',
						requires: [
							'aui-base',
							'aui-datatable',
							'aui-datatype',
							'aui-image-viewer',
							'aui-parse-content',
							'aui-set',
							'aui-sortable-list',
							'json',
							'liferay-form',
							'liferay-item-selector-dialog',
							'liferay-layouts-tree',
							'liferay-layouts-tree-radio',
							'liferay-layouts-tree-selectable',
							'liferay-map-base',
							'liferay-notice',
							'liferay-translation-manager',
							'liferay-util-window'
						]
					},
					'liferay-portlet-dynamic-data-mapping': {
						condition: {
							trigger: 'liferay-document-library'
						},
						path: 'main.js',
						requires: [
							'arraysort',
							'aui-form-builder-deprecated',
							'aui-form-validator',
							'aui-map',
							'aui-text-unicode',
							'json',
							'liferay-menu',
							'liferay-translation-manager',
							'liferay-util-window',
							'text'
						]
					},
					'liferay-portlet-dynamic-data-mapping-custom-fields': {
						condition: {
							trigger: 'liferay-document-library'
						},
						path: 'custom_fields.js',
						requires: ['liferay-portlet-dynamic-data-mapping']
					}
				},
				root: MODULE_PATH + '/js/'
			}
		}
	});
})();
