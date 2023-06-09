/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {localStorage} from 'frontend-js-web';

export function getSettingValue(defaultValue, sessionClicksValue, key) {
	if (themeDisplay.isSignedIn() && sessionClicksValue !== null) {
		return sessionClicksValue;
	}
	else {
		const localStorageValue = localStorage.getItem(
			key,
			localStorage.TYPES.FUNCTIONAL
		);

		if (localStorageValue !== null) {
			return localStorageValue;
		}
	}

	return defaultValue;
}

export function toggleClassName(className, value) {
	document.querySelector('body').classList.toggle(className, value);
}
