/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import TranslationManager, {
	fieldToTranslations,
} from '../../../src/main/resources/META-INF/resources/js/translation_manager/TranslationManager';

const FIELDS = {
	description: {
		ar_SA: 'test',
		ca_ES: 'test',
	},
	name: {
		ca_ES: 'test',
		en_US: 'test',
	},
};

const DEFAULT_PROPS = {
	defaultLanguageId: 'en_US',
	fields: FIELDS,
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
	selectedLanguageId: 'ar_SA',
};

const renderComponent = () => render(<TranslationManager {...DEFAULT_PROPS} />);

describe('TranslationManager', () => {
	it('renders component', () => {
		renderComponent();

		expect(screen.getByText('en-US')).toBeInTheDocument();
	});

	it('converts field to translation', () => {
		renderComponent();

		expect(fieldToTranslations(FIELDS)).toStrictEqual([
			{
				fieldName: 'description',
				languages: ['ar_SA', 'ca_ES'],
			},
			{
				fieldName: 'name',
				languages: ['ca_ES', 'en_US'],
			},
		]);
	});

	it('attaches inputLocalized:updateTranslationStatus event on mount', () => {
		renderComponent();

		expect(global.Liferay.on).toHaveBeenCalledWith(
			'inputLocalized:updateTranslationStatus',
			expect.any(Function)
		);
	});

	it('detaches inputLocalized:updateTranslationStatus event on unmount', () => {
		const {unmount} = renderComponent();

		unmount();

		expect(global.Liferay.detach).toHaveBeenCalledWith(
			'inputLocalized:updateTranslationStatus',
			expect.any(Function)
		);
	});

	it('attaches inputLocalized:localeChanged fire event on mount', () => {
		renderComponent();

		expect(
			global.Liferay.fire
		).toHaveBeenCalledWith('inputLocalized:localeChanged', {item: null});
	});
});
