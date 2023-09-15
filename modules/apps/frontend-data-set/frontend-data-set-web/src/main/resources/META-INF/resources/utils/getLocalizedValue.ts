/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface ILocalizedItemDetails {
	rootPropertyName: string;
	value: string;
	valuePath: Array<string>;
}

const languageId = Liferay.ThemeDisplay.getLanguageId();
const BCP47LanguageId = Liferay.ThemeDisplay.getBCP47LanguageId();
const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

function getLanguageKey(data: any): string {
	let languageKey = '';

	if (data[languageId]) {
		languageKey = languageId as string;
	}
	else if (data[defaultLanguageId]) {
		languageKey = defaultLanguageId as string;
	}
	else if (data['en_US']) {
		languageKey = 'en_US';
	}
	else {
		languageKey = Object.keys(data)[0];
	}

	return languageKey;
}

export function getLocalizedValue(
	item: any,
	fieldName: string | Array<string>
): ILocalizedItemDetails | null {
	if (!fieldName) {
		return null;
	}

	const rootPropertyName =
		typeof fieldName === 'string' ? fieldName : fieldName[0];
	let navigatedValue = item;
	const valuePath = [];

	if (Array.isArray(fieldName)) {
		fieldName.forEach((property) => {
			let formattedProperty = property;

			if (property === 'LANG') {
				if (navigatedValue[languageId]) {
					formattedProperty = languageId;
				}
				else if (navigatedValue[BCP47LanguageId]) {
					formattedProperty = BCP47LanguageId;
				}
				else {
					formattedProperty = defaultLanguageId;
				}
			}

			valuePath.push(formattedProperty);

			if (navigatedValue) {
				navigatedValue = navigatedValue[formattedProperty];
			}
		});
	}
	else if (
		typeof fieldName === 'string' &&
		item[fieldName] &&
		Object.keys(Liferay.Language.available).includes(
			Object.keys(item[fieldName])[0]
		)
	) {
		valuePath.push(fieldName);
		navigatedValue =
			navigatedValue[fieldName][getLanguageKey(item[fieldName])];
	}
	else {
		valuePath.push(fieldName);
		navigatedValue = navigatedValue[fieldName];
	}

	return {
		rootPropertyName,
		value: navigatedValue,
		valuePath,
	};
}
