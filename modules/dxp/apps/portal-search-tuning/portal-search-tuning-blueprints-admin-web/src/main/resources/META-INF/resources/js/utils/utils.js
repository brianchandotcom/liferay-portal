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
 * Function to validate the UI configuration, used to identify whether
 * a required value is not null, undefined, or simply an empty string
 *
 * Examples:
 * isNotEmpty([])
 * => true
 * isNotEmpty('')
 * => false
 * isNotEmpty(null)
 * => false
 *
 * @param {String|object} item Item to check
 * @return {boolean}
 */
export const isNotEmpty = (item) =>
	item !== null && item !== '' && typeof item !== 'undefined';

/**
 * Function to validate the UI configuration, used to identify whether
 * every item in array is not null, undefined, or simply an empty string
 *
 * @param {Array} item Item to check
 * @return {boolean}
 */
const isNotAllEmpty = (item) => item.every(isNotEmpty);

/**
 * Function to validate the UI configuration, used to identify whether
 * a required value is not null or undefined
 *
 * Examples:
 * isNotNullOrUndefined([])
 * => true
 * isNotNullOrUndefined('')
 * => true
 * isNotNullOrUndefined(null)
 * => false
 *
 * @param {String|object} item Item to check
 * @return {boolean}
 */
const isNotNullOrUndefined = (item) =>
	item !== null && typeof item !== 'undefined';

/**
 * Function to replace all instances of a string.
 *
 * Example:
 * replaceStr('title_${config.language}', '${config.language}', 'en_US')
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
 * configuration entry.
 *
 * Examples:
 * getDefaultValue({
 *  	defaultValue: 10,
 *  	key: 'config.title.boost',
 *  	label: 'Title Boost',
 *  	type: 'slider',
 *  })
 * => 10
 *
 * getDefaultValue({
 *  	key: 'config.lfr.enabled',
 *  	label: 'Enabled',
 *  	type: 'select',
 *  	typeOptions: [
 *  		{
 *  			label: 'True',
 *  			value: true,
 *  		},
 *  		{
 *  			label: 'False',
 *  			value: false,
 *  		},
 *  	],
 *  })
 * => true
 *
 * @param {object} item Configuration with key, name, type, defaultValue
 * @return {(string|Array|number)}
 */
export const getDefaultValue = (item) => {
	const itemValue = item.defaultValue;

	switch (item.type) {
		case INPUT_TYPES.SELECT:
			return isNotEmpty(itemValue)
				? itemValue
				: item.typeOptions && item.typeOptions[0].value
				? item.typeOptions[0].value
				: '';
		case INPUT_TYPES.DATE:
			return isNotEmpty(itemValue)
				? typeof itemValue == 'number'
					? itemValue
					: moment(itemValue).isValid()
					? moment(itemValue).unix()
					: ''
				: '';
		case INPUT_TYPES.ENTITY:
			return isNotEmpty(itemValue) &&
				itemValue.length > 0 &&
				itemValue.every(
					(item) => isNotEmpty(item.value) && isNotEmpty(item.label)
				)
				? itemValue
				: [];
		case INPUT_TYPES.MULTISELECT:
			return isNotEmpty(itemValue) ? itemValue : [];
		case INPUT_TYPES.NUMBER:
			return isNotEmpty(itemValue) && typeof itemValue == 'number'
				? itemValue
				: '';
		case INPUT_TYPES.SLIDER:
			return isNotEmpty(itemValue) && typeof itemValue == 'number'
				? itemValue
				: '';
		case INPUT_TYPES.FIELD_LIST:
			return isNotEmpty(itemValue) &&
				itemValue.every(
					(item) =>
						isNotNullOrUndefined(item.field) &&
						isNotNullOrUndefined(item.locale)
				)
				? itemValue
				: [];
		case INPUT_TYPES.FIELD:
			return isNotEmpty(itemValue) &&
				isNotNullOrUndefined(itemValue.field) &&
				isNotNullOrUndefined(itemValue.locale)
				? itemValue
				: {
						field: '',
						locale: '',
				  };
		case INPUT_TYPES.JSON:
			return isNotEmpty(itemValue) ? itemValue : {};
		default:
			return isNotEmpty(itemValue) ? itemValue : '';
	}
};

/**
 * Function for getting all the default values from a UI configuration.
 *
 * Example:
 * getUIConfigurationValues(
 *  	[
 *  		{
 *  			defaultValue: 10,
 *  			key: 'boost',
 *  			label: 'Boost',
 *  			type: 'slider',
 *  		},
 *  		{
 *  			defaultValue: 'en_US',
 *  			key: 'language',
 *  			label: 'Language',
 *  			type: 'text',
 *  		}
 *  	]
 * )
 * => {boost: 10, language: 'en_US'}
 *
 * @param {object} uiConfigurationJSON Object with UI configuration
 * @return {object}
 */
export const getUIConfigurationValues = (uiConfigurationJSON) => {
	if (uiConfigurationJSON) {
		return uiConfigurationJSON.reduce((acc, curr) => {
			return {
				...acc,
				[`${curr.key}`]: getDefaultValue(curr),
			};
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
	if (uiConfigurationJSON) {
		let flattenJSON = JSON.stringify(elementTemplateJSON);

		uiConfigurationJSON.map((config) => {
			let configValue = uiConfigurationValues[config.key];

			if (config.type === INPUT_TYPES.DATE) {
				configValue = uiConfigurationValues[config.key]
					? JSON.parse(
							moment
								.unix(uiConfigurationValues[config.key])
								.format(
									config.format
										? config.format
										: 'YYYYMMDDHHMMSS'
								)
					  )
					: '';
			}
			else if (config.type === INPUT_TYPES.ENTITY) {
				configValue = JSON.stringify(
					uiConfigurationValues[config.key].map((item) => item.id)
				);
			}
			else if (config.type === INPUT_TYPES.FIELD) {
				const {
					boost,
					field,
					languageIdPosition,
					locale = '',
				} = uiConfigurationValues[config.key];

				const transformedLocale =
					!locale || locale.includes('$') ? locale : `_${locale}`;

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
			else if (config.type === INPUT_TYPES.FIELD_LIST) {
				const fields = uiConfigurationValues[config.key]
					.filter(({field}) => !!field) // Remove blank fields
					.map(({boost, field, languageIdPosition, locale = ''}) => {
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

						return boost && boost > 0
							? `${localizedField}^${boost}`
							: localizedField;
					});

				configValue = JSON.stringify(fields);
			}
			else if (config.type === INPUT_TYPES.JSON) {
				configValue = JSON.stringify(uiConfigurationValues[config.key]);
			}
			else if (config.type === INPUT_TYPES.MULTISELECT) {
				configValue = JSON.stringify(
					uiConfigurationValues[config.key].map((item) =>
						toNumber(item.value)
					)
				);
			}
			else if (config.type === INPUT_TYPES.NUMBER) {
				const oldConfigValue = uiConfigurationValues[config.key];

				configValue = isNotEmpty(config.unitSuffix)
					? typeof oldConfigValue == 'string'
						? oldConfigValue.concat('', config.unitSuffix)
						: JSON.stringify(oldConfigValue).concat(
								'',
								config.unitSuffix
						  )
					: oldConfigValue;
			}

			let key = `\${${CONFIG_PREFIX}.${config.key}}`;

			// Check whether to add quotes around output

			if (
				typeof configValue === 'number' ||
				typeof configValue === 'boolean' ||
				config.type === INPUT_TYPES.ENTITY ||
				config.type === INPUT_TYPES.FIELD_LIST ||
				config.type === INPUT_TYPES.JSON ||
				config.type === INPUT_TYPES.MULTISELECT
			) {
				key = `"$\{${CONFIG_PREFIX}.${config.key}}"`;
			}

			flattenJSON = replaceStr(flattenJSON, key, configValue);
		});

		return JSON.parse(flattenJSON);
	}

	return elementTemplateJSON;
};

const ENTITY_KEYS = [
	'com.liferay.portal.kernel.model.Group',
	'com.liferay.portal.kernel.model.Organization',
	'com.liferay.portal.kernel.model.Role',
	'com.liferay.portal.kernel.model.Team',
	'com.liferay.portal.kernel.model.User',
	'com.liferay.portal.kernel.model.UserGroup',
];

/**
 * Function to validate the UI configuration, used to identify whether
 * user is missing a required value.
 *
 * Examples:
 * validateUIConfigurationJSON({
 *  	defaultValue: 10,
 *  	label: 'Title Boost',
 *  	type: 'slider'
 *  })
 * => false
 *
 * validateUIConfigurationJSON({
 *  	defaultValue: 3,
 *  	key: 'context.timespan',
 *  	label: 'Time Span',
 *  	type: 'number',
 *  	unit: 'days'
 *  }
 * => true
 *
 * @param {object} uiConfigurationJSON Object with UI configuration
 * @return {boolean}
 */
export const validateUIConfigurationJSON = (uiConfigurationJSON) => {
	return uiConfigurationJSON.every((item) => {
		if (item.type === INPUT_TYPES.JSON) {
			return isNotEmpty(item.key);
		}
		else if (item.type === INPUT_TYPES.ENTITY) {
			return (
				isNotAllEmpty([item.key, item.label, item.className]) &&
				ENTITY_KEYS.includes(item.className)
			);
		}
		else if (item.type === INPUT_TYPES.SELECT) {
			return (
				isNotAllEmpty([item.key, item.label, item.typeOptions]) &&
				item.typeOptions.length > 0 &&
				item.typeOptions.every(
					(option) =>
						isNotNullOrUndefined(option.label) &&
						isNotNullOrUndefined(option.value)
				)
			);
		}
		else {
			return isNotAllEmpty([item.type, item.key, item.label]);
		}
	});
};
