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
import {act, fireEvent, render} from '@testing-library/react';
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

const errorMessage = 'Error on deleting app';

const routeProps = {
	editPath: EDIT_PATH,
	match: {params: {}},
	scope: 'workflow',
};

window.confirm = jest.fn(() => true);

const mockFetch = jest
	.fn()
	.mockRejectedValueOnce({errorMessage})
	.mockResolvedValueOnce({});

jest.mock('frontend-js-web', () => ({
	createResourceURL: jest.fn(() => 'http://resource_url?'),
	fetch: () => mockFetch(),
}));

const mockGetItem = jest.fn().mockResolvedValueOnce(DROPDOWN_VALUES);

const mockRequest = jest
	.fn()
	.mockResolvedValueOnce(RESPONSES.MANY_ITEMS({size: 5}))
	.mockResolvedValueOnce(RESPONSES.NO_ITEMS)
	.mockResolvedValueOnce(RESPONSES.MANY_ITEMS({active: false, size: 5}))
	.mockResolvedValueOnce(RESPONSES.MANY_ITEMS({active: false, size: 4}));

jest.mock('app-builder-web/js/utils/client.es', () => ({
	confirmDelete: jest.fn(),
	getItem: () => mockGetItem(),
	parseResponse: (response) => response,
	request: () => mockRequest(),
}));

const mockToast = jest.fn();

jest.mock('app-builder-web/js/utils/toast.es', () => ({
	__esModule: true,
	errorToast: (message) => mockToast(message),
	successToast: (message) => mockToast(message),
}));

describe('ListApps', () => {
	describe('Rendering', () => {
		beforeEach(() => {
			jest.spyOn(time, 'fromNow').mockImplementation(() => 'months ago');
		});

		it('with 5 apps on list and opens a new app tooltip', async () => {
			const {container} = render(<ListApps {...routeProps} />, {
				wrapper: AppContextProviderWrapper,
			});

			const newAppButton = container.querySelector(
				'.nav-btn.nav-btn-monospaced.btn.btn-monospaced.btn-primary'
			);

			await waitForElementToBeRemoved(() =>
				container.querySelector('span.loading-animation')
			);

			expect(container.querySelector('tbody').children.length).toEqual(5);

			await fireEvent.mouseOver(newAppButton);

			expect(
				document.querySelector('.popover.clay-popover-bottom-right')
					.textContent
			).toContain(
				'workflow-powered-appcreate-an-app-that-integrates-a-step-driven-workflow-process'
			);

			await fireEvent.mouseOut(newAppButton);

			expect(
				container.querySelector('.popover.clay-popover-bottom-right')
			).not.toBeInTheDocument();
		});

		it('with empty state', async () => {
			const {container, queryByText} = render(
				<ListApps {...routeProps} />,
				{
					wrapper: AppContextProviderWrapper,
				}
			);

			await waitForElementToBeRemoved(() =>
				container.querySelector('span.loading-animation')
			);

			const {parentElement} = queryByText('create-new-app');

			expect(
				container.querySelector('.taglib-empty-result-message')
					.textContent
			).toContain(
				'there-are-no-apps-yetintegrate-the-data-collection-and-management-of-an-object-with-a-step-driven-workflow-process'
			);
			expect(parentElement.getAttribute('href')).toBe(
				'#/workflow/deploy'
			);
		});
	});

	describe('Deleting an app', () => {
		it('tries to delete an app and shows respective toast', async () => {
			const {container, getAllByText} = render(
				<AppContextProviderWrapper
					appContext={{
						baseResourceUrl: '',
						namespace: '',
					}}
				>
					<ListApps {...routeProps} />
				</AppContextProviderWrapper>
			);

			await waitForElementToBeRemoved(() =>
				document.querySelector('span.loading-animation')
			);

			expect(container.querySelector('tbody').children.length).toEqual(5);

			await fireEvent.click(
				container.querySelectorAll('button.page-link')[0]
			);

			await act(async () => {
				await fireEvent.click(getAllByText('delete')[0]);
			});

			expect(mockToast).toHaveBeenCalledWith(errorMessage);

			await act(async () => {
				await fireEvent.click(getAllByText('delete')[0]);
			});

			expect(mockToast).toHaveBeenCalledWith(
				'the-item-was-deleted-successfully'
			);

			expect(container.querySelector('tbody').children.length).toEqual(4);
		});
	});
});
