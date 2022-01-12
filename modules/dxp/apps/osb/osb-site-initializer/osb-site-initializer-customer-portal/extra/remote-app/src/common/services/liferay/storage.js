/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

// Deal with big changes in case of need to change to localStorage to sessionStorage
// Or Even with Cookie, we don't need to change everytime in every single file

const liferayStorage = sessionStorage;

/**
 * @description Use this to get value from Storage
 * @param {*} key Storage Key
 */

export function getItem(key) {
	return liferayStorage.getItem(key);
}

/**
 * @description Use this to verify if a key exists with some value
 * @param {*} key Storage Key
 */

export function itemExist(key) {
	return !!liferayStorage.getItem(key);
}

/**
 * @description Use this to remove values from Storage
 * @param {*} key Storage Key
 */

export function removeItem(key) {
	liferayStorage.removeItem(key);
}

/**
 * @description Use this to set values into Storage
 * @param {*} key Storage Key
 * @param {*} value Storage Value
 */

export function setItem(key, value) {
	liferayStorage.setItem(key, value);
}

export const Storage = {
	getItem,
	itemExist,
	removeItem,
	setItem,
};

export const STORAGE_KEYS = {
	KORONEIKI_APPLICATION: 'liferay-customer-portal-koroneiki-application',
	QUICK_LINKS_EXPANDED: 'liferay-customer-portal-quick-links-expanded',
	USER_APPLICATION: 'liferay-customer-portal-user-application',
};
