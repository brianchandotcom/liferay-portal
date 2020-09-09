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

import {InstanceListContext} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageProvider.es';
import {ModalContext} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalProvider.es';
import BulkUpdateDueDateModal from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/update-due-date/BulkUpdateDueDateModal.es';
import ToasterProvider from '../../../../../src/main/resources/META-INF/resources/js/shared/components/toaster/ToasterProvider.es';
import {MockRouter} from '../../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const {assignees, items, processSteps} = {
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
		.mockResolvedValue({data: {items: [items[0]], totalCount: 1}}),
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
	const [updateDueDate, setUpdateDueDate] = useState({});
	const processId = '12345';
	const [selectAll, setSelectAll] = useState(false);
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
						processId,
						selectTasks,
						setSelectTasks,
						setUpdateDueDate,
						updateDueDate,
						visibleModal: 'bulkUpdateDueDate',
					}}
				>
					<ToasterProvider>{children}</ToasterProvider>
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		</MockRouter>
	);
};

describe('The BulkReassignModal component should', () => {
	let getAllByTestId, getAllByText, getByTestId, getByText, renderResult;

	beforeAll(() => {
		renderResult = render(<BulkUpdateDueDateModal />, {
			wrapper: ContainerMock,
		});

		getAllByTestId = renderResult.getAllByTestId;
		getAllByText = renderResult.getAllByText;
		getByTestId = renderResult.getByTestId;
		getByText = renderResult.getByText;

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
		const modal = getByTestId('bulkUpdateDueDateModal');
		const stepBar = getByTestId('stepBar');
		const cancelBtn = getByTestId('cancelButton');
		const nextBtn = getByTestId('nextButton');

		const table = getByTestId('selectTaskStepTable');
		const checkbox = getAllByTestId('itemCheckbox');
		const checkAllButton = getByTestId('checkAllButton');
		const assigneeFilter = getByText('assignee');

		const content = modal.children[0].children[0];
		const header = content.children[0];

		expect(header).toHaveTextContent('select-tasks-to-update');

		expect(stepBar.children[0]).toHaveTextContent('select-tasks');
		expect(stepBar.children[1]).toHaveTextContent('step-x-of-x');

		expect(getAllByText('process-step').length).toBe(2);
		expect(assigneeFilter).not.toBeUndefined();

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

		expect(alertError).toHaveTextContent('your-request-has-failed');

		expect(nextBtn).not.toBeDisabled();

		fireEvent.click(nextBtn);

		expect(nextBtn).toBeDisabled();
	});

	test('Render "Update tasks due dates" step with items and back to previous step', async () => {
		const modal = getByTestId('bulkUpdateDueDateModal');

		const previousBtn = getByTestId('previousButton');
		const nextBtn = getByTestId('nextButton');

		const content = modal.children[0].children[0];
		const header = content.children[0];

		expect(header).toHaveTextContent('update-tasks-due-dates');

		await fireEvent.click(previousBtn);

		expect(header).toHaveTextContent('select-tasks-to-update');

		await fireEvent.click(nextBtn);

		expect(header).toHaveTextContent('update-tasks-due-dates');

		await fireEvent.click(nextBtn);
	});

	test('Render "Update tasks due dates" step with update fetch error and retrying', async () => {
		const alertError = getByTestId('alertError');
		const nextBtn = getByTestId('nextButton');

		expect(alertError).toHaveTextContent(
			'your-request-has-failed select-done-to-retry'
		);

		await fireEvent.click(nextBtn);

		const alertToast = await getByTestId('alertToast');
		const alertClose = alertToast.children[1];

		expect(alertToast).toHaveTextContent(
			'the-due-date-for-this-task-has-been-updated'
		);

		fireEvent.click(alertClose);

		const alertContainer = getByTestId('alertContainer');

		expect(alertContainer.children[0].children.length).toBe(0);
	});
});
