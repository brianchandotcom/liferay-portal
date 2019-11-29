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

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from './constants';

/**
 * @param {object} object
 * @param {string} defaultSegmentsExperienceKey
 */
function _editableFragmentMigrator(object, defaultSegmentsExperienceKey) {
	let alternativeObject = null;
	const defaultSegment = {};
	Object.keys(object).forEach(oKey => {
		if (oKey !== 'defaultValue' && typeof object[oKey] === 'string') {
			defaultSegment[oKey] = object[oKey];
		}
	});
	if (Object.keys(defaultSegment).length > 0) {
		alternativeObject = {
			[defaultSegmentsExperienceKey]: defaultSegment,
			defaultValue: object.defaultValue
		};
	}
	return alternativeObject || object;
}

/**
 * @param {object} editableValue
 * @param {string} defaultSegmentsExperienceKey
 */
function editableValuesMigrator(editableValue, defaultSegmentsExperienceKey) {
	const jsonEditableValues = JSON.parse(editableValue);
	let result;

	if (!defaultSegmentsExperienceKey) {
		result = jsonEditableValues;
	}
	else {
		result = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {}
		};
		Object.keys(
			jsonEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]
		).forEach(editableFragmentKey => {
			result[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][
				editableFragmentKey
			] = _editableFragmentMigrator(
				jsonEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][
					editableFragmentKey
				],
				defaultSegmentsExperienceKey
			);
		});
	}
	return result;
}

export default editableValuesMigrator;
