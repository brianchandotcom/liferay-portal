/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			imageuploaderweb: {
				base: MODULE_PATH + '/js/legacy/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-logo-editor': {
						path: 'logo_editor.js',
						requires: ['aui-image-cropper', 'liferay-portlet-base'],
					},
				},
				root: MODULE_PATH + '/js/legacy/',
			},
		},
	});
})();
