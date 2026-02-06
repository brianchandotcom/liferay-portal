/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import StateSelector from '../../js/components/StateSelector';

const mockStates = [
	{key: 'blocked', name: 'Blocked', nextStates: ['done', 'inProgress']},
	{key: 'done', name: 'Done', nextStates: ['inProgress']},
	{key: 'inProgress', name: 'In Progress', nextStates: ['blocked', 'done']},
	{
		key: 'notStarted',
		name: 'Not Started',
		nextStates: ['blocked', 'inProgress'],
	},
];

const mockOnChange = jest.fn();

describe('StateSelector', () => {
	it('renders with initial state', () => {
		const {getByText} = render(
			<StateSelector
				initialSelectedKey="notStarted"
				onChange={mockOnChange}
				states={mockStates}
			/>
		);

		expect(getByText('not-started')).toBeInTheDocument();
	});

	it('calls onChange when selection changes', () => {
		const {getByText} = render(
			<StateSelector
				initialSelectedKey="notStarted"
				onChange={mockOnChange}
				states={mockStates}
			/>
		);

		const trigger = getByText('not-started');

		fireEvent.click(trigger);

		const option = getByText('In Progress');

		fireEvent.click(option);

		expect(mockOnChange).toHaveBeenCalled();
	});

	it('does not update next states when the initialSelectedKey is provided', async () => {
		const {getAllByRole, getByRole, getByText} = render(
			<StateSelector
				initialSelectedKey="notStarted"
				onChange={mockOnChange}
				states={mockStates}
			/>
		);

		fireEvent.click(getByText('not-started'));

		let options = getAllByRole('option').map(
			(option) => option.textContent
		);

		expect(options.length).toBe(3);
		expect(options[0]).toBe('Not Started');
		expect(options[1]).toBe('In Progress');
		expect(options[2]).toBe('Blocked');

		fireEvent.click(getByRole('option', {name: 'In Progress'}));
		fireEvent.click(getByText('in-progress'));

		options = getAllByRole('option').map((option) => option.textContent);

		expect(options.length).toBe(3);
		expect(options[0]).toBe('Not Started');
		expect(options[1]).toBe('In Progress');
		expect(options[2]).toBe('Blocked');
	});

	it('updates next states in the specific display order when the selectedKey is provided', async () => {
		const ControlledStateSelector = () => {
			const [selectedKey, setSelectedKey] = React.useState('notStarted');

			return (
				<StateSelector
					onChange={setSelectedKey}
					selectedKey={selectedKey}
					states={mockStates}
				/>
			);
		};

		const {getAllByRole, getByRole, getByText} = render(
			<ControlledStateSelector />
		);

		fireEvent.click(getByText('not-started'));

		let options = getAllByRole('option').map(
			(option) => option.textContent
		);

		expect(options.length).toBe(3);
		expect(options[0]).toBe('Not Started');
		expect(options[1]).toBe('In Progress');
		expect(options[2]).toBe('Blocked');

		fireEvent.click(getByRole('option', {name: 'In Progress'}));
		fireEvent.click(getByText('in-progress'));

		options = getAllByRole('option').map((option) => option.textContent);

		expect(options.length).toBe(3);
		expect(options[0]).toBe('In Progress');
		expect(options[1]).toBe('Blocked');
		expect(options[2]).toBe('Done');

		fireEvent.click(getByRole('option', {name: 'Blocked'}));
		fireEvent.click(getByText('blocked'));

		options = getAllByRole('option').map((option) => option.textContent);

		expect(options.length).toBe(3);
		expect(options[0]).toBe('In Progress');
		expect(options[1]).toBe('Blocked');
		expect(options[2]).toBe('Done');

		fireEvent.click(getByRole('option', {name: 'Done'}));
		fireEvent.click(getByText('done'));

		options = getAllByRole('option').map((option) => option.textContent);

		expect(options.length).toBe(2);
		expect(options[0]).toBe('In Progress');
		expect(options[1]).toBe('Done');
	});
});
