/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const CUSTOM_FIELD_KEY = 'customField';
const CUSTOM_FIELD_DEFAULT_DENOMINATOR = '.';
const CUSTOM_FIELD_NEW_DENOMINATOR = '/';

export function parseSortForCustomField(key: string) {
	return key.startsWith(CUSTOM_FIELD_KEY)
		? key.replace(
				CUSTOM_FIELD_DEFAULT_DENOMINATOR,
				CUSTOM_FIELD_NEW_DENOMINATOR
			)
		: key;
}
