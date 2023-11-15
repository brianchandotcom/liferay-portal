/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare const LIST_ITEM_TYPES: {
	readonly header: 'header';
	readonly listItem: 'listItem';
};
export declare type ItemTypeKeys = keyof typeof LIST_ITEM_TYPES;
export declare type ItemTypeValues = typeof LIST_ITEM_TYPES[ItemTypeKeys];
