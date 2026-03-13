/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locale} from 'frontend-js-components-web';

import {ReplaceItem} from '../contexts/FindAndReplaceContext';

export function flatFields(item: ReplaceItem, localeId: Locale['id']) {
	const allFields = [
		...item.fields,
		...(item.related?.flatMap(({fields}) => fields) ?? []),
	];

	const entries: Array<{
		label: string;
		value: string;
	}> = [];

	for (const field of allFields) {
		if (field.value) {
			entries.push({
				label: field.label,
				value: field.value,
			});
		}

		if (field.value_i18n) {
			const translation = field.value_i18n[localeId];

			if (!translation) {
				continue;
			}

			entries.push({
				label: field.label,
				value: translation,
			});
		}
	}

	return entries;
}
