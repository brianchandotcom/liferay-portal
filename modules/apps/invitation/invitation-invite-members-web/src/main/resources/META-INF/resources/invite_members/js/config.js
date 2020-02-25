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
