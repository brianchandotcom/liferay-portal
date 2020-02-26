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

import {act, cleanup, render} from '@testing-library/react';
import React from 'react';

import DropDown from '../../../../src/main/resources/META-INF/resources/js/components/table/DropDown.es';

describe('DropDown', () => {
	afterEach(cleanup);

	it('shows dropdown when trigger is clicked and action is called', async () => {
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

		act(() => {
			trigger.dispatchEvent(new MouseEvent('click', {bubbles: true}));
		});

		expect(dropDownMenu.classList.contains('show')).toBe(true);

		act(() => {
			action.dispatchEvent(new MouseEvent('click', {bubbles: true}));
		});

		expect(actionCallback.mock.calls.length).toBe(1);
	});

	it('shows dropdown trigger with no action', async () => {
		const {baseElement, getByRole} = render(<DropDown actions={[]} />);

		const dropDownMenu = baseElement.querySelector('.dropdown-menu');
		expect(dropDownMenu).toBeNull();

		const trigger = getByRole('button');

		act(() => {
			trigger.dispatchEvent(new MouseEvent('click', {bubbles: true}));
		});

		expect(dropDownMenu).toBeNull();
	});

	it('shows dropdown with hidden action', async () => {
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

		expect(queryByText('action 1')).not.toBeNull();
		expect(queryByText('action 2')).toBeNull();
	});

	it('shows dropdown with divider', async () => {
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

		expect(queryByText('action 1')).not.toBeNull();
		expect(baseElement.querySelector('.dropdown-divider')).not.toBeNull();
		expect(queryByText('action 2')).not.toBeNull();
	});

	it('shows dropdown with name as a function', async () => {
		const {queryByText} = render(
			<DropDown
				actions={[
					{
						name: item => item.name,
					},
				]}
				item={{name: 'action'}}
			/>
		);

		expect(queryByText('action')).not.toBeNull();
	});
});
