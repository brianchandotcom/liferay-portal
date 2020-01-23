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
	AUI().applyConfig({
		groups: {
			dl: {
				base: MODULE_PATH + '/document_library/js/legacy/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'document-library-upload': {
						path: 'upload.js',
						requires: [
							'aui-component',
							'aui-data-set-deprecated',
							'aui-overlay-manager-deprecated',
							'aui-overlay-mask-deprecated',
							'aui-parse-content',
							'aui-progressbar',
							'aui-template-deprecated',
							'aui-tooltip',
							'liferay-history-manager',
							'liferay-search-container',
							'querystring-parse-simple',
							'uploader'
						]
					},
					'liferay-document-library': {
						path: 'main.js',
						requires: [
							'document-library-upload',
							'liferay-message',
							'liferay-portlet-base'
						]
					}
				},
				root: MODULE_PATH + '/document_library/js/legacy/'
			}
		}
	});
})();
