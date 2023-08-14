/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			journal: {
				base: MODULE_PATH + '/js/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-journal-navigation': {
						path: 'navigation.js',
						requires: [
							'aui-component',
							'liferay-portlet-base',
							'liferay-search-container',
						],
					},
					'liferay-portlet-journal': {
						path: 'main.js',
						requires: [
							'aui-base',
							'liferay-portlet-base',
							'liferay-util-window',
						],
					},
				},
				root: MODULE_PATH + '/js/',
			},
		},
	});
})();
