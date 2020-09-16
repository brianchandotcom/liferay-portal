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
import {configure} from '@testing-library/dom';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import ApplicationsMenu from '../../src/main/resources/META-INF/resources/js/ApplicationsMenu';

configure({
	testIdAttribute: 'data-qa-id',
});

const panelAppsURL = '/fetchUrl';

const mockedData = {
	categories: [
		{
			childCategories: [
				{
					key: 'childcategory1',
					label: 'Child Category 1',
					panelApps: [
						{
							label: 'Panel App 1',
							portletId: 'panelApp1',
							url: 'panelapp1url',
						},
						{
							label: 'Panel App 2',
							portletId: 'panelApp2',
							url: 'panelapp2url',
						},
					],
				},
				{
					key: 'childcategory2',
					label: 'Child Category 2',
					panelApps: [
						{
							label: 'Panel App 3',
							portletId: 'panelApp3',
							url: 'panelapp3url',
						},
						{
							label: 'Panel App 4',
							portletId: 'panelApp4',
							url: 'panelapp4url',
						},
					],
				},
			],
			key: 'category1',
			label: 'Category 1',
		},
	],
	portletNamespace: 'portletNamespace',
	sites: {
		mySites: [
			{
				key: 'mySite',
				label: 'My Site',
				logoURL: 'mySiteLogo',
				url: 'mySiteUrl',
			},
		],
		recentSites: [
			{
				key: 'Recent1',
				label: 'Recent Site 1',
				logoURL: 'recentSite1Logo',
				url: 'recentSite1Url',
			},
		],
	},
};

const renderApplicationsMenu = () => {
	return render(<ApplicationsMenu panelAppsURL={panelAppsURL} />);
};

describe('ApplicationsMenu', () => {
	beforeEach(() => {
		global.Liferay.Browser = {isMac: () => true};
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

	it('renders Applications Menu button', () => {
		const {getByTestId} = renderApplicationsMenu({});

		expect(getByTestId('applicationsMenu')).toBeInTheDocument();
	});

	it('fetches Applications Menu data when trigger button is focused', async () => {
		const {getByTestId} = renderApplicationsMenu();
		const trigger = getByTestId('applicationsMenu');

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

	it('fetches Applications Menu data when trigger button is hovered', async () => {
		const {getByTestId} = renderApplicationsMenu();
		const trigger = getByTestId('applicationsMenu');

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

	it('fetches Applications Menu data when trigger button is clicked', async () => {
		const {getByTestId} = renderApplicationsMenu();
		const trigger = getByTestId('applicationsMenu');

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

	it('renders Applications Menu modal with a close button when trigger button is clicked', async () => {
		const {
			getByTestId,
			getByTitle,
			queryByTitle,
		} = renderApplicationsMenu();

		const trigger = getByTestId('applicationsMenu');

		expect(queryByTitle('close')).not.toBeInTheDocument();

		await act(async () => {
			fireEvent.click(trigger);
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(getByTitle('close')).toBeInTheDocument();
	});

	it('closes Applications Menu modal when close button is clicked', async () => {
		const {getByTestId, getByTitle} = renderApplicationsMenu();
		const trigger = getByTestId('applicationsMenu');

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

	it('closes Applications Menu modal when clicking outside', async () => {
		const {getByTestId, getByTitle} = renderApplicationsMenu();
		const trigger = getByTestId('applicationsMenu');

		await act(async () => {
			fireEvent.click(trigger);
		});

		act(() => {
			jest.runAllTimers();
		});

		const closeButton = getByTitle('close');

		await act(async () => {
			const backdropElement = document.querySelector(
				'.fade.modal.d-block.show'
			);
			fireEvent.mouseDown(backdropElement);
			fireEvent.mouseUp(backdropElement);
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(closeButton).not.toBeInTheDocument();
	});
});
