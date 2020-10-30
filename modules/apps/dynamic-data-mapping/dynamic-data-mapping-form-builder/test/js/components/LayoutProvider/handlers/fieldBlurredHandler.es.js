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

import handleFieldAdded from '../../../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/handlers/fieldAddedHandler.es';
import handleFieldBlurred from '../../../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/handlers/fieldBlurredHandler.es';
import handleFieldEdited from '../../../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/handlers/fieldEditedHandler.es';
import {generateFieldName} from '../../../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/util/fields.es';
import mockFieldTypes from '../../../__mock__/mockFieldTypes.es';

describe('LayoutProvider/handlers/fieldBlurredHandler', () => {
	describe('handleFieldBlured(props, state, event)', () => {
		it('updates the field reference with original fieldReference when field reference is empty', () => {
			let state = {
				focusedField: {},
				pages: [{rows: [{columns: [{fields: []}]}]}],
				rules: [],
			};

			const props = {
				defaultLanguageId: 'en_US',
				editingLanguageId: 'en_US',
				fieldNameGenerator: (desiredName, currentName) => {
					const {pages} = state;

					return generateFieldName(pages, desiredName, currentName);
				},
			};

			// Adds a field

			state = handleFieldAdded(props, state, {
				data: {},
				fieldType: mockFieldTypes[0],
				indexes: {
					columnIndex: 0,
					pageIndex: 0,
					rowIndex: 0,
				},
			});

			const defaultFieldReference = state.focusedField.fieldName;

			// Edits the field reference with empty value

			state = handleFieldEdited(props, state, {
				propertyName: 'fieldReference',
				propertyValue: '',
			});

			// Leaves the field

			state = handleFieldBlurred(props, state, {
				propertyName: 'fieldReference',
				propertyValue: '',
			});

			expect(state.focusedField.fieldReference).toEqual(
				defaultFieldReference
			);
		});
	});

	it('updates the field reference with original fieldReference when field reference already exists', () => {
		let state = {
			focusedField: {},
			pages: [{rows: [{columns: [{fields: []}]}]}],
			rules: [],
		};

		const props = {
			defaultLanguageId: 'en_US',
			editingLanguageId: 'en_US',
			fieldNameGenerator: (desiredName, currentName) => {
				const {pages} = state;

				return generateFieldName(pages, desiredName, currentName);
			},
		};

		// Adds a field

		state = handleFieldAdded(props, state, {
			data: {},
			fieldType: mockFieldTypes[0],
			indexes: {
				columnIndex: 0,
				pageIndex: 0,
				rowIndex: 0,
			},
		});

		// Edits the field reference

		state = handleFieldEdited(props, state, {
			propertyName: 'fieldReference',
			propertyValue: 'NewReference',
		});

		// Adds a field

		state = handleFieldAdded(props, state, {
			data: {},
			fieldType: mockFieldTypes[0],
			indexes: {
				columnIndex: 0,
				pageIndex: 0,
				rowIndex: 0,
			},
		});

		const defaultFieldReference = state.focusedField.fieldName;

		// Edits the field reference

		state = handleFieldEdited(props, state, {
			propertyName: 'fieldReference',
			propertyValue: 'NewReference',
		});

		// Leaves the field

		state = handleFieldBlurred(props, state, {
			propertyName: 'fieldReference',
			propertyValue: 'NewReference',
		});

		expect(state.focusedField.fieldReference).toEqual(
			defaultFieldReference
		);
	});
});
