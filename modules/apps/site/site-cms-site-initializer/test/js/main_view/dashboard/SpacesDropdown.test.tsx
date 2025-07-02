/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {
	fireEvent,
	render,
	screen,
	waitFor,
	waitForElementToBeRemoved,
} from '@testing-library/react';
import React from 'react';

import {ViewDashboardContextProvider} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/ViewDashboardContext';
import {SpacesDropdown} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/SpacesDropdown';

const WrappedComponent = ({constants}: any) => (
	<ViewDashboardContextProvider value={{constants}}>
		<SpacesDropdown />
	</ViewDashboardContextProvider>
);

describe('[CMS Dashboard] Components: SpacesDropdown', () => {
	beforeEach(() => {
		global.fetch = jest.fn().mockResolvedValue({});

		jest.clearAllMocks();
	});

	it('renders correctly', async () => {
		global.fetch = jest.fn().mockResolvedValue({
			json: jest.fn().mockResolvedValue({items: []}),
			ok: true,
		});

		render(<WrappedComponent />);

		const spacesDropdownButton = screen.getByRole('button', {
			name: 'all-spaces',
		});

		expect(spacesDropdownButton).toBeInTheDocument();

		fireEvent.click(spacesDropdownButton);

		expect(screen.queryByText('filter-by-spaces')).toBeInTheDocument();

		expect(screen.queryByPlaceholderText('search')).toBeInTheDocument();

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getAllByRole('menuitem').length).toBe(1);

		expect(
			screen.queryByRole('menuitem', {name: 'all-spaces'})
		).toBeInTheDocument();
	});

	it('renders a space list', async () => {
		global.fetch = jest.fn().mockResolvedValue({
			json: jest.fn().mockResolvedValue({
				items: [
					{id: '01', name: 'space 01'},
					{id: '02', name: 'space 02'},
				],
			}),
			ok: true,
		});

		render(<WrappedComponent />);

		const spacesDropdownButton = screen.getByRole('button', {
			name: 'all-spaces',
		});

		fireEvent.click(spacesDropdownButton);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getAllByRole('menuitem').length).toBe(3);

		expect(
			screen.queryByRole('menuitem', {name: 'all-spaces'})
		).toBeInTheDocument();

		expect(
			screen.queryByRole('menuitem', {name: 'space 01'})
		).toBeInTheDocument();

		expect(
			screen.queryByRole('menuitem', {name: 'space 02'})
		).toBeInTheDocument();
	});

	it('search by a space and returns a filtered result', async () => {
		jest.useFakeTimers();

		global.fetch = jest
			.fn()
			.mockResolvedValueOnce({
				json: jest.fn().mockResolvedValue({
					items: [
						{id: '01', name: 'space 01'},
						{id: '02', name: 'space 02'},
					],
				}),
				ok: true,
			})
			.mockResolvedValueOnce({
				json: jest
					.fn()
					.mockResolvedValue({items: [{id: '02', name: 'space 02'}]}),
				ok: true,
			});

		render(<WrappedComponent />);

		const spacesDropdownButton = screen.getByRole('button', {
			name: 'all-spaces',
		});

		fireEvent.click(spacesDropdownButton);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getAllByRole('menuitem').length).toBe(3);

		fireEvent.change(screen.getByPlaceholderText('search'), {
			target: {
				value: 'space 02',
			},
		});

		jest.advanceTimersByTime(300);

		await waitFor(() => {
			expect(screen.getAllByRole('menuitem').length).toBe(1);

			expect(
				screen.queryByRole('menuitem', {name: 'space 02'})
			).toBeInTheDocument();
		});

		expect(
			screen.queryByRole('menuitem', {name: 'all-spaces'})
		).not.toBeInTheDocument();

		expect(
			screen.queryByRole('menuitem', {name: 'space 01'})
		).not.toBeInTheDocument();

		jest.useRealTimers();
	});

	it('search by a space and returns a empty result', async () => {
		jest.useFakeTimers();

		global.fetch = jest
			.fn()
			.mockResolvedValueOnce({
				json: jest.fn().mockResolvedValue({
					items: [
						{id: '01', name: 'space 01'},
						{id: '02', name: 'space 02'},
					],
				}),
				ok: true,
			})
			.mockResolvedValueOnce({
				json: jest.fn().mockResolvedValue({items: []}),
				ok: true,
			});

		render(<WrappedComponent />);

		const spacesDropdownButton = screen.getByRole('button', {
			name: 'all-spaces',
		});

		fireEvent.click(spacesDropdownButton);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getAllByRole('menuitem').length).toBe(3);

		fireEvent.change(screen.getByPlaceholderText('search'), {
			target: {
				value: 'empty?',
			},
		});

		jest.advanceTimersByTime(300);

		await waitFor(() => {
			expect(screen.getAllByRole('menuitem').length).toBe(1);

			expect(
				screen.queryByRole('menuitem', {
					name: 'no-filters-were-found',
				})
			).toBeInTheDocument();
		});

		expect(
			screen.queryByRole('menuitem', {name: 'all-spaces'})
		).not.toBeInTheDocument();

		jest.useRealTimers();
	});

	it('selects a new space', async () => {
		global.fetch = jest.fn().mockResolvedValue({
			json: jest.fn().mockResolvedValue({
				items: [
					{id: '01', name: 'space 01'},
					{id: '02', name: 'space 02'},
				],
			}),
			ok: true,
		});

		render(<WrappedComponent />);

		expect(screen.getByTestId('spaces')).toHaveTextContent('all-spaces');

		fireEvent.click(screen.getByTestId('spaces'));

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		fireEvent.click(screen.getByRole('menuitem', {name: 'space 02'}));

		expect(screen.getByTestId('spaces')).toHaveTextContent('space 02');
	});
});
