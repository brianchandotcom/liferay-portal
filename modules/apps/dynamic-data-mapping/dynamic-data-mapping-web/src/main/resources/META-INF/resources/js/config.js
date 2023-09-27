/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const LiferayAUI = Liferay.AUI;

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
							'liferay-map-base',
							'liferay-translation-manager',
							'liferay-util-window',
						],
					},
					'liferay-portlet-dynamic-data-mapping': {
						condition: {
							trigger: 'liferay-document-library',
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
							'text',
						],
					},
					'liferay-portlet-dynamic-data-mapping-custom-fields': {
						condition: {
							trigger: 'liferay-document-library',
						},
						path: 'custom_fields.js',
						requires: ['liferay-portlet-dynamic-data-mapping'],
					},
				},
				root: MODULE_PATH + '/js/',
			},
		},
	});
})();
