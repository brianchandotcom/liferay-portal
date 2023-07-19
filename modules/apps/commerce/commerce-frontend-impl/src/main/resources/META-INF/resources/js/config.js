/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			commercefrontend: {
				base: MODULE_PATH + '/js/',
				combine: Liferay.AUI.getCombine(),
				modules: {
					'liferay-commerce-frontend-management-bar-state': {
						condition: {
							trigger: 'liferay-management-bar',
						},
						path: 'management_bar_state.js',
						requires: ['liferay-management-bar'],
					},
				},
				root: MODULE_PATH + '/js/',
			},
		},
	});
})();
