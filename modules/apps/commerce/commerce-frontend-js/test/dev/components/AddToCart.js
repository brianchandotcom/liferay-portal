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

for (let i = 1; i <= 15; i++) {
	launcher('add_to_cart', 'add-to-cart-' + i, {
		channel: {
			currencyCode: 'USD',
			id: 41005,
		},
		cpInstance: {
			accountId: 43936,
			isInCart: false,
			options: '[]',
			skuId: 43712,
		},
		orderId: 43939,
		quantity: 1,
		settings: {
			block: false,
			disabled: false,
			iconOnly: false,
			withQuantity: {
				allowedQuantities: [],
				maxQuantity: 99,
				minQuantity: 1,
				multipleQuantity: 1,
			},
		},
		spritemap: './assets/clay/icons.svg',
	});
}
