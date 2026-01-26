/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import StateSelector from '../../js/components/StateSelector';

const mockStates = [
	{key: 'notStarted', name: 'Not Started', nextStates: ['inProgress']},
	{key: 'inProgress', name: 'In Progress', nextStates: ['notStarted']},
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

		expect(getByText('Not Started')).toBeInTheDocument();
	});

	it('calls onChange when selection changes', () => {
		const {getByText} = render(
			<StateSelector
				initialSelectedKey="notStarted"
				onChange={mockOnChange}
				states={mockStates}
			/>
		);

		const trigger = getByText('Not Started');

		fireEvent.click(trigger);

		const option = getByText('In Progress');

		fireEvent.click(option);

		expect(mockOnChange).toHaveBeenCalled();
	});
});
