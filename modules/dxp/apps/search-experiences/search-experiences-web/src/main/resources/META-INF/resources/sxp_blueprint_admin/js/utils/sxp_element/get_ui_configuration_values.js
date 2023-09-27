/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';

import toNumber from '../functions/to_number';
import {INPUT_TYPES} from '../types/inputTypes';
import cleanUIConfiguration from './clean_ui_configuration';
import getSXPElementJSON from './get_sxp_element_json';

/**
 * Function for getting all the default values from an SXPElement. For non-custom
 * json elements, returns the configuration values after looping over all fieldSets.
 * For custom json elements, returns a stringified sxpElement for the editor.
 *
 * @param {object} sxpElement SXPElement with elementDefinition
 * @return {object}
 */
export default function getUIConfigurationValues(sxpElement = {}) {
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

	return {
		sxpElement: JSON.stringify(getSXPElementJSON(sxpElement), null, '\t'),
	};
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
