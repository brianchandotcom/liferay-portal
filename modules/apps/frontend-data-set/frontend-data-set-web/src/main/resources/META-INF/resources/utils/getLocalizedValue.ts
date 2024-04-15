/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FDS_NESTED_FIELD_NAME_DELIMITER,
	FDS_NESTED_FIELD_NAME_PARENT_SUFFIX,
} from '../constants';
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

function resolveField(path: string | Array<string>, item: any) {
	if (
		Array.isArray(path) ||
		!path.includes(FDS_NESTED_FIELD_NAME_DELIMITER)
	) {
		const rootPropertyName = typeof path === 'string' ? path : path[0];

		return {resolvedFieldname: path, resolvedItem: item, rootPropertyName};
	}

	const itemPath = path.split(FDS_NESTED_FIELD_NAME_DELIMITER);

	if (path.includes(FDS_NESTED_FIELD_NAME_PARENT_SUFFIX)) {
		itemPath.pop();
	}

	const resolvedFieldname = itemPath[itemPath.length - 1];
	let resolvedItem = undefined;

	if (itemPath.length > 1) {
		resolvedItem =
			itemPath.slice(0, -1).reduce((prev, curr) => prev?.[curr], item) ||
			{};

		if (Array.isArray(resolvedItem)) {
			const key = resolvedFieldname;
			resolvedItem = resolvedItem.map((prop: any) => {
				const {[key]: value} = prop;

				return {[key]: value};
			});
		}
	}
	else {
		resolvedItem = item;
	}

	return {
		resolvedFieldname,
		resolvedItem,
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
