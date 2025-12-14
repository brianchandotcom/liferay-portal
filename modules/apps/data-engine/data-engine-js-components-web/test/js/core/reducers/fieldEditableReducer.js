/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../../../../src/main/resources/META-INF/resources/js/core/actions/eventTypes.es';
import fieldEditableReducer from '../../../../src/main/resources/META-INF/resources/js/core/reducers/fieldEditableReducer.es';

describe('fieldset nested rename', () => {
	it('keeps nested field reference in fieldset rows when renaming', () => {
		const nestedField = {
			fieldName: 'Text123',
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
												value: 'Text123',
											},
										],
									},
								],
							},
						],
					},
				],
			},
			type: 'text',
		};

		const fieldset = {
			fieldName: 'Fieldset1',
			nestedFields: [nestedField],
			rows: [
				{
					columns: [
						{
							fields: ['Text123'],
						},
					],
				},
			],
			settingsContext: {
				pages: [
					{
						rows: [
							{
								columns: [
									{
										fields: [
											{
												fieldName: 'rows',
												value: [
													{
														columns: [
															{
																fields: [
																	'Text123',
																],
															},
														],
													},
												],
											},
										],
									},
								],
							},
						],
					},
				],
			},
			type: 'fieldset',
		};

		const initialState = {
			defaultLanguageId: 'en_US',
			editingLanguageId: 'en_US',
			focusedField: nestedField,
			pages: [
				{
					rows: [
						{
							columns: [
								{
									fields: [fieldset],
								},
							],
						},
					],
				},
			],
			rules: [],
		};

		const action = {
			payload: {
				fieldName: 'Text123',
				propertyName: 'name',
				propertyValue: 'RenamedText',
			},
			type: EVENT_TYPES.FIELD.CHANGE,
		};

		const config = {
			generateFieldNameUsingFieldLabel: false,
			getFieldNameGenerator: () => () => 'RenamedText',
		};

		const newState = fieldEditableReducer(initialState, action, config);

		const updatedFieldset = newState.pages[0].rows[0].columns[0].fields[0];

		const rowsField =
			updatedFieldset.settingsContext.pages[0].rows[0].columns[0]
				.fields[0];

		expect(rowsField.value[0].columns[0].fields).toEqual(['RenamedText']);
	});
});
