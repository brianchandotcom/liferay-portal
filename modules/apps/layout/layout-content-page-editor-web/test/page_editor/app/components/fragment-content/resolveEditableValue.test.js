/**
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

import resolveEditableValue from '../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/editable-value/resolveEditableValue';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			defaultLanguageId: 'en_US',
		},
	})
);

describe('resolveEditableValue', () => {
	it('returns the editable value for the given language id', async () => {
		const result = resolveEditableValue(
			{
				defaultValue: 'default',
				es_ES: 'value',
			},
			'es_ES',
			() => {}
		);

		await expect(result).resolves.toStrictEqual('value');
	});

	it('returns the default language value if there is no value', async () => {
		const result = resolveEditableValue(
			{defaultValue: 'default', en_US: 'defaultLanguage'},
			'es_ES'
		);

		await expect(result).resolves.toStrictEqual('defaultLanguage');
	});

	it('returns the default value if there is no value', async () => {
		const result = resolveEditableValue({defaultValue: 'default'}, 'en_US');

		await expect(result).resolves.toStrictEqual('default');
	});

	it('calls given function to retrieve the editable value when it is mapped', async () => {
		const getField = jest.fn(() => Promise.resolve('mapped'));

		const result = resolveEditableValue(
			{
				classNameId: 3,
				classPK: 2,
				fieldId: 'field',
			},
			'en_US',
			getField
		);

		expect(getField).toBeCalledWith(
			expect.objectContaining({
				classNameId: 3,
				classPK: 2,
				fieldId: 'field',
				languageId: 'en_US',
			})
		);

		await expect(result).resolves.toStrictEqual('mapped');
	});
});
