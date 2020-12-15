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

import {
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import FriendlyURLHistory from '../../../src/main/resources/META-INF/resources/js/friendly_url_history/FriendlyURLHistory';

const activeUrl = '/test';

const defaultProps = {
	defaultLanguageId: 'en_US',
	deleteFriendlyURLEntryLocalizationURL: '/delete/friendly_url_history',
	friendlyURLEntryLocalizationsURL: '/get/friendly_url_history',
	portletNamespace: 'portletNamespace',
	restoreFriendlyURLEntryLocalizationURL: '/restore/friendly_url_history',
};

const fetchResponse = {
	en_US: {
		current: {
			friendlyURLEntryId: 36000,
			friendlyURLEntryLocalizationId: 300,
			urlTitle: activeUrl,
		},
		history: [
			{
				friendlyURLEntryId: 36003,
				friendlyURLEntryLocalizationId: 303,
				urlTitle: '/test-3',
			},
			{
				friendlyURLEntryId: 36002,
				friendlyURLEntryLocalizationId: 302,
				urlTitle: '/test-2',
			},
			{
				friendlyURLEntryId: 36001,
				friendlyURLEntryLocalizationId: 301,
				urlTitle: '/test-1',
			},
		],
	},
};

const fetchResponseAfterRestore = {
	en_US: {
		current: {
			friendlyURLEntryId: 36002,
			friendlyURLEntryLocalizationId: 30,
			urlTitle: '/test-2',
		},
		history: [
			{
				friendlyURLEntryId: 36003,
				friendlyURLEntryLocalizationId: 303,
				urlTitle: '/test-3',
			},
			{
				friendlyURLEntryId: 36001,
				friendlyURLEntryLocalizationId: 301,
				urlTitle: '/test-1',
			},
			{
				friendlyURLEntryId: 36000,
				friendlyURLEntryLocalizationId: 300,
				urlTitle: '/test',
			},
		],
	},
};

const renderComponent = (props) => render(<FriendlyURLHistory {...props} />);

describe('FriendlyURLHistory', () => {
	let historyButton;

	afterEach(cleanup);

	beforeAll(() => {
		Liferay.component = jest.fn().mockImplementation(() => {
			return {
				getSelectedLanguageId: () => 'en_US',
				updateInput: () => jest.fn(),
			};
		});
	});

	it('renders a button', () => {
		const {getByRole} = renderComponent({...defaultProps});

		historyButton = getByRole('button');

		expect(historyButton.className).toContain('btn-url-history');
	});

	it('renders a restore icon inside the button', () => {
		renderComponent({...defaultProps});

		expect(historyButton.querySelector('svg').classList).toContain(
			'lexicon-icon-restore'
		);
	});

	describe('FriendlyURLHistoryModal', () => {
		let result;

		afterEach(() => {
			fetch.resetMocks();
		});

		beforeEach(async () => {
			fetch.mockResponseOnce(JSON.stringify(fetchResponse));

			result = renderComponent({...defaultProps});

			historyButton = result.getByRole('button');

			await act(async () => {
				fireEvent.click(historyButton);
			});
		});

		it('renders a modal when user clicks on history button', async () => {
			const title = await waitForElement(() =>
				result.getByText('history')
			);

			expect(title);
		});

		it('renders the active url', async () => {
			const activeUrlElement = await waitForElement(() =>
				result.getByText(activeUrl)
			);

			expect(activeUrlElement);
		});

		it('renders the old friendly urls', async () => {
			const listItems = await waitForElement(() =>
				result.getAllByRole('listitem')
			);

			expect(listItems.length).toBe(4);
		});

		it('deletes the third old friendly url', async () => {
			fetch.mockResponseOnce(JSON.stringify({success: true}));

			const listItems = await waitForElement(() =>
				result.getAllByRole('listitem')
			);

			const deleteButtons = listItems.map((listitem) =>
				listitem.querySelector('button[data-title="forget-url"]')
			);

			await act(async () => {
				fireEvent.click(deleteButtons[2]);
			});

			expect(
				document.querySelectorAll('.modal-content li.list-group-item')
					.length
			).toBe(2);

			expect(fetch.mock.calls.length).toEqual(2);
		});

		it('restores the second old friendly url as the active url', async () => {
			fetch.mockResponseOnce(JSON.stringify({success: true}));
			fetch.mockResponseOnce(JSON.stringify(fetchResponseAfterRestore));

			const listItems = await waitForElement(() =>
				result.getAllByRole('listitem')
			);

			const restoreButtons = listItems.map((listitem) =>
				listitem.querySelector('button[data-title="restore-url"]')
			);

			await act(async () => {
				fireEvent.click(restoreButtons[1]);
			});

			expect(fetch.mock.calls.length).toEqual(3);

			expect(
				document.querySelector('.modal-content .active-url-text')
					.innerHTML
			).toBe('/test-2');
		});
	});
});
