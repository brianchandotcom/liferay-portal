/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locale} from 'frontend-js-components-web';

import {ReplaceItem} from '../contexts/FindAndReplaceContext';

export function getItemChanges(
	item: ReplaceItem,
	search: string,
	localeId: Locale['id'] | 'all'
) {
	let changes = 0;

	for (const field of item.fields) {
		changes = changes + getFieldChanges(field, search, localeId);
	}

	for (const relatedItem of item.related ?? []) {
		for (const field of relatedItem.fields) {
			changes = changes + getFieldChanges(field, search, localeId);
		}
	}

	return changes;
}

function getFieldChanges(
	field: ReplaceItem['fields'][number],
	search: string,
	localeId: Locale['id'] | 'all'
) {
	let changes = 0;

	if (localeId === 'all' && field.value) {
		changes = changes + count(field.value, search);
	}

	if (field.value_i18n) {
		if (localeId === 'all') {
			for (const translation of Object.values(field.value_i18n)) {
				changes = changes + count(translation, search);
			}
		}
		else {
			const translation = field.value_i18n[localeId];

			if (translation) {
				changes = changes + count(translation, search);
			}
		}
	}

	return changes;
}

function count(value: string, search: string) {
	let count = 0;
	let start = 0;

	while (start < value.length) {
		const index = value.indexOf(search, start);

		if (index === -1) {
			break;
		}

		count = count + 1;

		start = index + search.length;
	}

	return count;
}
