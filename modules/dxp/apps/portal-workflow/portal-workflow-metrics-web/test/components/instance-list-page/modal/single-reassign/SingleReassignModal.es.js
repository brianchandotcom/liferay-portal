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

import {render, fireEvent} from '@testing-library/react';
import React, {useState} from 'react';

import {ModalContext} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalContext.es';
import {SingleReassignModal} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/single-reassign/SingleReassignModal.es';
import {InstanceListContext} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/store/InstanceListPageStore.es';
import {MockRouter} from '../../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const ContainerMock = ({children}) => {
	const [singleModal, setSingleModal] = useState({
		selectedItem: {
			assetTitle: 'Blog1',
			assetType: 'Blogs Entry',
			assigneeUsers: [{id: 2, name: 'Test Test'}],
			id: 1,
			status: 'In Progress',
			taskNames: ['Review']
		},
		visible: true
	});

	return (
		<InstanceListContext.Provider value={{setInstanceId: jest.fn()}}>
			<ModalContext.Provider value={{setSingleModal, singleModal}}>
				{children}
			</ModalContext.Provider>
		</InstanceListContext.Provider>
	);
};

describe('The SingleReassignModal component should', () => {
	let getByTestId;

	const items = [
		{
			id: 1,
			name: '0test test0'
		}
	];

	const clientMock = {
		get: jest
			.fn()
			.mockRejectedValueOnce(new Error('Request failed'))
			.mockResolvedValueOnce({
				data: {
					items: [
						{assigneePerson: {id: 2, name: 'Test Test'}, id: 1}
					],
					totalCount: items.length
				}
			})
			.mockResolvedValueOnce({data: {items}}),
		post: jest
			.fn()
			.mockRejectedValueOnce(new Error('Request failed'))
			.mockResolvedValue({data: {items: []}})
	};

	beforeAll(() => {
		const renderResult = render(
			<MockRouter getClient={jest.fn(() => clientMock)}>
				<SingleReassignModal></SingleReassignModal>
			</MockRouter>,
			{
				wrapper: ContainerMock
			}
		);
		getByTestId = renderResult.getByTestId;

		jest.runAllTimers();
	});

	test('Render modal with error message and retry', () => {
		const alertError = getByTestId('alertError');
		const emptyState = getByTestId('emptyState');
		const retryBtn = getByTestId('retryButton');

		expect(alertError).toHaveTextContent(
			'your-connection-was-unexpectedly-lost'
		);
		expect(retryBtn).toHaveTextContent('retry');
		expect(emptyState.children[0]).toHaveTextContent(
			'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
		);

		fireEvent.click(retryBtn);
	});

	test('Render modal with items', () => {
		const table = getByTestId('singleReassignModalTable');
		const item = table.children[1].children[0];
		const cancelBtn = getByTestId('cancelButton');
		const reassignBtn = getByTestId('reassignButton');

		expect(cancelBtn).not.toHaveAttribute('disabled');
		expect(reassignBtn).toHaveAttribute('disabled');

		expect(item.children[0]).toHaveTextContent('1');
		expect(item.children[1]).toHaveTextContent('Blogs Entry: Blog1');
		expect(item.children[2]).toHaveTextContent('Review');
		expect(item.children[3]).toHaveTextContent('Test Test');

		const autocompleteInput = getByTestId('autocompleteInput');

		fireEvent.change(autocompleteInput, {target: {value: 'test'}});

		fireEvent.click(getByTestId('dropDownListItem'));

		expect(reassignBtn).not.toHaveAttribute('disabled');

		fireEvent.click(reassignBtn);

		expect(cancelBtn).toHaveAttribute('disabled');
		expect(reassignBtn).toHaveAttribute('disabled');
	});

	test('Render modal reassign error and retry', () => {
		const alertError = getByTestId('alertError');
		const reassignBtn = getByTestId('reassignButton');

		expect(alertError).toHaveTextContent(
			'your-connection-was-unexpectedly-lost'
		);
		expect(reassignBtn).not.toHaveAttribute('disabled');

		fireEvent.click(reassignBtn);
	});

	test('Render alert with success message and close modal', () => {
		const alertSuccess = getByTestId('alertSuccess');
		const alertClose = alertSuccess.children[1];

		expect(alertSuccess).toHaveTextContent('this-task-has-been-reassigned');

		fireEvent.click(alertClose);

		const alertContainer = getByTestId('alertContainer');
		expect(alertContainer.children[0].children.length).toBe(0);
	});
});
