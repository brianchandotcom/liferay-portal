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
import {
	act,
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
import React from 'react';

import EditEntry from '../../../src/main/resources/META-INF/resources/js/pages/entry/EditEntry.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import PermissionsContextProviderWrapper from '../../PermissionsContextProviderWrapper.es';
import {ENTRY} from '../../constants.es';

const context = {
	appId: 1,
	basePortletURL: 'portlet_url',
	baseResourceURL: 'resource_url',
	dataLayoutIds: [123],
	namespace: '_portlet_',
};

const mockFetch = jest.fn().mockResolvedValue();
const mockNavigate = jest.fn();
const mockToast = jest.fn();

jest.mock('dynamic-data-mapping-form-renderer', () => ({
	PagesVisitor: jest.fn().mockImplementation(() => ({
		mapFields: (callback) =>
			[
				{
					fieldName: 'Text',
					localizable: true,
					value: 'text',
					visible: true,
				},
				{
					fieldName: 'Text1',
					localizable: true,
					repeatable: true,
					value: 'text1',
					visible: true,
				},
				{type: 'fieldset'},
				{fieldName: 'Text2', value: 'text2'},
			].map(callback),
	})),
}));

jest.mock('frontend-js-web', () => ({
	createResourceURL: jest.fn().mockImplementation((url, params) => {
		const query = Object.keys(params).reduce(
			(query, key, index) =>
				`${index !== 0 ? '&' : ''}${query}${key}=${params[key]}`,
			'?'
		);

		return `${url}${query}`;
	}),
	debounce: jest.fn().mockResolvedValue(),
	fetch: (...args) => mockFetch(...args),
}));

const mockAddItem = jest.fn().mockResolvedValue(ENTRY.DATA_RECORD_APPS(1));
const mockGetItem = jest
	.fn()
	.mockResolvedValueOnce(ENTRY.APP_WORKFLOW)
	.mockResolvedValueOnce(ENTRY.APP_WORKFLOW)
	.mockResolvedValue({
		items: [
			{
				assignees: [{id: 0, name: 'User 1'}],
				classPK: 0,
				completed: false,
				taskNames: ['Review'],
			},
		],
		totalCount: 1,
	});

jest.mock('app-builder-web/js/utils/client.es', () => ({
	addItem: () => mockAddItem(),
	getItem: () => mockGetItem(),
}));

jest.mock('app-builder-web/js/utils/toast.es', () => ({
	__esModule: true,
	errorToast: (title) => mockToast(title),
	successToast: (title) => mockToast(title),
}));

describe('EditEntry', () => {
	afterEach(cleanup);

	afterAll(() => {
		jest.restoreAllMocks();
	});

	beforeAll(() => {
		const div = document.createElement('div');
		const form = document.createElement('form');
		const [mockFormId] = context.dataLayoutIds;

		form.id = mockFormId;
		div.id = 'edit-app-content';

		document.body.appendChild(div);
		document.body.appendChild(form);

		global.URLSearchParams = jest
			.fn()
			.mockImplementation((params) => params);

		window.Liferay.Util = {
			navigate: (url) => mockNavigate(url),
			ns: jest.fn().mockImplementation((_, params) => params),
		};

		window.Liferay.componentReady = jest.fn().mockResolvedValue({
			reactComponentRef: {
				current: {
					get: () => null,
					getFormNode: () => document.getElementById(mockFormId),
					validate: jest.fn().mockResolvedValue(true),
				},
			},
		});
	});

	it('renders on create mode', async () => {
		const {container, queryAllByRole} = render(
			<AppContextProviderWrapper appContext={context}>
				<EditEntry dataRecordId="0" />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		expect(
			container.querySelector('.control-menu-level-1-heading')
		).toHaveTextContent('add-entry');

		await waitForElementToBeRemoved(() =>
			container.querySelector('span.loading-animation')
		);

		const buttons = queryAllByRole('button');

		expect(buttons.length).toBe(2);
		expect(buttons[0]).toHaveTextContent('Submit');
		expect(buttons[1]).toHaveTextContent('cancel');

		await act(async () => {
			await fireEvent.click(buttons[0]);
		});

		expect(mockFetch).toHaveBeenCalledWith(
			`${context.baseResourceURL}?p_p_resource_id=/app_builder/add_data_record`,
			{
				body: {
					appBuilderAppId: 1,
					dataRecord:
						'{"dataRecordValues":{"Text":{"en_US":"text"},"Text1":{"en_US":["text1"]},"Text2":""}}',
					dataRecordId: '0',
				},
				method: 'POST',
			}
		);
		expect(mockToast).toHaveBeenCalledWith('an-entry-was-added');
		expect(mockNavigate).toHaveBeenCalledWith(context.basePortletURL);
	});

	it('renders on edit mode', async () => {
		const {container, queryAllByRole} = render(
			<AppContextProviderWrapper appContext={context}>
				<EditEntry dataRecordId="1" redirect="/home" />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		expect(
			container.querySelector('.control-menu-level-1-heading')
		).toHaveTextContent('edit-entry');

		await waitForElementToBeRemoved(() =>
			container.querySelector('span.loading-animation')
		);

		const buttons = queryAllByRole('button');

		expect(buttons.length).toBe(2);
		expect(buttons[0]).toHaveTextContent('Close');
		expect(buttons[1]).toHaveTextContent('cancel');

		await waitForElement(() => document.getElementById('workflowInfoBar'));

		await act(async () => {
			await fireEvent.click(buttons[0]);
		});

		expect(mockFetch).toHaveBeenCalledWith(
			`${context.baseResourceURL}?p_p_resource_id=/app_builder/update_data_record`,
			{
				body: {
					appBuilderAppId: 1,
					dataRecord: JSON.stringify({
						dataRecordValues: {
							Text: {en_US: 'text'},
							Text1: {en_US: ['text1']},
							Text2: '',
						},
					}),
					dataRecordId: '1',
					taskName: 'Review',
					transitionName: 'Close',
					workflowInstanceId: undefined,
				},
				method: 'POST',
			}
		);
		expect(mockToast).toHaveBeenCalledWith('an-entry-was-updated');
		expect(mockNavigate).toHaveBeenCalledWith('/home');
	});
});
