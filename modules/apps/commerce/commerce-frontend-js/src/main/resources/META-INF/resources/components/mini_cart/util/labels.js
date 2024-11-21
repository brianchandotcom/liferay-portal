/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ADD_PRODUCT,
	ORDER_IS_EMPTY,
	PROCEED_AS_GUEST,
	REMOVE_ALL_ITEMS,
	REVIEW_ORDER,
	SIGN_IN_TO_CHECKOUT,
	SUBMIT_ORDER,
	VIEW_DETAILS,
	YOUR_ORDER,
} from './constants';

export const DEFAULT_LABELS = {
	[ADD_PRODUCT]: Liferay.Language.get('add-a-product-to-the-cart'),
	[ORDER_IS_EMPTY]: Liferay.Language.get('your-order-is-empty'),
	[PROCEED_AS_GUEST]: Liferay.Language.get('proceed-as-guest'),
	[REMOVE_ALL_ITEMS]: Liferay.Language.get('remove-all-items'),
	[REVIEW_ORDER]: Liferay.Language.get('review-order'),
	[SIGN_IN_TO_CHECKOUT]: Liferay.Language.get('sign-in-to-checkout'),
	[SUBMIT_ORDER]: Liferay.Language.get('submit'),
	[VIEW_DETAILS]: Liferay.Language.get('view-details'),
	[YOUR_ORDER]: Liferay.Language.get('your-order'),
};
