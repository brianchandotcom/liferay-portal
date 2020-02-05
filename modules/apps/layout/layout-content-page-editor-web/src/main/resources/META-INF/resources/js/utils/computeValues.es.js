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

import {isNullOrUndefined} from './isNullOrUndefined.es';
import {prefixSegmentsExperienceId} from './prefixSegmentsExperienceId.es';

/**
 * Given an editable value and configuration, the function retrieves the content to be render
 *
 * The segmented values inside the editableValue are isolated.
 * When the configuration does not provide a selectedExperienceId, it is assumed that segmentation is not supported
 *
 * @param {object} editableValue
 * @param {string} editableValue.defaultValue
 * @param {object} configuration
 * @param {string} configuration.defaultLanguageId
 * @param {string} configuration.selectedExperienceId
 * @param {string} configuration.selectedLanguageId
 * @return {string}
 * @review
 */
export function computeEditableValue(editableValue, configuration = {}) {
	const {defaultValue, value} = getComputedEditableValue(
		editableValue,
		configuration
	);

	return value === undefined ? defaultValue : value;
}

/**
 * Given an editable value and configuration, the function retrieves an object with the default value and the segmented and translated value
 *
 * The segmented values inside the editableValue are isolated.
 * When the configuration does not provide a selectedExperienceId, it is assumed that segmentation is not supported
 *
 * @param {object} editableValue
 * @param {string} editableValue.defaultValue
 * @param {object} configuration
 * @param {string} configuration.defaultLanguageId
 * @param {string} configuration.selectedExperienceId
 * @param {string} configuration.selectedLanguageId
 * @return {{ defaultValue: string, value: string }}
 * @review
 */
export function getComputedEditableValue(editableValue, configuration = {}) {
	const {
		defaultLanguageId,
		selectedExperienceId,
		selectedLanguageId
	} = configuration;

	const result = {
		defaultValue: editableValue.defaultValue
	};

	if (!isNullOrUndefined(selectedExperienceId)) {
		const prefixedExperienceKey = prefixSegmentsExperienceId(
			selectedExperienceId
		);

		const segmentedValue = editableValue[prefixedExperienceKey];

		result.value = _getTranslatedValue(
			segmentedValue || editableValue,
			selectedLanguageId,
			defaultLanguageId
		);
	}
	else {
		result.value = _getTranslatedValue(
			editableValue,
			selectedLanguageId,
			defaultLanguageId
		);
	}

	if (isNullOrUndefined(result.value)) {
		result.value = isNullOrUndefined(selectedLanguageId)
			? editableValue[defaultLanguageId]
			: editableValue[selectedLanguageId];
	}

	return result;
}

function _getTranslatedValue(values, selectedLanguageId, defaultLanguageId) {
	const preferedTranslatedValue = values[selectedLanguageId];

	const translatedValue = isNullOrUndefined(preferedTranslatedValue)
		? values[defaultLanguageId]
		: preferedTranslatedValue;

	return translatedValue;
}

export function computeConfigurationEditableValue(
	configurationValue,
	{selectedExperienceId}
) {
	if (selectedExperienceId === undefined) {
		return configurationValue;
	}

	const prefixedExperienceKey = prefixSegmentsExperienceId(
		selectedExperienceId
	);

	return configurationValue[prefixedExperienceKey];
}
