/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			dl: {
				base: MODULE_PATH + '/document_library/js/legacy/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'document-library-upload-component': {
						path: 'DocumentLibraryUpload.js',
						requires: [
							'aui-component',
							'aui-data-set-deprecated',
							'aui-overlay-manager-deprecated',
							'aui-overlay-mask-deprecated',
							'aui-parse-content',
							'aui-progressbar',
							'aui-template-deprecated',
							'liferay-search-container',
							'querystring-parse-simple',
							'uploader',
						],
					},
				},
				root: MODULE_PATH + '/document_library/js/legacy/',
			},
		},
	});
})();
