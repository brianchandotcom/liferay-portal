/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const FIELD_TYPE_DEFAULT_FIELD = {
	text: {label: Liferay.Language.get('text'), name: 'text', type: 'text'},
} as const;

export const FIELD_TYPE_ICON = {
	text: 'custom-field',
} as const;

export const FIELD_TYPE_BUSINESS_TYPE = {
	text: 'Text',
} as const;

export type FieldBusinessType =
	(typeof FIELD_TYPE_BUSINESS_TYPE)[keyof typeof FIELD_TYPE_BUSINESS_TYPE];
