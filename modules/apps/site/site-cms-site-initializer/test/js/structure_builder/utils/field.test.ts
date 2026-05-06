/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {setDefaultLanguageLabels} from '../../../../src/main/resources/META-INF/resources/js/common/utils/getDefaultLanguageLabel';
import {
	FIELD_TYPE_LABEL,
	getDefaultField,
} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/field';
import getUuid from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/getUuid';

describe('getDefaultField', () => {
	let getDefaultLanguageIdSpy: jest.SpyInstance;
	let getLanguageIdSpy: jest.SpyInstance;

	beforeEach(() => {
		getDefaultLanguageIdSpy = jest.spyOn(
			Liferay.ThemeDisplay,
			'getDefaultLanguageId'
		);
		getLanguageIdSpy = jest.spyOn(Liferay.ThemeDisplay, 'getLanguageId');
	});

	afterEach(() => {
		jest.restoreAllMocks();
		setDefaultLanguageLabels({locale: 'en_US'});
	});

	it('populates label under both default and current language IDs when they differ', () => {
		getDefaultLanguageIdSpy.mockReturnValue('en_US');
		getLanguageIdSpy.mockReturnValue('es_ES');

		const field = getDefaultField({
			label: 'My Label',
			parent: getUuid(),
			type: 'text',
		});

		expect(field.label).toEqual({
			en_US: 'My Label',
			es_ES: 'My Label',
		});
	});

	it('produces a single label key when current and default language match', () => {
		getDefaultLanguageIdSpy.mockReturnValue('en_US');
		getLanguageIdSpy.mockReturnValue('en_US');

		const field = getDefaultField({
			label: 'My Label',
			parent: getUuid(),
			type: 'text',
		});

		expect(Object.keys(field.label)).toEqual(['en_US']);
		expect(field.label.en_US).toBe('My Label');
	});

	it('falls back to FIELD_TYPE_LABEL when no label is provided', () => {
		getDefaultLanguageIdSpy.mockReturnValue('en_US');
		getLanguageIdSpy.mockReturnValue('es_ES');

		const field = getDefaultField({
			parent: getUuid(),
			type: 'text',
		});

		expect(field.label).toEqual({
			en_US: FIELD_TYPE_LABEL.text,
			es_ES: FIELD_TYPE_LABEL.text,
		});
	});

	it('seeds the default-language label from the singleton when no label is provided', () => {
		getDefaultLanguageIdSpy.mockReturnValue('en_US');
		getLanguageIdSpy.mockReturnValue('es_ES');

		setDefaultLanguageLabels({
			'date-and-time': 'Date and time',
			'locale': 'en_US',
			'text': 'Text',
		});

		expect(
			getDefaultField({parent: getUuid(), type: 'text'}).label.en_US
		).toBe('Text');

		expect(
			getDefaultField({parent: getUuid(), type: 'datetime'}).label.en_US
		).toBe('Date and time');
	});
});
