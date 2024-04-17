/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {resolveField} from './resolveField';
export interface ILocalizedItemDetails {
	rootPropertyName: string;
	value: string;
	valuePath: Array<string>;
}

function getLanguageKey(data: any): string {
	const languageId = Liferay.ThemeDisplay.getLanguageId();
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

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

	const {resolvedFieldname, resolvedItem, rootPropertyName} = resolveField(
		fieldName,
		item
	);

	const i18nFieldName = `${resolvedFieldname}_i18n`;
	let navigatedValue = resolvedItem;
	const valuePath = [];

	if (Array.isArray(resolvedFieldname)) {
		resolvedFieldname.forEach((property) => {
			let formattedProperty = property;

			if (property === 'LANG') {
				if (navigatedValue[Liferay.ThemeDisplay.getLanguageId()]) {
					formattedProperty = Liferay.ThemeDisplay.getLanguageId();
				}
				else if (
					navigatedValue[Liferay.ThemeDisplay.getBCP47LanguageId()]
				) {
					formattedProperty = Liferay.ThemeDisplay.getBCP47LanguageId();
				}
				else {
					formattedProperty = Liferay.ThemeDisplay.getDefaultLanguageId();
				}
			}

			valuePath.push(formattedProperty);

			if (navigatedValue) {
				navigatedValue = navigatedValue[formattedProperty];
			}
		});
	}
	else if (
		typeof resolvedFieldname === 'string' &&
		resolvedItem[i18nFieldName] &&
		Object.keys(Liferay.Language.available).includes(
			Object.keys(resolvedItem[i18nFieldName])[0]
		)
	) {
		valuePath.push(resolvedFieldname);
		navigatedValue =
			navigatedValue[i18nFieldName][
				getLanguageKey(resolvedItem[i18nFieldName])
			];
	}
	else if (
		typeof resolvedFieldname === 'string' &&
		resolvedItem[resolvedFieldname] &&
		Object.keys(Liferay.Language.available).includes(
			Object.keys(resolvedItem[resolvedFieldname])[0]
		)
	) {
		valuePath.push(resolvedFieldname);
		navigatedValue =
			navigatedValue[resolvedFieldname][
				getLanguageKey(resolvedItem[resolvedFieldname])
			];
	}
	else {
		valuePath.push(resolvedFieldname);
		if (Array.isArray(navigatedValue)) {
			navigatedValue = navigatedValue.map(
				(value) => getLocalizedValue(value, resolvedFieldname)?.value
			);
		}
		else {
			navigatedValue = navigatedValue[resolvedFieldname];
		}
	}

	if (fieldName !== resolvedFieldname) {
		valuePath.unshift(rootPropertyName);
	}

	return {
		rootPropertyName,
		value: navigatedValue,
		valuePath,
	};
}
