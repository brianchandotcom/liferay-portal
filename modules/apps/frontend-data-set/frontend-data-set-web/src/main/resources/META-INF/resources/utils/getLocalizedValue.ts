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

function resolveField(path: string | Array<string>, item: any) {
	const DELIMITER = '.';
	const FULL_OBJECT_IDENTIFIER = '*';

	if (Array.isArray(path) || !path.includes(DELIMITER)) {
		const rootPropertyName = typeof path === 'string' ? path : path[0];

		return {resolvedFieldname: path, resolvedItem: item, rootPropertyName};
	}

	const itemPath = path.split(DELIMITER);

	if (itemPath[itemPath.length - 1] === FULL_OBJECT_IDENTIFIER) {
		itemPath.pop();
	}

	return {
		resolvedFieldname: itemPath[itemPath.length - 1],
		resolvedItem:
			itemPath.length > 1
				? itemPath
						.slice(0, -1)
						.reduce((prev, curr) => prev?.[curr], item) || {}
				: item,
		rootPropertyName: itemPath[0],
	};
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
		navigatedValue = navigatedValue[resolvedFieldname];
	}

	return {
		rootPropertyName,
		value: navigatedValue,
		valuePath,
	};
}
