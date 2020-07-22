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
import {waitForElementToBeRemoved} from '@testing-library/dom';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import ListStandardApps from '../../../../src/main/resources/META-INF/resources/js/pages/apps/ListStandardApps.es';
import * as time from '../../../../src/main/resources/META-INF/resources/js/utils/time.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import {RESPONSES} from '../../constants.es';

const DROPDOWN_VALUES = {
	items: [
		{
			id: 123,
			name: {
				en_US: 'Object test',
			},
		},
	],
};

const EDIT_PATH = [
	'/standard/:dataDefinitionId(\\d+)?/deploy',
	'/standard/:dataDefinitionId(\\d+)?/:appId(\\d+)',
];

const routeProps = {
	editPath: EDIT_PATH,
	match: {params: {dataDefinitionId: '123'}},
};

describe('ListStandardApps', () => {
	beforeEach(() => {
		jest.spyOn(time, 'fromNow').mockImplementation(() => 'months ago');
	});

	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	it('renders opening a new app popover and lists 5 apps', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.MANY_ITEMS(5)));
		fetch.mockResponse(JSON.stringify(DROPDOWN_VALUES));

		const {container} = render(<ListStandardApps {...routeProps} />, {
			wrapper: AppContextProviderWrapper,
		});

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(container.querySelector('tbody').children.length).toEqual(5);

		const newAppButton = document.querySelector(
			'.nav-btn.nav-btn-monospaced.btn.btn-monospaced.btn-primary'
		);

		await fireEvent.click(newAppButton);
		await fireEvent.mouseOver(newAppButton);

		expect(
			document.querySelector('.popover.apps-popover')
		).toBeInTheDocument();
		expect(
			document.querySelector('.popover.clay-popover-bottom-right')
				.textContent
		).toContain(
			'standard-appcreate-an-app-to-collect-and-manage-an-objects-data'
		);

		await fireEvent.click(newAppButton);
		await fireEvent.mouseOut(newAppButton);

		expect(document.querySelector('.popover.apps-popover.hide'));
		expect(
			document.querySelector('.popover.clay-popover-bottom-right')
		).not.toBeInTheDocument();

		await fireEvent.click(newAppButton);

		expect(document.querySelector('.popover.apps-popover.hide'));
	});

	it('renders with empty state', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.NO_ITEMS));
		fetch.mockResponse(JSON.stringify(DROPDOWN_VALUES));

		const push = jest.fn();

		const history = {location: {pathname: '/', search: ''}, push};

		const {container, queryByText} = render(
			<AppContextProviderWrapper history={history}>
				<ListStandardApps {...routeProps} />
			</AppContextProviderWrapper>
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(
			container.querySelector('.taglib-empty-result-message').textContent
		).toContain(
			'there-are-no-apps-yetcreate-a-standard-app-to-collect-and-manage-an-objects-data'
		);

		const createNewAppButton = queryByText('create-new-app');

		await fireEvent.click(createNewAppButton);

		const continueButton = document.querySelector(
			'.apps-popover > .popover-footer button:nth-child(2)'
		);

		expect(continueButton.textContent).toBe('continue');
		expect(continueButton).toBeDisabled();

		const dropdownItemsLabel = document.querySelectorAll(
			'.select-dropdown-menu li > button .float-right'
		);

		expect(dropdownItemsLabel[0].textContent).toBe('custom');

		const dropdownItems = document.querySelectorAll(
			'.select-dropdown-menu li > button .float-left'
		);

		expect(dropdownItems[0].textContent).toBe('Object test');

		await fireEvent.click(dropdownItems[0]);

		expect(continueButton).not.toBeDisabled();

		await fireEvent.click(continueButton);

		expect(push).toHaveBeenCalledWith({
			pathname: '/standard/123/deploy',
			search: '?backUrl=/',
		});
	});
});
