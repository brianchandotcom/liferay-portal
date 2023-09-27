/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare const FRAGMENT_ENTRY_TYPES: {
	readonly composition: 'composition';
	readonly input: 'input';
};
export declare type FragmentEntryType = typeof FRAGMENT_ENTRY_TYPES[keyof typeof FRAGMENT_ENTRY_TYPES];
