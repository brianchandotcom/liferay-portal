/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const CUSTOM_FIELD_NAME_PREFIX = 'customField';
const CUSTOM_FIELD_NAME_DELIMITER = '.';
const CUSTOM_FIELD_NAME_ODATA_DELIMITER = '/';

export function parseSortForCustomField(key: string) {
	return key.startsWith(CUSTOM_FIELD_NAME_PREFIX)
		? key.replace(
				CUSTOM_FIELD_NAME_DELIMITER,
				CUSTOM_FIELD_NAME_ODATA_DELIMITER
			)
		: key;
}
