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

import launcher from '../../../src/main/resources/META-INF/resources/components/add_to_cart/entry';

import '../../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('add_to_cart', 'add-to-cart', {
	accountId: 43936, //48323,
	addToCartButton: {
		disabled: false,
		rtl: true,
	},
	channelId: 41005, //43940,
	currencyCode: 'USD',
	orderId: 43939,
	orderQuantity: Array.from({length: 99}, (_, i) => i + 1),
	productId: 43939, //43657, // "Mount"   no"43630",

	settings: {
		allowedQuantity: [],

		// allowedQuantity: [2, 4, 6667, 3, 44, 1],

		maxQuantity: 23,
		minQuantity: 1,
	},
	skuId: 43712, //43657,
	spritemap: './assets/icons.svg',
});
