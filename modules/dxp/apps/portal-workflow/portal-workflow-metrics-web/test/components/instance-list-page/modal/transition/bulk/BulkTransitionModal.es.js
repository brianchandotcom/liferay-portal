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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React, {useState} from 'react';

import {InstanceListContext} from '../../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageProvider.es';
import {ModalContext} from '../../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalProvider.es';
import BulkTransitionModal from '../../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/transition/bulk/BulkTransitionModal.es';
import ToasterProvider from '../../../../../../src/main/resources/META-INF/resources/js/shared/components/toaster/ToasterProvider.es';
import {MockRouter} from '../../../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const tasks = (range) =>
	new Array(range).fill({}).map((_, id) => ({
		assetTitle: `title${id + 1}`,
		assetType: 'Blogs Entry',
		assignee: {
			additionalName: '',
			contentType: 'UserAccount',
			familyName: 'Test',
			givenName: 'Test',
			id: 20127,
			name: 'Test Test',
			profileURL: '/web/test',
		},
		assigneeRoles: [],
		classPK: id + 1,
		completed: false,
		dateCreated: '2020-03-03T12:04:46Z',
		description: '',
		id: id + 1,
		instanceId: id + 1,
		label: 'Review',
		name: 'review',
		processId: id + 1,
	}));

const {data, items, processSteps} = {
	data: {
		workflowTaskTransitions: [
			{
				transitions: [
					{
						label: 'Approve',
						name: 'approve',
					},
					{
						label: 'Reject',
						name: 'reject',
					},
				],
				workflowDefinitionVersion: '1',
				workflowTaskLabel: 'Review',
				workflowTaskName: 'review',
			},
			{
				transitions: [
					{
						label: 'Approve',
						name: 'approve',
					},
					{
						label: 'Reject',
						name: 'reject',
					},
				],
				workflowDefinitionVersion: '1',
				workflowTaskLabel: 'Review',
				workflowTaskName: 'review',
			},
			{
				transitions: [
					{
						label: 'Approve',
						name: 'approve',
					},
					{
						label: 'Reject',
						name: 'reject',
					},
				],
				workflowDefinitionVersion: '1',
				workflowTaskLabel: 'Review',
				workflowTaskName: 'review',
			},
			{
				transitions: [
					{
						label: 'Approve',
						name: 'approve',
					},
					{
						label: 'Reject',
						name: 'reject',
					},
				],
				workflowDefinitionVersion: '1',
				workflowTaskLabel: 'Review',
				workflowTaskName: 'review',
			},
			{
				transitions: [
					{
						label: 'Approve',
						name: 'approve',
					},
					{
						label: 'Reject',
						name: 'reject',
					},
				],
				workflowDefinitionVersion: '1',
				workflowTaskLabel: 'Review',
				workflowTaskName: 'review',
			},
		],
	},
	items: tasks(13),
	lastPage: 9,
	page: 1,
	pageSize: 5,
	processSteps: [
		{key: 'review', name: 'Review'},
		{key: 'update', name: 'Update'},
	],
	totalCount: 45,
};

const mockTasks = {
	data: {items, totalCount: items.length + 1},
};

const ContainerMockPrimary = ({children}) => {
	const processId = '12345';

	const [bulkTransition, setBulkTransition] = useState({
		transition: {errors: {}, onGoing: false},
		transitionTasks: [],
	});
	const [selectedItems, setSelectedItems] = useState([
		{
			assetTitle: 'Blog1',
			assetType: 'Blogs Entry',
			assignees: [{id: 2, name: 'Test Test'}],
			id: 1,
			status: 'In Progress',
			taskNames: ['Review'],
		},
		{
			assetTitle: 'Blog2',
			assetType: 'Blogs Entry',
			assignees: [{id: 2, name: 'Test Test'}],
			id: 2,
			status: 'In Progress',
			taskNames: ['Review'],
		},
	]);
	const [selectTasks, setSelectTasks] = useState({
		selectAll: false,
		tasks: [],
	});

	const clientMock = {
		patch: jest
			.fn()
			.mockRejectedValueOnce(new Error('request-failure'))
			.mockResolvedValue({data: {}}),
		post: jest
			.fn()
			.mockRejectedValueOnce(new Error('request-failure'))
			.mockResolvedValueOnce(mockTasks)
			.mockResolvedValueOnce(mockTasks)
			.mockRejectedValueOnce(new Error('request-failure'))
			.mockResolvedValue({data}),
		request: jest
			.fn()
			.mockResolvedValueOnce({data: {items: processSteps}})
			.mockResolvedValueOnce({data: {items: processSteps}})
			.mockResolvedValueOnce({data: {items: processSteps}}),
	};

	return (
		<MockRouter client={clientMock}>
			<InstanceListContext.Provider
				value={{
					selectedItems,
					setSelectedItems,
				}}
			>
				<ModalContext.Provider
					value={{
						bulkTransition,
						processId,
						selectTasks,
						setBulkTransition,
						setSelectTasks,
						visibleModal: 'bulkTransition',
					}}
				>
					<ToasterProvider>{children}</ToasterProvider>
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		</MockRouter>
	);
};

const ContainerMockSecondary = ({children}) => {
	const processId = '12345';

	const [bulkTransition, setBulkTransition] = useState({
		transition: {errors: {}, onGoing: false},
		transitionTasks: [],
	});
	const selectAll = true;
	const [selectedItems, setSelectedItems] = useState([
		{
			assetTitle: 'Blog1',
			assetType: 'Blogs Entry',
			assignees: [{id: 2, name: 'Test Test'}],
			id: 1,
			status: 'In Progress',
			taskNames: ['Review'],
		},
		{
			assetTitle: 'Blog2',
			assetType: 'Blogs Entry',
			assignees: [{id: 2, name: 'Test Test'}],
			id: 2,
			status: 'In Progress',
			taskNames: ['Review'],
		},
	]);
	const [selectTasks, setSelectTasks] = useState({
		selectAll: false,
		tasks: [],
	});
	const [visibleModal] = useState('bulkTransition');

	const clientMock = {
		get: jest.fn().mockResolvedValueOnce({data}),
		post: jest.fn().mockResolvedValue({
			data: {items, totalCount: items.length + 1},
		}),
		request: jest.fn().mockResolvedValueOnce({data: {items: processSteps}}),
	};

	return (
		<MockRouter client={clientMock}>
			<InstanceListContext.Provider
				value={{
					selectAll,
					selectedItems,
					setSelectedItems,
				}}
			>
				<ModalContext.Provider
					value={{
						bulkTransition,
						processId,
						selectTasks,
						setBulkTransition,
						setSelectTasks,
						visibleModal,
					}}
				>
					<ToasterProvider>{children}</ToasterProvider>
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		</MockRouter>
	);
};

describe('The BulkTransitionModal component should', () => {
	let getAllByTestId, getAllByText, getByTestId;

	beforeAll(() => {
		const component = render(<BulkTransitionModal />, {
			wrapper: ContainerMockPrimary,
		});
		getAllByTestId = component.getAllByTestId;
		getAllByText = component.getAllByText;
		getByTestId = component.getByTestId;

		jest.runAllTimers();
	});

	test('Render "Select tasks" step with fetch error and retrying', () => {
		const alertError = getByTestId('alertError');
		const emptyState = getByTestId('emptyState');
		const retryButton = getByTestId('retryButton');

		expect(alertError).toHaveTextContent('your-request-has-failed');

		expect(emptyState.children[0].children[1]).toHaveTextContent('retry');
		expect(emptyState.children[0].children[0]).toHaveTextContent(
			'unable-to-retrieve-data'
		);

		fireEvent.click(retryButton);
	});

	test('Render "Select tasks" step with items', () => {
		const modal = getByTestId('bulkTransitionModal');
		const stepBar = getByTestId('stepBar');
		const cancelBtn = getByTestId('cancelButton');
		const nextBtn = getByTestId('nextButton');

		const table = getByTestId('selectTaskStepTable');
		const checkbox = getAllByTestId('itemCheckbox');
		const checkAllButton = getByTestId('checkAllButton');
		const content = modal.children[0].children[0];
		const header = content.children[0];

		expect(header).toHaveTextContent('select-steps-to-transition');

		expect(stepBar.children[0]).toHaveTextContent('select-steps');
		expect(stepBar.children[1]).toHaveTextContent('step-x-of-x');

		expect(getAllByText('process-step').length).toBe(2);

		expect(cancelBtn).toHaveTextContent('cancel');
		expect(nextBtn).toHaveTextContent('next');
		expect(nextBtn).toHaveAttribute('disabled');

		const items = table.children[1].children;

		expect(checkbox[0].checked).toBe(false);
		expect(items[0].children[1]).toHaveTextContent('1');
		expect(items[0].children[2]).toHaveTextContent('Blogs Entry: title1');
		expect(items[0].children[3]).toHaveTextContent('Review');
		expect(items[0].children[4]).toHaveTextContent('Test Test');

		expect(checkbox[1].checked).toBe(false);
		expect(items[1].children[1]).toHaveTextContent('2');
		expect(items[1].children[2]).toHaveTextContent('Blogs Entry: title2');
		expect(items[1].children[3]).toHaveTextContent('Review');
		expect(items[1].children[4]).toHaveTextContent('Test Test');

		expect(checkAllButton.checked).toBe(false);

		fireEvent.click(checkAllButton);

		let label = getByTestId('toolbarLabel');

		expect(checkbox[0].checked).toBe(true);
		expect(checkbox[1].checked).toBe(true);
		expect(checkAllButton.checked).toBe(true);
		expect(label).toHaveTextContent('x-of-x-selected');

		const clearButton = getByTestId('clear');

		fireEvent.click(clearButton);

		expect(checkbox[0].checked).toBe(false);
		expect(checkbox[1].checked).toBe(false);
		expect(checkAllButton.checked).toBe(false);

		fireEvent.click(checkbox[0]);

		label = getByTestId('toolbarLabel');

		expect(checkbox[0].checked).toBe(true);
		expect(checkbox[1].checked).toBe(false);
		expect(checkAllButton.checked).toBe(false);
		expect(label).toHaveTextContent('x-of-x-selected');

		fireEvent.click(checkbox[0]);

		expect(checkbox[0].checked).toBe(false);
		expect(checkbox[1].checked).toBe(false);
		expect(checkAllButton.checked).toBe(false);

		fireEvent.click(checkAllButton);

		expect(checkbox[0].checked).toBe(true);
		expect(checkbox[1].checked).toBe(true);
		expect(checkAllButton.checked).toBe(true);
		expect(label).toHaveTextContent('x-of-x-selected');

		expect(nextBtn).not.toBeDisabled();

		const selectAllButton = getByTestId('selectAll');

		fireEvent.click(selectAllButton);

		label = getByTestId('toolbarLabel');

		expect(label).toHaveTextContent('all-selected');

		expect(nextBtn).not.toBeDisabled();

		fireEvent.click(nextBtn);
	});

	test('Render "Select transitions" step failing the fetch and retry then', () => {
		const alertError = getByTestId('alertError');
		const nextButton = getByTestId('nextButton');

		expect(alertError).toHaveTextContent('your-request-has-failed');

		fireEvent.click(nextButton);
	});

	test('Load the second step and all transitions successfully', () => {
		const modal = getByTestId('bulkTransitionModal');

		const content = modal.children[0].children[0];
		const header = content.children[0];
		const stepBar = getByTestId('stepBar');
		const nextBtn = getByTestId('nextButton');

		expect(header).toHaveTextContent('choose-transition-per-step');
		expect(stepBar.children[0]).toHaveTextContent('choose-transition');
		expect(stepBar.children[1]).toHaveTextContent('step-x-of-x');

		fireEvent.click(nextBtn);
	});

	test('Load transitions and show alert message when select "done" and fail the patch request and retry the request', () => {
		const alertError = getByTestId('alertError');
		const nextBtn = getByTestId('nextButton');

		expect(alertError).toHaveTextContent(
			'your-request-has-failed select-done-to-retry'
		);

		fireEvent.click(nextBtn);
	});

	test('Show alert message when attempt to transition without selecting any transition go to previous step and forward', () => {
		const alertError = getByTestId('alertError');

		expect(alertError).toHaveTextContent('your-request-has-failed');

		const nextBtn = getByTestId('nextButton');
		const modal = getByTestId('bulkTransitionModal');

		const content = modal.children[0].children[0];

		const header = content.children[0];

		const previousButton = getByTestId('previousButton');

		fireEvent.click(previousButton);

		expect(header).toHaveTextContent('select-steps-to-transition');

		fireEvent.click(nextBtn);
	});

	test('Select a transition to "approve", click "Show All" button, add a comment and retry patch request successfully', async () => {
		const addCommentButton = getByTestId('addCommentButton');
		const nextBtn = getByTestId('nextButton');
		const selectTransitionFooter = getByTestId('selectTransitionFooter');
		const showAllButton = getByTestId('showAllButton');
		const transitionSelect = getByTestId('transitionSelect');

		fireEvent.change(transitionSelect, {target: {value: 'approve'}});

		expect(transitionSelect.value).toEqual('approve');

		expect(showAllButton).toHaveTextContent('show-all');

		fireEvent.click(showAllButton);

		expect(showAllButton).toHaveTextContent('show-less');

		expect(selectTransitionFooter.children).toHaveLength(1);
		expect(selectTransitionFooter.children[0].type).toEqual('button');

		fireEvent.click(addCommentButton);

		expect(selectTransitionFooter.children).toHaveLength(2);
		expect(selectTransitionFooter.children[1].type).toEqual('textarea');

		const commentField = getByTestId('commentField');

		fireEvent.change(commentField, {
			target: {value: 'Transition comment text'},
		});

		expect(commentField.value).toEqual('Transition comment text');

		await fireEvent.click(nextBtn);

		const alertToast = await getAllByTestId('alertToast');

		expect(alertToast[0]).toHaveTextContent(
			'the-selected-step-has-transitioned-successfully'
		);
	});

	test('Check all tasks and step forward to "Select Transition" step and show loading view', async () => {
		cleanup();
		const {getByTestId} = render(<BulkTransitionModal />, {
			wrapper: ContainerMockSecondary,
		});

		await jest.runAllTimers();
		const checkAllButton = getByTestId('checkAllButton');
		const nextBtn = getByTestId('nextButton');

		await fireEvent.click(checkAllButton);

		const selectAll = getByTestId('selectAll');

		fireEvent.click(selectAll);

		await fireEvent.click(nextBtn);

		const loadingState = getByTestId('loadingState');

		expect(loadingState.children[0]).toHaveClass('loading-animation');
	});
});
