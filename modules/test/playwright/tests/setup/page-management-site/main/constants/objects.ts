/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const DEFAULT_ENTRIES_ERCS = [
	'lemon-basket-entry-1',
	'lemon-basket-entry-2',
];

export const OBJECT_ENTITIES = {
	'All Fields': {
		ERC: 'all-fields-object-erc',
		name: 'allfieldses',
		plural: 'All Fields',
	},
	'Lemon': {
		ERC: 'lemon-object-erc',
		name: 'lemons',
		plural: 'Lemons',
	},
	'Lemon Basket': {
		ERC: 'lemon-basket-object-erc',
		name: 'lemonbaskets',
		plural: 'Lemon Baskets',
	},
	'Potato': {
		ERC: 'potato-object-erc',
		name: 'potatos',
		plural: 'Potatoes',
	},
};

export type PageManagementObjectEntity = keyof typeof OBJECT_ENTITIES;
