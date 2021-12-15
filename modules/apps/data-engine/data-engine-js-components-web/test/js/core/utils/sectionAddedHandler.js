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

import {default as sectionAdded} from '../../../../src/main/resources/META-INF/resources/js/core/utils/sectionAddedHandler';
import * as fieldSupport from '../../../../src/main/resources/META-INF/resources/js/utils/fieldSupport';

const mockPages = [
	{
		rows: [
			{
				columns: [
					{
						fields: [],
						size: 6,
					},
					{
						fields: [
							{
								fieldName: 'FieldsGroup98239067',
								nestedFields: [
									{
										fieldName: 'Date78971332',
									},
									{
										fieldName: 'Numeric21612294',
									},
								],
								rows: [
									{
										columns: [
											{
												fields: ['Date78971332'],
												size: 12,
											},
										],
									},
									{
										columns: [
											{
												fields: ['Numeric21612294'],
												size: 12,
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
																	fieldName:
																		'nestedFields',
																},
																{
																	fieldName:
																		'rows',
																},
															],
															size: 12,
														},
													],
												},
											],
										},
									],
								},
								type: 'fieldset',
							},
						],
						size: 6,
					},
				],
			},
		],
	},
];

describe('core/utils/sectionAddedHandler', () => {

	// LPS-143808

	it('add new field by dragging over a fieldset without specifying the location', () => {
		const addFieldToPageSpy = jest.spyOn(fieldSupport, 'addFieldToPage');

		const fieldTypes = [
			{
				label: 'Fields Group',
				name: 'fieldset',
				settingsContext: {
					pages: [],
				},
			},
		];
		const newField = {
			fieldName: 'Text12345678',
			instanceId: 'j1qwbgsz',
			type: 'text',
		};

		const {pages} = sectionAdded(
			{
				fieldTypes,
			},
			{
				pages: mockPages,
			},
			{
				data: {
					fieldName: 'FieldsGroup98239067',
					parentFieldName: 'parentFieldName',
				},
				newField,
				useFieldName: 'Text12345678',
			}
		);

		expect(addFieldToPageSpy).toHaveBeenCalledWith({
			fieldTypes,
			indexes: {
				columnIndex: 0,
				pageIndex: 0,
				rowIndex: 2,
			},
			newField,
			pages: mockPages,
			parentFieldName: 'FieldsGroup98239067',
		});

		const fieldset = pages[0].rows[0].columns[1].fields[0];

		expect(fieldset.rows[0].columns[0].fields[0]).toBe('Date78971332');
		expect(fieldset.rows[1].columns[0].fields[0]).toBe('Numeric21612294');
		expect(fieldset.rows[2].columns[0].fields[0]).toBe('Text12345678');
	});
});
