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

const storageAvailable = (type) => {
	var storage;
	try {
		storage = window[type];
		var x = '__storage_test__';
		storage.setItem(x, x);
		storage.removeItem(x);

		return true;
	}
	catch (e) {
		return (
			e instanceof DOMException &&

			// everything except Firefox

			(e.code === 22 ||

				// Firefox

				e.code === 1014 ||

				// test name field too, because code might not be present
				// everything except Firefox

				e.name === 'QuotaExceededError' ||

				// Firefox

				e.name === 'NS_ERROR_DOM_QUOTA_REACHED') &&

			// acknowledge QuotaExceededError only if there's something already stored

			storage &&
			storage.length !== 0
		);
	}
};

export const generateKey = (key) => {
	return `API_GUI_FORM_VALUES_${key}`;
};

export const setLocalStorage = (key, values) => {
	if (storageAvailable('localStorage')) {
		localStorage.setItem(key, JSON.stringify(values));
	}
};

export const getLocalStorage = (key) => {
	if (storageAvailable('localStorage')) {
		const item = localStorage.getItem(key);

		if (!item) {
			return {};
		}

		return JSON.parse(item);
	}
};
