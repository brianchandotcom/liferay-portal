/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const OPERATORS = {
	CONTAINS: {
		label: Liferay.Language.get('contains'),
		value: 'contains',
	},
	DOES_NOT_CONTAIN: {
		label: Liferay.Language.get('does-not-contain'),
		value: 'does-not-contain',
	},
	EQUAL: {
		label: Liferay.Language.get('is-equal-to'),
		value: 'equal',
	},
	GREATER_THAN: {
		label: Liferay.Language.get('is-greater-than'),
		value: 'greater-than',
	},
	GREATER_THAN_OR_EQUALS: {
		label: Liferay.Language.get('is-greater-than-or-equal-to'),
		value: 'greater-than-or-equals',
	},
	IS_EMPTY: {
		label: Liferay.Language.get('is-empty'),
		value: 'is-empty',
	},
	IS_NOT_EMPTY: {
		label: Liferay.Language.get('is-not-empty'),
		value: 'is-not-empty',
	},
	LESS_THAN: {
		label: Liferay.Language.get('is-less-than'),
		value: 'less-than',
	},
	LESS_THAN_OR_EQUALS: {
		label: Liferay.Language.get('is-less-than-or-equal-to'),
		value: 'less-than-or-equals',
	},
	NOT_EQUAL: {
		label: Liferay.Language.get('is-not-equal-to'),
		value: 'not-equal',
	},
} as const;

export default OPERATORS;
