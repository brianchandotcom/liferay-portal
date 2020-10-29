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

import {
	ADD_PRODUCT,
	ORDER_IS_EMPTY,
	REMOVE_ALL_ITEMS,
	REVIEW_ORDER,
	SUBMIT_ORDER,
	VIEW_DETAILS,
	YOUR_ORDER,
} from './constants';

export const DEFAULT_LABELS = {
	[ADD_PRODUCT]: Liferay.Language.get('add-a-product-to-the-cart'),
	[ORDER_IS_EMPTY]: Liferay.Language.get('your-order-is-empty'),
	[REMOVE_ALL_ITEMS]: Liferay.Language.get('remove-all-items'),
	[REVIEW_ORDER]: Liferay.Language.get('review-order'),
	[SUBMIT_ORDER]: Liferay.Language.get('submit'),
	[VIEW_DETAILS]: Liferay.Language.get('view-details'),
	[YOUR_ORDER]: Liferay.Language.get('your-order'),
};
