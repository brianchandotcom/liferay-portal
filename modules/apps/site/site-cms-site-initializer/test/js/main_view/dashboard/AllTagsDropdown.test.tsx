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
import {AllTagsDropdown} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/AllTagsDropdown';
import {Item} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/FilterDropdown';
import {initialFilters} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/InventoryAnalysisCard';

const WrappedComponent = ({
	onSelectItem,
}: {
	onSelectItem: (item: Item) => void;
}) => {
	const [selectedItem, setSelectedItem] = React.useState<Item>(
		initialFilters.tag
	);

	return (
		<ViewDashboardContextProvider value={{}}>
			<AllTagsDropdown
				item={selectedItem}
				onSelectItem={(item) => {
					setSelectedItem(item);
					onSelectItem(item);
				}}
			/>
		</ViewDashboardContextProvider>
	);
};

describe('[CMS Dashboard] Components: AllTagsDropdown', () => {
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

		const tagsDropdownButton = screen.getByRole('button', {
			name: 'all-tags',
		});

		expect(tagsDropdownButton).toBeInTheDocument();

		fireEvent.click(tagsDropdownButton);

		expect(screen.queryByText('filter-by-tag')).toBeInTheDocument();

		expect(screen.queryByPlaceholderText('search')).toBeInTheDocument();

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getAllByRole('menuitem').length).toBe(1);

		const menuitem = screen.getByRole('menuitem', {
			name: 'all-tags',
		});

		expect(menuitem).toBeInTheDocument();

		fireEvent.click(menuitem);

		expect(onSelectItem).toHaveBeenCalledTimes(1);

		expect(onSelectItem).toHaveBeenCalledWith({
			label: 'all-tags',
			value: 'all',
		});
	});

	it('renders a tag list', async () => {
		global.fetch = jest.fn().mockResolvedValue({
			json: jest.fn().mockResolvedValue({
				items: [
					{
						id: '01',
						name: 'tag 01',
					},
					{
						id: '02',
						name: 'tag 02',
					},
				],
			}),
			ok: true,
		});

		render(<WrappedComponent onSelectItem={jest.fn()} />);

		const tagsDropdownButton = screen.getByRole('button', {
			name: 'all-tags',
		});

		fireEvent.click(tagsDropdownButton);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getAllByRole('menuitem').length).toBe(3);

		expect(
			screen.queryByRole('menuitem', {name: 'all-tags'})
		).toBeInTheDocument();

		expect(
			screen.queryByRole('menuitem', {name: 'tag 01'})
		).toBeInTheDocument();

		expect(
			screen.queryByRole('menuitem', {name: 'tag 02'})
		).toBeInTheDocument();
	});

	it('search by a tag and returns a filtered result', async () => {
		jest.useFakeTimers();

		global.fetch = jest
			.fn()
			.mockResolvedValueOnce({
				json: jest.fn().mockResolvedValue({
					items: [
						{id: '01', name: 'tag 01'},
						{id: '02', name: 'tag 02'},
					],
				}),
				ok: true,
			})
			.mockResolvedValueOnce({
				json: jest
					.fn()
					.mockResolvedValue({items: [{id: '02', name: 'tag 02'}]}),
				ok: true,
			});

		render(<WrappedComponent onSelectItem={jest.fn()} />);

		const tagsDropdownButton = screen.getByRole('button', {
			name: 'all-tags',
		});

		fireEvent.click(tagsDropdownButton);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getAllByRole('menuitem').length).toBe(3);

		fireEvent.change(screen.getByPlaceholderText('search'), {
			target: {
				value: 'tag 02',
			},
		});

		jest.advanceTimersByTime(300);

		await waitFor(() => {
			expect(screen.getAllByRole('menuitem').length).toBe(1);

			expect(
				screen.queryByRole('menuitem', {name: 'tag 02'})
			).toBeInTheDocument();
		});

		expect(
			screen.queryByRole('menuitem', {name: 'all-tags'})
		).not.toBeInTheDocument();

		expect(
			screen.queryByRole('menuitem', {name: 'tag 01'})
		).not.toBeInTheDocument();

		jest.useRealTimers();
	});

	it('search by a tag and returns a empty result', async () => {
		jest.useFakeTimers();

		global.fetch = jest
			.fn()
			.mockResolvedValueOnce({
				json: jest.fn().mockResolvedValue({
					items: [
						{id: '01', name: 'tag 01'},
						{id: '02', name: 'tag 02'},
					],
				}),
				ok: true,
			})
			.mockResolvedValueOnce({
				json: jest.fn().mockResolvedValue({items: []}),
				ok: true,
			});

		render(<WrappedComponent onSelectItem={jest.fn()} />);

		const tagsDropdownButton = screen.getByRole('button', {
			name: 'all-tags',
		});

		fireEvent.click(tagsDropdownButton);

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
			screen.queryByRole('menuitem', {name: 'all-tags'})
		).not.toBeInTheDocument();

		jest.useRealTimers();
	});

	it('selects a new tag', async () => {
		global.fetch = jest.fn().mockResolvedValue({
			json: jest.fn().mockResolvedValue({
				items: [
					{id: '01', name: 'tag 01'},
					{id: '02', name: 'tag 02'},
				],
			}),
			ok: true,
		});

		render(<WrappedComponent onSelectItem={() => {}} />);

		expect(screen.getByTestId('tags')).toHaveTextContent('all-tags');

		fireEvent.click(screen.getByTestId('tags'));

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		fireEvent.click(screen.getByRole('menuitem', {name: 'tag 02'}));

		expect(screen.getByTestId('tags')).toHaveTextContent('tag 02');
	});
});
