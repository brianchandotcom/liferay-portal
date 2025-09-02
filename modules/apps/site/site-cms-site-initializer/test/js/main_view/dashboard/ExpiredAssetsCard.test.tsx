/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {
	render,
	screen,
	waitForElementToBeRemoved,
} from '@testing-library/react';
import React from 'react';

import ApiHelper from '../../../../src/main/resources/META-INF/resources/js/common/services/ApiHelper';
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

	it('is accessible', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue(mockData);

		const {container} = render(<ExpiredAssetsCard />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		await checkAccessibility({context: container});
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
});
