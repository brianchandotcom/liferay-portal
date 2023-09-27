/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Available editable types
 */
export declare const EDITABLE_TYPES: {
	readonly 'action': 'action';
	readonly 'backgroundImage': 'background-image';
	readonly 'date-time': 'date-time';
	readonly 'html': 'html';
	readonly 'image': 'image';
	readonly 'link': 'link';
	readonly 'rich-text': 'rich-text';
	readonly 'text': 'text';
};
export declare type EditableType = typeof EDITABLE_TYPES[keyof typeof EDITABLE_TYPES];
