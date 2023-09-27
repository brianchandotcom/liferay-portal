/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';

import {CONFIG_PREFIX} from '../constants';
import isEmpty from '../functions/is_empty';
import replaceStr from '../functions/replace_str';
import toNumber from '../functions/to_number';
import {INPUT_TYPES} from '../types/inputTypes';
import cleanUIConfiguration from './clean_ui_configuration';
import isCustomJSONSXPElement from './is_custom_json_sxp_element';
import parseCustomSXPElement from './parse_custom_sxp_element';

/**
 * Function for replacing the ${variable_name} with actual value.
 *
 * @param {object} _.sxpElement SXP Element with elementDefinition
 * @param {object} _.uiConfigurationValues Values that will replace the keys in uiConfiguration
 * @return {object}
 */
export default function replaceTemplateVariable({
	sxpElement,
	uiConfigurationValues,
}) {
	const fieldSets = cleanUIConfiguration(
		sxpElement.elementDefinition?.uiConfiguration
	).fieldSets;

	if (!!fieldSets.length && !isCustomJSONSXPElement(uiConfigurationValues)) {
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

					const transformedLocale = !locale ? locale : `_${locale}`;

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
								const transformedLocale = !locale
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
					const initialValue = initialConfigValue.value
						? toNumber(initialConfigValue.value)
						: initialConfigValue;

					configValue =
						typeof config.typeOptions?.unitSuffix === 'string'
							? typeof initialValue === 'string'
								? initialValue.concat(
										config.typeOptions?.unitSuffix
								  )
								: JSON.stringify(initialValue).concat(
										config.typeOptions?.unitSuffix
								  )
							: initialValue;
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
