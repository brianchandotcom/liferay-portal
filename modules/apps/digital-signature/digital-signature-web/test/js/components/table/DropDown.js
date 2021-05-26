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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import DropDown from '../../../../src/main/resources/META-INF/resources/js/components/table/DropDown';

describe('DropDown', () => {
	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('shows dropdown when trigger is clicked and action is called', () => {
		const actionCallback = jest.fn();

		const {baseElement, getAllByRole} = render(
			<DropDown
				actions={[
					{
						action: actionCallback,
						name: 'action',
					},
				]}
			/>
		);

		const dropDownMenu = baseElement.querySelector('.dropdown-menu');

		expect(dropDownMenu.classList.contains('show')).toBe(false);

		const [trigger, action] = getAllByRole('button');
		fireEvent.click(trigger);

		expect(dropDownMenu.classList.contains('show')).toBe(true);

		fireEvent.click(action);

		expect(actionCallback.mock.calls.length).toBe(1);
	});

	it('shows dropdown trigger with no action', () => {
		const {baseElement, getByRole} = render(<DropDown actions={[]} />);

		const dropDownMenu = baseElement.querySelector('.dropdown-menu');
		expect(dropDownMenu).toBeFalsy();

		const trigger = getByRole('button');

		fireEvent.click(trigger);

		expect(dropDownMenu).toBeFalsy();
	});

	it('shows dropdown with hidden action', () => {
		const {queryByText} = render(
			<DropDown
				actions={[
					{
						action: () => {},
						name: 'action 1',
					},
					{
						action: () => {},
						name: 'action 2',
						show: () => false,
					},
				]}
			/>
		);

		expect(queryByText('action 1')).toBeTruthy();
		expect(queryByText('action 2')).toBeFalsy();
	});

	it('shows dropdown with divider', () => {
		const {baseElement, queryByText} = render(
			<DropDown
				actions={[
					{
						action: () => {},
						name: 'action 1',
					},
					{
						name: 'divider',
					},
					{
						action: () => {},
						name: 'action 2',
					},
				]}
			/>
		);

		expect(queryByText('action 1')).toBeTruthy();
		expect(baseElement.querySelector('.dropdown-divider')).toBeTruthy();
		expect(queryByText('action 2')).toBeTruthy();
	});

	it('shows dropdown with name as a function', () => {
		const {queryByText} = render(
			<DropDown
				actions={[
					{
						name: (item) => item.name,
					},
				]}
				item={{name: 'action'}}
			/>
		);

		expect(queryByText('action')).toBeTruthy();
	});
});
