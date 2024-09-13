/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fieldToTranslations} from '../../../src/main/resources/META-INF/resources/js/translation_manager/useTranslationProgress';

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import TranslationManager from '../../../src/main/resources/META-INF/resources/js/translation_manager/TranslationManager';

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
	namespace: 'test',
	selectedLanguageId: 'en_US',
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

	it('attaches inputLocalized:updateTranslationStatus event when the button is clicked', () => {
		renderComponent();

		userEvent.click(screen.getByRole('combobox'));

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
		const renderComponent = () =>
			render(
				<>
					<div data-languageid="en_US" data-value="en_US" />

					<TranslationManager {...DEFAULT_PROPS} />
				</>
			);

		renderComponent();

		const item = document.createElement('div');
		item.dataset.value = 'en_US';
		item.dataset.languageid = 'en_US';

		expect(global.Liferay.fire).toHaveBeenCalledWith(
			'inputLocalized:localeChanged',
			{
				item,
			}
		);
	});
});
