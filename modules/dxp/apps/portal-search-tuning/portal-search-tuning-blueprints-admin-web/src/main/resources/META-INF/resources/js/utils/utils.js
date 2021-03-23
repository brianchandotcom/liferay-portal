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

import {openToast} from 'frontend-js-web';
import moment from 'moment';

import {CONFIG_PREFIX} from './constants';
import {INPUT_TYPES} from './inputTypes';

export const openErrorToast = (config) => {
	openToast({
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
		...config,
	});
};

export const openSuccessToast = (config) => {
	openToast({
		message: Liferay.Language.get('your-request-completed-successfully'),
		title: Liferay.Language.get('success'),
		type: 'success',
		...config,
	});
};

/**
 * Function used to identify whether a required value is not null or undefined
 *
 * Examples:
 * isDefined(false)
 * => true
 * isDefined([])
 * => true
 * isDefined('')
 * => true
 * isDefined(null)
 * => false
 *
 * @param {String|object} item Item to check
 * @return {boolean}
 */
export const isDefined = (item) => item !== null && typeof item !== 'undefined';

/**
 * Checks if a value is blank. For example: `''` or `{}`.
 * @param {*} value The value to check.
 * @return {boolean}
 */
export const isEmpty = (value) => {
	if (typeof value === 'string' && value === '') {
		return true;
	}

	if (typeof value === 'object' && !Object.keys(value).length) {
		return true;
	}

	return !isDefined(value);
};

/**
 * Function to replace all instances of a string.
 *
 * Example:
 * replaceStr('title_${configuration.language}', '${configuration.language}', 'en_US')
 * => 'title_en_US'
 *
 * @param {String} str Original string
 * @param {String} search Snippet to look for
 * @param {String} replace Snippet to replace with
 * @return {String}
 */
export const replaceStr = (str, search, replace) => {
	return str.split(search).join(replace);
};

/**
 * Function to return a new object with renamed keys.
 *
 * Example:
 * renameKeys({"en-US": "Hello", "zh-CN": "Ni Hao"}, (str) => str.replace('-', '_'))
 * => {en_US: "Hello", zh_CN: "Ni Hao"}
 *
 * @param {Object} obj Original object
 * @return {Object}
 */
export const renameKeys = (obj, func) => {
	const newObj = {};

	Object.keys(obj).map((key) => {
		newObj[`${func(key)}`] = obj[key];
	});

	return newObj;
};

const SPLIT_REGEX = /({\d+})/g;

/**
 * Utility function for substituting variables into language keys.
 *
 * Examples:
 * sub(Liferay.Language.get('search-x'), ['all'])
 * => 'search all'
 * sub(Liferay.Language.get('search-x'), [<b>all<b>], false)
 * => 'search <b>all</b>'
 *
 * @param {string} langKey This is the language key used from our properties file
 * @param {string} args Arguments to pass into language key
 * @param {string} join Boolean used to indicate whether to call `.join()` on
 * the array before it is returned. Use `false` if subbing in JSX.
 * @return {(string|Array)}
 */
export function sub(langKey, args, join = true) {
	const keyArray = langKey
		.split(SPLIT_REGEX)
		.filter((val) => val.length !== 0);

	for (let i = 0; i < args.length; i++) {
		const arg = args[i];

		const indexKey = `{${i}}`;

		let argIndex = keyArray.indexOf(indexKey);

		while (argIndex >= 0) {
			keyArray.splice(argIndex, 1, arg);

			argIndex = keyArray.indexOf(indexKey);
		}
	}

	return join ? keyArray.join('') : keyArray;
}

/**
 * Function turn string into number, otherwise returns itself.
 *
 * Example:
 * toNumber('234')
 * => 234
 * toNumber(234)
 * => 234
 * toNumber('0234')
 * => '0234'
 *
 * @param {String} str String
 * @return {number}
 */
export const toNumber = (str) => {
	try {
		return JSON.parse(str);
	}
	catch {
		return str;
	}
};

/**
 * Function for retrieving a valid default value from one element
 * configuration entry. Returns the proper empty value for invalid values.
 *
 * Examples:
 * getDefaultValue({
 *  	defaultValue: 10,
 *  	label: 'Title Boost',
 *  	name: 'boost',
 *  	type: 'slider',
 *  })
 * => 10
 *
 * getDefaultValue({
 * 		label: 'Enabled',
 * 		name: 'enabled',
 * 		type: 'select',
 * 		typeOptions: {
 * 			options: [
 * 				{
 * 					label: 'True',
 * 					value: true,
 * 				},
 * 				{
 * 					label: 'False',
 * 					value: false,
 * 				},
 * 			],
 * 		},
 * 	})
 * => true
 *
 * @param {object} item Configuration with label, name, type, defaultValue
 * @return {(string|Array|number)}
 */
export const getDefaultValue = (item) => {
	const itemValue = item.defaultValue;
	const itemTypeOptions = item.typeOptions || {};

	switch (item.type) {
		case INPUT_TYPES.DATE:
			return typeof itemValue == 'number'
				? itemValue
				: moment(itemValue, ['MM-DD-YYYY', 'YYYY-MM-DD']).isValid()
				? moment(itemValue, ['MM-DD-YYYY', 'YYYY-MM-DD']).unix()
				: '';
		case INPUT_TYPES.FIELD_MAPPING:
			return typeof itemValue == 'object' && itemValue.field
				? itemValue
				: {
						field: '',
						locale: '',
				  };
		case INPUT_TYPES.FIELD_MAPPING_LIST:
			return Array.isArray(itemValue)
				? itemValue.filter(({field}) => !!field) // Remove empty fields
				: [];
		case INPUT_TYPES.ITEM_SELECTOR:
			return Array.isArray(itemValue)
				? itemValue.filter((item) => item.label && item.value)
				: [];
		case INPUT_TYPES.JSON:
			return typeof itemValue == 'object' ? itemValue : {};
		case INPUT_TYPES.MULTISELECT:
			return Array.isArray(itemValue)
				? itemValue.filter((item) => item.label && item.value)
				: [];
		case INPUT_TYPES.NUMBER:
			return typeof itemValue == 'number'
				? itemValue
				: typeof toNumber(itemValue) == 'number'
				? toNumber(itemValue)
				: '';
		case INPUT_TYPES.SELECT:

			// use isDefined in case of false or 0

			return isDefined(itemValue)
				? itemValue
				: itemTypeOptions.options &&
				  isDefined(itemTypeOptions.options[0].value)
				? itemTypeOptions.options[0].value
				: '';
		case INPUT_TYPES.SLIDER:
			return typeof itemValue == 'number'
				? itemValue
				: typeof toNumber(itemValue) == 'number'
				? toNumber(itemValue)
				: '';
		default:
			return typeof itemValue == 'string' ? itemValue : '';
	}
};

/**
 * Function for getting all the default values from a UI configuration.
 *
 * Example:
 * getUIConfigurationValues({
 * 	fieldSets: [
 * 		{
 * 			fields: [
 * 				{
 * 					defaultValue: 10,
 * 					label: 'Boost',
 * 					name: 'boost',
 * 					type: 'slider',
 * 				},
 * 			],
 * 		},
 * 		{
 * 			fields: [
 * 				{
 * 					defaultValue: 'en_US',
 * 					label: 'Language',
 * 					name: 'language',
 * 					type: 'text',
 * 				},
 * 			],
 * 		},
 * 	],
 * });
 * => {boost: 10, language: 'en_US'}
 *
 * @param {object} uiConfigurationJSON Object with UI configuration
 * @return {object}
 */
export const getUIConfigurationValues = (uiConfigurationJSON) => {
	if (uiConfigurationJSON && uiConfigurationJSON.fieldSets) {
		return uiConfigurationJSON.fieldSets.reduce((allValues, fieldSet) => {
			const uiConfigurationValues = fieldSet.fields
				? fieldSet.fields.reduce((acc, curr) => {
						return {
							...acc,
							[`${curr.name}`]: getDefaultValue(curr),
						};
				  }, {})
				: {};

			// gets uiConfigurationValues within each fields array

			return {...allValues, ...uiConfigurationValues};
		}, {});
	}

	return {};
};

/**
 * Function for replacing the ${variable_name} with actual value.
 *
 * @param {object} _.uiConfigurationJSON Object with UI configuration
 * @param {object} _.elementTemplateJSON Actual element template for blueprint configuration
 * @param {object} _.uiConfigurationValues Values that will replace the keys in uiConfigurationJSON
 * @return {object}
 */
export const getElementOutput = ({
	elementTemplateJSON,
	uiConfigurationJSON,
	uiConfigurationValues,
}) => {
	if (uiConfigurationJSON && uiConfigurationJSON.fieldSets) {
		let flattenJSON = JSON.stringify(elementTemplateJSON);

		uiConfigurationJSON.fieldSets.map((fieldSet) => {
			if (fieldSet.fields) {
				fieldSet.fields.map((config) => {
					let configValue = '';
					const initialConfigValue =
						uiConfigurationValues[config.name];
					const configTypeOptions = config.typeOptions || {};

					if (config.type === INPUT_TYPES.DATE) {
						configValue = initialConfigValue
							? JSON.parse(
									moment
										.unix(initialConfigValue)
										.format(
											configTypeOptions.format ||
												'YYYYMMDDHHMMSS'
										)
							  )
							: '';
					}
					else if (config.type === INPUT_TYPES.ITEM_SELECTOR) {
						configValue = JSON.stringify(
							initialConfigValue.map((item) => item.value)
						);
					}
					else if (config.type === INPUT_TYPES.FIELD_MAPPING) {
						const {
							boost,
							field,
							languageIdPosition,
							locale = '',
						} = initialConfigValue;

						const transformedLocale =
							!locale || locale.includes('$')
								? locale
								: `_${locale}`;

						let localizedField;

						if (languageIdPosition > -1) {
							localizedField =
								field.substring(0, languageIdPosition) +
								transformedLocale +
								field.substring(languageIdPosition);
						}
						else {
							localizedField = field + transformedLocale;
						}

						configValue =
							boost && boost > 0
								? `${localizedField}^${boost}`
								: localizedField;
					}
					else if (config.type === INPUT_TYPES.FIELD_MAPPING_LIST) {
						const fields = initialConfigValue
							.filter(({field}) => !!field) // Remove blank fields
							.map(
								({
									boost,
									field,
									languageIdPosition,
									locale = '',
								}) => {
									const transformedLocale =
										!locale || locale.includes('$')
											? locale
											: `_${locale}`;

									let localizedField;

									if (languageIdPosition > -1) {
										localizedField =
											field.substring(
												0,
												languageIdPosition
											) +
											transformedLocale +
											field.substring(languageIdPosition);
									}
									else {
										localizedField =
											field + transformedLocale;
									}

									return boost && boost > 0
										? `${localizedField}^${boost}`
										: localizedField;
								}
							);

						configValue = JSON.stringify(fields);
					}
					else if (config.type === INPUT_TYPES.JSON) {
						configValue = JSON.stringify(initialConfigValue);
					}
					else if (config.type === INPUT_TYPES.MULTISELECT) {
						configValue = JSON.stringify(
							initialConfigValue.map((item) => item.value)
						);
					}
					else if (config.type === INPUT_TYPES.NUMBER) {
						configValue =
							typeof configTypeOptions.unitSuffix == 'string'
								? typeof initialConfigValue == 'string'
									? initialConfigValue.concat(
											configTypeOptions.unitSuffix
									  )
									: JSON.stringify(initialConfigValue).concat(
											configTypeOptions.unitSuffix
									  )
								: initialConfigValue;
					}
					else {
						configValue = initialConfigValue;
					}

					// Check whether to add quotes around output

					const key =
						typeof configValue === 'number' ||
						typeof configValue === 'boolean' ||
						config.type === INPUT_TYPES.ITEM_SELECTOR ||
						config.type === INPUT_TYPES.FIELD_MAPPING_LIST ||
						config.type === INPUT_TYPES.JSON ||
						config.type === INPUT_TYPES.MULTISELECT
							? `"$\{${CONFIG_PREFIX}.${config.name}}"`
							: `\${${CONFIG_PREFIX}.${config.name}}`;

					flattenJSON = replaceStr(flattenJSON, key, configValue);
				});
			}
		});

		return JSON.parse(flattenJSON);
	}

	return elementTemplateJSON;
};
