/**
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import resolveEditableValue from '../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/editable_value/resolveEditableValue';

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
