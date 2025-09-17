/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {
	render,
	screen,
	waitForElementToBeRemoved,
} from '@testing-library/react';
import React from 'react';

import ApiHelper from '../../../../src/main/resources/META-INF/resources/js/common/services/ApiHelper';
import {
	ViewDashboardContextProvider,
	initialSpace,
} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/ViewDashboardContext';
import {ExpiredAssetsCard} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/ExpiredAssetsCard';

const assetsList = [
	{
		href: 'http://fakeurl.com',
		title: 'Understanding Quantum Computing for Beginners',
		usages: 1,
	},
	{
		href: 'http://fakeurl.com',
		title: 'A Guide to Sustainable Energy Solutions',
		usages: 8,
	},
	{
		href: 'http://fakeurl.com',
		title: 'Top 10 Tips for Remote Work Productivity',
		usages: 6,
	},
];

const mockData = {
	data: {
		items: assetsList,
		totalCount: 3,
	},
	error: null,
};

describe('[CMS Dashboard] ExpiredAssetsCard', () => {
	it('renders correctly', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue(mockData);

		const {getByText} = render(<ExpiredAssetsCard />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(getByText('EXPIRED-ASSETS')).toBeTruthy();
	});

	it('displays the number of usage for each item', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue(mockData);

		const {container} = render(<ExpiredAssetsCard />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		const items = container.querySelectorAll(
			'.cms-dashboard__expired-assets__item'
		);

		for (const [index, item] of items.entries()) {
			if (assetsList[index].usages === 1) {
				expect(item.textContent).toContain('x-usage');
			}
			else {
				expect(item.textContent).toContain('x-usages');
			}
		}
	});

	it('renders 10 items per page by default', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue({
			data: {
				items: [
					...assetsList,
					...assetsList,
					...assetsList,
					...assetsList,
				],
				totalCount: 12,
			},
			error: null,
		});

		const {container, getByText} = render(<ExpiredAssetsCard />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		const items = container.querySelectorAll(
			'.cms-dashboard__expired-assets__item'
		);

		expect(getByText('Showing 1 to 10 of 12')).toBeTruthy();

		expect(items.length).toBe(10);
	});

	it('renders modal view button for each asset', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue(mockData);

		render(<ExpiredAssetsCard />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		const viewModalButtons = screen.getAllByTestId('view-asset-button');

		expect(viewModalButtons.length).toBe(3);
	});

	it('renders EmptyStateCard when there are no expired assets', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue({
			data: {
				items: [],
				totalCount: 0,
			},
			error: null,
		});

		render(<ExpiredAssetsCard />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getByText('no-assets-expired-yet')).toBeInTheDocument();

		expect(
			screen.getByText('there-are-no-assets-expired-in-the-spaces')
		).toBeInTheDocument();

		const images = screen.getAllByRole('img');

		const emptyStateImage = images.find((image) =>
			image.getAttribute('src')?.includes('cms_empty_state.svg')
		);

		expect(emptyStateImage).toBeInTheDocument();
	});

	it('renders Clear Filters empty state when filters applied but no results', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue({
			data: {
				items: [],
				totalCount: 0,
			},
			error: null,
		});

		const customValue = {
			filters: {
				language: {label: 'English', value: 'en_US'},
				space: initialSpace,
			},
		};

		render(
			<ViewDashboardContextProvider value={customValue}>
				<ExpiredAssetsCard />
			</ViewDashboardContextProvider>
		);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(screen.getByText('no-results-found')).toBeInTheDocument();
		expect(
			screen.getByText('sorry,-no-results-were-found')
		).toBeInTheDocument();

		expect(screen.getByText('clear-filters')).toBeInTheDocument();

		const images = screen.getAllByRole('img');
		const emptyStateImage = images.find((image) =>
			image.getAttribute('src')?.includes('search_state.svg')
		);

		expect(emptyStateImage).toBeInTheDocument();
	});
});
