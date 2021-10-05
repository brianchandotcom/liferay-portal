// The reason why we need this, is because we don't want to
// Deal with big changes in case of need to change to localStorage to sessionStorage
// Or Even with Cookie, we don't need to change everytime in every single file

const liferayStorage = localStorage;

/**
 * @description Use this to get value from Storage
 * @param {*} key Storage Key
 */

export const getItem = (key) => {
	return liferayStorage.getItem(key);
};

/**
 * @description Use this to verify if a key exists with some value
 * @param {*} key Storage Key
 */

export const itemExist = (key) => {
	return !!liferayStorage.getItem(key);
};

/**
 * @description Use this to remove values from Storage
 * @param {*} key Storage Key
 */

export const removeItem = (key) => {
	liferayStorage.removeItem(key);
};

/**
 * @description Use this to set values into Storage
 * @param {*} key Storage Key
 * @param {*} value Storage Value
 */

export const setItem = (key, value) => {
	liferayStorage.setItem(key, value);
};

export const Storage = {
	getItem,
	itemExist,
	removeItem,
	setItem,
};

export const STORAGE_KEYS = {
	APPLICATION_FORM: 'raylife-application-form',
	APPLICATION_ID: 'raylife-application-id',
	BACK_TO_EDIT: 'raylife-back-to-edit',
	BASIC_STEP_CLICKED: 'basic-step-clicked',
	CONTEXTUAL_MESSAGE: 'raylife-contextual-message',
	PRODUCT: 'raylife-product',
	PRODUCT_ID: 'raylife-product-id',
};
