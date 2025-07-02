/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {InventoryAnalysisDataType} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/InventoryAnalysisCard';
import PaginatedTable from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/PaginatedTable';

const mockData: InventoryAnalysisDataType = {
	inventoryAnalysisItems: [
		{
			count: 100,
			key: 'title-1',
			title: 'title 1',
		},
		{
			count: 300,
			key: 'title-2',
			title: 'title 2',
		},
		{
			count: 50,
			key: 'title-3',
			title: 'title 3',
		},
		{
			count: 200,
			key: 'title-4',
			title: 'title 4',
		},
		{
			count: 150,
			key: 'title-5',
			title: 'title 5',
		},
		{
			count: 400,
			key: 'title-6',
			title: 'title 6',
		},
		{
			count: 250,
			key: 'title-7',
			title: 'title 7',
		},
		{
			count: 350,
			key: 'title-8',
			title: 'title 8',
		},
		{
			count: 500,
			key: 'title-9',
			title: 'title 9',
		},
		{
			count: 600,
			key: 'title-10',
			title: 'title 10',
		},
		{
			count: 700,
			key: 'title-11',
			title: 'title 11',
		},
	],
	totalCount: 4050,
};

const WrappedComponent = ({
	currentStructureTypeLabel,
	inventoryAnalysisData,
}: {
	currentStructureTypeLabel: string;
	inventoryAnalysisData: InventoryAnalysisDataType;
}) => (
	<PaginatedTable
		currentStructureTypeLabel={currentStructureTypeLabel}
		inventoryAnalysisData={inventoryAnalysisData}
	/>
);

describe('[CMS Dashboard] Components: PaginatedTable', () => {
	it('renders its data correctly', async () => {
		render(
			<WrappedComponent
				currentStructureTypeLabel="Category"
				inventoryAnalysisData={mockData}
			/>
		);

		const table = screen.getByRole('table');
		expect(table).toBeInTheDocument();

		const tableRows = table.querySelectorAll('tr');
		expect(tableRows.length).toBe(11);
	});

	it('displays the default delta options in the items per page dropdown', async () => {
		render(
			<WrappedComponent
				currentStructureTypeLabel="Category"
				inventoryAnalysisData={mockData}
			/>
		);

		const itemsPerPageDropdown = screen.getByRole('combobox', {
			name: 'Items Per Page',
		});

		await itemsPerPageDropdown.click();

		const expectedOptions = ['10', '20', '30', '50'];
		const dropdownOptions = screen.getAllByRole('option');

		expect(dropdownOptions).toHaveLength(expectedOptions.length);

		dropdownOptions.forEach((option, index) => {
			expect(option).toHaveTextContent(expectedOptions[index]);
		});
	});

	it('paginates items correctly when navigating between pages', async () => {
		render(
			<WrappedComponent
				currentStructureTypeLabel="Category"
				inventoryAnalysisData={mockData}
			/>
		);

		const nextPageButton = screen.getByRole('button', {
			name: 'Go to the next page, 2',
		});

		let tableRows = screen.getAllByRole('row');
		expect(tableRows.length).toBe(11);

		await nextPageButton.click();

		tableRows = screen.getAllByRole('row');
		expect(tableRows.length).toBe(2);
	});

	it('paginates items according to selected delta', async () => {
		render(
			<WrappedComponent
				currentStructureTypeLabel="Category"
				inventoryAnalysisData={mockData}
			/>
		);

		const itemsPerPageDropdown = screen.getByRole('combobox', {
			name: 'Items Per Page',
		});

		await itemsPerPageDropdown.click();

		const option20Items = screen.getByRole('option', {name: '20 items'});
		await option20Items.click();

		const table = screen.getByRole('table');
		const tableRows = table.querySelectorAll('tr');

		expect(tableRows.length).toBe(12);
	});

	it('displays the total count', async () => {
		render(
			<WrappedComponent
				currentStructureTypeLabel="Category"
				inventoryAnalysisData={mockData}
			/>
		);

		const totalItems = screen.getByText(/Showing \d+ to \d+ of \d+/);

		expect(totalItems).toHaveTextContent('Showing 1 to 10 of 11');
	});

	it('displays the correct item range per page', async () => {
		render(
			<WrappedComponent
				currentStructureTypeLabel="Category"
				inventoryAnalysisData={mockData}
			/>
		);

		const nextPageButton = screen.getByRole('button', {
			name: 'Go to the next page, 2',
		});

		await nextPageButton.click();

		const paginationResults = screen.getByText(/Showing \d+ to \d+ of \d+/);

		expect(paginationResults).toHaveTextContent('Showing 11 to 11 of 11');
	});

	it('displays the name, count, and assets percentage for each item', async () => {
		render(
			<WrappedComponent
				currentStructureTypeLabel="Category"
				inventoryAnalysisData={mockData}
			/>
		);

		const tableRows = screen.getAllByRole('row');

		tableRows.slice(1).forEach((row, index) => {
			const cells = row.querySelectorAll('td');

			expect(cells[0]).toHaveTextContent(
				mockData.inventoryAnalysisItems[index].title
			);

			expect(cells[1]).toHaveTextContent(
				mockData.inventoryAnalysisItems[index].count.toString()
			);

			const percentage = (
				(mockData.inventoryAnalysisItems[index].count /
					mockData.totalCount) *
				100
			).toFixed(2);

			expect(cells[2]).toHaveTextContent(`${percentage}%`);
		});
	});
});
