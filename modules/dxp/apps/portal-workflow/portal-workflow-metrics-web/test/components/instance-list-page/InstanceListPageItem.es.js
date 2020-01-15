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
import React from 'react';

import {Table} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageTable.es';
import {ModalContext} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalContext.es';
import {InstanceListContext} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/store/InstanceListPageStore.es';

const instance = {
	assetTitle: 'New Post',
	assetType: 'Blog',
	assigneeUsers: [{id: 20124, name: 'Test Test'}],
	creatorUser: {
		name: 'User 1'
	},
	dateCreated: new Date('2019-01-01'),
	id: 1,
	taskNames: ['Review', 'Update']
};

describe('The instance list item should', () => {
	afterEach(cleanup);

	test('Be rendered with "User 1", "Jan 01, 2019, 12:00 AM", and "Review, Update" columns', () => {
		const {getByTestId} = render(
			<InstanceListContext.Provider value={{setInstanceId: jest.fn()}}>
				<ModalContext.Provider
					value={{setSingleModal: () => {}, singleModal: false}}
				>
					<Table.Item {...instance} />
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		);

		const creatorUserCell = getByTestId('creatorUserCell');
		const dateCreatedCell = getByTestId('dateCreatedCell');
		const taskNamesCell = getByTestId('taskNamesCell');

		expect(creatorUserCell.innerHTML).toBe('User 1');
		expect(dateCreatedCell.innerHTML).toBe('Jan 01, 2019, 12:00 AM');
		expect(taskNamesCell.innerHTML).toBe('Review, Update');
	});

	test('Be rendered with check icon when the slaStatus is "OnTime"', () => {
		const {getByTestId} = render(
			<InstanceListContext.Provider value={{setInstanceId: jest.fn()}}>
				<ModalContext.Provider
					value={{setSingleModal: () => {}, singleModal: false}}
				>
					<Table.Item {...instance} slaStatus="OnTime" />
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		);

		const instanceStatusIcon = getByTestId('icon');

		expect([...instanceStatusIcon.classList]).toContain(
			'lexicon-icon-check-circle'
		);
	});

	test('Be rendered with exclamation icon when the slaStatus is "Overdue"', () => {
		const {getByTestId} = render(
			<InstanceListContext.Provider value={{setInstanceId: jest.fn()}}>
				<ModalContext.Provider
					value={{setSingleModal: () => {}, singleModal: false}}
				>
					<Table.Item {...instance} slaStatus="Overdue" />
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		);

		const instanceStatusIcon = getByTestId('icon');

		expect([...instanceStatusIcon.classList]).toContain(
			'lexicon-icon-exclamation-circle'
		);
	});

	test('Be rendered with hr icon when the slaStatus is "Untracked"', () => {
		const {getByTestId} = render(
			<InstanceListContext.Provider value={{setInstanceId: jest.fn()}}>
				<ModalContext.Provider
					value={{setSingleModal: () => {}, singleModal: false}}
				>
					<Table.Item {...instance} slaStatus="Untracked" />
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		);

		const instanceStatusIcon = getByTestId('icon');

		expect([...instanceStatusIcon.classList]).toContain('lexicon-icon-hr');
	});

	test('Call setInstanceId with "1" as instance id param', () => {
		const contextMock = {setInstanceId: jest.fn()};
		instance.status = 'Completed';

		const {getByTestId} = render(
			<InstanceListContext.Provider value={contextMock}>
				<ModalContext.Provider
					value={{
						setInstanceDetailsModal: () => {},
						setSingleModal: () => {},
						singleModal: false
					}}
				>
					<Table.Item {...instance} />
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		);

		const instanceIdLink = getByTestId('instanceIdLink');

		fireEvent.click(instanceIdLink);

		expect(contextMock.setInstanceId).toBeCalledWith(1);
	});
});

describe('The InstanceListPageItem quick action menu should', () => {
	afterEach(cleanup);

	const instance = {
		assetTitle: 'New Post',
		assetType: 'Blog',
		dateCreated: new Date('2019-01-01'),
		id: 1,
		taskNames: ['Review']
	};

	const setSingleModal = jest.fn();

	test('set modal visualization by clicking the reassign task button', () => {
		const {getByTestId} = render(
			<InstanceListContext.Provider value={{setInstanceId: jest.fn()}}>
				<ModalContext.Provider
					value={{setSingleModal, singleModal: false}}
				>
					<Table.Item {...instance} />
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		);

		const reassignTaskButton = getByTestId('dropDownItem');

		fireEvent.click(reassignTaskButton);
		expect(setSingleModal).toHaveBeenCalledTimes(1);
	});
});

describe('The InstanceListPageItem instance checkbox component should', () => {
	afterEach(cleanup);

	const instance = {
		assetTitle: 'New Post',
		assetType: 'Blog',
		dateCreated: new Date('2019-01-01'),
		id: 1,
		taskNames: ['Review']
	};

	let selectedItems = [];
	const setSelectedItems = jest.fn(value => {
		selectedItems = value;
	});

	test('Set checkbox value by clicking it', () => {
		const {getByTestId} = render(
			<InstanceListContext.Provider
				value={{
					selectedItems,
					setInstanceId: jest.fn(),
					setSelectedItems
				}}
			>
				<ModalContext.Provider
					value={{setSingleModal: () => {}, singleModal: false}}
				>
					<Table.Item {...instance} />
				</ModalContext.Provider>
			</InstanceListContext.Provider>
		);

		const instanceCheckbox = getByTestId('instanceCheckbox');

		expect(instanceCheckbox.checked).toEqual(false);

		fireEvent.click(instanceCheckbox);
		expect(instanceCheckbox.checked).toEqual(true);

		fireEvent.click(instanceCheckbox);
		expect(instanceCheckbox.checked).toEqual(false);
	});
});
