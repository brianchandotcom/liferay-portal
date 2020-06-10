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
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import EditApp from '../../../../src/main/resources/META-INF/resources/js/pages/apps/edit/EditApp.es';
import AppContextProviderWrapper from '../../../AppContextProviderWrapper.es';

const history = {
	goBack: jest.fn(),
};

const customObjectItems = {
	items: [
		{
			dataDefinitionKey: '37496',
			dateCreated: '2020-06-05T13:43:16Z',
			dateModified: '2020-06-05T13:44:08Z',
			defaultLanguageId: 'en_US',
			id: 37497,
			name: {
				en_US: 'Object 01',
			},
		},
	],
};

const nativeObjectItems = {items: []};

const formViewItems = {
	items: [
		{
			dataDefinitionId: 37497,
			dataLayoutKey: '37626',
			dataLayoutPages: [
				{
					dataLayoutRows: [
						{
							dataLayoutColumns: [
								{
									columnSize: 12,
									fieldNames: ['Text'],
								},
							],
						},
					],
					description: {
						en_US: '',
					},
					title: {
						en_US: '',
					},
				},
			],
			dataRules: [],
			dateCreated: '2020-06-08T12:12:23Z',
			dateModified: '2020-06-08T12:12:23Z',
			defaultLanguageId: 'en_US',
			description: {},
			id: 37625,
			name: {
				en_US: 'Form 01',
			},
			paginationMode: 'wizard',
			siteId: 20124,
			userId: 20126,
		},
	],
};

const tableViewItems = {
	items: [
		{
			appliedFilters: {},
			dataDefinitionId: 37497,
			dateCreated: '2020-06-08T12:12:31Z',
			dateModified: '2020-06-08T12:12:31Z',
			defaultLanguageId: 'en_US',
			fieldNames: ['Text'],
			id: 37628,
			name: {
				en_US: 'Table 01',
			},
			siteId: 20124,
			sortField: '',
			userId: 20126,
		},
	],
};

describe('EditApp', () => {
	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('renders control menu, upperToolbar and sidebar component correctly when creating a new app', async () => {
		fetch.mockResponseOnce(JSON.stringify(customObjectItems));
		fetch.mockResponseOnce(JSON.stringify(nativeObjectItems));
		fetch.mockResponseOnce(JSON.stringify(formViewItems));
		fetch.mockResponseOnce(JSON.stringify(tableViewItems));

		const routeProps = {
			history,
			match: {params: {}},
		};

		const {
			getByLabelText,
			getByPlaceholderText,
			getByTestId,
			getByText,
			queryByText,
		} = render(<EditApp {...routeProps} />, {
			wrapper: AppContextProviderWrapper,
		});

		const dataAndViewsButton = getByText('data-and-views');
		const deployButton = getByText('deploy');
		const nameInput = getByPlaceholderText('untitled-app');

		expect(queryByText('configuration')).toBeTruthy();
		expect(queryByText('new-workflow-powered-app')).toBeTruthy();
		expect(queryByText('cancel')).toBeTruthy();

		expect(nameInput.value).toBe('');
		expect(deployButton).toBeDisabled();

		await fireEvent.click(dataAndViewsButton);

		const backButton = getByTestId('back-button');
		const sidebarHeader = document.querySelector('div.tab-title');

		expect(queryByText('configuration')).toBeNull();
		expect(sidebarHeader).toHaveTextContent('data-and-views');
		expect(sidebarHeader).toContainElement(backButton);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);
		await fireEvent.click(getByText('Object 01'));

		expect(getByLabelText('main-data-object')).toHaveTextContent(
			'Object 01'
		);
		expect(getByLabelText('form-view')).toHaveTextContent(
			'select-a-form-view'
		);
		expect(getByLabelText('table-view')).toHaveTextContent(
			'select-a-table-view'
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		await fireEvent.click(getByText('Form 01'));
		await fireEvent.click(getByText('Table 01'));

		expect(getByLabelText('form-view')).toHaveTextContent('Form 01');
		expect(getByLabelText('table-view')).toHaveTextContent('Table 01');

		await fireEvent.change(nameInput, {target: {value: 'Test'}});

		expect(deployButton).toBeEnabled();
	});

	it('renders upperToolbar and data and views with respective infos when editing an app', async () => {
		const app = {
			active: true,
			appDeployments: [
				{
					settings: {},
					type: 'standalone',
				},
			],
			dataDefinitionId: 37497,
			dataDefinitionName: 'Object 01',
			dataLayoutId: 37625,
			dataListViewId: 37628,
			dateCreated: '2020-06-08T12:13:14Z',
			dateModified: '2020-06-08T12:13:14Z',
			defaultLanguageId: 'en_US',
			id: 37634,
			name: {
				en_US: 'Test',
			},
			scope: 'workflow',
			siteId: 20124,
			userId: 20126,
		};

		fetch.mockResponseOnce(JSON.stringify(app));
		fetch.mockResponseOnce(JSON.stringify(customObjectItems));
		fetch.mockResponseOnce(JSON.stringify(nativeObjectItems));
		fetch.mockResponseOnce(JSON.stringify(formViewItems));
		fetch.mockResponseOnce(JSON.stringify(tableViewItems));

		const routeProps = {
			history,
			match: {params: {appId: '37634'}},
		};

		const {getByLabelText, getByPlaceholderText, getByText} = render(
			<EditApp {...routeProps} />,
			{
				wrapper: AppContextProviderWrapper,
			}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		await fireEvent.click(getByText('data-and-views'));

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(getByPlaceholderText('untitled-app').value).toBe('Test');

		expect(getByLabelText('main-data-object')).toHaveTextContent(
			'Object 01'
		);
		expect(getByLabelText('form-view')).toHaveTextContent('Form 01');
		expect(getByLabelText('table-view')).toHaveTextContent('Table 01');
		expect(getByText('deploy')).toBeEnabled();
	});
});
