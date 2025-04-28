/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			editor: {
				base: MODULE_PATH + '/js/legacy/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'inline-editor-ckeditor': {
						path: 'main.js',
						requires: [
							'array-invoke',
							'liferay-inline-editor-base',
							'node-event-simulate',
							'overlay',
							'yui-later',
						],
					},
					'liferay-inline-editor-base': {
						path: 'inline_editor_base.js',
						requires: ['aui-base', 'aui-overlay-base-deprecated'],
					},
				},
				root: MODULE_PATH + '/js/legacy/',
			},
		},
	});
})();
