/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getBusinessTypeLabel(businessType: string) {
	switch (businessType) {
		case 'DateTime':
			return Liferay.Language.get('date-time');

		case 'LongInteger':
			return Liferay.Language.get('long-integer');

		case 'LongText':
			return Liferay.Language.get('long-text');

		case 'MultiselectPicklist':
			return Liferay.Language.get('multiselect-picklist');

		case 'PrecisionDecimal':
			return Liferay.Language.get('precision-decimal');

		case 'RichText':
			return Liferay.Language.get('rich-text');

		default:
			return Liferay.Language.get(`${businessType.toLowerCase()}`);
	}
}
