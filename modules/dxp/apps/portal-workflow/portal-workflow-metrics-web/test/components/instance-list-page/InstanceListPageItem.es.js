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

import {InstanceListContext} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageProvider.es';
import {Table} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageTable.es';
import {ModalContext} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalProvider.es';
import {MockRouter} from '../../mock/MockRouter.es';

const instance = {
	assetTitle: 'New Post',
	assetType: 'Blog',
	assignees: [{id: 20124, name: 'Test Test'}],
	creator: {
		name: 'User 1',
	},
	dateCreated: new Date('2019-01-01'),
	id: 1,
	taskNames: ['Review', 'Update'],
};

const setInstanceId = jest.fn();
const setVisibleModal = jest.fn();

const ContainerMock = ({children}) => {
	const [, setSelectAll] = useState(false);
	const [selectedItem, setSelectedItem] = useState({
		assetTitle: 'Blog1',
		assetType: 'Blogs Entry',
		assignees: [{id: 2, name: 'Test Test'}],
		id: 1,
		status: 'In Progress',
		taskNames: ['Review', 'Update'],
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
	]);
	const [singleTransition, setSingleTransition] = useState({});
	const [visibleModal] = useState('');

	return (
		<MockRouter>
			<InstanceListContext.Provider
				value={{
					selectedItem,
					selectedItems,
					setInstanceId,
					setSelectAll,
					setSelectedItem,
					setSelectedItems,
				}}
			>
				<ModalContext.Provider
					value={{
						setSingleTransition,
						setVisibleModal,
						singleTransition,
						visibleModal,
					}}
				>
					{children}
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		</MockRouter>
	);
};

describe('The instance list item should', () => {
	afterEach(cleanup);

	test('Be rendered with "User 1", "Jan 01, 2019, 12:00 AM", and "Review, Update" columns', () => {
		const {getByTestId} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const creatorCell = getByTestId('creatorCell');
		const dateCreatedCell = getByTestId('dateCreatedCell');
		const taskNamesCell = getByTestId('taskNamesCell');

		expect(creatorCell.innerHTML).toBe('User 1');
		expect(dateCreatedCell.innerHTML).toBe('Jan 01, 2019, 12:00 AM');
		expect(taskNamesCell.innerHTML).toBe('Review, Update');
	});

	test('Be rendered with check icon when the slaStatus is "OnTime"', () => {
		const {getByTestId} = render(
			<Table.Item {...instance} slaStatus="OnTime" />,
			{
				wrapper: ContainerMock,
			}
		);

		const instanceStatusIcon = getByTestId('statusIcon');

		expect([...instanceStatusIcon.classList]).toContain(
			'lexicon-icon-check-circle'
		);
	});

	test('Be rendered with exclamation icon when the slaStatus is "Overdue"', () => {
		const {getByTestId} = render(
			<Table.Item {...instance} slaStatus="Overdue" />,
			{
				wrapper: ContainerMock,
			}
		);

		const instanceStatusIcon = getByTestId('statusIcon');

		expect([...instanceStatusIcon.classList]).toContain(
			'lexicon-icon-exclamation-circle'
		);
	});

	test('Be rendered with hr icon when the slaStatus is "Untracked"', () => {
		const {getByTestId} = render(
			<Table.Item {...instance} slaStatus="Untracked" />,
			{
				wrapper: ContainerMock,
			}
		);

		const instanceStatusIcon = getByTestId('statusIcon');

		expect([...instanceStatusIcon.classList]).toContain('lexicon-icon-hr');
	});

	test('Call setInstanceId with "1" as instance id param', () => {
		instance.status = 'Completed';

		const {getByTestId} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const instanceIdLink = getByTestId('instanceIdLink');

		fireEvent.click(instanceIdLink);

		expect(setInstanceId).toBeCalledWith(1);
	});

	test('set BulkReassign modal visualization by clicking the reassign task button', () => {
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const reassignTaskButton = getByText('reassign-task');

		fireEvent.click(reassignTaskButton);

		expect(setVisibleModal).toHaveBeenCalled();
	});

	test('set BulkTransition modal visualization by clicking the reassign task button', () => {
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const reassignTaskButton = getByText('Transition');

		fireEvent.click(reassignTaskButton);

		expect(setVisibleModal).toHaveBeenCalled();
	});
});

describe('The InstanceListPageItem quick action menu should', () => {
	afterEach(cleanup);

	const instance = {
		assetTitle: 'New Post',
		assetType: 'Blog',
		dateCreated: new Date('2019-01-01'),
		id: 1,
		taskNames: ['Review'],
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
	};

	test('set SingleReassign modal visualization by clicking the reassign task button', () => {
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const reassignTaskButton = getByText('reassign-task');

		fireEvent.click(reassignTaskButton);

		expect(setVisibleModal).toHaveBeenCalled();
	});

	test('set SingleUpdateDueDate modal visualization by clicking the reassign task button', () => {
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const reassignTaskButton = getByText('update-due-date');

		fireEvent.click(reassignTaskButton);

		expect(setVisibleModal).toHaveBeenCalled();
	});

	test('set SingleUpdateDueDate modal visualization by clicking the reassign task button', () => {
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const reassignTaskButton = getByText('update-due-date');

		fireEvent.click(reassignTaskButton);

		expect(setVisibleModal).toHaveBeenCalled();
	});

	test('set SingleUpdateDueDate modal visualization by clicking the reassign task button', () => {
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const reassignTaskButton = getByText('Approve');

		fireEvent.click(reassignTaskButton);

		expect(setVisibleModal).toHaveBeenCalled();
	});
});

describe('The InstanceListPageItem instance checkbox component should', () => {
	afterEach(cleanup);

	const instance = {
		assetTitle: 'New Post',
		assetType: 'Blog',
		dateCreated: new Date('2019-01-01'),
		id: 1,
		taskNames: ['Review'],
	};

	test('Set checkbox value by clicking it', () => {
		const {getByTestId} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const instanceCheckbox = getByTestId('instanceCheckbox');

		expect(instanceCheckbox.checked).toEqual(true);

		fireEvent.click(instanceCheckbox);
		expect(instanceCheckbox.checked).toEqual(false);

		fireEvent.click(instanceCheckbox);
		expect(instanceCheckbox.checked).toEqual(true);
	});
});
