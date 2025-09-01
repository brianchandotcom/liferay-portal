/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {
	fireEvent,
	render,
	screen,
	waitForElementToBeRemoved,
} from '@testing-library/react';
import React from 'react';

import ApiHelper from '../../../../src/main/resources/META-INF/resources/js/common/services/ApiHelper';
import {ExpiredAssetsCard} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/components/ExpiredAssetsCard';
import {AssetType} from '../../../../src/main/resources/META-INF/resources/js/main_view/dashboard/utils/assetTypes';

const assetsList = [
	{
		assetType: AssetType.JournalArticle,
		title: 'Understanding Quantum Computing for Beginners',
		usages: 1,
	},
	{
		assetType: AssetType.KnowledgeTransfer,
		title: 'A Guide to Sustainable Energy Solutions',
		usages: 8,
	},
	{
		assetType: AssetType.WebContent,
		title: 'Top 10 Tips for Remote Work Productivity',
		usages: 6,
	},
];

const AssetTypeSvgIconClass: Record<AssetType, string> = {
	[AssetType.JournalArticle]: 'lexicon-icon-blogs',
	[AssetType.KnowledgeTransfer]: 'lexicon-icon-wiki',
	[AssetType.WebContent]: 'lexicon-icon-web-content',
	[AssetType.Blog]: '',
	[AssetType.CustomStructure]: '',
	[AssetType.Document]: '',
};

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

	it('renders the correct icon depending on the asset type', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue(mockData);

		const {container} = render(<ExpiredAssetsCard />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		const items = container.querySelectorAll(
			'.cms-dashboard__expired-assets__item'
		);

		for (const [index, item] of items.entries()) {
			expect(item.textContent).toContain(assetsList[index].title);

			expect(item.querySelector('svg')).toHaveClass(
				AssetTypeSvgIconClass[assetsList[index].assetType]
			);
		}
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

	it('opens modal when clicked in View Asset button', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue({
			data: {
				items: [
					{
						assetType: AssetType.JournalArticle,
						title: 'Understanding Quantum Computing for Beginners',
						usages: 1,
					},
				],
				totalCount: 1,
			},
			error: null,
		});

		const {getByRole} = render(<ExpiredAssetsCard />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		const viewModalButton = screen.getByTestId('view-asset-button');

		// TODO not working

		fireEvent.click(viewModalButton);

		expect(getByRole('heading')).toBeTruthy();
	});
});
