/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import StateLabel from '../../js/components/StateLabel';

const dueDate = '2026-02-04T00:00:00Z';
const mockedSystemDate = '2026-02-05T00:00:00Z';

describe('StateLabel', () => {
	beforeAll(() => {
		jest.useFakeTimers();

		jest.setSystemTime(new Date(mockedSystemDate));
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('renders the name text', () => {
		const state = {
			key: 'inProgress',
			name: 'In Progress',
		};

		const {getByText} = render(<StateLabel state={state} />);

		expect(getByText(state.name)).toBeInTheDocument();
	});

	it('renders nothing if no state is provided', () => {
		const {container} = render(<StateLabel />);

		expect(container).toBeEmptyDOMElement();
	});

	it('renders the overdue label when the due date has passed', () => {
		const state = {
			key: 'inProgress',
			name: 'In Progress',
		};

		const {getByText} = render(
			<StateLabel dueDate={dueDate} state={state} />
		);

		expect(getByText(state.name)).toBeInTheDocument();
		expect(getByText('overdue')).toBeInTheDocument();
	});

	it('does not render the overdue label if the state is "done"', () => {
		const state = {
			key: 'done',
			name: 'Done',
		};

		const {getByText, queryByText} = render(
			<StateLabel dueDate={dueDate} state={state} />
		);

		expect(getByText(state.name)).toBeInTheDocument();
		expect(queryByText('overdue')).not.toBeInTheDocument();
	});

	it('does not render the overdue label if no due date is provided', () => {
		const state = {
			key: 'inProgress',
			name: 'In Progress',
		};

		const {getByText, queryByText} = render(<StateLabel state={state} />);

		expect(getByText(state.name)).toBeInTheDocument();
		expect(queryByText('overdue')).not.toBeInTheDocument();
	});
});
