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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import TranslationAdminSelector from '../../src/main/resources/META-INF/resources/translation_manager/TranslationAdminSelector';

const activeLanguageIds = ['en_US', 'ca_ES'];

const availableLocales = [
	{
		displayName: 'English (United States)',
		id: 'en_US',
		label: 'en-US',
		symbol: 'en-us',
	},
	{
		displayName: 'Arabic (Saudi Arabia)',
		id: 'ar_SA',
		label: 'ar-SA',
		symbol: 'ar-sa',
	},
	{
		displayName: 'Catalan (Spain)',
		id: 'ca_ES',
		label: 'ca-ES',
		symbol: 'ca-es',
	},
	{
		displayName: 'Chinese (China)',
		id: 'zh_CN',
		label: 'zh-CN',
		symbol: 'zh-cn',
	},
	{
		displayName: 'Dutch (Netherlands)',
		id: 'nl_NL',
		label: 'nl-NL',
		symbol: 'nl-nl',
	},
	{
		displayName: 'Finnish (Finland)',
		id: 'fi_FI',
		label: 'fi-FI',
		symbol: 'fi-fi',
	},
	{
		displayName: 'French (France)',
		id: 'fr_FR',
		label: 'fr-FR',
		symbol: 'fr-fr',
	},
	{
		displayName: 'German (Germany)',
		id: 'de_DE',
		label: 'de-DE',
		symbol: 'de-de',
	},
	{
		displayName: 'Hungarian (Hungary)',
		id: 'hu_HU',
		label: 'hu-HU',
		symbol: 'hu-hu',
	},
	{
		displayName: 'Japanese (Japan)',
		id: 'ja_JP',
		label: 'ja-JP',
		symbol: 'ja-jp',
	},
	{
		displayName: 'Portuguese (Brazil)',
		id: 'pt_BR',
		label: 'pt-BR',
		symbol: 'pt-br',
	},
	{
		displayName: 'Spanish (Spain)',
		id: 'es_ES',
		label: 'es-ES',
		symbol: 'es-es',
	},
	{
		displayName: 'Swedish (Sweden)',
		id: 'sv_SE',
		label: 'sv-SE',
		symbol: 'sv-se',
	},
];

const defaultLanguageId = 'en_US';

const props = {
	activeLanguageIds,
	availableLocales,
	defaultLanguageId,
	onActiveLanguageIdsChange: jest.fn(),
	onSelectedLanguageIdChange: jest.fn(),
};

jest.mock(
	'../../src/main/resources/META-INF/resources/translation_manager/TranslationAdminModal',
	() => {
		return ({onClose, visible}) => {
			const handleOnClose = () => {
				onClose(['ar_SA', 'en_US', 'nl_NL']);
			};

			return (
				<>
					{visible && (
						<div className="modal">
							<button className="close" onClick={handleOnClose}>
								Close
							</button>
						</div>
					)}
				</>
			);
		};
	}
);

const TranslationAdminSelectorWithState = () => {
	const [activeLanguageIds, setActiveLanguageIds] = React.useState(
		props.activeLanguageIds
	);

	return (
		<>
			<button
				data-testid="change-state"
				onClick={() =>
					setActiveLanguageIds(['ar_SA', 'en_US', 'nl_NL'])
				}
			>
				Change State
			</button>

			<TranslationAdminSelector
				{...props}
				activeLanguageIds={activeLanguageIds}
			/>
		</>
	);
};

describe('TranslationAdminSelector', () => {
	afterAll(() => {
		jest.useRealTimers();
	});

	afterEach(() => {
		jest.clearAllTimers();
		jest.restoreAllMocks();
		cleanup();
	});

	beforeAll(() => {
		jest.useFakeTimers();
	});

	it('renders a dropdown trigger with the selected locale flag icon as content', () => {
		const {asFragment} = render(<TranslationAdminSelector {...props} />);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders an open dropdown with the list of active languages', async () => {
		const {getByTitle} = render(<TranslationAdminSelector {...props} />);

		const trigger = getByTitle('select-translation-language');

		fireEvent.click(trigger);

		const dropdownMenu = document.querySelector('.dropdown-menu');

		expect(dropdownMenu).toMatchSnapshot();
	});

	it('renders an open dropdown with the list of active languages and manage translation option', async () => {
		const {getByTitle} = render(
			<TranslationAdminSelector adminMode={true} {...props} />
		);

		const trigger = getByTitle('select-translation-language');

		fireEvent.click(trigger);

		const dropdownMenu = document.querySelector('.dropdown-menu');

		expect(dropdownMenu).toMatchSnapshot();
	});

	it('renders a modal when click on Manage Translations', async () => {
		const {getByTitle} = render(
			<TranslationAdminSelector adminMode={true} {...props} />
		);

		const trigger = getByTitle('select-translation-language');

		fireEvent.click(trigger);

		const dropdownMenu = document.querySelector('.dropdown-menu');

		const manageTranslationsTrigger = dropdownMenu.querySelector(
			'[data-testid="translation-modal-trigger"]'
		);

		fireEvent.click(manageTranslationsTrigger);

		act(() => {
			jest.runAllTimers();
		});

		const modal = document.querySelector('.modal');

		expect(modal).toMatchSnapshot();
	});

	it('renders an open dropdown with updated active locales when added from the Manage Translations Modal', async () => {
		const {getByTitle} = render(
			<TranslationAdminSelector adminMode={true} {...props} />
		);

		const trigger = getByTitle('select-translation-language');

		fireEvent.click(trigger);

		const dropdownMenu = document.querySelector('.dropdown-menu');

		const manageTranslationsTrigger = dropdownMenu.querySelector(
			'[data-testid="translation-modal-trigger"]'
		);

		fireEvent.click(manageTranslationsTrigger);

		act(() => {
			jest.runAllTimers();
		});

		const modalCloseButton = document.querySelector('.modal .close');

		fireEvent.click(modalCloseButton);

		act(() => {
			jest.runAllTimers();
		});

		fireEvent.click(trigger);

		act(() => {
			jest.runAllTimers();
		});

		expect(dropdownMenu).toMatchSnapshot();
		expect(props.onActiveLanguageIdsChange).toHaveBeenLastCalledWith([
			'ar_SA',
			'en_US',
			'nl_NL',
		]);
	});

	it('calls onSelectedLocaleChange callback on dropdown locale selection', () => {
		const {getByTitle} = render(<TranslationAdminSelector {...props} />);

		const trigger = getByTitle('select-translation-language');

		fireEvent.click(trigger);

		act(() => {
			jest.runAllTimers();
		});

		const dropdownMenu = document.querySelector('.dropdown-menu');

		const localeElement = dropdownMenu.querySelectorAll(
			'.dropdown-item'
		)[1];

		fireEvent.click(localeElement);

		act(() => {
			jest.runAllTimers();
		});

		expect(props.onSelectedLanguageIdChange).toHaveBeenLastCalledWith(
			'ca_ES'
		);
	});

	it('is used as a controlled component', () => {
		const {getByTestId, getByTitle} = render(
			<TranslationAdminSelectorWithState />
		);

		const changeStateButton = getByTestId('change-state');

		fireEvent.click(changeStateButton);

		act(() => {
			jest.runAllTimers();
		});

		const trigger = getByTitle('select-translation-language');

		fireEvent.click(trigger);

		act(() => {
			jest.runAllTimers();
		});

		const dropdownMenu = document.querySelector('.dropdown-menu');

		expect(dropdownMenu).toMatchSnapshot();
	});

	it('selects the default locale if the current selected one is removed from the active locales list', () => {
		const {asFragment, getByTitle} = render(
			<TranslationAdminSelector
				adminMode={true}
				selectedLanguageId="ca_ES"
				{...props}
			/>
		);

		const trigger = getByTitle('select-translation-language');

		fireEvent.click(trigger);

		const dropdownMenu = document.querySelector('.dropdown-menu');

		const manageTranslationsTrigger = dropdownMenu.querySelector(
			'[data-testid="translation-modal-trigger"]'
		);

		fireEvent.click(manageTranslationsTrigger);

		act(() => {
			jest.runAllTimers();
		});

		const modalCloseButton = document.querySelector('.modal .close');

		fireEvent.click(modalCloseButton);

		act(() => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});
});
