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
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import EditApp from '../../../../src/main/resources/META-INF/resources/js/pages/apps/edit/EditApp.es';
import AppContextProviderWrapper from '../../../AppContextProviderWrapper.es';

const app = {
	active: false,
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
	id: 37634,
	name: {
		en_US: 'Test',
	},
	siteId: 20124,
	userId: 20126,
};

const appContextMock = {
	baseResourceUrl: '',
	namespace: '',
};

const customObjectItems = {
	items: [
		{
			availableLanguageIds: ['en_US', 'pt_BR'],
			contentType: 'app-builder',
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
									fieldNames: ['Text1'],
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
			description: {},
			id: 37625,
			name: {
				en_US: 'Form 01',
			},
			paginationMode: 'wizard',
			siteId: 20124,
			userId: 20126,
		},
		{
			dataDefinitionId: 37497,
			dataLayoutKey: '37627',
			dataLayoutPages: [
				{
					dataLayoutRows: [
						{
							dataLayoutColumns: [
								{
									columnSize: 12,
									fieldNames: ['Text2'],
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
			dateCreated: '2020-06-09T12:12:23Z',
			dateModified: '2020-06-09T12:12:23Z',
			description: {},
			id: 37626,
			name: {
				en_US: 'Form 02',
			},
			paginationMode: 'wizard',
			siteId: 20124,
			userId: 20126,
		},
	],
};

const history = {
	push: jest.fn(),
};

const roleItems = {
	items: [
		{
			availableLanguages: ['en-US'],
			dateCreated: '2020-07-01T13:25:25Z',
			dateModified: '2020-07-01T13:25:25Z',
			description:
				'Account Managers who belong to an organization can administer all accounts associated to that organization.',
			id: 37238,
			name: 'Account Manager',
			roleType: 'organization',
		},
	],
};

const routeProps = {
	match: {
		params: {},
	},
};

const tableViewItems = {
	items: [
		{
			appliedFilters: {},
			dataDefinitionId: 37497,
			dateCreated: '2020-06-08T12:12:31Z',
			dateModified: '2020-06-08T12:12:31Z',
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

const workflow = {
	appId: 37634,
	appWorkflowStates: [
		{
			appWorkflowTransitions: [
				{
					name: 'Submit',
					primary: true,
					transitionTo: 'Step 1',
				},
			],
			initial: true,
			name: 'Start',
		},
		{
			appWorkflowTransitions: [],
			initial: false,
			name: 'Closed',
		},
	],
	appWorkflowTasks: [
		{
			appWorkflowDataLayoutLinks: [
				{
					dataLayoutId: 37625,
					readOnly: true,
				},
			],
			appWorkflowRoleAssignments: [
				{
					roleId: 37238,
					roleName: 'Account Manager',
				},
			],
			appWorkflowTransitions: [
				{
					name: 'Close',
					primary: true,
					transitionTo: 'Closed',
				},
			],
			name: 'Step 1',
		},
	],
};

const errorMessage = 'Error on saving the app';

const mockFetch = jest
	.fn()
	.mockRejectedValueOnce({errorMessage})
	.mockResolvedValue(app);
const mockGetItem = jest
	.fn()
	.mockResolvedValueOnce(roleItems)
	.mockResolvedValueOnce(customObjectItems)
	.mockResolvedValueOnce(customObjectItems)
	.mockResolvedValueOnce(formViewItems)
	.mockResolvedValueOnce(tableViewItems)
	.mockResolvedValueOnce(roleItems)
	.mockResolvedValueOnce(customObjectItems)
	.mockResolvedValueOnce(app)
	.mockResolvedValueOnce(workflow)
	.mockResolvedValueOnce(customObjectItems.items[0])
	.mockResolvedValueOnce(formViewItems)
	.mockResolvedValueOnce(tableViewItems)
	.mockResolvedValueOnce(customObjectItems)
	.mockResolvedValueOnce(formViewItems)
	.mockResolvedValueOnce(tableViewItems)
	.mockResolvedValueOnce(formViewItems);

const mockToast = jest.fn();

jest.mock('frontend-js-web', () => ({
	createResourceURL: jest.fn(() => 'http://resource_url?'),
	debounce: jest.fn().mockResolvedValue(),
	fetch: () => mockFetch(),
}));

jest.mock('app-builder-web/js/utils/client.es', () => ({
	getItem: () => mockGetItem(),
	parseResponse: (response) => response,
}));

jest.mock('app-builder-web/js/utils/toast.es', () => ({
	__esModule: true,
	errorToast: (message) => mockToast(message),
	successToast: (message) => mockToast(message),
}));

describe('EditApp', () => {
	describe('Creating a new app', () => {
		let result;

		it('render upper toolbar and set the app name', async () => {
			result = render(
				<AppContextProviderWrapper
					appContext={appContextMock}
					history={history}
				>
					<EditApp {...routeProps} />
				</AppContextProviderWrapper>
			);

			const nameInput = result.getByPlaceholderText('untitled-app');

			expect(
				result.getByText('new-workflow-powered-app')
			).toBeInTheDocument();
			expect(nameInput.value).toBe('');
			expect(result.getByText('cancel')).toBeEnabled();
			expect(result.getByText('save')).toBeDisabled();

			await act(async () => {
				await fireEvent.change(nameInput, {
					target: {value: 'Workflow App Test'},
				});
			});

			expect(nameInput.value).toBe('Workflow App Test');
		});

		it('render sidebar, workflow steps builder', async () => {
			expect(result.getByText('step-configuration')).toBeInTheDocument();

			const steps = result.container.querySelectorAll('.step');

			expect(steps.length).toBe(2);
			expect(steps[0]).toHaveTextContent('initial-step');
			expect(steps[1]).toHaveTextContent('final-step');

			const stepNameInput = result.container.querySelector(
				'.form-group-outlined input'
			);

			expect(stepNameInput.value).toBe('initial-step');
		});

		it('rename default steps', async () => {
			expect(result.getByText('step-configuration')).toBeInTheDocument();

			const stepNameInput = result.container.querySelector(
				'.form-group-outlined input'
			);

			await fireEvent.change(stepNameInput, {
				target: {value: 'Created'},
			});

			expect(result.getByText('Created')).toBeInTheDocument();

			await fireEvent.click(result.getByText('final-step'));

			expect(stepNameInput.value).toBe('final-step');

			await fireEvent.change(stepNameInput, {
				target: {value: 'Closed'},
			});

			await fireEvent.click(result.getByText('Created'));

			expect(stepNameInput.value).toBe('Created');
		});

		it('configure first step data', async () => {
			await fireEvent.click(result.getByText('data-and-views'));

			expect(
				result.container.querySelector('div.tab-title')
			).toHaveTextContent('data-and-views');

			await waitForElementToBeRemoved(() =>
				document.querySelector('span.loading-animation')
			);

			await fireEvent.click(result.getByText('Object 01'));

			expect(result.getByLabelText('main-data-object')).toHaveTextContent(
				'Object 01'
			);
			expect(result.getByLabelText('form-view')).toHaveTextContent(
				'select-a-form-view'
			);
			expect(result.getByLabelText('table-view')).toHaveTextContent(
				'select-a-table-view'
			);

			await waitForElementToBeRemoved(() =>
				document.querySelector('span.loading-animation')
			);

			await fireEvent.click(result.getByText('Form 01'));

			expect(result.getByLabelText('form-view')).toHaveTextContent(
				'Form 01'
			);

			await fireEvent.click(result.getByText('Form 02'));

			expect(result.getByLabelText('form-view')).toHaveTextContent(
				'Form 02'
			);

			await fireEvent.click(result.getByText('Table 01'));

			expect(result.getByLabelText('table-view')).toHaveTextContent(
				'Table 01'
			);
		});

		it('change app name language and set translated name', async () => {
			const nameInput = result.getByPlaceholderText('untitled-app');

			await fireEvent.click(result.getByText('pt-BR'));

			await act(async () => {
				await fireEvent.change(nameInput, {
					target: {value: 'Workflow App Test PT'},
				});
			});

			expect(nameInput.value).toBe('Workflow App Test PT');

			await fireEvent.click(result.getByText('en-US'));

			expect(nameInput.value).toBe('Workflow App Test');
		});

		it('add a new step, set the assignees and forms', async () => {
			await fireEvent.click(result.getByTitle('create-new-step'));

			expect(result.getByText('step-configuration')).toBeInTheDocument();

			const deployButton = result.getByText('deploy');

			expect(deployButton).toBeDisabled();

			const stepNameInput = result.container.querySelector(
				'.form-group-outlined input'
			);

			expect(stepNameInput.value).toBe('step-x');

			await fireEvent.change(stepNameInput, {
				target: {value: 'Initial Review'},
			});

			await fireEvent.mouseDown(result.getByText('Account Manager'));

			expect(
				result.container.querySelector('.label-dismissible span')
			).toHaveTextContent('Account Manager');

			expect(deployButton).toBeEnabled();

			await fireEvent.click(result.getByText('data-and-views'));

			await fireEvent.click(result.getByText('add-new-form-view'));

			const stepFormViews = result.container.querySelectorAll(
				'.step-form-view'
			);

			expect(stepFormViews[0]).toHaveTextContent('Form 02');

			await fireEvent.click(result.getAllByText('editable')[0]);

			await fireEvent.click(result.getAllByText('Form 01')[1]);

			expect(stepFormViews[1]).toHaveTextContent('Form 01');

			await fireEvent.click(result.getAllByTitle('remove')[0]);
		});

		it('add a new other step and set primary and secondary actions', async () => {
			await fireEvent.click(result.getByTitle('create-new-step'));

			const stepNameInput = result.container.querySelector(
				'.form-group-outlined input'
			);

			await fireEvent.change(stepNameInput, {
				target: {value: 'Secondary Review'},
			});

			await fireEvent.click(result.getByText('actions'));

			const title = result.container.querySelector('div.tab-title');

			expect(title).toHaveTextContent('actions');

			await fireEvent.change(result.getByDisplayValue('submit'), {
				target: {value: 'Close'},
			});

			await fireEvent.click(result.getByText('add-new-action'));

			await fireEvent.click(result.getByText('remove'));

			await fireEvent.click(result.getByText('add-new-action'));

			await fireEvent.change(
				result.getByDisplayValue('secondary-action'),
				{target: {value: 'Reject'}}
			);

			const backButton = title.children[0];

			await fireEvent.click(backButton);

			expect(result.getByText('Close → Closed')).toBeInTheDocument();
			expect(
				result.getByText('Reject → Initial Review')
			).toBeInTheDocument();
		});

		it('add a new intermediate step and remove', async () => {
			await fireEvent.click(result.getByText('Initial Review'));

			await fireEvent.click(result.getByTitle('create-new-step'));

			const stepNameInput = result.container.querySelector(
				'.form-group-outlined input'
			);

			await fireEvent.change(stepNameInput, {
				target: {value: 'Intermediate'},
			});

			await fireEvent.click(result.getByText('Initial Review'));

			expect(
				result.getByText('submit → Intermediate')
			).toBeInTheDocument();

			await fireEvent.click(result.getByText('Secondary Review'));

			expect(result.getByText('Close → Closed')).toBeInTheDocument();
			expect(
				result.getByText('Reject → Intermediate')
			).toBeInTheDocument();

			await fireEvent.click(result.getAllByText('delete-step')[1]);

			expect(result.getByText('Close → Closed')).toBeInTheDocument();
			expect(
				result.getByText('Reject → Initial Review')
			).toBeInTheDocument();
		});

		it('renaming steps and validate actions', async () => {
			await fireEvent.click(result.getByText('Initial Review'));

			const stepNameInput = result.container.querySelector(
				'.form-group-outlined input'
			);

			await fireEvent.change(stepNameInput, {
				target: {value: 'Review'},
			});

			await fireEvent.click(result.getByText('Closed'));

			await fireEvent.change(stepNameInput, {
				target: {value: 'Finished'},
			});

			await fireEvent.click(result.getByText('Secondary Review'));

			expect(result.getByText('Close → Finished')).toBeInTheDocument();
			expect(result.getByText('Reject → Review')).toBeInTheDocument();
		});

		it('delete the created steps and save', async () => {
			const saveButton = result.getByText('save');
			let steps = result.container.querySelectorAll('.step');

			expect(saveButton).toBeDisabled();
			expect(steps.length).toBe(4);

			await fireEvent.click(result.getAllByText('delete-step')[1]);

			steps = result.container.querySelectorAll('.step');

			expect(steps.length).toBe(3);

			await fireEvent.click(result.getByText('Review'));

			expect(result.getByText('submit → Finished')).toBeInTheDocument();

			await fireEvent.click(result.getByText('delete-step'));

			steps = result.container.querySelectorAll('.step');

			expect(steps.length).toBe(2);
			expect(saveButton).toBeEnabled();

			await fireEvent.click(saveButton);
		});

		it('calls error toast after saving', async () => {
			expect(mockToast).toHaveBeenCalledWith(errorMessage);

			await fireEvent.click(result.getByText('save'));
		});

		it('calls success toast after saving', () => {
			expect(mockToast).toHaveBeenCalledWith(
				'the-app-was-saved-successfully'
			);

			expect(history.push).toHaveBeenCalled();
			cleanup();
		});
	});

	describe('Editing an existing app', () => {
		let result;

		it('render upper toolbar', async () => {
			routeProps.match.params.appId = '37634';

			result = render(
				<AppContextProviderWrapper
					appContext={appContextMock}
					history={history}
				>
					<EditApp {...routeProps} />
				</AppContextProviderWrapper>
			);

			expect(
				result.getByText('edit-workflow-powered-app')
			).toBeInTheDocument();

			await waitForElementToBeRemoved(() =>
				document.querySelector('span.loading-animation')
			);

			expect(result.getByText('cancel')).toBeEnabled();
			expect(result.getByText('save')).toBeEnabled();
			expect(result.getByText('deploy')).toBeEnabled();
			expect(result.getByPlaceholderText('untitled-app').value).toBe(
				'Test'
			);
		});

		it('render sidebar, workflow steps builder', async () => {
			const steps = result.container.querySelectorAll('.step-card');

			expect(steps.length).toBe(3);
			expect(steps[0]).toHaveTextContent('Start');
			expect(steps[1]).toHaveTextContent('Step 1');
			expect(steps[2]).toHaveTextContent('Closed');

			let stepNameInput = result.container.querySelector(
				'.form-group-outlined input'
			);

			expect(stepNameInput.value).toBe('Start');

			const dataAndViewsButton = result.container.querySelectorAll(
				'.sidebar-body button'
			)[0];

			expect(dataAndViewsButton).toHaveTextContent('Form 01');
			expect(dataAndViewsButton).toHaveTextContent('Table 01');

			await fireEvent.click(dataAndViewsButton);

			await waitForElementToBeRemoved(() =>
				document.querySelector('span.loading-animation')
			);

			expect(result.getByLabelText('main-data-object')).toHaveTextContent(
				'Object 01'
			);
			expect(result.getByLabelText('form-view')).toHaveTextContent(
				'Form 01'
			);
			expect(result.getByLabelText('table-view')).toHaveTextContent(
				'Table 01'
			);

			await fireEvent.click(steps[1]);

			expect(result.getByText('step-configuration')).toBeInTheDocument();
			expect(
				result.container.querySelector('.label-dismissible span')
			).toHaveTextContent('Account Manager');
			expect(
				result.container.querySelectorAll('.tab-button span')[1]
					.parentElement
			).toHaveTextContent('Form 01');

			stepNameInput = result.container.querySelector(
				'.form-group-outlined input'
			);

			expect(stepNameInput.value).toBe('Step 1');
		});

		it('rename final step', async () => {
			await fireEvent.click(result.getByText('Closed'));

			const stepNameInput = result.container.querySelector(
				'.form-group-outlined input'
			);

			expect(stepNameInput.value).toBe('Closed');

			await fireEvent.change(stepNameInput, {target: {value: 'End'}});

			expect(result.getByText('End')).toBeInTheDocument();
		});

		it('update deploy settings and save', async () => {
			jest.useFakeTimers();

			await fireEvent.click(result.getByText('deploy'));

			await act(async () => {
				await jest.runAllTimers();
			});

			await act(async () => {
				await fireEvent.click(result.getByText('done'));
			});
		});

		it('calls success toast after saving', () => {
			expect(mockToast).toHaveBeenCalledWith(
				'the-app-was-saved-successfully'
			);

			expect(history.push).toHaveBeenCalled();
		});
	});
});
