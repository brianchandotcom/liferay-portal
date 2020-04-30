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

import {fireEvent, getByTestId, waitForElement} from '@testing-library/react';
import ReactDOM from 'react-dom';

import LocalizableText from '../../../src/main/resources/META-INF/resources/LocalizableText/LocalizableText.es';
import withContextMock from '../__mocks__/withContextMock.es';

let component;
const spritemap = 'icons.svg';

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
const LocalizableTextWithContextMock = withContextMock(LocalizableText);

describe('Field LocalizableText', () => {
	beforeAll(() => {

		// @ts-ignore

		ReactDOM.createPortal = jest.fn((element) => {
			return element;
		});
	});

	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('is not readOnly', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			readOnly: false,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			label: 'label',
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		expect(component).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			placeholder: 'Placeholder',
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			required: false,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		expect(component).toMatchSnapshot();
	});

	it('renders values', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		expect(component).toMatchSnapshot();
	});

	it('renders no values when values come empty', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			value: {},
		});

		expect(component).toMatchSnapshot();
	});

	it('shows default language value when no other language is selected', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		const triggerElement = getByTestId(component.element, 'triggerText');

		expect(triggerElement.textContent).toEqual('en-us');

		expect(component).toMatchSnapshot();
	});

	it('emits field edit event on field change', (done) => {
		const EXPECTED_VALUE =
			'{"ca_ES":"Teste ES","en_US":"Test 2 EUA","pt_BR":"Teste BR"}';
		const handleFieldEdited = (data) => {
			expect(data).toEqual(
				expect.objectContaining({
					fieldInstance: expect.any(Object),
					originalEvent: expect.any(Object),
					value: EXPECTED_VALUE,
				})
			);
			done();
		};

		const events = {fieldEdited: handleFieldEdited};

		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			events,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		const inputComponent = getByTestId(
			component.element,
			'visibleChangeInput'
		);

		fireEvent.change(inputComponent, {
			target: {
				value: 'Test 2 EUA',
			},
		});

		expect(component).toMatchSnapshot();
	});

	it('fills with the selected language value when the selected language is translated', async () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		const triggerButton = getByTestId(component.element, 'triggerButton');

		fireEvent.click(triggerButton);

		jest.runAllTimers();

		const dropdownItem = await waitForElement(() =>
			getByTestId(component.element, 'availableLocalesDropdownca_ES')
		);

		fireEvent.click(dropdownItem);

		jest.runAllTimers();

		const inputElement = await waitForElement(() =>
			getByTestId(component.element, 'visibleChangeInput')
		);

		expect(inputElement.value).toEqual('Teste ES');

		expect(triggerButton.textContent).toEqual('ca-es');

		expect(component).toMatchSnapshot();
	});

	it('fills with the default language value when the selected language is not translated', async () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		const triggerElement = getByTestId(component.element, 'triggerText');

		expect(triggerElement.textContent).toEqual('en-us');

		fireEvent.click(triggerElement);

		jest.runAllTimers();

		const dropdownItem = await waitForElement(() =>
			getByTestId(component.element, 'availableLocalesDropdownja_JP')
		);

		fireEvent.click(dropdownItem);

		jest.runAllTimers();

		const inputComponent = getByTestId(
			component.element,
			'visibleChangeInput'
		);

		expect(triggerElement.textContent).toEqual('ja-jp');

		expect(inputComponent.value).toEqual('Test EUA');

		expect(component).toMatchSnapshot();
	});

	it('adds a new translation for an untranslated item', async () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		const triggerElement = getByTestId(component.element, 'triggerText');

		expect(triggerElement.textContent).toEqual('en-us');

		fireEvent.click(triggerElement);

		jest.runAllTimers();

		const dropdownItem = await waitForElement(() =>
			getByTestId(component.element, 'availableLocalesDropdownja_JP')
		);

		fireEvent.click(dropdownItem);

		jest.runAllTimers();

		const inputComponent = getByTestId(
			component.element,
			'visibleChangeInput'
		);

		expect(inputComponent.textContent).toEqual('');

		fireEvent.change(inputComponent, {
			target: {
				value: 'Test JP',
			},
		});

		jest.runAllTimers();

		expect(inputComponent.value).toEqual('Test JP');

		expect(component).toMatchSnapshot();
	});

	it('removes the translation of an item already translated', async () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		const triggerElement = getByTestId(component.element, 'triggerText');

		fireEvent.click(triggerElement);

		jest.runAllTimers();

		const dropdownItem = await waitForElement(() =>
			getByTestId(component.element, 'availableLocalesDropdownpt_BR')
		);

		fireEvent.click(dropdownItem);

		jest.runAllTimers();

		const inputComponent = getByTestId(
			component.element,
			'visibleChangeInput'
		);

		expect(inputComponent.value).toEqual('Teste BR');

		fireEvent.change(inputComponent, {
			target: {
				value: '',
			},
		});

		jest.runAllTimers();

		expect(inputComponent.value).toEqual('');

		expect(component).toMatchSnapshot();
	});
});
