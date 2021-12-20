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
	fireEvent,
	queryAllByRole,
	queryAllByText,
	render,
	waitFor,
} from '@testing-library/react';
import React from 'react';

import Languages from '../src/main/resources/META-INF/resources/js/Languages.es';

const availableLocales = [
	{displayName: 'a', localeId: 'a'},
	{displayName: 'b', localeId: 'b'},
	{displayName: 'c', localeId: 'c'},
	{displayName: 'd', localeId: 'd'},
];

const siteAvailableLocales = [
	{displayName: 'a', localeId: 'a'},
	{displayName: 'b', localeId: 'b'},
];

const defaultProps = {
	availableLocales,
	defaultLocaleId: 'a',
	manageCustomLanguagesURL: '',
	portletNamespace: 'portletNamespace',
	siteAvailableLocales,
	siteDefaultLocaleId: 'b',
};
const renderLanguagesComponent = (props) => render(<Languages {...props} />);

describe('Languages', () => {
	it('renders a radio group with the first option checked', () => {
		const {getAllByRole} = renderLanguagesComponent(defaultProps);

		const radioInputs = getAllByRole('radio');

		expect(radioInputs.length).toBe(2);
		expect(radioInputs[0].checked).toBeTruthy();
	});

	it('renders a list with the availableLocales', () => {
		const {container, getAllByRole} = renderLanguagesComponent(
			defaultProps
		);

		expect(getAllByRole('table').length).toBe(1);

		const trsElement = container.querySelectorAll('tbody > tr');

		expect(trsElement).toHaveLength(4);
	});

	it('renders a "default" label at the first element', () => {
		const {container, getByText} = renderLanguagesComponent(defaultProps);

		expect(getByText('default')).toBeTruthy();

		const firstLanguageElement = container.querySelectorAll('tr')[1];

		expect(firstLanguageElement.querySelector('.label-info')).toBeTruthy();
	});

	it('renders a "edit" button if custom option is checked', () => {
		const {getByText} = renderLanguagesComponent({
			...defaultProps,
			inheritLocales: false,
		});

		expect(getByText('edit')).toBeTruthy();
	});

	it('renders inputs with the default values', () => {
		const {getByDisplayValue} = renderLanguagesComponent({
			...defaultProps,
			inheritLocales: false,
		});

		expect(
			getByDisplayValue(defaultProps.siteDefaultLocaleId)
		).toBeTruthy();
		expect(getByDisplayValue('b')).toBeTruthy();
	});

	it('changes the default language', () => {
		const {
			container,
			getAllByText,
			getByDisplayValue,
		} = renderLanguagesComponent({
			...defaultProps,
			inheritLocales: false,
		});

		const actions = getAllByText('make-default');

		expect(actions).toHaveLength(2);

		fireEvent.click(actions[0]);

		const firstElement = container.querySelectorAll('tbody > tr')[0];

		expect(firstElement.querySelector('.label-info')).toBeTruthy();
		expect(getByDisplayValue(availableLocales[0].localeId)).toBeTruthy();
	});

	it('fires default locale changed event', () => {
		const {getAllByText} = renderLanguagesComponent({
			...defaultProps,
			inheritLocales: false,
		});

		fireEvent.click(getAllByText('make-default')[0]);

		expect(Liferay.fire).toHaveBeenCalled();

		expect(Liferay.fire.mock.calls[0][0]).toBe(
			'inputLocalized:defaultLocaleChanged'
		);
	});

	it('renders a warning when default language is changed', () => {
		const {getAllByText, getByText} = renderLanguagesComponent({
			...defaultProps,
			inheritLocales: false,
		});

		fireEvent.click(getAllByText('make-default')[0]);

		expect(
			getByText(
				'this-change-will-only-affect-the-newly-created-localized-content'
			)
		).toBeTruthy();
	});

	// LPS-111488

	it('render a dropdown menu with the correct order', () => {
		const result = renderLanguagesComponent({
			...defaultProps,
			inheritLocales: false,
			siteAvailableLocales: availableLocales,
		});

		const dropdownMenuSecond = result.baseElement.querySelectorAll(
			'.dropdown-menu'
		)[1];
		const Buttons = queryAllByRole(dropdownMenuSecond, 'button');

		expect(Buttons[0].textContent).toBe('make-default');
		expect(Buttons[1].textContent).toBe('move-up');
		expect(Buttons[2].textContent).toBe('move-down');
	});

	it('renders a list with move up actions in all elements except the first one', () => {
		const result = renderLanguagesComponent({
			...defaultProps,
			inheritLocales: false,
			siteAvailableLocales: availableLocales,
		});

		const dropdownTriggers = result.container.querySelectorAll('.dropdown');
		const moveDownButtons = result.getAllByText('move-up');
		const dropdownMenus = result.baseElement.querySelectorAll(
			'.dropdown-menu'
		);
		const dropdownMenuFirst = dropdownMenus[0];

		expect(dropdownTriggers).toHaveLength(4);
		expect(moveDownButtons).toHaveLength(3);
		expect(queryAllByText(dropdownMenuFirst, 'move-up')).toHaveLength(0);
	});

	it('renders a list with move down actions in all elements except the last one', () => {
		const result = renderLanguagesComponent({
			...defaultProps,
			inheritLocales: false,
			siteAvailableLocales: availableLocales,
		});

		const dropdownTriggers = result.container.querySelectorAll('.dropdown');
		const moveDownButtons = result.getAllByText('move-down');
		const dropdownMenus = result.baseElement.querySelectorAll(
			'.dropdown-menu'
		);
		const dropdownMenuLast = dropdownMenus[dropdownMenus.length - 1];

		expect(dropdownTriggers).toHaveLength(4);
		expect(moveDownButtons).toHaveLength(3);
		expect(queryAllByText(dropdownMenuLast, 'move-down')).toHaveLength(0);
	});

	it('move up the third element', () => {
		const result = renderLanguagesComponent({
			...defaultProps,
			inheritLocales: false,
			siteAvailableLocales: availableLocales,
		});

		expect(
			result.container.querySelectorAll('tbody > tr')[2].textContent
		).toBe('c');
		fireEvent.click(result.getAllByText('move-up')[1]);
		expect(
			result.container.querySelectorAll('tbody > tr')[1].textContent
		).toBe('c');
	});

	it('move down the first element', () => {
		const result = renderLanguagesComponent({
			...defaultProps,
			inheritLocales: false,
			siteAvailableLocales: availableLocales,
		});

		expect(
			result.container.querySelectorAll('tbody > tr')[0].textContent
		).toBe('a');
		fireEvent.click(result.getAllByText('move-down')[0]);
		expect(
			result.container.querySelectorAll('tbody > tr')[1].textContent
		).toBe('a');
	});

	describe('ManageLanguages', () => {
		let result;

		beforeEach(() => {
			result = renderLanguagesComponent({
				...defaultProps,
				inheritLocales: false,
			});

			fireEvent.click(result.getByText('edit'));
		});

		it('renders a modal when user clicks on Edit button', async () => {
			const title = await result.findByText('language-selection');

			expect(title).toBeTruthy();
		});

		it('renders custom locales checked', async () => {
			const checkboxes = await result.findAllByRole('checkbox');

			expect(checkboxes).toHaveLength(4);

			expect(checkboxes[0]).toHaveProperty('checked', true);
			expect(checkboxes[1]).toHaveProperty('checked', true);
			expect(checkboxes[2]).toHaveProperty('checked', false);
			expect(checkboxes[3]).toHaveProperty('checked', false);
		});

		it('custom locale check is disabled', async () => {
			const checkboxes = await result.findAllByRole('checkbox');

			expect(checkboxes[1]).toHaveProperty('disabled', true);
		});

		it('uncheck custom locale and save', async () => {
			const checkboxes = await result.findAllByRole('checkbox');

			fireEvent.click(checkboxes[0]);

			fireEvent.click(result.getByText('done'));

			const languagesList = await waitFor(() =>
				result.container.querySelectorAll('tbody > tr')
			);

			expect(languagesList).toHaveLength(1);
			expect(result.getAllByDisplayValue('b')).toBeTruthy();
		});

		it('add custom locale and save', async () => {
			const checkboxes = await result.findAllByRole('checkbox');

			fireEvent.click(checkboxes[2]);

			fireEvent.click(result.getByText('done'));

			const languagesList = await waitFor(() =>
				result.container.querySelectorAll('tbody > tr')
			);

			expect(languagesList).toHaveLength(3);
			expect(result.getByDisplayValue('a,b,c')).toBeTruthy();
		});
	});
});
