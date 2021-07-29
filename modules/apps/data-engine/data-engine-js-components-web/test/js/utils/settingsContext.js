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

import {
	getFieldProperty,
	getFieldValue,
} from 'data-engine-js-components-web/js/core/utils/fields';

import {
	updateFieldDataType,
	updateFieldLabel,
	updateFieldName,
	updateFieldOptions,
	updateFieldProperty,
} from '../../../src/main/resources/META-INF/resources/js/utils/settingsContext';
import mockPages from '../__mock__/mockPages.es';

const field = {
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
										value: 'oldFieldName',
									},
									{
										fieldName: 'label',
										value: 'Old Field Label',
									},
									{
										fieldName: 'readOnly',
										value: false,
									},
									{
										fieldName: 'dataType',
										value: 'oldDataType',
									},
									{
										fieldName: 'predefinedValue',
										value: [
											{
												label: 'Predefined',
												value: 'Predefined',
											},
										],
									},
									{
										fieldName: 'validation',
										validation: {
											dataType: 'oldDataType',
											fieldName: 'oldFieldName',
										},
										value: {
											expression:
												'isEmailAddress(oldFieldName)',
										},
									},
								],
							},
						],
					},
				],
			},
		],
	},
};

xdescribe('utils/settingsContext', () => {
	describe('updateFieldLabel(state, field, value)', () => {
		it('updates the focused field "label" property', () => {
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldLabel(state, field, 'New Label');

			expect(updatedField.label).toEqual('New Label');
		});

		it('updates the settingsContext of the focused field with the new field label', () => {
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldLabel(state, field, 'New Label');

			expect(
				getFieldValue(updatedField.settingsContext.pages, 'label')
			).toEqual('New Label');
		});

		it('automaticallys update the field name if it was auto generated from its label', () => {
			const mockFocusedField = {
				...field,
				fieldName: 'GeneratedFieldName',
				label: 'Generated Field Name',
			};

			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldLabel(
				state,
				mockFocusedField,
				'New Label'
			);

			expect(updatedField.fieldName).toEqual('NewLabel');
			expect(
				getFieldValue(updatedField.settingsContext.pages, 'name')
			).toEqual('NewLabel');
		});

		it('does not automatically update the field name if it was not auto generated from its label', () => {
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldLabel(state, field, 'New Label');

			expect(updatedField.fieldName).toEqual('oldFieldName');
			expect(
				getFieldValue(updatedField.settingsContext.pages, 'name')
			).toEqual('oldFieldName');
		});
	});

	describe('updateFieldName(state, field, value)', () => {
		it('updates the focused field "fieldName" property', () => {
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldName(state, field, 'newName');

			expect(updatedField.fieldName).toEqual('newName');
		});

		it('updates the settingsContext of the focused field with the new field name', () => {
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldName(state, field, 'newName');

			expect(
				getFieldValue(updatedField.settingsContext.pages, 'name')
			).toEqual('newName');
		});

		it('updates the validation expression of the validation field of the settingsContext with the new field name', () => {
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldName(state, field, 'newName');

			expect(
				getFieldValue(updatedField.settingsContext.pages, 'validation')
					.expression
			).toEqual('isEmailAddress(newName)');
			expect(
				getFieldProperty(
					updatedField.settingsContext.pages,
					'validation',
					'validation'
				).fieldName
			).toEqual('newName');
		});

		it('falls back to the previous valid name when trying to change to an invalid one', () => {
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldName(state, field, 'oldName');

			expect(
				getFieldValue(updatedField.settingsContext.pages, 'name')
			).toEqual('oldName');
		});
	});

	describe('updateFieldDataType(state, field, value)', () => {
		it('updates the focused field "dataType" property', () => {
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldDataType(
				state,
				field,
				'newDataType'
			);

			expect(updatedField.dataType).toEqual('newDataType');
		});

		it('updates the settingsContext of the focused field with the new dataType', () => {
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldDataType(
				state,
				field,
				'newDataType'
			);

			expect(
				getFieldValue(updatedField.settingsContext.pages, 'dataType')
			).toEqual('newDataType');
		});

		it('updates the validation expression of the validation field of the settingsContext with the new dataType', () => {
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldDataType(
				state,
				field,
				'newDataType'
			);

			expect(
				getFieldProperty(
					updatedField.settingsContext.pages,
					'validation',
					'validation'
				).dataType
			).toEqual('newDataType');
		});
	});

	describe('updateFieldOptions(state, field, options)', () => {
		it('updates the focused field "options" property', () => {
			const newOptions = [
				{
					label: 'New Label',
					value: 'NewLabel',
				},
			];
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldOptions(state, field, newOptions);

			expect(updatedField.options).toEqual(newOptions);
		});
	});

	describe('updateFieldProperty(state, field, options)', () => {
		it('updates the desired property', () => {
			const state = {
				pages: mockPages,
			};

			const updatedField = updateFieldProperty(
				state,
				field,
				'readOnly',
				true
			);

			expect(updatedField.readOnly).toEqual(true);
		});
	});
});
