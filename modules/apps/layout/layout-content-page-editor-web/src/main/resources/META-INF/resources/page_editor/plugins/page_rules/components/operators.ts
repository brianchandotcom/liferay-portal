/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const OPERATORS = {
	EQUAL: {
		label: Liferay.Language.get('is-equal-to'),
		value: 'equal',
	},
	GREATER_THAN: {
		label: Liferay.Language.get('is-greater-than'),
		value: 'greater-than',
	},
	LESS_THAN: {
		label: Liferay.Language.get('is-less-than'),
		value: 'less-than',
	},
	NOT_EQUAL: {
		label: Liferay.Language.get('is-not-equal-to'),
		value: 'not-equal',
	},
} as const;

export default OPERATORS;
