// The reason why we need this, is because we don't want to
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
