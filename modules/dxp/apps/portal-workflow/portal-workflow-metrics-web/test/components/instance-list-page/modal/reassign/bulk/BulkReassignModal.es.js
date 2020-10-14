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

import {InstanceListContext} from '../../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageProvider.es';
import {ModalContext} from '../../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalProvider.es';
import BulkReassignModal from '../../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/reassign/bulk/BulkReassignModal.es';
import ToasterProvider from '../../../../../../src/main/resources/META-INF/resources/js/shared/components/toaster/ToasterProvider.es';
import {MockRouter} from '../../../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const {assignees, items, processSteps, workflowTaskAssignableUsers} = {
	assignees: [{id: 1, name: 'Test Test'}],
	items: [
		{
			assetTitle: 'Blog 1',
			assetType: 'Blog',
			assignee: {
				id: 1,
				name: 'Test Test',
			},
			id: 1,
			instanceId: 1,
			label: 'Review',
		},
		{
			assetTitle: 'Blog 2',
			assetType: 'Blog',
			assignee: {
				id: 1,
				name: 'Test Test',
			},
			id: 2,
			instanceId: 2,
			label: 'Update',
		},
	],
	processSteps: [
		{key: 'review', name: 'Review'},
		{key: 'update', name: 'Update'},
	],
	selectedItems: [{id: 1}, {id: 2}],
	workflowTaskAssignableUsers: [
		{
			assignableUsers: [
				{
					id: 1,
					name: '1test test1',
				},
				{
					id: 2,
					name: '2test test2',
				},
				{
					id: 3,
					name: '3test test3',
				},
				{
					id: 4,
					name: '4test test4',
				},
				{
					id: 5,
					name: 'Test Test',
				},
			],
			workflowTaskId: 1,
		},
		{
			assignableUsers: [
				{
					id: 5,
					name: 'Test Test',
				},
			],
			workflowTaskId: 0,
		},
	],
};

const clientMock = {
	patch: jest
		.fn()
		.mockRejectedValueOnce(new Error('request-failure'))
		.mockResolvedValueOnce({data: {}}),
	post: jest
		.fn()
		.mockRejectedValueOnce(new Error('request-failure'))
		.mockResolvedValueOnce({data: {items, totalCount: items.length + 1}})
		.mockRejectedValueOnce(new Error('request-failure'))
		.mockResolvedValueOnce({data: {items: [items[0]], totalCount: 1}})
		.mockRejectedValueOnce(new Error('request-failure'))
		.mockResolvedValueOnce({data: {workflowTaskAssignableUsers}})
		.mockResolvedValueOnce({data: {items: [items[0]], totalCount: 1}})
		.mockResolvedValue({data: {workflowTaskAssignableUsers}}),
	request: jest
		.fn()
		.mockResolvedValueOnce({data: {items: processSteps}})
		.mockResolvedValueOnce({data: {items: assignees}})
		.mockResolvedValueOnce({data: {items: processSteps}})
		.mockResolvedValueOnce({data: {items: assignees}})
		.mockResolvedValueOnce({data: {items: processSteps}})
		.mockResolvedValueOnce({data: {items: assignees}}),
};

const ContainerMock = ({children}) => {
	const [bulkReassign, setBulkReassign] = useState({
		reassignedTasks: [],
		reassigning: false,
		selectedAssignee: null,
		useSameAssignee: false,
	});
	const processId = '12345';
	const [selectAll, setSelectAll] = useState(false);
	const [visibleModal, setVisibleModal] = useState('bulkReassign');

	const [selectTasks, setSelectTasks] = useState({
		selectAll: false,
		tasks: [],
	});

	const [selectedItems, setSelectedItems] = useState([]);

	return (
		<MockRouter client={clientMock}>
			<InstanceListContext.Provider
				value={{
					selectAll,
					selectedItems,
					setSelectAll,
					setSelectedItems,
				}}
			>
				<ModalContext.Provider
					value={{
						bulkReassign,
						closeModal: setVisibleModal,
						processId,
						selectTasks,
						setBulkReassign,
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

describe('The BulkReassignModal component should', () => {
	let getAllByRole, getAllByText, getByText, renderResult;

	beforeAll(() => {
		renderResult = render(<BulkReassignModal />, {
			wrapper: ContainerMock,
		});

		getAllByRole = renderResult.getAllByRole;
		getAllByText = renderResult.getAllByText;
		getByText = renderResult.getByText;

		jest.runAllTimers();
	});

	test('Render "Select tasks" step with fetch error and retrying', () => {
		const alertError = getByText('your-request-has-failed');
		const emptyStateMessage = getByText('unable-to-retrieve-data');
		const retryBtn = getByText('retry');

		expect(alertError).toBeTruthy();
		expect(emptyStateMessage).toBeTruthy();

		fireEvent.click(retryBtn);
	});

	test('Render "Select tasks" step with items', () => {
		const assigneeFilter = getByText('assignee');
		const cancelBtn = getByText('cancel');
		const checkAllButton = document.querySelectorAll(
			'.custom-control-input'
		)[0];
		const modal = document.querySelector('.modal');
		const nextBtn = getByText('next');
		const rows = getAllByRole('row');
		const stepBar = document.querySelector('.step-of-bar');
		const table = document.querySelector('.table');

		const content = modal.children[0].children[0];
		const checkbox1 = rows[1].querySelector('input.custom-control-input');
		const checkbox2 = rows[2].querySelector('input.custom-control-input');

		const header = content.children[0];

		expect(header).toHaveTextContent('select-tasks-to-reassign');
		expect(stepBar.children[0]).toHaveTextContent('select-tasks');
		expect(stepBar.children[1]).toHaveTextContent('step-x-of-x');
		expect(getAllByText('process-step').length).toBe(2);
		expect(assigneeFilter).not.toBeUndefined();
		expect(cancelBtn).toBeTruthy();
		expect(nextBtn).toHaveAttribute('disabled');

		const items = table.children[1].children;

		expect(checkbox1.checked).toBe(false);
		expect(items[0].children[1]).toHaveTextContent('1');
		expect(items[0].children[2]).toHaveTextContent('Blog: Blog 1');
		expect(items[0].children[3]).toHaveTextContent('Review');
		expect(items[0].children[4]).toHaveTextContent('Test Test');

		expect(checkbox2.checked).toBe(false);
		expect(items[1].children[1]).toHaveTextContent('2');
		expect(items[1].children[2]).toHaveTextContent('Blog: Blog 2');
		expect(items[1].children[3]).toHaveTextContent('Update');
		expect(items[1].children[4]).toHaveTextContent('Test Test');

		expect(checkAllButton.checked).toBe(false);

		fireEvent.click(checkAllButton);

		let label = getByText('x-of-x-selected');

		expect(checkbox1.checked).toBe(true);
		expect(checkbox2.checked).toBe(true);
		expect(checkAllButton.checked).toBe(true);
		expect(label).toBeTruthy();

		const clearButton = getByText('clear');

		fireEvent.click(clearButton);

		expect(checkbox1.checked).toBe(false);
		expect(checkbox2.checked).toBe(false);
		expect(checkAllButton.checked).toBe(false);

		fireEvent.click(checkbox1);

		label = getByText('x-of-x-selected');

		expect(checkbox1.checked).toBe(true);
		expect(checkbox2.checked).toBe(false);
		expect(checkAllButton.checked).toBe(false);
		expect(label).toBeTruthy();

		fireEvent.click(checkbox1);

		expect(checkbox1.checked).toBe(false);
		expect(checkbox2.checked).toBe(false);
		expect(checkAllButton.checked).toBe(false);

		fireEvent.click(checkAllButton);

		label = getByText('x-of-x-selected');

		expect(checkbox1.checked).toBe(true);
		expect(checkbox2.checked).toBe(true);
		expect(checkAllButton.checked).toBe(true);
		expect(label).toBeTruthy();
		expect(nextBtn).not.toBeDisabled();

		const selectAllButton = getByText('select-all');

		fireEvent.click(selectAllButton);

		label = getByText('all-selected');

		expect(label).toBeTruthy();
		expect(nextBtn).not.toBeDisabled();

		fireEvent.click(nextBtn);
	});

	test('Render "Select tasks" step with next error and retrying', () => {
		const alertError = getByText('your-request-has-failed');
		const nextBtn = getByText('next');

		expect(alertError).toBeTruthy();
		expect(nextBtn).not.toBeDisabled();

		fireEvent.click(nextBtn);

		expect(nextBtn).toBeDisabled();
	});

	test('Render "Select assignees" step with fetch error and retrying', () => {
		const alertError = getByText('your-request-has-failed');
		const emptyStateMessage = getByText('failed-to-retrieve-assignees');
		const retryBtn = getByText('retry');

		expect(alertError).toBeTruthy();
		expect(emptyStateMessage).toBeTruthy();

		fireEvent.click(retryBtn);
	});

	test('Render "Select assignees" step with items and back to previous step', async () => {
		const modal = document.querySelector('.modal');
		const nextBtn = getByText('reassign');
		const previousBtn = getByText('previous');

		const content = modal.children[0].children[0];

		const header = content.children[0];

		expect(header).toHaveTextContent('select-new-assignees');

		await fireEvent.click(previousBtn);

		expect(header).toHaveTextContent('select-tasks-to-reassign');

		await fireEvent.click(nextBtn);
	});

	test('Render "Select assignees" step with items', async () => {
		const assigneeInputs = document.querySelectorAll('input.form-control');
		const cancelBtn = getByText('cancel');
		const modal = document.querySelector('.modal');
		const nextBtn = getByText('reassign');
		const previousBtn = getByText('previous');
		const stepBar = document.querySelector('.step-of-bar');
		const table = document.querySelector('.table');
		const useSameAssignee = document.querySelector(
			'input.custom-control-input'
		);

		const content = modal.children[0].children[0];

		const header = content.children[0];

		expect(header).toHaveTextContent('select-new-assignees');
		expect(stepBar.children[0]).toHaveTextContent('select-assignees');
		expect(stepBar.children[1]).toHaveTextContent('step-x-of-x');
		expect(cancelBtn).toBeTruthy();
		expect(previousBtn).toBeTruthy();
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

		const dropDownLists = document.querySelectorAll('#dropDownList');

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
		const alertError = getByText(
			'your-request-has-failed select-reassign-to-retry'
		);
		const nextBtn = getByText('reassign');

		expect(alertError).toBeTruthy();

		await fireEvent.click(nextBtn);

		const alertToast = document.querySelector('.alert-dismissible');

		const alertClose = alertToast.children[1];

		expect(alertToast).toHaveTextContent('this-task-has-been-reassigned');

		fireEvent.click(alertClose);

		const alertContainer = document.querySelector('.alert-container');

		expect(alertContainer.children[0].children.length).toBe(0);
	});
});
