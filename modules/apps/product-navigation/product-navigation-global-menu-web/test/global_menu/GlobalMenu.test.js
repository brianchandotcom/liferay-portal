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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import GlobalMenu from '../../src/main/resources/META-INF/resources/js/GlobalMenu';

const panelAppsURL = '/fetchUrl';

const mockedData = {
	categories: [
		{
			childCategories: [
				{
					label: 'Child Category 1',
					panelApps: [
						{
							portletId: 'panelApp1',
							label: 'Panel App 1',
							url: 'panelapp1url',
						},
						{
							portletId: 'panelApp2',
							label: 'Panel App 2',
							url: 'panelapp2url',
						},
					],
					key: 'childcategory1',
				},
				{
					label: 'Child Category 2',
					panelApps: [
						{
							portletId: 'panelApp3',
							label: 'Panel App 3',
							url: 'panelapp3url',
						},
						{
							portletId: 'panelApp4',
							label: 'Panel App 4',
							url: 'panelapp4url',
						},
					],
					key: 'childcategory2',
				},
			],
			label: 'Category 1',
			key: 'category1',
		},
	],
	portletNamespace: 'portletNamespace',
	sites: {
		recentSites: [
			{
				label: 'Recent Site 1',
				key: 'Recent1',
				logoURL: 'recentSite1Logo',
				url: 'recentSite1Url',
			},
		],
		mySites: [
			{
				label: 'My Site',
				key: 'mySite',
				logoURL: 'mySiteLogo',
				url: 'mySiteUrl',
			},
		],
	},
};

const renderGlobalMenu = () => {
	return render(<GlobalMenu panelAppsURL={panelAppsURL} />);
};

describe('GlobalMenu', () => {
	beforeEach(() => {
		jest.useFakeTimers();
		fetch.mockResponseOnce(JSON.stringify(mockedData));
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	afterEach(() => {
		jest.clearAllTimers();
		jest.restoreAllMocks();
		cleanup();
	});

	it('renders Global Menu button', () => {
		const {getByTitle} = renderGlobalMenu({});

		expect(getByTitle('global-menu')).toBeInTheDocument();
	});

	it('fetch Global Menu data when trigger button is focused', async () => {
		const {getByTitle} = renderGlobalMenu();
		const trigger = getByTitle('global-menu');

		await act(async () => {
			fireEvent.focus(trigger);
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(global.fetch).toHaveBeenCalledWith(
			panelAppsURL,
			expect.anything()
		);
	});

	it('fetch Global Menu data when trigger button is hovered', async () => {
		const {getByTitle} = renderGlobalMenu();
		const trigger = getByTitle('global-menu');

		await act(async () => {
			fireEvent.mouseOver(trigger);
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(global.fetch).toHaveBeenCalledWith(
			panelAppsURL,
			expect.anything()
		);
	});

	it('fetch Global Menu data when trigger button is clicked', async () => {
		const {getByTitle} = renderGlobalMenu();
		const trigger = getByTitle('global-menu');

		await act(async () => {
			fireEvent.click(trigger);
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(global.fetch).toHaveBeenCalledWith(
			panelAppsURL,
			expect.anything()
		);
	});

	it('renders Global Menu modal with a close button when trigger button is clicked', async () => {
		const {getByTitle} = renderGlobalMenu();
		const trigger = getByTitle('global-menu');

		await act(async () => {
			fireEvent.click(trigger);
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(getByTitle('close')).toBeInTheDocument();
	});

	it('closes Global Menu modal when close button is clicked', async () => {
		const {getByTitle} = renderGlobalMenu();
		const trigger = getByTitle('global-menu');

		await act(async () => {
			fireEvent.click(trigger);
		});

		act(() => {
			jest.runAllTimers();
		});

		const closeButton = getByTitle('close');

		await act(async () => {
			fireEvent.click(closeButton);
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(closeButton).not.toBeInTheDocument();
	});

	it('closes Global Menu modal when click outside', async () => {
		const {getByTitle} = renderGlobalMenu();
		const trigger = getByTitle('global-menu');

		await act(async () => {
			fireEvent.click(trigger);
		});

		act(() => {
			jest.runAllTimers();
		});

		const closeButton = getByTitle('close');

		await act(async () => {
			fireEvent.click(document.body);
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(closeButton).not.toBeInTheDocument();
	});
});
