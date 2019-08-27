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

import mockPages from '../../../__mock__/mockPages.es';
import {
	getFieldProperty,
	getFieldValue
} from '../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/util/fields.es';
import {
	updateFocusedFieldDataType,
	updateFocusedFieldLabel,
	updateFocusedFieldName,
	updateFocusedFieldOptions,
	updateFocusedFieldProperty
} from '../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/util/focusedField.es';

const focusedField = {
	fieldName: 'oldFieldName',
	label: 'Old Field Label',
	settingsContext: {
		pages: [
			{
				rows: [
					{
						columns: [
							{
								fields: [
									{
										fieldName: 'name',
										value: 'oldFieldName'
									},
									{
										fieldName: 'label',
										value: 'Old Field Label'
									},
									{
										fieldName: 'readOnly',
										value: false
									},
									{
										fieldName: 'dataType',
										value: 'oldDataType'
									},
									{
										fieldName: 'predefinedValue',
										value: [
											{
												label: 'Predefined',
												value: 'Predefined'
											}
										]
									},
									{
										fieldName: 'validation',
										validation: {
											dataType: 'oldDataType',
											fieldName: 'oldFieldName'
										},
										value: {
											expression:
												'isEmailAddress(oldFieldName)'
										}
									}
								]
							}
						]
					}
				]
			}
		]
	}
};

describe('LayoutProvider/util/focusedField', () => {
	describe('updateFocusedFieldLabel(state, focusedField, value)', () => {
		it('updates the focused field "label" property', () => {
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldLabel(
				state,
				focusedField,
				'New Label'
			);

			expect(newFocusedField.label).toEqual('New Label');
		});

		it('updates the settingsContext of the focused field with the new field label', () => {
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldLabel(
				state,
				focusedField,
				'New Label'
			);

			expect(
				getFieldValue(newFocusedField.settingsContext.pages, 'label')
			).toEqual('New Label');
		});

		it('automaticallys update the field name if it was auto generated from its label', () => {
			const mockFocusedField = {
				...focusedField,
				fieldName: 'GeneratedFieldName',
				label: 'Generated Field Name'
			};

			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldLabel(
				state,
				mockFocusedField,
				'New Label'
			);

			expect(newFocusedField.fieldName).toEqual('NewLabel');
			expect(
				getFieldValue(newFocusedField.settingsContext.pages, 'name')
			).toEqual('NewLabel');
		});

		it('does not automatically update the field name if it was not auto generated from its label', () => {
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldLabel(
				state,
				focusedField,
				'New Label'
			);

			expect(newFocusedField.fieldName).toEqual('oldFieldName');
			expect(
				getFieldValue(newFocusedField.settingsContext.pages, 'name')
			).toEqual('oldFieldName');
		});
	});

	describe('updateFocusedFieldName(state, focusedField, value)', () => {
		it('updates the focused field "fieldName" property', () => {
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldName(
				state,
				focusedField,
				'newName'
			);

			expect(newFocusedField.fieldName).toEqual('newName');
		});

		it('updates the settingsContext of the focused field with the new field name', () => {
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldName(
				state,
				focusedField,
				'newName'
			);

			expect(
				getFieldValue(newFocusedField.settingsContext.pages, 'name')
			).toEqual('newName');
		});

		it('updates the validation expression of the validation field of the settingsContext with the new field name', () => {
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldName(
				state,
				focusedField,
				'newName'
			);

			expect(
				getFieldValue(
					newFocusedField.settingsContext.pages,
					'validation'
				).expression
			).toEqual('isEmailAddress(newName)');
			expect(
				getFieldProperty(
					newFocusedField.settingsContext.pages,
					'validation',
					'validation'
				).fieldName
			).toEqual('newName');
		});

		it('falls back to the previous valid name when trying to change to an invalid one', () => {
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldName(
				state,
				focusedField,
				'oldName'
			);

			expect(
				getFieldValue(newFocusedField.settingsContext.pages, 'name')
			).toEqual('oldName');
		});
	});

	describe('updateFocusedFieldDataType(state, focusedField, value)', () => {
		it('updates the focused field "dataType" property', () => {
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldDataType(
				state,
				focusedField,
				'newDataType'
			);

			expect(newFocusedField.dataType).toEqual('newDataType');
		});

		it('updates the settingsContext of the focused field with the new dataType', () => {
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldDataType(
				state,
				focusedField,
				'newDataType'
			);

			expect(
				getFieldValue(newFocusedField.settingsContext.pages, 'dataType')
			).toEqual('newDataType');
		});

		it('updates the validation expression of the validation field of the settingsContext with the new dataType', () => {
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldDataType(
				state,
				focusedField,
				'newDataType'
			);

			expect(
				getFieldProperty(
					newFocusedField.settingsContext.pages,
					'validation',
					'validation'
				).dataType
			).toEqual('newDataType');
		});
	});

	describe('updateFocusedFieldOptions(state, focusedField, options)', () => {
		it('updates the focused field "options" property', () => {
			const newOptions = [
				{
					label: 'New Label',
					value: 'NewLabel'
				}
			];
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldOptions(
				state,
				focusedField,
				newOptions
			);

			expect(newFocusedField.options).toEqual(newOptions);
		});
	});

	describe('updateFocusedFieldProperty(state, focusedField, options)', () => {
		it('updates the desired property', () => {
			const state = {
				pages: mockPages
			};

			const newFocusedField = updateFocusedFieldProperty(
				state,
				focusedField,
				'readOnly',
				true
			);

			expect(newFocusedField.readOnly).toEqual(true);
		});
	});
});
