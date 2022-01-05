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

import moment from 'moment';

import {CONFIG_PREFIX} from './constants';
import {INPUT_TYPES} from './inputTypes';

/**
 * Function to get valid classNames and return them sorted.
 *
 * @param {Array} items Array of objects with classNames
 * @return {Array} Array of classNames
 */
export function filterAndSortClassNames(items) {
	return items
		.map(({className}) => className)
		.filter((item) => item)
		.sort();
}

/**
 * Function used to identify whether a required value is not undefined
 *
 * Examples:
 * isDefined(false)
 * => true
 * isDefined([])
 * => true
 * isDefined('')
 * => true
 * isDefined(null)
 * => true
 *
 * @param {String|object} item Item to check
 * @return {boolean}
 */
export function isDefined(item) {
	return typeof item !== 'undefined';
}

/**
 * Checks if a value is blank. For example: `''` or `{}` or `[]`.
 * For fieldMapping and fieldMappingList, checks if fields are blank.
 * @param {*} value The value to check.
 * @param {*} type Input type (optional).
 * @return {boolean}
 */
export function isEmpty(value, type = '') {
	if (typeof value === 'string' && value === '') {
		return true;
	}

	if (typeof value === 'object' && !Object.keys(value).length) {
		return true;
	}

	if (type === INPUT_TYPES.FIELD_MAPPING) {
		return !value.field;
	}

	if (type === INPUT_TYPES.FIELD_MAPPING_LIST) {
		return value.every(({field}) => !field);
	}

	return !isDefined(value);
}

/**
 * Used for converting a JSON string to display in a code mirror editor.
 * @param {String} jsonString The JSON string to convert.
 * @return {String} The converted JSON string.
 */
export function parseAndPrettifyJSON(json) {
	if (!isDefined(json)) {
		return '';
	}

	try {
		return JSON.stringify(JSON.parse(json), null, 2);
	}
	catch (error) {
		if (process.env.NODE_ENV === 'development') {
			console.error(error);
		}

		return json;
	}
}

/**
 * Function to remove duplicates in an array.
 *
 * @param {Array} items Array of items with repeated values
 * @return {Array}
 */
export function removeDuplicates(items) {
	return items.filter(
		(item, position, self) => self.indexOf(item) === position
	);
}

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
export function replaceStr(str, search, replace) {
	return str.split(search).join(replace);
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
export function toNumber(str) {
	try {
		return JSON.parse(str);
	}
	catch {
		return str;
	}
}

/**
 * Cleans up the uiConfiguration to prevent page load failures
 * - Checks that `fieldSets` and `fields` are arrays
 * - Removes fields without a `name` property
 * - Removes fields with a duplicate `name` property
 *
 * Example:
 *	cleanUIConfiguration({
 *		fieldSets: [
 *			{
 *				fields: [
 *					{
 *						defaultValue: 1,
 *						label: 'Boost',
 *						name: 'boost',
 *						type: 'number',
 *					},
 *					{
 *						label: 'Text',
 *						type: 'text',
 *					},
 *				],
 *			},
 *		],
 *	});
 *	=> {
 *		fieldSets: [
 *			{
 *				fields: [
 *					{
 *						defaultValue: 1,
 *						label: 'Boost',
 *						name: 'boost',
 *						type: 'number',
 *					},
 *				],
 *			},
 *		],
 *	}
 *
 * @param {object} uiConfiguration Object with UI configuration
 * @return {object}
 */
export function cleanUIConfiguration(uiConfiguration = {}) {
	const fieldSets = [];

	if (Array.isArray(uiConfiguration.fieldSets)) {
		const fieldNames = [];

		uiConfiguration.fieldSets.forEach((fieldSet) => {
			if (Array.isArray(fieldSet.fields)) {
				const fields = [];

				fieldSet.fields.forEach((config) => {
					if (config.name && !fieldNames.includes(config.name)) {
						fieldNames.push(config.name);
						fields.push(config);
					}
				});

				if (fields.length > 0) {
					fieldSets.push({fields});
				}
			}
		});
	}

	return {fieldSets};
}

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
export function getDefaultValue(item) {
	const itemValue = item.defaultValue;

	switch (item.type) {
		case INPUT_TYPES.DATE:
			return typeof itemValue === 'number'
				? itemValue
				: moment(itemValue, ['MM-DD-YYYY', 'YYYY-MM-DD']).isValid()
				? moment(itemValue, ['MM-DD-YYYY', 'YYYY-MM-DD']).unix()
				: '';
		case INPUT_TYPES.FIELD_MAPPING:
			return typeof itemValue === 'object' && itemValue.field
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
			return typeof itemValue === 'object'
				? JSON.stringify(itemValue, null, '\t')
				: '{}';
		case INPUT_TYPES.MULTISELECT:
			return Array.isArray(itemValue)
				? itemValue.filter((item) => item.label && item.value)
				: [];
		case INPUT_TYPES.NUMBER:
			return typeof itemValue === 'number'
				? itemValue
				: typeof toNumber(itemValue) === 'number'
				? toNumber(itemValue)
				: '';
		case INPUT_TYPES.SELECT:
			return typeof itemValue === 'string'
				? itemValue
				: typeof item.typeOptions?.options?.[0]?.value === 'string'
				? item.typeOptions.options[0].value
				: '';
		case INPUT_TYPES.SLIDER:
			return typeof itemValue === 'number'
				? itemValue
				: typeof toNumber(itemValue) === 'number'
				? toNumber(itemValue)
				: '';
		default:
			return typeof itemValue === 'string' ? itemValue : '';
	}
}

/**
 * Function for replacing the ${variable_name} with actual value.
 *
 * @param {object} _.sxpElement SXP Element with elementDefinition
 * @param {object} _.uiConfigurationValues Values that will replace the keys in uiConfiguration
 * @return {object}
 */
export function getConfigurationEntry({sxpElement, uiConfigurationValues}) {
	const fieldSets = cleanUIConfiguration(
		sxpElement.elementDefinition?.uiConfiguration
	).fieldSets;

	if (fieldSets.length > 0) {
		let flattenJSON = JSON.stringify(
			sxpElement.elementDefinition?.configuration || {}
		);

		fieldSets.map(({fields}) => {
			fields.map((config) => {
				let configValue = '';

				const initialConfigValue = uiConfigurationValues[config.name];

				if (
					config.typeOptions?.nullable &&
					isEmpty(initialConfigValue, config.type)
				) {

					// Remove property entirely if blank.
					// Check for regex with leading and trailing commas first.

					const nullRegex = `\\"[\\w\\._]+\\"\\:\\"\\$\\{${CONFIG_PREFIX}\\.${config.name}}\\"`;

					flattenJSON = replaceStr(
						flattenJSON,
						new RegExp(nullRegex + `,`),
						''
					);

					flattenJSON = replaceStr(
						flattenJSON,
						new RegExp(`,` + nullRegex),
						''
					);

					flattenJSON = replaceStr(
						flattenJSON,
						new RegExp(nullRegex),
						''
					);
				}
				else if (config.type === INPUT_TYPES.DATE) {
					configValue = initialConfigValue
						? JSON.parse(
								moment
									.unix(initialConfigValue)
									.format(
										config.typeOptions?.format ||
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

					localizedField = replaceStr(localizedField, /[\\"]+/, '');

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
										field.substring(0, languageIdPosition) +
										transformedLocale +
										field.substring(languageIdPosition);
								}
								else {
									localizedField = field + transformedLocale;
								}

								localizedField = replaceStr(
									localizedField,
									/[\\"]+/,
									''
								);

								return boost && boost > 0
									? `${localizedField}^${boost}`
									: localizedField;
							}
						);

					configValue = JSON.stringify(fields);
				}
				else if (config.type === INPUT_TYPES.JSON) {
					try {
						JSON.parse(initialConfigValue);
						configValue = initialConfigValue;
					}
					catch {
						configValue = '{}';
					}
				}
				else if (config.type === INPUT_TYPES.KEYWORDS) {
					configValue =
						replaceStr(initialConfigValue, /[\\"]+/, '') ||
						'${keywords}';
				}
				else if (config.type === INPUT_TYPES.MULTISELECT) {
					configValue = JSON.stringify(
						initialConfigValue.map((item) => item.value)
					);
				}
				else if (config.type === INPUT_TYPES.NUMBER) {
					configValue =
						typeof config.typeOptions?.unitSuffix === 'string'
							? typeof initialConfigValue === 'string'
								? initialConfigValue.concat(
										config.typeOptions?.unitSuffix
								  )
								: JSON.stringify(initialConfigValue).concat(
										config.typeOptions?.unitSuffix
								  )
							: initialConfigValue;
				}
				else if (config.type === INPUT_TYPES.SLIDER) {
					configValue = initialConfigValue;
				}
				else {
					configValue = replaceStr(initialConfigValue, /[\\"]+/, '');
				}

				// Check whether to add quotes around output

				const key =
					typeof configValue === 'number' ||
					config.type === INPUT_TYPES.ITEM_SELECTOR ||
					config.type === INPUT_TYPES.FIELD_MAPPING_LIST ||
					config.type === INPUT_TYPES.JSON ||
					config.type === INPUT_TYPES.MULTISELECT
						? `"$\{${CONFIG_PREFIX}.${config.name}}"`
						: `\${${CONFIG_PREFIX}.${config.name}}`;

				flattenJSON = replaceStr(flattenJSON, key, configValue);
			});
		});

		return JSON.parse(flattenJSON);
	}

	return (
		parseCustomSXPElement(sxpElement, uiConfigurationValues)
			.elementDefinition?.configuration || {}
	);
}

/**
 * Function for parsing custom json element text into sxpElement
 *
 * @param {object} sxpElement Original sxpElement (default)
 * @param {object} uiConfigurationValues Contains custom JSON for sxpElement
 * @return {object}
 */
export function parseCustomSXPElement(sxpElement, uiConfigurationValues) {
	try {
		if (isDefined(uiConfigurationValues.sxpElement)) {
			return JSON.parse(uiConfigurationValues.sxpElement);
		}

		return sxpElement;
	}
	catch {
		return sxpElement;
	}
}

/**
 * Function for getting all the default values from an SXPElement. For non-custom
 * json elements, returns the configuration values after looping over all fieldSets.
 * For custom json elements, returns a stringified sxpElement for the editor.
 *
 * @param {object} sxpElement SXPElement with elementDefinition
 * @return {object}
 */
export function getUIConfigurationValues(sxpElement = {}) {
	const uiConfiguration = sxpElement.elementDefinition?.uiConfiguration;

	if (uiConfiguration) {
		return cleanUIConfiguration(uiConfiguration).fieldSets.reduce(
			(uiConfigurationValues, fieldSet) => {
				const fieldsUIConfigurationValues = fieldSet.fields.reduce(
					(acc, curr) => ({
						...acc,
						[`${curr.name}`]: getDefaultValue(curr),
					}),
					{}
				);

				// gets uiConfigurationValues within each fields array

				return {
					...uiConfigurationValues,
					...fieldsUIConfigurationValues,
				};
			},
			{}
		);
	}

	return {sxpElement: JSON.stringify(sxpElement, null, '\t')};
}

/**
 * Converts the attributes list to the format expected by the
 * `searchContextAttributes` property.
 *
 * For example:
 * Input: [{key: 'key1', value: 'value1'}, {key: 'key2', value: 'value2'}]
 * Output: {key1: 'value1', key2: 'value2'}
 * @param {array} attributes A list of objects with `key` and `value` properties.
 */
export function transformToSearchContextAttributes(attributes) {
	return attributes
		.filter((attribute) => attribute.key) // Removes empty keys
		.reduce(
			(searchContextAttributes, attribute) => ({
				...searchContextAttributes,
				[attribute.key]: attribute.value,
			}),
			{}
		);
}
