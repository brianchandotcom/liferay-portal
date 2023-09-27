/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			productcontent: {
				base: MODULE_PATH + '/js/',
				combine: Liferay.AUI.getCombine(),
				modules: {
					'liferay-commerce-product-content': {
						path: 'product_content.js',
						requires: [
							'aui-base',
							'aui-io-request',
							'aui-parse-content',
							'liferay-portlet-base',
							'liferay-portlet-url',
						],
					},
				},
				root: MODULE_PATH + '/js/',
			},
		},
	});
})();
