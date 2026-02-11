/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, waitFor} from '@testing-library/react';
import {openModal} from 'frontend-js-components-web';
import React from 'react';

import Task from '../../js/components/props_transformer/views/kanban_view/components/Task';
import {KanbanViewContext} from '../../js/components/props_transformer/views/kanban_view/context';
import {mockNavigate} from '../../tests/js/__mocks__/frontend-js-web';

const mockDisplayAssignSuccessToast = jest.fn();
const mockDisplayErrorToast = jest.fn();
const mockGetUserAccount = jest.fn();
const mockLoadData = jest.fn();
const mockPatchTaskById = jest.fn();

jest.mock('@liferay/site-cms-site-initializer', () => ({
	displayErrorToast: (...args: any[]) => mockDisplayErrorToast(...args),
}));

jest.mock('frontend-js-components-web', () => ({
	openModal: jest.fn(),
	openToast: jest.fn(),
}));

jest.mock('../../js/utils/api', () => ({
	getUserAccount: (...args: any[]) => mockGetUserAccount(...args),
	patchTaskById: (...args: any[]) => mockPatchTaskById(...args),
}));

jest.mock('../../js/utils/toastUtil', () => ({
	displayAssignSuccessToast: (...args: any[]) =>
		mockDisplayAssignSuccessToast(...args),
}));

describe('Kanban Task actions', () => {
	const baseProps = {
		embedded: {
			assignTo: {name: 'Alice', portrait: 'p.jpg'},
			cmpProjectToCMPTasks: {title: 'Project A'},
			id: 42,
			state: {key: 'in-progress', name: 'In Progress'},
			title: 'Task title',
		},
	} as any;

	const renderTask = (itemsActions = []) =>
		render(
			<KanbanViewContext.Provider
				value={{
					boardData: {},
					changeTaskStatus: () => {},
					itemsActions,
					loadData: mockLoadData,
				}}
			>
				<Task {...baseProps} />
			</KanbanViewContext.Provider>
		);

	it('assigns to current user when assign-to-me selected', async () => {
		mockGetUserAccount.mockResolvedValue({
			externalReferenceCode: 'u1',
			name: 'Current User',
		});
		mockPatchTaskById.mockResolvedValue({error: null});

		const {getByText} = renderTask();

		fireEvent.click(getByText('assign-to-me'));

		await waitFor(() => expect(mockGetUserAccount).toHaveBeenCalled());

		await waitFor(() => expect(mockPatchTaskById).toHaveBeenCalled());

		await waitFor(() => {
			expect(mockLoadData).toHaveBeenCalled();
			expect(mockDisplayAssignSuccessToast).toHaveBeenCalledWith(
				'Task title',
				'Current User'
			);
		});
	});

	it('call openModal modal when assign-to-... is clicked', async () => {
		const {getByText} = renderTask();

		fireEvent.click(getByText('assign-to-...'));

		expect(openModal).toHaveBeenCalledTimes(1);
	});

	it('navigates when edit/view actions are clicked', async () => {
		const itemsActions = [
			{data: {id: 'edit'}, href: '/edit/{embedded.id}'},
			{data: {id: 'actionLink'}, href: '/view/{embedded.id}'},
		];

		const {getByText} = renderTask(itemsActions as any);

		const editButton = getByText('edit');

		fireEvent.click(editButton);

		await waitFor(() => {
			expect(mockNavigate).toHaveBeenCalledWith('/edit/42');
		});

		const viewButton = getByText('view');

		fireEvent.click(viewButton);

		await waitFor(() => {
			expect(mockNavigate).toHaveBeenCalledWith('/view/42');
		});
	});

	it('shows error toast when assign-to-me fails', async () => {
		mockGetUserAccount.mockResolvedValue({
			externalReferenceCode: 'u1',
			name: 'Current User',
		});
		mockPatchTaskById.mockResolvedValue({error: 'error'});

		const {getByText} = renderTask();

		fireEvent.click(getByText('assign-to-me'));

		await waitFor(() => expect(mockPatchTaskById).toHaveBeenCalled());

		expect(mockDisplayErrorToast).toHaveBeenCalledWith('error');
	});
});
