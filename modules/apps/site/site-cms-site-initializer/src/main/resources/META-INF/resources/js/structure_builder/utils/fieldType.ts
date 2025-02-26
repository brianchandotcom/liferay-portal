/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-nocheck

import {v4 as uuidv4} from 'uuid';

import {Field} from '../contexts/StateContext';

// Constants

const FIELD_TYPE_LABEL = {
	text: Liferay.Language.get('text'),
} as const;

export const FIELD_TYPE_ICON = {
	text: 'custom-field',
} as const;

export const FIELD_TYPE_BUSINESS_TYPE = {
	text: 'Text',
} as const;

// Types

export type FieldBusinessType =
	(typeof FIELD_TYPE_BUSINESS_TYPE)[keyof typeof FIELD_TYPE_BUSINESS_TYPE];

// Functions

export function getDefaultField(type: Field['type']) {
	return {
		erc: uuidv4(),
		label: FIELD_TYPE_LABEL[type],
		name: normalizeName(type),
		type,
	};
}
