/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			navigationmenuweb: {
				base: MODULE_PATH + '/js/legacy/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-navigation-interaction': {
						path: 'navigation_interaction.js',
						plugins: {
							'liferay-navigation-interaction-touch': {
								condition: {
									name: 'liferay-navigation-interaction-touch',
									test(A) {
										return A.UA.touchEnabled;
									},
									trigger: 'liferay-navigation-interaction',
								},
							},
						},
						requires: [
							'aui-base',
							'aui-component',
							'event-mouseenter',
							'node-focusmanager',
							'plugin',
						],
					},
					'liferay-navigation-interaction-touch': {
						path: 'navigation_interaction_touch.js',
						requires: [
							'event-tap',
							'event-touch',
							'liferay-navigation-interaction',
						],
					},
				},
				root: MODULE_PATH + '/js/legacy/',
			},
		},
	});
})();
