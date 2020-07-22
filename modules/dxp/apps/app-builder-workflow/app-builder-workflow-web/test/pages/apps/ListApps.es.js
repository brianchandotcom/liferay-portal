/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import '@testing-library/jest-dom/extend-expect';
import {waitForElementToBeRemoved} from '@testing-library/dom';
import {fireEvent, render} from '@testing-library/react';
import * as time from 'app-builder-web/js/utils/time.es';
import React from 'react';

import ListApps from '../../../src/main/resources/META-INF/resources/js/pages/apps/ListApps.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import {RESPONSES} from '../../constants.es';

const DROPDOWN_VALUES = {
	items: [
		{
			id: 1,
			name: {
				en_US: 'object',
			},
		},
	],
};

const EDIT_PATH = ['/workflow/deploy', '/workflow/:appId(\\d+)'];

const routeProps = {editPath: EDIT_PATH, match: {params: {}}};

describe('ListApps', () => {
	beforeEach(() => {
		jest.spyOn(time, 'fromNow').mockImplementation(() => 'months ago');
	});

	afterEach(jest.restoreAllMocks);

	it('renders with 5 apps on list and opens a new app tooltip', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.MANY_ITEMS(5)));
		fetch.mockResponse(JSON.stringify(DROPDOWN_VALUES));

		const {container} = render(<ListApps {...routeProps} />, {
			wrapper: AppContextProviderWrapper,
		});

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(container.querySelector('tbody').children.length).toEqual(5);

		const newAppButton = document.querySelector(
			'.nav-btn.nav-btn-monospaced.btn.btn-monospaced.btn-primary'
		);

		await fireEvent.mouseOver(newAppButton);

		expect(
			document.querySelector('.popover.clay-popover-bottom-right')
				.textContent
		).toContain(
			'workflow-powered-appcreate-an-app-that-integrates-a-step-driven-workflow-process'
		);

		await fireEvent.mouseOut(newAppButton);

		expect(
			document.querySelector('.popover.clay-popover-bottom-right')
		).not.toBeInTheDocument();
	});

	it('renders with empty state', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.NO_ITEMS));
		fetch.mockResponse(JSON.stringify(DROPDOWN_VALUES));

		const {container, queryByText} = render(<ListApps {...routeProps} />, {
			wrapper: AppContextProviderWrapper,
		});

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(
			container.querySelector('.taglib-empty-result-message').textContent
		).toContain(
			'there-are-no-apps-yetintegrate-the-data-collection-and-management-of-an-object-with-a-step-driven-workflow-process'
		);

		const {parentElement} = queryByText('create-new-app');

		expect(parentElement.getAttribute('href')).toBe('#/workflow/deploy');
	});
});
