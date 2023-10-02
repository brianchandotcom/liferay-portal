/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare const LAYOUT_TYPES: {
	readonly content: '-1';
	readonly display: '1';
	readonly master: '3';
	readonly pageTemplate: '0';
};
export declare type LayoutType = typeof LAYOUT_TYPES[keyof typeof LAYOUT_TYPES];
