/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getLocalizedValue from '../../../../src/main/resources/META-INF/resources/js/common/utils/getLocalizedValue';

describe('getLocalizedValue', () => {
	let getLanguageIdSpy: jest.SpyInstance;

	beforeEach(() => {
		getLanguageIdSpy = jest.spyOn(Liferay.ThemeDisplay, 'getLanguageId');
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('returns the value at the current language when present', () => {
		getLanguageIdSpy.mockReturnValue('es_ES');

		expect(
			getLocalizedValue({
				en_US: 'Home',
				es_ES: 'Inicio',
			} as Liferay.Language.LocalizedValue<string>)
		).toBe('Inicio');
	});

	it('falls back to the default language when current language is missing', () => {
		getLanguageIdSpy.mockReturnValue('es_ES');

		expect(
			getLocalizedValue({
				en_US: 'Home',
			} as Liferay.Language.LocalizedValue<string>)
		).toBe('Home');
	});

	it('returns an empty string when both current and default are missing', () => {
		getLanguageIdSpy.mockReturnValue('es_ES');

		expect(
			getLocalizedValue({
				fr_FR: 'Maison',
			} as Liferay.Language.LocalizedValue<string>)
		).toBe('');
	});

	it('returns an empty string when value is undefined', () => {
		expect(
			getLocalizedValue(
				undefined as unknown as Liferay.Language.LocalizedValue<string>
			)
		).toBe('');
	});

	it('respects an explicit languageId argument', () => {
		getLanguageIdSpy.mockReturnValue('es_ES');

		expect(
			getLocalizedValue(
				{
					en_US: 'Home',
					es_ES: 'Inicio',
				} as Liferay.Language.LocalizedValue<string>,
				'en_US'
			)
		).toBe('Home');
	});

	it('falls back to the default language when the explicit languageId is missing', () => {
		expect(
			getLocalizedValue(
				{
					en_US: 'Home',
				} as Liferay.Language.LocalizedValue<string>,
				'es_ES'
			)
		).toBe('Home');
	});
});
