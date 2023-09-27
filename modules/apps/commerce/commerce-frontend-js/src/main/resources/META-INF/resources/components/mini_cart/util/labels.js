/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
