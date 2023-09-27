/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getFieldProperty,
	getFieldValue,
} from '../../../src/main/resources/META-INF/resources/js/core/utils/fields';
import {updateField} from '../../../src/main/resources/META-INF/resources/js/utils/settingsContext';

describe('utils/settingsContext', () => {
	describe('updateField(state, field, propertyName, propertyValue)', () => {
		let field;
		let state;

		beforeEach(() => {
			field = {
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
													fieldName:
														'predefinedValue',
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
														fieldName:
															'oldFieldName',
													},
													value: {
														expression: {
															value:
																'isEmailAddress(oldFieldName)',
														},
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

			state = {
				editingLanguageId: 'en_US',
				fieldNameGenerator: (value) => value.replaceAll(' ', ''),
			};
		});

		it('updates the desired property', () => {
			const {readOnly} = updateField(state, field, 'readOnly', true);

			expect(readOnly).toEqual(true);
		});

		describe('propertyName = "dataType"', () => {
			it('updates the field "dataType" property', () => {
				const {dataType} = updateField(
					state,
					field,
					'dataType',
					'newDataType'
				);

				expect(dataType).toEqual('newDataType');
			});

			it('updates the settingsContext of the field with the new dataType', () => {
				const {
					settingsContext: {pages},
				} = updateField(state, field, 'dataType', 'newDataType');

				expect(getFieldValue(pages, 'dataType')).toEqual('newDataType');
			});

			it('updates the validation expression of the validation field of the settingsContext with the new dataType', () => {
				const {
					settingsContext: {pages},
				} = updateField(state, field, 'dataType', 'newDataType');

				expect(
					getFieldProperty(pages, 'validation', 'validation').dataType
				).toEqual('newDataType');
			});
		});

		describe('propertyName = "label"', () => {
			it('updates the field "label" property', () => {
				const updatedField = updateField(
					state,
					field,
					'label',
					'New Label'
				);

				expect(updatedField.label).toEqual('New Label');
			});

			it('updates the settingsContext of the field with the new field label', () => {
				const updatedField = updateField(
					state,
					field,
					'label',
					'New Label'
				);

				expect(
					getFieldValue(updatedField.settingsContext.pages, 'label')
				).toEqual('New Label');
			});

			it('automaticallys update the field name if it was auto generated from its label', () => {
				const updatedField = updateField(
					{
						...state,
						defaultLanguageId: 'en_US',
						generateFieldNameUsingFieldLabel: true,
					},
					field,
					'label',
					'New Label'
				);

				expect(updatedField.fieldName).toEqual('NewLabel');
				expect(
					getFieldValue(updatedField.settingsContext.pages, 'name')
				).toEqual('NewLabel');
			});

			it('does not automatically update the field name if it was not auto generated from its label', () => {
				const updatedField = updateField(
					state,
					field,
					'label',
					'New Label'
				);

				expect(updatedField.fieldName).toEqual('oldFieldName');
				expect(
					getFieldValue(updatedField.settingsContext.pages, 'name')
				).toEqual('oldFieldName');
			});
		});

		describe('propertyName = "name"', () => {
			it('updates the field "fieldName" property', () => {
				const {fieldName} = updateField(
					state,
					field,
					'name',
					'newName'
				);

				expect(fieldName).toEqual('newName');
			});

			it('updates the settingsContext of the field with the new field name', () => {
				const {
					settingsContext: {pages},
				} = updateField(state, field, 'name', 'newName');

				expect(getFieldValue(pages, 'name')).toEqual('newName');
			});

			it('updates the validation expression of the validation field of the settingsContext with the new field name', () => {
				const {
					settingsContext: {pages},
				} = updateField(state, field, 'name', 'newName');

				expect(
					getFieldValue(pages, 'validation').expression.value
				).toEqual('isEmailAddress(newName)');
				expect(
					getFieldProperty(pages, 'validation', 'validation')
						.fieldName
				).toEqual('newName');
			});

			it('falls back to the previous valid name when trying to change to an invalid one', () => {
				const {
					settingsContext: {pages},
				} = updateField(state, field, 'name', 'oldName');

				expect(getFieldValue(pages, 'name')).toEqual('oldName');
			});
		});

		describe('propertyName = "options"', () => {
			it('updates the field "options" property', () => {
				const value = {
					en_US: [{label: 'New Label', value: 'NewLabel'}],
				};

				const {options} = updateField(state, field, 'options', value);

				expect(options).toEqual(value.en_US);
			});
		});
	});
});
