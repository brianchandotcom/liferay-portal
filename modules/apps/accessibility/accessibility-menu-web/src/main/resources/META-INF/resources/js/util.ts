/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {localStorage} from 'frontend-js-web';

export function getSettingValue(
	defaultValue: boolean,
	sessionClicksValue: boolean | undefined | null,
	key: string
) {
	if (
		window.themeDisplay.isSignedIn() &&
		!isNullOrUndefined(sessionClicksValue)
	) {
		return sessionClicksValue as boolean;
	}
	else {
		const localStorageValue = localStorage.getItem(
			key,
			localStorage.TYPES.FUNCTIONAL
		);

		if (!isNullOrUndefined(localStorageValue)) {
			return localStorageValue === 'true' ? true : false;
		}
	}

	return defaultValue;
}

export function isNullOrUndefined(value: boolean | string | undefined | null) {
	return value === null || value === undefined;
}

export function toggleClassName(className: string, value: boolean) {
	window.document.querySelector('body')!.classList.toggle(className, value);
}
