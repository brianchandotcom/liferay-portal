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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import InstanceListPage from '../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPage.es';
import {
	SingleReassignModalContext,
	SingleReassignModal
} from '../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/single-reassign/SingleReassignModal.es';
import {InstanceListContext} from '../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/store/InstanceListPageStore.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The SingleReassignModal component should', () => {
	afterEach(() => cleanup);

	const clientMock = {
		get: jest.fn().mockResolvedValue({data: {items: []}})
	};

	const setShowModal = jest.fn();
	const showModal = {
		selectedItem: {
			assetTitle: 'Blog2',
			assetType: 'Blogs Entry',
			assigneeUsers: [{id: 20124, name: 'Test Test'}],
			creatorUser: {id: 20124, name: 'Test Test'},
			dateCreated: '2019-12-10T17:44:44Z',
			id: 40330,
			slaStatus: 'Overdue',
			status: 'Completed',
			taskNames: ['Update']
		},
		visible: true
	};

	test('Render body and header with no items', () => {
		const {getByTestId} = render(
			<MockRouter clientHeadless={clientMock}>
				<InstanceListContext.Provider
					value={{setInstanceId: jest.fn()}}
				>
					<SingleReassignModalContext.Provider
						value={{setShowModal, showModal}}
					>
						<InstanceListPage.SingleReassignModal />
					</SingleReassignModalContext.Provider>
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const reassignModal = getByTestId('reassignModal');

		expect(reassignModal.innerHTML).not.toEqual(undefined);
	});

	test('Render LoadingView component', () => {
		const {getAllByTestId} = render(
			<SingleReassignModal.LoadingView></SingleReassignModal.LoadingView>
		);

		const loadingState = getAllByTestId('loadingState');

		expect(loadingState[0].innerHTML).not.toEqual(undefined);
	});

	test('Render ErrorView component', () => {
		const {getByTestId} = render(
			<SingleReassignModal.ErrorView></SingleReassignModal.ErrorView>
		);

		const emptyState = getByTestId('emptyState');

		expect(emptyState.innerHTML).toContain(
			'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
		);
	});

	test('Render body and header with items', () => {
		const clientMock = {
			get: jest.fn().mockResolvedValue({data: {items: [{id: 123}]}})
		};

		const {getAllByTestId} = render(
			<MockRouter clientHeadless={clientMock}>
				<InstanceListContext.Provider
					value={{setInstanceId: jest.fn()}}
				>
					<SingleReassignModalContext.Provider
						value={{setShowModal, showModal}}
					>
						<InstanceListPage.SingleReassignModal />
					</SingleReassignModalContext.Provider>
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const reassignModal = getAllByTestId('reassignModal');

		expect(reassignModal[0].innerHTML).not.toEqual(undefined);
	});

	test('Render body and header with no selected item', () => {
		const clientMock = {
			get: jest.fn().mockResolvedValue({data: {items: [{id: 123}]}})
		};
		const showModal = {};

		const {getAllByTestId} = render(
			<MockRouter clientHeadless={clientMock}>
				<InstanceListContext.Provider
					value={{setInstanceId: jest.fn()}}
				>
					<SingleReassignModalContext.Provider
						value={{setShowModal, showModal}}
					>
						<InstanceListPage.SingleReassignModal></InstanceListPage.SingleReassignModal>
					</SingleReassignModalContext.Provider>
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const reassignModal = getAllByTestId('reassignModal');

		expect(reassignModal[0].innerHTML).not.toEqual(undefined);
	});

	// test('Fire reassign button event when clicked', async () => {
	// 	const clientMock = {
	// 		get: jest.fn().mockResolvedValue({data: {items: [{id: 123}]}})
	// 	};
	// 	const showModal = {};

	// 	const {getByTestId} = await render(
	// 		<MockRouter clientHeadless={clientMock}>
	// 			<InstanceListContext.Provider
	// 				value={{setInstanceId: jest.fn()}}
	// 			>
	// 				<SingleReassignModalContext.Provider
	// 					value={{setShowModal, showModal}}
	// 				>
	// 					<InstanceListPage.SingleReassignModal></InstanceListPage.SingleReassignModal>
	// 				</SingleReassignModalContext.Provider>
	// 			</InstanceListContext.Provider>
	// 		</MockRouter>
	// 	);

	// 	const reassignButton = getByTestId('reassignButton');
	// 	fireEvent.click(reassignButton);
	// });
});
