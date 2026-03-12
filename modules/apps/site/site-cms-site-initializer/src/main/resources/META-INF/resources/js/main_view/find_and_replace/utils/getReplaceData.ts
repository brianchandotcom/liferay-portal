/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locale} from 'frontend-js-components-web';

import {ReplaceItem, ReplaceItemField} from '../contexts/FindAndReplaceContext';

type Values = Record<
	string,
	ReplaceItemField['value'] | ReplaceItemField['value_i18n']
>;

export function getReplaceData({
	localeId,
	replaceItems,
	replacement,
	search,
}: {
	localeId: Locale['id'] | 'all';
	replaceItems: ReplaceItem[];
	replacement: string;
	search: string;
}) {
	const items: Array<{className: string; id: number}> = [];
	const values: Values = {};

	for (const replaceItem of replaceItems) {
		items.push({
			className: replaceItem.className,
			id: Number(replaceItem.id),
		});

		const itemValues = replaceFieldValues(
			replaceItem.fields,
			localeId,
			search,
			replacement
		);

		const relatedValues: Record<string, Array<Values>> = {};

		for (const relatedItem of replaceItem.related || []) {
			relatedValues[relatedItem.name] = [
				{
					externalReferenceCode: relatedItem.externalReferenceCode,
					...replaceFieldValues(
						relatedItem.fields,
						localeId,
						search,
						replacement
					),
				},
			];
		}

		values[replaceItem.id] = {
			...itemValues,
			...relatedValues,
		};
	}

	return {
		items,
		values,
	};
}

function replaceFieldValues(
	fields: ReplaceItemField[],
	localeId: Locale['id'] | 'all',
	search: string,
	replacement: string
) {
	const values: Values = {};

	for (const field of fields) {
		if (field.value_i18n) {
			if (localeId === 'all') {
				values[`${field.name}_i18n`] = Object.fromEntries(
					Object.entries(field.value_i18n).map(
						([translationLocaleId, value]) => [
							translationLocaleId,
							value.replaceAll(search, replacement),
						]
					)
				);
			}
			else {
				const translation = field.value_i18n[localeId];

				if (translation) {
					values[`${field.name}_i18n`] = {
						...field.value_i18n,
						[localeId]: translation.replaceAll(search, replacement),
					};
				}
			}
		}
		else if (localeId === 'all' && field.value) {
			values[field.name] = field.value.replaceAll(search, replacement);
		}
	}

	return values;
}
