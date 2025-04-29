/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			layout: {
				base: MODULE_PATH + '/js/legacy/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-layout': {
						path: 'layout.js',
					},
					'liferay-layout-column': {
						path: 'layout_column.js',
						requires: ['aui-sortable-layout', 'dd'],
					},
				},
				root: MODULE_PATH + '/js/legacy/',
			},
		},
	});
})();
