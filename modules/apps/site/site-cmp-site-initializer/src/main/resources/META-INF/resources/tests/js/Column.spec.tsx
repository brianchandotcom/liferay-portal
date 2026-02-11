/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, waitFor} from '@testing-library/react';
import React from 'react';

import Column from '../../js/components/props_transformer/views/kanban_view/components/Column';
import {KanbanViewContext} from '../../js/components/props_transformer/views/kanban_view/context';

const mockOpenCMPModal = jest.fn();

jest.mock('../../js/utils/openCMPModal', () => ({
	openCMPModal: (...args: any[]) => mockOpenCMPModal(...args),
}));

jest.mock(
	'../../js/components/props_transformer/views/kanban_view/components/Task',
	() => () => <div data-testid="task" />
);

describe('Kanban Column', () => {
	it('opens create modal when click on add task button', async () => {
		const column = {
			displayType: 'info',
			icon: {color: '#fff', name: 'star'},
			key: 'in-progress',
			name: 'In Progress',
			tasks: [{embedded: {id: 1}}, {embedded: {id: 2}}],
		} as any;

		const {getByRole} = render(
			<KanbanViewContext.Provider
				value={{
					boardData: {},
					changeTaskStatus: () => {},
					itemsActions: [],
					loadData: () => {},
				}}
			>
				<Column column={column} />
			</KanbanViewContext.Provider>
		);

		const addButton = getByRole('button', {name: 'add-task'});

		fireEvent.click(addButton);

		await waitFor(() => expect(mockOpenCMPModal).toHaveBeenCalled());
	});

	it('renders header, icon when present, task count and opens create modal', async () => {
		const column = {
			displayType: 'info',
			icon: {color: '#fff', name: 'star'},
			key: 'in-progress',
			name: 'In Progress',
			tasks: [{embedded: {id: 1}}, {embedded: {id: 2}}],
		} as any;

		const {container, getAllByTestId, getByText} = render(
			<KanbanViewContext.Provider
				value={{
					boardData: {},
					changeTaskStatus: () => {},
					itemsActions: [],
					loadData: () => {},
				}}
			>
				<Column column={column} />
			</KanbanViewContext.Provider>
		);

		expect(getByText('In Progress')).toBeInTheDocument();
		expect(getByText('2')).toBeInTheDocument();

		expect(
			container.querySelector('.lexicon-icon-star')
		).toBeInTheDocument();

		expect(getAllByTestId('task').length).toBe(2);
	});
});
