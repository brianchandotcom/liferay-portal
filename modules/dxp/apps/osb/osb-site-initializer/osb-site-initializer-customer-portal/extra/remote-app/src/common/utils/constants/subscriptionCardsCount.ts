/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const SUBSCRIPTION_TYPES = {
	Blank: ['Partnership'],
	Purchased: [
		'Enterprise Search',
		'LXC - SM',
		'Analytics Cloud',
		'Commerce for Cloud',
		'Commerce',
		'Liferay Experience Cloud',
		'Other',
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
		'Developer Services',
		'Developer Subscription',
		'Developer Support',
		'Developer Tools',
		'Maintenance Services',
		'Managed Services',
		'Managed Services - Developer Support',
		'Managed Services - Standard',
		'CSP - Custom User Tier',
		'CSP - Up to 100 Users',
		'CSP - Up to 10K Users',
		'CSP - Up to 1K Users',
		'CSP - Up to 20K Users',
		'CSP - Up to 500 Users',
		'CSP - Up to 5K Users',
		'Extended Premium Support - Liferay DXP 7.1',
		'Extended Premium Support - DXP 7.0',
		'Extended Premium Support',
		'Business Plan',
		'Enterprise Plan',
		'Pro Plan',
	],
	nonBlankProducts: ['Contact', 'Mobile Device'],
	purchasedProduct: [],
} as const;

export const PRODUCT_DISPLAY_EXCEPTION_INSTANCE_SIZE = {
	purchasedProductInstanceSize: [
		'Extended Premium Support - Liferay DXP 7.1',
		'Extended Premium Support - DXP 7.0',
		'Extended Premium Support',
	],
} as const;
