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
	act,
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
import {PageProvider} from 'dynamic-data-mapping-form-renderer';
import React from 'react';
import ReactDOM from 'react-dom';

import LocalizableText from '../../../src/main/resources/META-INF/resources/LocalizableText/LocalizableText.es';

const spritemap = 'icons.svg';

const LocalizableTextWithProvider = (props) => (
	<PageProvider value={{editingLanguageId: 'en_US'}}>
		<LocalizableText {...props} />
	</PageProvider>
);

const defaultLocalizableTextConfig = {
	availableLocales: [
		{
			displayName: 'English (United States)',
			icon: 'en-us',
			localeId: 'en_US',
		},
		{displayName: 'العربية (السعودية)', icon: 'ar-sa', localeId: 'ar_SA'},
		{displayName: 'català (Espanya)', icon: 'ca-es', localeId: 'ca_ES'},
		{displayName: '中文 (中国)', icon: 'zh-cn', localeId: 'zh_CN'},
		{
			displayName: 'Nederlands (Nederland)',
			icon: 'nl-nl',
			localeId: 'nl_NL',
		},
		{displayName: 'suomi (Suomi)', icon: 'fi-fi', localeId: 'fi_FI'},
		{displayName: 'français (France)', icon: 'fr-fr', localeId: 'fr_FR'},
		{
			displayName: 'Deutsch (Deutschland)',
			icon: 'de-de',
			localeId: 'de_DE',
		},
		{
			displayName: 'magyar (Magyarország)',
			icon: 'hu-hu',
			localeId: 'hu_HU',
		},
		{displayName: '日本語 (日本)', icon: 'ja-jp', localeId: 'ja_JP'},
		{displayName: 'português (Brasil)', icon: 'pt-br', localeId: 'pt_BR'},
		{displayName: 'español (España)', icon: 'es-es', localeId: 'es_ES'},
		{displayName: 'svenska (Sverige)', icon: 'sv-se', localeId: 'sv_SE'},
	],
	defaultLocale: {
		displayName: 'English (United States)',
		icon: 'en-us',
		localeId: 'en_US',
	},
	name:
		'_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_ddm$$emailArticleAddedSubject$uoeJR4Me$0$$en_US',
	spritemap,
};

describe('Field LocalizableText', () => {
	// eslint-disable-next-line no-console
	const originalWarn = console.warn;

	afterAll(() => {
		// eslint-disable-next-line no-console
		console.warn = originalWarn;
	});

	beforeAll(() => {
		// eslint-disable-next-line no-console
		console.warn = (...args) => {
			if (/DataProvider: Trying/.test(args[0])) {
				return;
			}
			originalWarn.call(console, ...args);
		};

		// @ts-ignore

		ReactDOM.createPortal = jest.fn((element) => {
			return element;
		});
	});

	afterEach(cleanup);

	beforeEach(() => {
		jest.useFakeTimers();
		fetch.mockResponse(JSON.stringify({}));
	});

	it('is not readOnly', () => {
		const {container} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				readOnly={false}
				value={{
					ca_ES: 'Teste ES',
					en_US: 'Test EUA',
					pt_BR: 'Teste BR',
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a label', () => {
		const {container} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				label="label"
				value={{
					ca_ES: 'Teste ES',
					en_US: 'Test EUA',
					pt_BR: 'Teste BR',
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		const {container} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				placeholder="Placeholder"
				value={{
					ca_ES: 'Teste ES',
					en_US: 'Test EUA',
					pt_BR: 'Teste BR',
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('is not required', () => {
		const {container} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				required={false}
				value={{
					ca_ES: 'Teste ES',
					en_US: 'Test EUA',
					pt_BR: 'Teste BR',
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders values', () => {
		const {container} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				value={{
					ca_ES: 'Teste ES',
					en_US: 'Test EUA',
					pt_BR: 'Teste BR',
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders no values when values come empty', () => {
		const {container} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				value={{}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('shows default language value when no other language is selected', () => {
		const {container, getByTestId} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				value={{
					ca_ES: 'Teste ES',
					en_US: 'Test EUA',
					pt_BR: 'Teste BR',
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const triggerElement = getByTestId('triggerText');

		expect(triggerElement.textContent).toEqual('en-us');

		expect(container).toMatchSnapshot();
	});

	it('emits field edit event on field change', () => {
		const EXPECTED_VALUE =
			'{"ca_ES":"Teste ES","en_US":"Test 2 EUA","pt_BR":"Teste BR"}';

		const handleFieldEdited = jest.fn();

		const {container, getByTestId} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				onChange={handleFieldEdited}
				value={{
					ca_ES: 'Teste ES',
					en_US: 'Test EUA',
					pt_BR: 'Teste BR',
				}}
			/>
		);

		const inputComponent = getByTestId('visibleChangeInput');

		fireEvent.change(inputComponent, {
			target: {
				value: 'Test 2 EUA',
			},
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(handleFieldEdited).toHaveBeenCalledWith(
			expect.any(Object),
			EXPECTED_VALUE
		);

		expect(container).toMatchSnapshot();
	});

	it('fills with the selected language value when the selected language is translated', async () => {
		const {container, getByTestId} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				onChange={jest.fn()}
				value={{
					ca_ES: 'Teste ES',
					en_US: 'Test EUA',
					pt_BR: 'Teste BR',
				}}
			/>
		);

		const triggerButton = getByTestId('triggerButton');

		fireEvent.click(triggerButton);

		act(() => {
			jest.runAllTimers();
		});

		const dropdownItem = await waitForElement(() =>
			getByTestId('availableLocalesDropdownca_ES')
		);

		fireEvent.click(dropdownItem);

		act(() => {
			jest.runAllTimers();
		});

		const inputElement = await waitForElement(() =>
			getByTestId('visibleChangeInput')
		);

		expect(inputElement.value).toEqual('Teste ES');

		expect(triggerButton.textContent).toEqual('ca-es');

		expect(container).toMatchSnapshot();
	});

	it('fills with the default language value when the selected language is not translated', async () => {
		const {container, getByTestId} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				onChange={jest.fn()}
				value={{
					ca_ES: 'Teste ES',
					en_US: 'Test EUA',
					pt_BR: 'Teste BR',
				}}
			/>
		);

		const triggerElement = getByTestId('triggerText');

		expect(triggerElement.textContent).toEqual('en-us');

		fireEvent.click(triggerElement);

		act(() => {
			jest.runAllTimers();
		});

		const dropdownItem = await waitForElement(() =>
			getByTestId('availableLocalesDropdownja_JP')
		);

		fireEvent.click(dropdownItem);

		act(() => {
			jest.runAllTimers();
		});

		const inputComponent = getByTestId('visibleChangeInput');

		expect(triggerElement.textContent).toEqual('ja-jp');

		expect(inputComponent.value).toEqual('Test EUA');

		expect(container).toMatchSnapshot();
	});

	it('adds a new translation for an untranslated item', async () => {
		const {container, getByTestId} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				onChange={jest.fn()}
				value={{
					ca_ES: 'Teste ES',
					en_US: 'Test EUA',
					pt_BR: 'Teste BR',
				}}
			/>
		);

		const triggerElement = getByTestId('triggerText');

		expect(triggerElement.textContent).toEqual('en-us');

		fireEvent.click(triggerElement);

		act(() => {
			jest.runAllTimers();
		});

		const dropdownItem = await waitForElement(() =>
			getByTestId('availableLocalesDropdownja_JP')
		);

		fireEvent.click(dropdownItem);

		act(() => {
			jest.runAllTimers();
		});

		const inputComponent = getByTestId('visibleChangeInput');

		expect(inputComponent.textContent).toEqual('');

		fireEvent.change(inputComponent, {
			target: {
				value: 'Test JP',
			},
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(inputComponent.value).toEqual('Test JP');

		expect(container).toMatchSnapshot();
	});

	it('removes the translation of an item already translated', async () => {
		const {container, getByTestId} = render(
			<LocalizableTextWithProvider
				{...defaultLocalizableTextConfig}
				onChange={jest.fn()}
				value={{
					ca_ES: 'Teste ES',
					en_US: 'Test EUA',
					pt_BR: 'Teste BR',
				}}
			/>
		);

		const triggerElement = getByTestId('triggerText');

		fireEvent.click(triggerElement);

		act(() => {
			jest.runAllTimers();
		});

		const dropdownItem = await waitForElement(() =>
			getByTestId('availableLocalesDropdownpt_BR')
		);

		fireEvent.click(dropdownItem);

		act(() => {
			jest.runAllTimers();
		});

		const inputComponent = getByTestId('visibleChangeInput');

		expect(inputComponent.value).toEqual('Teste BR');

		fireEvent.change(inputComponent, {
			target: {
				value: '',
			},
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(inputComponent.value).toEqual('');

		expect(container).toMatchSnapshot();
	});

	describe('Submit Button Label', () => {
		it('does not have the maxLength property equal to 25', () => {
			const {getByTestId} = render(
				<LocalizableTextWithProvider
					{...defaultLocalizableTextConfig}
				/>
			);

			const inputComponent = getByTestId('visibleChangeInput');

			expect(inputComponent.maxLength).not.toBe(25);
		});

		it('has the maxLength property equal to 25 for the Submit Button Label input', () => {
			const {getByTestId} = render(
				<LocalizableTextWithProvider
					{...defaultLocalizableTextConfig}
					fieldName="submitLabel"
				/>
			);

			const inputComponent = getByTestId('visibleChangeInput');

			expect(inputComponent.maxLength).toBe(25);
		});
	});
});
