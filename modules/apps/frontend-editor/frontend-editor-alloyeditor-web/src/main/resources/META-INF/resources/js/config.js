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
			alloyeditor: {
				base: MODULE_PATH + '/js/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-alloy-editor': {
						path: 'alloyeditor.js',
						requires: [
							'aui-component',
							'liferay-notification',
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
					'liferay-editor-image-uploader': {
						path: 'editor_image_uploader.js',
						requires: [
							'aui-alert',
							'aui-base',
							'aui-progressbar',
							'uploader',
						],
					},
				},
				root: MODULE_PATH + '/js/',
			},
		},
	});
})();
