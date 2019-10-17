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

import * as FormSupport from 'dynamic-data-mapping-form-renderer/js/components/FormRenderer/FormSupport.es';

import {updateFocusedField} from '../util/focusedField.es';
import {updateRulesReferences} from '../util/rules.es';

export const updatePages = (pages, oldFieldProperties, newFieldProperties) => {
	const {fieldName} = oldFieldProperties;

	return FormSupport.updateField(pages, fieldName, newFieldProperties);
};

export const updateField = (props, state, fieldName, fieldValue) => {
	const {focusedField, pages, rules} = state;
	const updatedFocusedField = updateFocusedField(
		props,
		state,
		fieldName,
		fieldValue
	);

	return {
		focusedField: updatedFocusedField,
		pages: updatePages(pages, focusedField, updatedFocusedField),
		rules: updateRulesReferences(
			rules || [],
			focusedField,
			updatedFocusedField
		)
	};
};

export const handleFieldEdited = (props, state, event) => {
	const {propertyName, propertyValue} = event;
	let newState = {};

	if (propertyName !== 'name' || propertyValue !== '') {
		newState = updateField(props, state, propertyName, propertyValue);
	}

	return newState;
};

export default handleFieldEdited;
