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

import React from 'react';

import {DEFAULT_LABELS} from './util/labels';

/**
 * MiniCartContext Default Shape and Values
 *
 * (exported for test purposes)
 */
export const DEFAULT_MINI_CART_CONTEXT_VALUE = {
	CartViews: {},
	actionURLs: {},
	cartState: {},
	closeCart: () => {},
	displayDiscountLevels: false,
	displayTotalItemsQuantity: false,
	isOpen: false,
	isUpdating: false,
	labels: DEFAULT_LABELS,
	openCart: () => {},
	setIsUpdating: () => {},
	summaryDataMapper: () => {},
	toggleable: true,
	updateCartModel: () => {},
	updateCartState: () => {},
};

export default React.createContext(DEFAULT_MINI_CART_CONTEXT_VALUE);
