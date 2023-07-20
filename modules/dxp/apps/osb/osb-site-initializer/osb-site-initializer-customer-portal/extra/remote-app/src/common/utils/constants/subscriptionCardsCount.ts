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

export const SUBSCRIPTION_TYPES = {
	Blank: ['Partnership', 'Others'],
	Purchased: [
		'Enterprise Search',
		'LXC - SM',
		'Analytics Cloud',
		'Commerce for Cloud',
		'Commerce',
		'Liferay Experience Cloud',
	],
	PurchasedAndProvisioned: ['Portal', 'DXP'],
} as const;

export const PRODUCT_DISPLAY_EXCEPTION = {
	blankProducts: [
		'CSP - Custom',
		'CSP - Up ',
		'Pro',
		'Business',
		'Enterprise',
	],
	nonBlankProducts: ['Contact', 'Mobile Device'],
	purchasedProduct: ['Extended Premium Support'],
} as const;
