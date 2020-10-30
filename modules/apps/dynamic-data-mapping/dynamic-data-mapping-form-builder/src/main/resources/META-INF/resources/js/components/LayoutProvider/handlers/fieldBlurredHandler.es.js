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

import {updateField, updateFieldReference} from '../util/settingsContext.es';
import {findInvalidFieldReference, updateState} from './fieldEditedHandler.es';

export const handleFieldBlurred = (props, state, event) => {
	let newState = {
		fieldHovered: {},
		pages: state.pages,
	};

	if (event) {
		const {propertyName, propertyValue} = event;

		if (propertyName === 'name' && propertyValue === '') {
			newState = updateField(state, propertyName, propertyValue);
		}

		if (
			propertyName === 'fieldReference' &&
			(propertyValue === '' ||
				findInvalidFieldReference(
					state.pages,
					state.focusedField,
					propertyValue
				))
		) {
			newState = updateState(
				props,
				{
					...state,
					focusedField: updateFieldReference(
						state.focusedField,
						false
					),
				},
				propertyName,
				state.focusedField.fieldName
			);
		}
	}

	return newState;
};

export default handleFieldBlurred;
