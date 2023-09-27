/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			alloyeditor: {
				base: MODULE_PATH + '/js/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-alloy-editor': {
						path: 'alloyeditor.js',
						requires: [
							'aui-component',
							'liferay-portlet-base',
							'timers',
						],
					},
					'liferay-alloy-editor-source': {
						path: 'alloyeditor_source.js',
						requires: [
							'aui-debounce',
							'liferay-fullscreen-source-editor',
							'liferay-source-editor',
							'plugin',
						],
					},
				},
				root: MODULE_PATH + '/js/',
			},
		},
	});
})();
