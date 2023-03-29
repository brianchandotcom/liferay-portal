import React from 'react';
import UserDropdown, {Menus} from '../index';
import {BrowserRouter} from 'react-router-dom';
import {fireEvent, render} from '@testing-library/react';

jest.unmock('react-dom');

const mockMenus = (): Menus => ({
	base: [
		{
			items: [
				{
					childMenuId: 'language',
					label: 'Language'
				},
				{
					externalLink: true,
					label: 'Link 1',
					url: '/link-1'
				},
				{
					label: 'Link 2',
					url: '/link-externo-2'
				}
			],
			subheaderLabel: 'test@test.com'
		}
	],
	language: [
		{
			items: [
				{
					label: 'English'
				},
				{
					label: 'Japanese'
				}
			]
		}
	]
});

describe('UserDropdown', () => {
	it('should render', () => {
		const {container} = render(
			<BrowserRouter>
				<UserDropdown
					initialActiveMenu='base'
					menus={mockMenus()}
					userName='Test Test'
				/>
			</BrowserRouter>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render dropdown menu when clicked', () => {
		const {container} = render(
			<BrowserRouter>
				<UserDropdown
					initialActiveMenu='base'
					menus={mockMenus()}
					userName='Test Test'
				/>
			</BrowserRouter>
		);

		const toggleButton = container.querySelector('.user-menu');

		fireEvent.click(toggleButton);

		expect(document.body.querySelector('.dropdown-menu')).toBeTruthy();
		expect(document.body).toMatchSnapshot();
	});

	it('should descend into nested menu when child button is clicked', () => {
		const {container} = render(
			<BrowserRouter>
				<UserDropdown
					initialActiveMenu='base'
					menus={mockMenus()}
					userName='Test Test'
				/>
			</BrowserRouter>
		);

		const toggleButton = container.querySelector('.user-menu');

		fireEvent.click(toggleButton);

		const dropdownMenu = document.body.querySelector('.dropdown-menu');

		expect(
			dropdownMenu.getElementsByClassName('button-root')[0].textContent
		).toBe('Language');

		fireEvent.click(dropdownMenu.getElementsByClassName('button-root')[0]);

		jest.runAllTimers();

		expect(
			dropdownMenu.getElementsByClassName('button-root')[1].textContent
		).toBe('English');
		expect(
			dropdownMenu.getElementsByClassName('button-root')[2].textContent
		).toBe('Japanese');
	});

	it('should ascend from nested menu when back button is clicked', () => {
		const {container} = render(
			<BrowserRouter>
				<UserDropdown
					initialActiveMenu='base'
					menus={mockMenus()}
					userName='Test Test'
				/>
			</BrowserRouter>
		);

		const toggleButton = container.querySelector('.user-menu');

		fireEvent.click(toggleButton);

		const dropdownMenu = document.body.querySelector('.dropdown-menu');

		expect(
			dropdownMenu.getElementsByClassName('button-root')[0].textContent
		).toBe('Language');

		fireEvent.click(dropdownMenu.getElementsByClassName('button-root')[0]);

		jest.runAllTimers();

		expect(
			dropdownMenu.getElementsByClassName('button-root')[1].textContent
		).toBe('English');
		expect(
			dropdownMenu.getElementsByClassName('button-root')[2].textContent
		).toBe('Japanese');

		fireEvent.click(dropdownMenu.getElementsByClassName('button-root')[0]);

		jest.runAllTimers();

		expect(
			dropdownMenu.getElementsByClassName('button-root')[0].textContent
		).toBe('Language');
	});

	it('should go back to the initialActiveMenu on close', () => {
		const {container} = render(
			<BrowserRouter>
				<UserDropdown
					initialActiveMenu='base'
					menus={mockMenus()}
					userName='Test Test'
				/>
			</BrowserRouter>
		);

		const toggleButton = container.querySelector('.user-menu');

		fireEvent.click(toggleButton);

		const dropdownMenu = document.body.querySelector('.dropdown-menu');

		expect(
			dropdownMenu.getElementsByClassName('button-root')[0].textContent
		).toBe('Language');

		fireEvent.click(dropdownMenu.getElementsByClassName('button-root')[0]);

		jest.runAllTimers();

		expect(
			dropdownMenu.getElementsByClassName('button-root')[1].textContent
		).toBe('English');
		expect(
			dropdownMenu.getElementsByClassName('button-root')[2].textContent
		).toBe('Japanese');

		fireEvent.click(toggleButton);
		fireEvent.click(toggleButton);

		jest.runAllTimers();

		expect(
			dropdownMenu.getElementsByClassName('button-root')[0].textContent
		).toBe('Language');
	});
});
