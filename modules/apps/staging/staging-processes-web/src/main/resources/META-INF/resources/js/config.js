/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			stagingprocessesweb: {
				base: MODULE_PATH + '/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-staging-processes-export-import': {
						path: 'js/main.js',
						requires: [
							'aui-datatype',
							'aui-dialog-iframe-deprecated',
							'aui-modal',
							'aui-parse-content',
							'aui-toggler',
							'liferay-portlet-base',
							'liferay-util-window',
						],
					},
				},
				root: MODULE_PATH + '/',
			},
		},
	});
})();
