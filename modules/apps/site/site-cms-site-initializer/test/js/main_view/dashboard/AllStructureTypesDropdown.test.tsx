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
import {AllStructureTypesDropdown} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/AllStructureTypesDropdown';
import {Item} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/FilterDropdown';
import {initialFilters} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/InventoryAnalysisCard';

const WrappedComponent = ({
	onSelectItem,
}: {
	onSelectItem: (item: Item) => void;
}) => {
	const [selectedItem, setSelectedItem] = React.useState<Item>(
		initialFilters.structure
	);

	return (
		<ViewDashboardContextProvider value={{}}>
			<AllStructureTypesDropdown
				item={selectedItem}
				onSelectItem={(item) => {
					setSelectedItem(item);
					onSelectItem(item);
				}}
			/>
		</ViewDashboardContextProvider>
	);
};

describe('[CMS Dashboard] Components: AllStructureTypesDropdown', () => {
	beforeEach(() => {
		global.fetch = jest.fn().mockResolvedValue({});

		jest.clearAllMocks();
	});

	it('renders correctly', async () => {
		global.fetch = jest.fn().mockResolvedValue({
			json: jest.fn().mockResolvedValue({items: []}),
			ok: true,
		});

		const onSelectItem = jest.fn();

		render(<WrappedComponent onSelectItem={onSelectItem} />);

		const button = screen.getByRole('button', {
			name: 'all-structures',
		});

		expect(button).toBeInTheDocument();

		fireEvent.click(button);

		expect(
			screen.queryByText('filter-by-structure-type')
		).toBeInTheDocument();

		expect(screen.queryByPlaceholderText('search')).toBeInTheDocument();

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getAllByRole('menuitem').length).toBe(1);

		const menuitem = screen.getByRole('menuitem', {
			name: 'all-structures',
		});

		expect(menuitem).toBeInTheDocument();

		fireEvent.click(menuitem);

		expect(onSelectItem).toHaveBeenCalledTimes(1);

		expect(onSelectItem).toHaveBeenCalledWith({
			label: 'all-structures',
			value: 'all',
		});
	});

	it('renders a structure list', async () => {
		global.fetch = jest.fn().mockResolvedValue({
			json: jest.fn().mockResolvedValue({
				items: [
					{
						id: '01',
						label: {
							en_US: 'structure 01',
						},
					},
					{
						id: '02',
						label: {
							en_US: 'structure 02',
						},
					},
				],
			}),
			ok: true,
		});

		render(<WrappedComponent onSelectItem={jest.fn()} />);

		const button = screen.getByRole('button', {
			name: 'all-structures',
		});

		fireEvent.click(button);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getAllByRole('menuitem').length).toBe(3);

		expect(
			screen.queryByRole('menuitem', {name: 'all-structures'})
		).toBeInTheDocument();

		expect(
			screen.queryByRole('menuitem', {name: 'structure 01'})
		).toBeInTheDocument();

		expect(
			screen.queryByRole('menuitem', {name: 'structure 02'})
		).toBeInTheDocument();
	});

	it('searches by structure name and returns a filtered result', async () => {
		jest.useFakeTimers();

		global.fetch = jest
			.fn()
			.mockResolvedValueOnce({
				json: jest.fn().mockResolvedValue({
					items: [
						{
							id: '01',
							label: {
								en_US: 'structure 01',
							},
						},
						{
							id: '02',
							label: {
								en_US: 'structure 02',
							},
						},
					],
				}),
				ok: true,
			})
			.mockResolvedValueOnce({
				json: jest.fn().mockResolvedValue({
					items: [
						{
							id: '02',
							label: {
								en_US: 'structure 02',
							},
						},
					],
				}),
				ok: true,
			});

		render(<WrappedComponent onSelectItem={jest.fn()} />);

		const dropdownButton = screen.getByRole('button', {
			name: 'all-structures',
		});

		fireEvent.click(dropdownButton);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getAllByRole('menuitem').length).toBe(3);

		fireEvent.change(screen.getByPlaceholderText('search'), {
			target: {value: 'structure 02'},
		});

		jest.advanceTimersByTime(300);

		await waitFor(() => {
			expect(screen.getAllByRole('menuitem').length).toBe(1);

			expect(
				screen.getByRole('menuitem', {name: 'structure 02'})
			).toBeInTheDocument();
		});

		expect(
			screen.queryByRole('menuitem', {name: 'structure 01'})
		).not.toBeInTheDocument();

		expect(
			screen.queryByRole('menuitem', {name: 'all-structures'})
		).not.toBeInTheDocument();

		jest.useRealTimers();
	});

	it('search by a structure and returns a empty result', async () => {
		jest.useFakeTimers();

		global.fetch = jest.fn().mockResolvedValue({
			json: jest.fn().mockResolvedValue({
				items: [
					{
						id: '01',
						label: {
							en_US: 'structure 01',
						},
					},
					{
						id: '02',
						label: {
							en_US: 'structure 02',
						},
					},
				],
			}),
			ok: true,
		});

		render(<WrappedComponent onSelectItem={jest.fn()} />);

		const structuresDropdownButton = screen.getByRole('button', {
			name: 'all-structures',
		});

		fireEvent.click(structuresDropdownButton);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getAllByRole('menuitem').length).toBe(3);

		global.fetch = jest.fn().mockResolvedValue({
			json: jest.fn().mockResolvedValue({items: []}),
			ok: true,
		});

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
			screen.queryByRole('menuitem', {name: 'all-structures'})
		).not.toBeInTheDocument();

		jest.useRealTimers();
	});

	it('selects a new strucuture', async () => {
		global.fetch = jest.fn().mockResolvedValue({
			json: jest.fn().mockResolvedValue({
				items: [
					{
						id: '01',
						label: {
							en_US: 'structure 01',
						},
					},
					{
						id: '02',
						label: {
							en_US: 'structure 02',
						},
					},
				],
			}),
			ok: true,
		});

		render(<WrappedComponent onSelectItem={() => {}} />);

		expect(screen.getByTestId('structures')).toHaveTextContent(
			'all-structures'
		);

		fireEvent.click(screen.getByTestId('structures'));

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		fireEvent.click(screen.getByRole('menuitem', {name: 'structure 02'}));

		expect(screen.getByTestId('structures')).toHaveTextContent(
			'structure 02'
		);
	});
});
