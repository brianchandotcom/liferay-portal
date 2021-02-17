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

import dataConverter, {
	getDataDefinitionField,
	getDefaultDataLayout,
} from '../../../../src/main/resources/META-INF/resources/data_layout_builder/js/utils/dataConverter.es';

describe('dataConverter', () => {
	it('is getting component form data property', () => {
		expect(
			dataConverter._fromDDMFormToDataDefinitionPropertyName('fieldName')
		).toBe('name');
		expect(
			dataConverter._fromDDMFormToDataDefinitionPropertyName(
				'nestedFields'
			)
		).toBe('nestedDataDefinitionFields');
		expect(
			dataConverter._fromDDMFormToDataDefinitionPropertyName(
				'predefinedValue'
			)
		).toBe('defaultValue');
		expect(
			dataConverter._fromDDMFormToDataDefinitionPropertyName('type')
		).toBe('fieldType');
		expect(
			dataConverter._fromDDMFormToDataDefinitionPropertyName(
				'otherProperty'
			)
		).toBe('otherProperty');
	});

	it('is getting defaultDataLayout', () => {
		const dataDefinition = {
			dataDefinitionFields: [],
		};

		expect(getDefaultDataLayout(dataDefinition)).toMatchObject({
			dataLayoutPages: [{dataLayoutRows: []}],
		});
	});

	it('is getting data definition field formatted value', () => {
		expect(
			dataConverter._getDataDefinitionFieldFormattedValue('json', {
				test: 'test',
			})
		).toBe('{"test":"test"}');

		expect(
			dataConverter._getDataDefinitionFieldFormattedValue('', 'test')
		).toBe('test');
	});

	it('is getting data definition field', () => {
		expect(
			getDataDefinitionField(
				{
					nestedFields: [],
					settingsContext: {
						pages: [],
					},
				},
				[],
				'en_US'
			)
		).toMatchObject({
			customProperties: {},
			nestedDataDefinitionFields: [],
		});
	});

	it('is component custom property', () => {
		expect(dataConverter._isCustomProperty('defaultValue')).toBe(false);
		expect(dataConverter._isCustomProperty('otherProperty')).toBe(true);
	});
});
