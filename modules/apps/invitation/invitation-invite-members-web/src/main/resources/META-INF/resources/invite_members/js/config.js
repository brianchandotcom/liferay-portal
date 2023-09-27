/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			'invite-members': {
				base: MODULE_PATH + '/invite_members/js/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-portlet-invite-members': {
						path: 'main.js',
						requires: [
							'aui-base',
							'autocomplete-base',
							'datasource-io',
							'datatype-number',
							'liferay-portlet-base',
							'liferay-util-window',
							'node-core',
						],
					},
				},
				root: MODULE_PATH + '/invite_members/js/',
			},
		},
	});
})();
