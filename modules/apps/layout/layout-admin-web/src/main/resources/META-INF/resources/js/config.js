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
			layoutcustomizationsettings: {
				base: MODULE_PATH + '/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-layout-customization-settings': {
						path: 'js/layout_customization_settings.js',
						requires: [
							'aui-base',
							'aui-overlay-mask-deprecated',
							'liferay-portlet-base'
						]
					}
				},
				root: MODULE_PATH + '/'
			}
		}
	});
})();
