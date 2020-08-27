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
