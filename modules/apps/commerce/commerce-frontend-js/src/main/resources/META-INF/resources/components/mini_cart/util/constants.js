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

/**
 * Cart implementation constants
 */
export const DEFAULT_ORDER_DETAILS_PORTLET_ID =
	'com_liferay_commerce_order_content_web_internal_portlet_' +
	'CommerceOpenOrderContentPortlet';
export const ORDER_DETAILS_ENDPOINT = '/pending-orders';
export const ORDER_UUID_PARAMETER = 'commerceOrderUuid';
export const WORKFLOW_STATUS_APPROVED = 0;
export const PRODUCT_QUANTITY_NOT_VALID_ERROR = Liferay.Language.get(
	'the-product-quantity-is-not-valid'
);
export const MAXIMUM_PRODUCT_QUANTITY_NOT_VALID_ERROR = Liferay.Language.get(
	'max-quantity-per-order-is-x'
);
export const MINIMUM_PRODUCT_QUANTITY_NOT_VALID_ERROR = Liferay.Language.get(
	'the-minimum-quantity-is-x'
);
export const PRODUCT_MULTIPLE_OF_QUANTITY_NOT_VALID_ERROR = Liferay.Language.get(
	'quantity-must-be-a-multiple-of-x'
);
export const MAXIMUM_ALLOWED_QUANTITY_NOT_VALID_ERROR = Liferay.Language.get(
	'the-maximum-allowed-quantity-for-x-is-x'
);
export const UNEXPECTED_ERROR = Liferay.Language.get(
	'an-unexpected-error-occurred'
);

/**
 * CartItem implementation constants
 */

export const INITIAL_ITEM_STATE = {
	isGettingRemoved: false,
	isRemovalCanceled: false,
	isRemoved: false,
	removalTimeoutRef: null,
};
export const REMOVAL_TIMEOUT = 2000;
export const REMOVAL_CANCELING_TIMEOUT = 700;

/**
 * Cart component types keys constants
 */
export const CART = 'Cart';
export const HEADER = 'Header';
export const ITEM = 'Item';
export const ITEMS_LIST = 'ItemsList';
export const ITEMS_LIST_ACTIONS = 'ItemsListActions';
export const OPENER = 'Opener';
export const ORDER_BUTTON = 'OrderButton';
export const REQUEST_QUOTE_BUTTON = 'RequestQuoteButton';
export const SUMMARY = 'Summary';

/**
 * Cart labels keys constants
 *
 * These strings are not used as language keys,
 * but rather to both document and override language keys.
 *
 * @see ./labels.js
 */
export const ADD_PRODUCT = 'Add a product to the cart';
export const ORDER_IS_EMPTY = 'Your order is empty';
export const REMOVE_ALL_ITEMS = 'Remove all items';
export const REVIEW_ORDER = 'Review order';
export const SUBMIT_ORDER = 'Submit order';
export const VIEW_DETAILS = 'View details';
export const YOUR_ORDER = 'Your order';
