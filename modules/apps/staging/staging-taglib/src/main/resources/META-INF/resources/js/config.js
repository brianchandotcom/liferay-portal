/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			stagingTaglib: {
				base: MODULE_PATH + '/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-export-import-management-bar-button': {
						path:
							'export_import_entity_management_bar_button/js/main.js',
						requires: [
							'aui-component',
							'liferay-search-container',
							'liferay-search-container-select',
						],
					},
				},
				root: MODULE_PATH + '/',
			},
		},
	});
})();
