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

import AddToCart from '../../src/main/resources/META-INF/resources/components/add_to_cart/AddToCart';
import AddToCartButton from '../../src/main/resources/META-INF/resources/components/add_to_cart/AddToCartButton';
import launcher from '../../src/main/resources/META-INF/resources/utilities/launcher';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

const commonProps = {
	accountId: '43879',
	cartId: '43882',
	cartUUID: 'a711bf49-a2d3-2c8d-23c9-abaff7d288a5',
	channel: {
		currencyCode: 'USD',
		groupId: '42398',
		id: '42397',
	},
	settings: {
		iconOnly: false,
		quantityDetails: {
			allowedQuantities: [],
			maxQuantity: 10000,
			minQuantity: 1,
			multipleQuantity: 1,
		},
	},
	size: 'sm',
};

const addToCartProps = {
	...commonProps,
	cpInstance: {
		inCart: false,
		options: 'null',
		quantity: 3,
		skuId: 42633,
	},
};

const addToCartButtonProps = {
	...commonProps,
	cpInstances: [
		{
			inCart: false,
			options: 'null',
			quantity: 3,
			skuId: 42633,
		},
	],
};

launcher(AddToCart, 'add_to_cart', 'add-to-cart-card', {
	...addToCartProps,
	cpInstance: {
		...addToCartProps.cpInstance,
		inCart: true,
	},
	settings: {
		...addToCartProps.settings,
		alignment: 'full-width',
		inline: false,
		size: 'md',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-card-select', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		alignment: 'full-width',
		inline: false,
		quantityDetails: {
			...addToCartProps.settings.quantityDetails,
			allowedQuantities: [3, 5, 10, 100],
		},
		size: 'md',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-product-detail', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		inline: true,
		size: 'lg',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-product-detail-select', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		inline: true,
		quantityDetails: {
			...addToCartProps.settings.quantityDetails,
			allowedQuantities: [3, 5, 10, 100],
		},
		size: 'lg',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-sbd', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		alignment: 'full-width',
		iconOnly: true,
		inline: false,
		size: 'sm',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-sbd-select', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		alignment: 'full-width',
		iconOnly: true,
		inline: false,
		quantityDetails: {
			...addToCartProps.settings.quantityDetails,
			allowedQuantities: [3, 5, 10, 100],
		},
		size: 'sm',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-sbd-select', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		alignment: 'full-width',
		iconOnly: true,
		inline: false,
		quantityDetails: {
			...addToCartProps.settings.quantityDetails,
			allowedQuantities: [3, 5, 10, 100],
		},
		size: 'sm',
	},
});

launcher(AddToCartButton, 'add_to_cart_button', 'add-to-cart-button', {
	...addToCartButtonProps,
	settings: {
		...addToCartButtonProps.settings,
		alignment: 'full-width',
		iconOnly: true,
		inline: false,
		quantityDetails: {
			...addToCartButtonProps.settings.quantityDetails,
			allowedQuantities: [3, 5, 10, 100],
		},
		size: 'sm',
	},
});
