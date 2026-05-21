/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const SUPPORTED_FIELD_TYPES = new Set<string>([
	'boolean',
	'date',
	'date-time',
	'file',
	'long-text',
	'multiselect',
	'number',
	'select',
	'text',
	'url',
]);

export default SUPPORTED_FIELD_TYPES;
