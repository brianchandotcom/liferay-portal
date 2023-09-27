/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
