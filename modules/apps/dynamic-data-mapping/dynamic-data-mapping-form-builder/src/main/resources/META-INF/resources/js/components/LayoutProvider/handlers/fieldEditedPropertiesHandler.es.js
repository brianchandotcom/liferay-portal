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

import {getField} from '../../../util/fieldSupport.es';
import {updateRulesReferences} from '../util/rules.es';
import {updateField} from '../util/settingsContext.es';
import {updatePages} from './fieldEditedHandler.es';

const updateState = (props, state, properties) => {
	const {activePage, focusedField, pages, rules} = state;
	const {fieldName: previousFocusedFieldName} = focusedField;
	let newFocusedField;

	const {defaultLanguageId} = props;

	if (properties.length === 1) {
		const [{name, value}] = properties;
		newFocusedField = updateField(props, focusedField, name, value);
	}
	else {
		newFocusedField = properties.reduce(
			(initialField, {name, value}, index) => {
				let useField = initialField;
				if (index === 1) {
					if (initialField.name === 'nestedFields') {
						initialField.value = initialField.value.map(
							(nestedField) => {
								return updateField(
									props,
									nestedField,
									'label',
									nestedField.label[defaultLanguageId]
								);
							}
						);
					}

					useField = updateField(
						props,
						focusedField,
						initialField.name,
						initialField.value
					);
				}

				return updateField(props, useField, name, value);
			}
		);
	}

	const newPages = updatePages(
		props,
		pages,
		previousFocusedFieldName,
		newFocusedField
	);

	return {
		activePage,
		focusedField: newFocusedField,
		pages: newPages,
		rules: updateRulesReferences(
			rules || [],
			focusedField,
			newFocusedField
		),
	};
};

export default (props, state, event) => {
	const {fieldName, properties} = event;

	if (!Array.isArray(properties) || !properties.length) {
		return;
	}

	properties.filter(({name, value}) => name !== 'name' || value !== '');

	state = {
		...state,
		...(fieldName && {focusedField: getField(state.pages, fieldName)}),
	};

	return updateState(props, state, properties);
};
