/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import fetch from 'jest-fetch-mock';

import '@testing-library/jest-dom/extend-expect';
import {useModal} from '@clayui/modal';
import {act, cleanup, render, screen, waitFor} from '@testing-library/react';
import React from 'react';
import ReactDOM from 'react-dom';

import {TEmptyState} from '../../table/StateRenderer';
import Modal from '../Modal';
import {EPeople} from '../People';

const responseWithData = {
	actions: {},
	facets: [],
	items: [
		{
			id: 44275,
			name: 'test',
			selected: false,
		},
		{
			id: 44279,
			name: 'test2',
			selected: false,
		},
	],
	lastPage: 1,
	page: 1,
	pageSize: 20,
	totalCount: 7,
};

const responseWithEmptyState = {
	actions: {},
	facets: [],
	items: [],
	lastPage: 1,
	page: 1,
	pageSize: 20,
	totalCount: 0,
};

const ComponentWithData = () => {
	const {observer} = useModal({onClose: () => {}});

	const emptyState: TEmptyState = {
		contentRenderer: () => <></>,
		description: 'Empty State Description',
		noResultsTitle: 'Empty State No Results Title',
		title: 'Empty State Title',
	};

	return (
		<Modal
			columns={[
				{
					expanded: true,
					id: 'name',
					label: Liferay.Language.get('user-groups'),
				},
			]}
			emptyState={emptyState}
			name={EPeople.UserGroupIds}
			observer={observer}
			onCloseModal={() => {}}
			requestFn={async () => responseWithData}
			syncAllAccounts
			syncAllContacts
			syncedIds={{
				syncedAccountGroupIds: ['aaa'],
				syncedOrganizationIds: ['bbb'],
				syncedUserGroupIds: ['ccc'],
			}}
			title="Assign Modal Title"
		/>
	);
};

const ComponentWithEmptyState = () => {
	const {observer} = useModal({onClose: () => {}});

	const emptyState: TEmptyState = {
		contentRenderer: () => <></>,
		description: 'Empty State Description',
		noResultsTitle: 'Empty State No Results Title',
		title: 'Empty State Title',
	};

	return (
		<Modal
			columns={[]}
			emptyState={emptyState}
			name={EPeople.UserGroupIds}
			observer={observer}
			onCloseModal={() => {}}
			requestFn={async () => responseWithEmptyState}
			syncAllAccounts
			syncAllContacts
			syncedIds={{
				syncedAccountGroupIds: ['aaa'],
				syncedOrganizationIds: ['bbb'],
				syncedUserGroupIds: ['ccc'],
			}}
			title="Assign Modal Title"
		/>
	);
};

describe('People Modal', () => {
	beforeAll(() => {
		// @ts-ignore

		ReactDOM.createPortal = jest.fn((element) => {
			return element;
		});

		jest.useFakeTimers();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	afterEach(() => {
		jest.clearAllTimers();
		jest.restoreAllMocks();
		cleanup();
	});

	it('renders component with data without crashing it', async () => {
		fetch.mockResponse(JSON.stringify(responseWithData));

		await act(async () => {
			render(<ComponentWithData />);

			jest.runAllTimers();

			await waitFor(() => screen.getByText('test'));

			await waitFor(() => screen.getByText('test2'));
		});

		const modalContent = document.querySelector('.modal-content');

		const tableColumnText = screen.getByText('test');

		const tableColumnText2 = screen.getByText('test2');

		expect(modalContent).toBeInTheDocument();

		expect(tableColumnText).toBeInTheDocument();

		expect(tableColumnText2).toBeInTheDocument();
	});

	it('renders component with Empty State without crashing it', async () => {
		fetch.mockResponse(JSON.stringify(responseWithEmptyState));

		await act(async () => {
			render(<ComponentWithEmptyState />);
			jest.runAllTimers();
		});

		const assignModalTitle = screen.getByText('Assign Modal Title');

		const emptyStateTitle = screen.getByText('Empty State Title');

		const emptyStateDescription = screen.getByText(
			'Empty State Description'
		);

		expect(assignModalTitle).toBeInTheDocument();

		expect(emptyStateTitle).toBeInTheDocument();

		expect(emptyStateDescription).toBeInTheDocument();
	});
});
