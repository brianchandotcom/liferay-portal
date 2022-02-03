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
	_fromDDMFormToDataDefinitionPropertyName,
	fieldToDataDefinition,
} from '../../../src/main/resources/META-INF/resources/js/utils/dataConverter';

describe('dataConverter', () => {
	it('is getting component form data property', () => {
		expect(_fromDDMFormToDataDefinitionPropertyName('fieldName')).toBe(
			'name'
		);
		expect(_fromDDMFormToDataDefinitionPropertyName('nestedFields')).toBe(
			'nestedDataDefinitionFields'
		);
		expect(
			_fromDDMFormToDataDefinitionPropertyName('predefinedValue')
		).toBe('defaultValue');
		expect(_fromDDMFormToDataDefinitionPropertyName('type')).toBe(
			'fieldType'
		);
		expect(_fromDDMFormToDataDefinitionPropertyName('otherProperty')).toBe(
			'otherProperty'
		);
	});

	it('is getting data definition field', () => {
		expect(
			fieldToDataDefinition({
				nestedFields: [],
				settingsContext: {pages: []},
			})
		).toMatchObject({
			customProperties: {},
			nestedDataDefinitionFields: [],
		});
	});
});
