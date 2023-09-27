/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			controlmenu: {
				base: MODULE_PATH + '/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-product-navigation-control-menu': {
						path:
							'control_menu/js/product_navigation_control_menu.js',
						requires: ['aui-node', 'event-touch'],
					},
				},
				root: MODULE_PATH + '/',
			},
		},
	});
})();
