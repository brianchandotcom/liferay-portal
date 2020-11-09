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
 *	Cart implementation constants
 */
export const DEFAULT_ORDER_DETAILS_PORTLET_ID =
	'com_liferay_commerce_order_content_web_internal_portlet_' +
	'CommerceOpenOrderContentPortlet';
export const DISCOUNT_LEVEL_PREFIX = 'discountPercentageLevel';
export const ORDER_DETAILS_ENDPOINT = '/pending-orders';
export const ORDER_UUID_PARAMETER = 'commerceOrderUuid';
export const WORKFLOW_STATUS_APPROVED = 0;

/**
 *	Cart component types keys constants
 */
export const CART = 'Cart';
export const HEADER = 'Header';
export const ITEM = 'Item';
export const ITEMS_LIST = 'ItemsList';
export const ITEMS_LIST_ACTIONS = 'ItemsListActions';
export const OPENER = 'Opener';
export const ORDER_BUTTON = 'OrderButton';
export const SUMMARY = 'Summary';

/**
 * 	Cart labels keys constants
 *
 * 	These strings are not used as language keys,
 * 	but rather to both document and override language keys.
 *
 * 	@see ./labels.js
 */
export const ADD_PRODUCT = 'Add a product to the cart';
export const ORDER_IS_EMPTY = 'Your order is empty';
export const REMOVE_ALL_ITEMS = 'Remove all items';
export const REVIEW_ORDER = 'Review order';
export const SUBMIT_ORDER = 'Submit order';
export const VIEW_DETAILS = 'View details';
export const YOUR_ORDER = 'Your order';
