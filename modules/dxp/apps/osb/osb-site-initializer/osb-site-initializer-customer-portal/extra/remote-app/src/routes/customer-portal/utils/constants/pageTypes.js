/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {PRODUCT_TYPES} from './productTypes';

const productsFormatted = Object.entries(
	PRODUCT_TYPES
).map(([productKey, productName]) => [
	productKey,
	productName.replace(' ', '_').toLowerCase(),
]);

export const PAGE_TYPES = {
	home: 'home',
	overview: 'overview',
	teamMembers: 'team_members',
	...Object.fromEntries(productsFormatted),
};
