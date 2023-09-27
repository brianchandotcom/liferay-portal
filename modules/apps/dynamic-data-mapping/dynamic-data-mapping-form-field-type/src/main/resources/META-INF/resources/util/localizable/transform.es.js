/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function convertValueToJSON(value) {
	if (value && typeof value === 'string') {
		try {
			return JSON.parse(value);
		}
		catch (error) {
			console.warn('Unable to parse JSON', value);
		}
	}

	return value;
}

export function convertStringToObject(value, localeId) {
	if (typeof value === 'string') {
		const object = new Object();

		object[localeId] = value;

		return object;
	}

	return value;
}

export function getEditingValue({
	defaultLocale,
	editingLocale,
	fieldName,
	value,
}) {
	const valueJSON = convertValueToJSON(value);

	if (valueJSON) {
		if (fieldName === 'submitLabel') {
			return valueJSON[editingLocale.localeId] || '';
		}
		else {
			return (
				valueJSON[editingLocale.localeId] ||
				valueJSON[defaultLocale.localeId] ||
				''
			);
		}
	}

	return editingLocale;
}

export function getInitialInternalValue({editingLocale, value}) {
	const valueJSON = convertValueToJSON(value);

	return valueJSON ? valueJSON[editingLocale?.localeId] : '';
}

const convertValueToString = (value) => {
	if (value && typeof value === 'object') {
		return JSON.stringify(value);
	}

	return value;
};

const isTranslated = ({localeId, value}) => {
	const valueJSON = convertValueToJSON(value);

	if (valueJSON) {
		return !!valueJSON[localeId];
	}

	return false;
};

const isDefaultLocale = ({defaultLocale, localeId}) => {
	return defaultLocale.localeId === localeId;
};

export function normalizeLocaleId(localeId) {
	if (!localeId || localeId === '') {
		throw new Error(`localeId ${localeId} is invalid`);
	}

	return localeId.replaceAll('_', '-').toLowerCase();
}

export function transformAvailableLocales(
	availableLocales,
	defaultLocale,
	value
) {
	return {
		availableLocales: availableLocales?.map((availableLocale) => ({
			displayName: availableLocale[1].label,
			icon: normalizeLocaleId(availableLocale[0]),
			isDefault: isDefaultLocale({
				defaultLocale,
				localeId: availableLocale[0],
			}),
			isTranslated: isTranslated({
				localeId: availableLocale[0],
				value,
			}),
			localeId: availableLocale[0],
		})),
	};
}

export function transformAvailableLocalesAndValue({
	availableLocales,
	defaultLocale,
	value,
}) {
	return {
		availableLocales: availableLocales?.map((availableLocale) => ({
			...availableLocale,
			icon: normalizeLocaleId(availableLocale.localeId),
			isDefault: isDefaultLocale({
				defaultLocale,
				localeId: availableLocale.localeId,
			}),
			isTranslated: isTranslated({
				localeId: availableLocale.localeId,
				value,
			}),
		})),
		value: convertValueToString(value),
	};
}

export function transformEditingLocale({defaultLocale, editingLocale, value}) {
	return {
		displayName: editingLocale.label,
		icon: editingLocale.icon,
		isDefault: isDefaultLocale({defaultLocale, localeId: editingLocale.id}),
		isTranslated: isTranslated({localeId: editingLocale.id, value}),
		localeId: editingLocale.id,
	};
}
