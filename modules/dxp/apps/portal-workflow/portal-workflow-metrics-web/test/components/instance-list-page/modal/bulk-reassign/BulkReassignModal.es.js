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

import {fireEvent, render} from '@testing-library/react';
import React, {useState} from 'react';

import InstanceListPage from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPage.es';
import {ModalContext} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalContext.es';
import {InstanceListContext} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/store/InstanceListPageStore.es';
import {MockRouter} from '../../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const {items, selectedItems, workflowTaskAssignableUsers} = {
	items: [
		{
			assigneePerson: {
				id: 1,
				name: 'Test Test'
			},
			id: 1,
			name: 'Review',

			objectReviewed: {assetTitle: 'Blog 1', assetType: 'Blog'},
			workflowInstanceId: 1
		},
		{
			assigneePerson: {
				id: 1,
				name: 'Test Test'
			},
			id: 2,
			name: 'Update',
			objectReviewed: {assetTitle: 'Blog 2', assetType: 'Blog'},
			workflowInstanceId: 2
		}
	],
	selectedItems: [{id: 1}, {id: 2}],
	workflowTaskAssignableUsers: [
		{
			assignableUsers: [
				{
					id: 1,
					name: '1test test1'
				},
				{
					id: 2,
					name: '2test test2'
				},
				{
					id: 3,
					name: '3test test3'
				},
				{
					id: 4,
					name: '4test test4'
				},
				{
					id: 5,
					name: 'Test Test'
				}
			],
			workflowTaskId: 1
		},
		{
			assignableUsers: [
				{
					id: 5,
					name: 'Test Test'
				}
			],
			workflowTaskId: 0
		}
	]
};

const clientMock = {
	get: jest
		.fn()
		.mockRejectedValueOnce(new Error('request-failure'))
		.mockResolvedValueOnce({data: {items, totalCount: items.length + 1}})
		.mockRejectedValueOnce(new Error('request-failure'))
		.mockResolvedValueOnce({data: {items: [items[0]], totalCount: 1}})
		.mockRejectedValueOnce(new Error('request-failure'))
		.mockResolvedValueOnce({data: {workflowTaskAssignableUsers}})
		.mockResolvedValueOnce({data: {items, totalCount: items.length}})
		.mockResolvedValueOnce({data: {items: [items[0]], totalCount: 1}})
		.mockResolvedValue({data: {workflowTaskAssignableUsers}}),
	patch: jest
		.fn()
		.mockRejectedValueOnce(new Error('request-failure'))
		.mockResolvedValueOnce({data: {}})
};

const ContainerMock = ({children}) => {
	const [bulkModal, setBulkModal] = useState({
		reassignedTasks: [],
		reassigning: false,
		selectedAssignee: null,
		selectedTasks: [],
		useSameAssignee: false,
		visible: true
	});

	return (
		<MockRouter client={clientMock}>
			<InstanceListContext.Provider value={{selectedItems}}>
				<ModalContext.Provider value={{bulkModal, setBulkModal}}>
					{children}
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		</MockRouter>
	);
};

describe('The BulkReassignModal component should', () => {
	let getAllByTestId, getByTestId;

	beforeAll(() => {
		const renderResult = render(
			<ContainerMock>
				<InstanceListPage.BulkReassignModal />
			</ContainerMock>
		);

		getAllByTestId = renderResult.getAllByTestId;
		getByTestId = renderResult.getByTestId;

		jest.runAllTimers();
	});

	test('Render "Select tasks" step with fetch error and retrying', () => {
		const emptyState = getByTestId('emptyState');
		const alertError = getByTestId('alertError');

		const retryBtn = emptyState.children[0].children[1];

		expect(alertError).toHaveTextContent('your-request-has-failed');

		expect(emptyState.children[0].children[1]).toHaveTextContent('retry');
		expect(emptyState.children[0].children[0]).toHaveTextContent(
			'unable-to-retrieve-data'
		);

		fireEvent.click(retryBtn);
	});

	test('Render "Select tasks" step with items', () => {
		const modal = getByTestId('bulkReassignModal');
		const stepBar = getByTestId('stepOfBar');
		const cancelBtn = getByTestId('cancelButton');
		const nextBtn = getByTestId('nextButton');

		const table = getByTestId('bulkReassignModalTable');
		const checkbox = getAllByTestId('itemCheckbox');
		const checkAllButton = getByTestId('checkAllButton');

		const content = modal.children[0].children[0].children[0];
		const header = content.children[0].children[0];

		expect(header).toHaveTextContent('select-tasks-to-reassign');

		expect(stepBar.children[0]).toHaveTextContent('select-tasks');
		expect(stepBar.children[1]).toHaveTextContent('step-x-of-x');

		expect(cancelBtn).toHaveTextContent('cancel');
		expect(nextBtn).toHaveTextContent('next');
		expect(nextBtn).toHaveAttribute('disabled');

		const items = table.children[1].children;

		expect(checkbox[0].checked).toBe(false);
		expect(items[0].children[1]).toHaveTextContent('1');
		expect(items[0].children[2]).toHaveTextContent('Blog: Blog 1');
		expect(items[0].children[3]).toHaveTextContent('Review');
		expect(items[0].children[4]).toHaveTextContent('Test Test');

		expect(checkbox[1].checked).toBe(false);
		expect(items[1].children[1]).toHaveTextContent('2');
		expect(items[1].children[2]).toHaveTextContent('Blog: Blog 2');
		expect(items[1].children[3]).toHaveTextContent('Update');
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

		label = getByTestId('toolbarLabel');

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

	test('Render "Select tasks" step with next error and retrying', () => {
		const alertError = getByTestId('alertError');
		const nextBtn = getByTestId('nextButton');

		expect(alertError).toHaveTextContent(
			'your-connection-was-unexpectedly-lost'
		);

		expect(nextBtn).not.toBeDisabled();

		fireEvent.click(nextBtn);

		expect(nextBtn).toBeDisabled();
	});

	test('Render "Select assignees" step with fetch error and retrying', () => {
		const emptyState = getByTestId('emptyState');
		const alertError = getByTestId('alertError');

		const retryBtn = emptyState.children[0].children[1];

		expect(alertError).toHaveTextContent('your-request-has-failed');

		expect(emptyState.children[0].children[1]).toHaveTextContent('retry');
		expect(emptyState.children[0].children[0]).toHaveTextContent(
			'failed-to-retrieve-assignees'
		);

		fireEvent.click(retryBtn);
	});

	test('Render "Select assignees" step with items and back to previous step', async () => {
		const modal = getByTestId('bulkReassignModal');

		const previousBtn = getByTestId('previousButton');
		const nextBtn = getByTestId('nextButton');

		const content = modal.children[0].children[0].children[0];
		const header = content.children[0].children[0];

		expect(header).toHaveTextContent('select-new-assignees');

		await fireEvent.click(previousBtn);

		expect(header).toHaveTextContent('select-tasks-to-reassign');

		await fireEvent.click(nextBtn);
	});

	test('Render "Select assignees" step with items', async () => {
		const modal = getByTestId('bulkReassignModal');
		const stepBar = getByTestId('stepOfBar');

		const cancelBtn = getByTestId('cancelButton');
		const previousBtn = getByTestId('previousButton');
		const nextBtn = getByTestId('nextButton');

		const table = getByTestId('bulkReassignModalTable');
		const useSameAssignee = getByTestId('useSameAssignee');
		const assigneeInputs = getAllByTestId('autocompleteInput');

		const content = modal.children[0].children[0].children[0];
		const header = content.children[0].children[0];

		expect(header).toHaveTextContent('select-new-assignees');

		expect(stepBar.children[0]).toHaveTextContent('select-assignees');
		expect(stepBar.children[1]).toHaveTextContent('step-x-of-x');

		expect(cancelBtn).toHaveTextContent('cancel');
		expect(previousBtn).toHaveTextContent('previous');
		expect(nextBtn).toHaveTextContent('reassign');
		expect(nextBtn).toHaveAttribute('disabled');

		const items = table.children[1].children;

		expect(items[0].children[0]).toHaveTextContent('1');
		expect(items[0].children[1]).toHaveTextContent('Blog: Blog 1');
		expect(items[0].children[2]).toHaveTextContent('Review');
		expect(items[0].children[3]).toHaveTextContent('Test Test');

		expect(assigneeInputs[0]).toHaveAttribute('disabled');
		expect(assigneeInputs[1]).not.toHaveAttribute('disabled');
		expect(useSameAssignee.checked).toBe(false);

		await fireEvent.click(useSameAssignee);

		expect(useSameAssignee.checked).toBe(true);
		expect(assigneeInputs[0]).not.toHaveAttribute('disabled');
		expect(assigneeInputs[1]).toHaveAttribute('disabled');
		expect(useSameAssignee.checked).toBe(true);

		await fireEvent.change(assigneeInputs[0], {target: {value: 'test'}});

		const dropDownLists = await getAllByTestId('dropDownList');

		await fireEvent.mouseDown(dropDownLists[0].children[0].children[0]);

		expect(assigneeInputs[0].value).toBe('Test Test');
		expect(assigneeInputs[1].value).toBe('Test Test');

		await fireEvent.click(useSameAssignee);

		expect(assigneeInputs[0]).toHaveAttribute('disabled');
		expect(assigneeInputs[1]).not.toHaveAttribute('disabled');

		expect(assigneeInputs[0].value).toBe('');
		expect(assigneeInputs[1].value).toBe('');

		fireEvent.change(assigneeInputs[1], {target: {value: '1test'}});

		fireEvent.mouseDown(dropDownLists[1].children[0].children[0]);

		expect(assigneeInputs[1].value).toBe('1test test1');

		expect(nextBtn).not.toHaveAttribute('disabled');

		fireEvent.click(nextBtn);

		expect(nextBtn).toHaveAttribute('disabled');
	});

	test('Render "Select assignees" step with reassign fetch error and retrying', async () => {
		const alertError = getByTestId('alertError');
		const nextBtn = getByTestId('nextButton');

		expect(alertError).toHaveTextContent(
			'your-request-has-failed select-reassign-to-retry'
		);

		await fireEvent.click(nextBtn);

		const alertSuccess = await getByTestId('alertSuccess');
		const alertClose = alertSuccess.children[1];

		expect(alertSuccess).toHaveTextContent('x-tasks-have-been-reassigned');

		fireEvent.click(alertClose);

		const alertContainer = getByTestId('alertContainer');

		expect(alertContainer.children[0].children.length).toBe(0);
	});
});
