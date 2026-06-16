/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import CalendarTaskCard from '../../js/components/props_transformer/views/calendar_view/CalendarTaskCard';
import {ITaskObjectEntry} from '../../js/utils/types';

jest.mock('@liferay/object-dynamic-data-mapping-form-field-type', () => ({
	AssigneeAvatar: ({name, portrait}: {name: string; portrait: string}) => (
		<img alt={name} src={portrait} />
	),
}));

const futureDueDate = '2026-02-10T00:00:00Z';
const mockedSystemDate = '2026-02-05T00:00:00Z';
const pastDueDate = '2026-02-04T00:00:00Z';

function createTask(overrides: Partial<ITaskObjectEntry> = {}) {
	return {
		assignTo: {
			name: 'Jane Doe',
			portrait: 'https://example.com/jane.png',
		},
		dueDate: futureDueDate,
		state: {
			key: 'inProgress',
			name: 'In Progress',
		},
		title: 'Design the landing page',
		...overrides,
	} as ITaskObjectEntry;
}

describe('CalendarTaskCard', () => {
	beforeAll(() => {
		jest.useFakeTimers();

		jest.setSystemTime(new Date(mockedSystemDate));
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('does not render the blocked icon when the task is not blocked', () => {
		const {container} = render(<CalendarTaskCard task={createTask()} />);

		expect(
			container.querySelector('.lexicon-icon-block')
		).not.toBeInTheDocument();
	});

	it('does not render the overdue icon when the state is done', () => {
		const {container} = render(
			<CalendarTaskCard
				task={createTask({
					dueDate: pastDueDate,
					state: {key: 'done', name: 'Done'},
				})}
			/>
		);

		expect(
			container.querySelector('.lexicon-icon-exclamation-full')
		).not.toBeInTheDocument();
	});

	it('renders the assignee avatar', () => {
		const {getByAltText} = render(<CalendarTaskCard task={createTask()} />);

		const avatar = getByAltText('Jane Doe');

		expect(avatar).toBeInTheDocument();
		expect(avatar).toHaveAttribute('src', 'https://example.com/jane.png');
	});

	it('renders the blocked icon when the task is blocked', () => {
		const {container} = render(
			<CalendarTaskCard
				task={createTask({state: {key: 'blocked', name: 'Blocked'}})}
			/>
		);

		expect(
			container.querySelector('.lexicon-icon-block')
		).toBeInTheDocument();
	});

	it('renders the overdue icon when the due date is past and the state is not done', () => {
		const {container} = render(
			<CalendarTaskCard task={createTask({dueDate: pastDueDate})} />
		);

		expect(
			container.querySelector('.lexicon-icon-exclamation-full')
		).toBeInTheDocument();
	});

	it('renders the task title', () => {
		const {getByText} = render(<CalendarTaskCard task={createTask()} />);

		expect(getByText('Design the landing page')).toBeInTheDocument();
	});

	it('shows the overdue icon instead of the blocked icon when a blocked task is also overdue', () => {
		const {container} = render(
			<CalendarTaskCard
				task={createTask({
					dueDate: pastDueDate,
					state: {key: 'blocked', name: 'Blocked'},
				})}
			/>
		);

		expect(
			container.querySelector('.lexicon-icon-exclamation-full')
		).toBeInTheDocument();
		expect(
			container.querySelector('.lexicon-icon-block')
		).not.toBeInTheDocument();
	});
});
