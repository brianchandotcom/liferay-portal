/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import TranslationOptions from '../../../src/main/resources/META-INF/resources/js/translation_manager/TranslationOptions';

const DEFAULT_FIELDS = {
	descriptionMapAsXML: {},
	friendlyURL: {},
	titleMapAsXML: {},
};

const DEFAULT_PROPS = {
	defaultLanguageId: 'en_US',
	fields: DEFAULT_FIELDS,
	locales: [
		{
			displayName: 'English',
			id: 'en_US',
			label: 'en-US',
			symbol: 'en-us',
		},
		{
			displayName: 'Arabic',
			id: 'ar_SA',
			label: 'ar-SA',
			symbol: 'ar-sa',
		},
		{
			displayName: 'Catalan',
			id: 'ca_ES',
			label: 'ca-ES',
			symbol: 'ca-es',
		},
	],
	namespace: '_com_liferay_journal_web_portlet_JournalPortlet_',
	selectedLanguageId: 'en_US',
};

const TRANSLATED_PROPS = {
	...DEFAULT_PROPS,
	selectedLanguageId: 'ar_SA',
	translationProgress: {
		totalItems: 4,
		translatedItems: {
			ar_SA: 1,
		},
	},
};

const renderDefaultComponent = () =>
	render(<TranslationOptions {...DEFAULT_PROPS} />);

const renderTranslatedComponent = () =>
	render(<TranslationOptions {...TRANSLATED_PROPS} />);

describe('TranslationOptions', () => {
	Liferay.FeatureFlags['LPD-11253'] = true;

	describe('Reset Translations Button', () => {
		it('reset translations button is disabled with default language', () => {
			renderDefaultComponent();

			const resetTranslationsButton = screen.getByText(
				'reset-translation'
			);

			expect(resetTranslationsButton).toBeDisabled();
		});

		it('reset translations button is enabled when there is a translation in progress', () => {
			renderTranslatedComponent();

			const resetTranslationsButton = screen.getByText(
				'reset-translation'
			);

			expect(resetTranslationsButton).not.toBeDisabled();
		});
	});
});
