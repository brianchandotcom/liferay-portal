/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Available editable types
 */
export const EDITABLE_TYPES = {
	'action': 'action',
	'backgroundImage': 'background-image',
	'date-time': 'date-time',
	'html': 'html',
	'image': 'image',
	'link': 'link',
	'rich-text': 'rich-text',
	'text': 'text',
} as const;

export type EditableType = typeof EDITABLE_TYPES[keyof typeof EDITABLE_TYPES];
