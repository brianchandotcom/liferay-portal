/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

export const PRODUCT_DISPLAY_EXCEPTION_INSTANCE_SIZE = {
	purchasedProductInstanceSize: [
		'Extended Premium Support - Liferay DXP 7.1',
		'Extended Premium Support - DXP 7.0',
		'Extended Premium Support',
	],
} as const;
