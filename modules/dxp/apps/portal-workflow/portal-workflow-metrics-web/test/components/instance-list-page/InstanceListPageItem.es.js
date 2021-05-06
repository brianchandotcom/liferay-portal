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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
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
const openModal = jest.fn();

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
						openModal,
						setSingleTransition,
						singleTransition,
						visibleModal: '',
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
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const creatorCell = getByText('User 1');
		const dateCreatedCell = getByText('Jan 01, 2019, 12:00 AM');
		const taskNamesCell = getByText('Review, Update');

		expect(creatorCell).toBeTruthy();
		expect(dateCreatedCell).toBeTruthy();
		expect(taskNamesCell).toBeTruthy();
	});

	test('Be rendered with check icon when the slaStatus is "OnTime"', () => {
		const {container} = render(
			<Table.Item {...instance} slaStatus="OnTime" />,
			{
				wrapper: ContainerMock,
			}
		);

		const instanceStatusIcon = container.querySelector(
			'.lexicon-icon-check-circle'
		);

		expect(instanceStatusIcon).toBeTruthy();
	});

	test('Be rendered with exclamation icon when the slaStatus is "Overdue"', () => {
		const {container} = render(
			<Table.Item {...instance} slaStatus="Overdue" />,
			{
				wrapper: ContainerMock,
			}
		);

		const instanceStatusIcon = container.querySelector(
			'.lexicon-icon-exclamation-circle'
		);

		expect(instanceStatusIcon).toBeTruthy();
	});

	test('Be rendered with hr icon and due date when the slaStatus is "Untracked"', () => {
		const {container} = render(
			<Table.Item {...instance} slaStatus="Untracked" />,
			{
				wrapper: ContainerMock,
			}
		);

		const instanceStatusIcon = container.querySelector('.lexicon-icon-hr');

		expect(instanceStatusIcon).toBeTruthy();

		const instanceStatus = container.querySelector('.due-date.text-info');

		expect(instanceStatus).toBeTruthy();
		expect(instanceStatus.innerHTML).toEqual('-');
	});

	test('Be rendered with due date success when the slaStatus is "OnTime" and slaResult status is "RUNNING"', () => {
		const slaResult = {
			dateOverdue: '2021-04-16T12:44:25Z',
			name: 'SLA Test',
			onTime: true,
			remainingTime: 99999000,
			status: 'RUNNING',
		};

		const {baseElement, container, getByText} = render(
			<Table.Item
				{...instance}
				slaResults={[slaResult]}
				slaStatus="OnTime"
			/>,
			{
				wrapper: ContainerMock,
			}
		);

		act(() => {
			jest.runAllTimers();
		});

		const dueDateCol = container.querySelector('.due-date.text-success');

		expect(dueDateCol).toBeTruthy();

		const dueDateBadge = dueDateCol.querySelector('.due-date-badge');

		expect(dueDateBadge).toBeTruthy();

		const dateText = getByText('Apr 16');

		expect(dateText).toBeTruthy();

		fireEvent.mouseOver(dateText);

		act(() => {
			jest.advanceTimersByTime(1001);
		});

		let popoverElement = baseElement.querySelector('.popover');

		expect(popoverElement).toBeTruthy();

		fireEvent.mouseEnter(popoverElement);

		const slaNamePopoverText = getByText('SLA Test:');

		expect(slaNamePopoverText).toBeTruthy();

		const slaDateTimeRemaingTime = getByText(
			'Apr 16, 12:44 PM (1d 03h 46min left)'
		);

		expect(slaDateTimeRemaingTime).toBeTruthy();

		fireEvent.mouseLeave(popoverElement);
		fireEvent.mouseOut(dateText);

		popoverElement = baseElement.querySelector('.popover');

		expect(popoverElement).toBeNull();
	});

	test('Be rendered with due date danger when the slaStatus is "Overdue" and slaResult status is "RUNNING"', () => {
		const slaResult = {
			dateOverdue: '2021-04-16T12:44:25Z',
			name: 'SLA Test',
			onTime: false,
			remainingTime: -99999000,
			status: 'RUNNING',
		};

		const {baseElement, container, getByText} = render(
			<Table.Item
				{...instance}
				slaResults={[slaResult]}
				slaStatus="Overdue"
			/>,
			{
				wrapper: ContainerMock,
			}
		);

		const dueDateCol = container.querySelector('.due-date.text-danger');

		expect(dueDateCol).toBeTruthy();

		const dueDateBadge = dueDateCol.querySelector('.due-date-badge');

		expect(dueDateBadge).toBeTruthy();

		const dateText = getByText('Apr 16');

		expect(dateText).toBeTruthy();

		fireEvent.mouseOver(dateText);

		act(() => {
			jest.advanceTimersByTime(1001);
		});

		let popoverElement = baseElement.querySelector('.popover');

		expect(popoverElement).toBeTruthy();

		const slaNamePopoverText = getByText('SLA Test:');

		expect(slaNamePopoverText).toBeTruthy();

		const slaDateTimeRemaingTime = getByText(
			'Apr 16, 12:44 PM (1d 03h 46min overdue)'
		);

		expect(slaDateTimeRemaingTime).toBeTruthy();

		fireEvent.mouseOut(dateText);

		popoverElement = baseElement.querySelector('.popover');

		expect(popoverElement).toBeNull();
	});

	test('Be rendered with due date when the year is not the current year', () => {
		const slaResult = {
			dateOverdue: '2020-04-16T12:44:25Z',
			status: 'RUNNING',
		};

		const {getByText} = render(
			<Table.Item
				{...instance}
				slaResults={[slaResult]}
				slaStatus="Overdue"
			/>,
			{
				wrapper: ContainerMock,
			}
		);

		const dateText = getByText('Apr 16, 2020');

		expect(dateText).toBeTruthy();
	});

	test('Be rendered with remaining time when the SLA is less than a minute.', () => {
		const slaResult = {
			dateOverdue: '2021-04-16T12:44:25Z',
			name: 'SLA Test',
			onTime: true,
			remainingTime: 10000,
			status: 'RUNNING',
		};

		const {baseElement, getByText} = render(
			<Table.Item
				{...instance}
				slaResults={[slaResult]}
				slaStatus="OnTime"
			/>,
			{
				wrapper: ContainerMock,
			}
		);

		const dateText = getByText('Apr 16');

		expect(dateText).toBeTruthy();

		fireEvent.mouseOver(dateText);

		act(() => {
			jest.advanceTimersByTime(1001);
		});

		const popoverElement = baseElement.querySelector('.popover');

		expect(popoverElement).toBeTruthy();

		const slaDateTimeRemaingTime = getByText(
			'Apr 16, 12:44 PM (10sec left)'
		);

		expect(slaDateTimeRemaingTime).toBeTruthy();
	});

	test('Be rendered with due date when the slaResults is empty', () => {
		const {container} = render(
			<Table.Item {...instance} slaResults={[]} slaStatus="OnTime" />,
			{
				wrapper: ContainerMock,
			}
		);

		const dueDateCol = container.querySelector('.due-date.text-info');

		expect(dueDateCol).toBeTruthy();
		expect(dueDateCol.innerHTML).toEqual('-');
	});

	test('Call setInstanceId with "1" as instance id param', () => {
		instance.status = 'Completed';

		const {container} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const instanceIdLink = container.querySelector('.link-text');

		fireEvent.click(instanceIdLink);

		expect(setInstanceId).toBeCalledWith(1);
	});

	test('set BulkReassign modal visualization by clicking the reassign task button', () => {
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const reassignTaskButton = getByText('reassign-task');

		fireEvent.click(reassignTaskButton);

		expect(openModal).toHaveBeenCalled();
	});

	test('set BulkTransition modal visualization by clicking the reassign task button', () => {
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const reassignTaskButton = getByText('Transition');

		fireEvent.click(reassignTaskButton);

		expect(openModal).toHaveBeenCalled();
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

		expect(openModal).toHaveBeenCalled();
	});

	test('set SingleUpdateDueDate modal visualization by clicking the reassign task button', () => {
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const reassignTaskButton = getByText('update-due-date');

		fireEvent.click(reassignTaskButton);

		expect(openModal).toHaveBeenCalled();
	});

	test('set SingleUpdateDueDate modal visualization by clicking the reassign task button', () => {
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const reassignTaskButton = getByText('update-due-date');

		fireEvent.click(reassignTaskButton);

		expect(openModal).toHaveBeenCalled();
	});

	test('set SingleUpdateDueDate modal visualization by clicking the reassign task button', () => {
		const {getByText} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const reassignTaskButton = getByText('Approve');

		fireEvent.click(reassignTaskButton);

		expect(openModal).toHaveBeenCalled();
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
		const {container} = render(<Table.Item {...instance} />, {
			wrapper: ContainerMock,
		});

		const instanceCheckbox = container.querySelector(
			'input.custom-control-input'
		);

		expect(instanceCheckbox.checked).toEqual(true);

		fireEvent.click(instanceCheckbox);
		expect(instanceCheckbox.checked).toEqual(false);

		fireEvent.click(instanceCheckbox);
		expect(instanceCheckbox.checked).toEqual(true);
	});
});
