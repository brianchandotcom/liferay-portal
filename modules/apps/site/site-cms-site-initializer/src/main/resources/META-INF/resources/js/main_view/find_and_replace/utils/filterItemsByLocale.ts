/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locale} from 'frontend-js-components-web';

import {
	RelatedItem,
	ReplaceItem,
	ReplaceItemField,
} from '../contexts/FindAndReplaceContext';

export function filterItemsByLocale(
	items: ReplaceItem[],
	localeId: Locale['id']
) {
	return items
		.filter((item) => hasTranslation(item, localeId))
		.map((item) => ({
			...item,
			fields: filterFields(item.fields, localeId),
			related: item.related
				?.filter((relatedItem) => hasTranslation(relatedItem, localeId))
				.map((relatedItem) => ({
					...relatedItem,
					fields: filterFields(relatedItem.fields, localeId),
				})),
		}));
}

function hasTranslation(
	item: ReplaceItem | RelatedItem,
	localeId: Locale['id']
): boolean {
	const translated = item.fields.some(
		(field) => field.value_i18n?.[localeId]
	);

	if (translated) {
		return true;
	}

	if (!('related' in item)) {
		return false;
	}

	return (item.related ?? []).some((relatedItem) =>
		hasTranslation(relatedItem, localeId)
	);
}

function filterFields(fields: ReplaceItemField[], localeId: Locale['id']) {
	const filteredFields: ReplaceItemField[] = [];

	for (const field of fields) {
		if (!field.value_i18n) {
			continue;
		}

		const translation = field.value_i18n[localeId];

		if (!translation) {
			continue;
		}

		filteredFields.push(field);
	}

	return filteredFields;
}
