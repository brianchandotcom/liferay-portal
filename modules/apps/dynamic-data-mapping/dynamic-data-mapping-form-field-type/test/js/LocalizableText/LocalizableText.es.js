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

import dom from 'metal-dom';

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
	name: 'localizableTextField',
	spritemap,
};
const LocalizableTextWithContextMock = withContextMock(LocalizableText);

describe('Field LocalizableText', () => {
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
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			label: 'label',
		});

		expect(component).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			placeholder: 'Placeholder',
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			required: false,
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

		expect(component.editingLocale.localeId).toEqual('en_US');
		expect(component._value).toEqual('Test EUA');

		expect(component).toMatchSnapshot();
	});

	it('emits field edit event on field change', done => {
		const handleFieldEdited = jest.fn();

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

		component.on('fieldEdited', () => {
			expect(handleFieldEdited).toHaveBeenCalled();

			done();
		});

		component._handleFieldChanged({
			target: {
				value: 'Test 2 EUA',
			},
		});

		jest.runAllTimers();

		expect(component).toMatchSnapshot();
	});

	it('fills with the selected language value when the selected language is translated', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		expect(component.editingLocale.localeId).toEqual('en_US');

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector('[data-locale-id="ca_ES"]'),
			'click'
		);

		jest.runAllTimers();

		expect(component.editingLocale.localeId).toEqual('ca_ES');
		expect(component._isDefaultLocale('ca_ES')).toEqual(false);
		expect(component._isTranslated('ca_ES')).toEqual(true);

		expect(component._value).toEqual('Teste ES');

		expect(component).toMatchSnapshot();
	});

	it('fills with the default language value when the selected language is not translated', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		expect(component.editingLocale.localeId).toEqual('en_US');

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector('[data-locale-id="ja_JP"]'),
			'click'
		);

		jest.runAllTimers();

		expect(component._isDefaultLocale('en_US')).toEqual(true);

		expect(component.editingLocale.localeId).toEqual('ja_JP');
		expect(component._isDefaultLocale('ja_JP')).toEqual(false);
		expect(component._isTranslated('ja_JP')).toEqual(false);

		expect(component._value).toEqual('Test EUA');

		expect(component).toMatchSnapshot();
	});

	it('adds a new translation for an untranslated item', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector('[data-locale-id="ja_JP"]'),
			'click'
		);

		jest.runAllTimers();

		expect(component._isTranslated('ja_JP')).toEqual(false);

		component._handleFieldChanged({
			target: {
				value: 'Test JP',
			},
		});

		jest.runAllTimers();

		expect(component._isTranslated('ja_JP')).toEqual(true);
		expect(component._value).toEqual('Test JP');

		expect(component).toMatchSnapshot();
	});

	it('removes the translation of an item already translated', () => {
		component = new LocalizableTextWithContextMock({
			...defaultLocalizableTextConfig,
			value: {
				ca_ES: 'Teste ES',
				en_US: 'Test EUA',
				pt_BR: 'Teste BR',
			},
		});

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector('[data-locale-id="pt_BR"]'),
			'click'
		);

		jest.runAllTimers();

		expect(component._isTranslated('pt_BR')).toEqual(true);

		component._handleFieldChanged({
			target: {
				value: '',
			},
		});

		jest.runAllTimers();

		expect(component._isTranslated('pt_BR')).toEqual(false);
		expect(component._value).toEqual('');

		expect(component).toMatchSnapshot();
	});
});
