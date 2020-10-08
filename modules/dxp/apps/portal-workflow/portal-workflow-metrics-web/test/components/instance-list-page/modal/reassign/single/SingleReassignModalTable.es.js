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
import SingleReassignModal from '../../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/reassign/single/SingleReassignModal.es';
import {MockRouter} from '../../../../../mock/MockRouter.es';

const ContainerMock = ({children}) => {
	const clientMock = {
		get: jest
			.fn()
			.mockResolvedValue({data: {items: [{id: 1, name: 'Test'}]}}),
	};
	const [selectedItem, setSelectedItem] = useState({
		assetTitle: 'Blog1',
		assetType: 'Blogs Entry',
		assignees: [{id: 2, name: 'Test Test'}],
		id: 1,
		status: 'In Progress',
		taskNames: ['Review'],
	});
	const [selectedItems, setSelectedItems] = useState([]);

	return (
		<MockRouter client={clientMock}>
			<InstanceListContext.Provider
				value={{
					selectedItem,
					selectedItems,
					setSelectedItem,
					setSelectedItems,
				}}
			>
				<ModalContext.Provider>{children}</ModalContext.Provider>
			</InstanceListContext.Provider>
		</MockRouter>
	);
};

describe('The SingleReassignModalTable component should', () => {
	afterEach(cleanup);

	const data = {
		items: [
			{
				assigneePerson: {
					id: 20124,
					name: 'Test Test',
				},
				completed: true,
				id: 40336,
				instanceId: 40330,
				name: 'review',
			},
		],
	};

	const mockItem = {
		assetTitle: 'Blog2',
		assetType: 'Blogs Entry',
		assignees: [{id: 20124, name: 'Test Test'}],
		creator: {id: 20124, name: 'Test Test'},
		dateCreated: '2019-12-10T17:44:44Z',
		id: 40330,
		slaStatus: 'Overdue',
		status: 'Completed',
		taskNames: ['Update'],
	};

	const setAssigneeId = jest.fn();

	test('Render with statuses Completed and Overdue', () => {
		const {container} = render(
			<SingleReassignModal.Table
				data={data}
				setAssigneeId={setAssigneeId}
				{...mockItem}
			/>,
			{
				wrapper: ContainerMock,
			}
		);

		const singleReassignModalTable = container.querySelector('.table');

		expect(singleReassignModalTable.innerHTML).not.toBeUndefined();
		expect(singleReassignModalTable.innerHTML).not.toBeNull();
	});

	test('Render with no taskName', () => {
		const {container} = render(
			<SingleReassignModal.Table
				data={data}
				setAssigneeId={setAssigneeId}
				{...mockItem}
			/>,
			{
				wrapper: ContainerMock,
			}
		);

		const singleReassignModalTable = container.querySelector('.table');

		expect(singleReassignModalTable.innerHTML).not.toBeUndefined();
		expect(singleReassignModalTable.innerHTML).not.toBeNull();
	});

	test('Render with no data', () => {
		const {container} = render(
			<SingleReassignModal.Table
				data={{}}
				setAssigneeId={setAssigneeId}
				{...mockItem}
			/>,
			{
				wrapper: ContainerMock,
			}
		);

		const singleReassignModalTable = container.querySelector('.table');

		expect(singleReassignModalTable.innerHTML).not.toBeUndefined();
		expect(singleReassignModalTable.innerHTML).not.toBeNull();
	});

	test('Render with taskNames', () => {
		const data = {
			items: [
				{
					assigneeRoles: [],
					completed: true,
					dateCompletion: '2019-12-10T17:45:38Z',
					dateCreated: '2019-12-10T17:44:45Z',
					definitionId: 38902,
					definitionName: 'Single Approver',
					definitionVersion: '',
					description: '',
					instanceId: 40330,
					name: 'review',
					objectReviewed: {
						id: 40324,
						resourceType: 'BlogPosting',
					},
				},
			],
		};

		const {container} = render(
			<SingleReassignModal.Table
				data={data}
				setAssigneeId={setAssigneeId}
				{...mockItem}
			/>,
			{
				wrapper: ContainerMock,
			}
		);

		const singleReassignModalTable = container.querySelector('.table');

		expect(singleReassignModalTable.innerHTML).not.toBeUndefined();
		expect(singleReassignModalTable.innerHTML).not.toBeNull();
	});
});

describe('The AssigneeInput component should', () => {
	const setReassignMock = jest.fn();
	const clientMock = {
		get: jest
			.fn()
			.mockResolvedValue({data: {items: [{id: 1, name: 'Test'}]}}),
	};

	test('Render change assignee input text to Test', () => {
		cleanup();

		render(
			<MockRouter client={clientMock}>
				<SingleReassignModal.Table.AssigneeInput
					reassignedTasks={{
						tasks: [{assigneeId: 20124, id: 39347}],
					}}
					setReassignedTasks={setReassignMock}
					taskId={39347}
				/>
			</MockRouter>
		);

		const autocompleteInput = document.querySelector('input.form-control');

		fireEvent.change(autocompleteInput, {target: {value: 'Test'}});

		expect(autocompleteInput.value).toBe('Test');
	});

	test('Change its text to "Test"', () => {
		cleanup();

		const clientMock = {
			get: jest
				.fn()
				.mockResolvedValue({data: {items: [{id: 1, name: 'Test'}]}}),
		};

		render(
			<MockRouter client={clientMock}>
				<SingleReassignModal.Table.AssigneeInput
					reassignedTasks={{
						tasks: [{assigneeId: 20124, id: 39347}],
					}}
					setReassignedTasks={setReassignMock}
					taskId={39347}
				/>
			</MockRouter>
		);

		expect(clientMock.get).toHaveBeenCalled();
	});

	test('Select a new assignee', () => {
		cleanup();

		render(
			<MockRouter client={clientMock}>
				<SingleReassignModal.Table.AssigneeInput
					reassignedTasks={{tasks: []}}
					setReassignedTasks={setReassignMock}
					taskId={39347}
				/>
			</MockRouter>
		);

		const autocompleteInput = document.querySelector('input.form-control');

		fireEvent.change(autocompleteInput, {target: {value: 'Test'}});

		fireEvent.blur(autocompleteInput);

		const dropDownListItem = document.querySelector('.dropdown-item');

		fireEvent.click(dropDownListItem);
	});

	test('Select a new assignee with input already filled', () => {
		cleanup();

		render(
			<MockRouter client={clientMock}>
				<SingleReassignModal.Table.AssigneeInput
					reassignedTasks={{tasks: [{assigneeId: 20124, id: 39347}]}}
					setReassignedTasks={setReassignMock}
					taskId={39347}
				/>
			</MockRouter>
		);

		const autocompleteInput = document.querySelector('input.form-control');

		fireEvent.change(autocompleteInput, {target: {value: 'Test'}});

		fireEvent.blur(autocompleteInput);

		const dropDownListItem = document.querySelector('.dropdown-item');

		fireEvent.click(dropDownListItem);
	});
});
