/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import ViewWorkflowTasks from '../../../../src/main/resources/META-INF/resources/js/main_view/home/ViewWorkflowTasks';

jest.mock('frontend-js-web', () => {
	const actual = jest.requireActual('frontend-js-web');

	return {
		...actual,
		createPortletURL: (
			base: string,
			parameters: Record<string, any> = {}
		) => {
			try {
				const url = new URL(base, 'http://localhost');
				Object.entries(parameters).forEach(([k, v]) =>
					url.searchParams.set(k, String(v))
				);

				return {toString: () => url.toString()};
			}
			catch {
				return {toString: () => String(base)};
			}
		},
	};
});

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/services/WorkflowService',
	() => {
		const mockResult = {
			items: [
				{
					assignedDate: new Date().toISOString(),
					auditUser: 'Test User',
					auditUserImageURL: '',
					completed: false,
					dateDue: new Date().toISOString(),
					id: 123,
					name: 'review',
					objectReviewed: {
						assetTitle: 'Mock Asset',
						assetType: 'MockType',
					},
					workflowLogs: [],
				},
			],
			totalCount: 1,
		};

		return {
			__esModule: true,
			getWorkflowTasksAssignedToMe: jest
				.fn()
				.mockResolvedValue(mockResult),
			getWorkflowTasksAssignedToMyRoles: jest
				.fn()
				.mockResolvedValue(mockResult),
		};
	}
);

describe('[CMS Dashboard] Components: ViewWorkflowTasks', () => {
	const originalWindowOpen = window.open;

	beforeAll(() => {
		window.open = jest.fn();
	});

	afterAll(() => {
		window.open = originalWindowOpen;
	});

	const defaultProps = {
		id: 'myWorkflowTasksSection',
		myRolesWorkflowTasksURL: 'http://www.test.com/myRolesWorkflowTasks',
		myWorkflowTasksURL: 'http://www.test.com/myWorkflowTasks',
		objectDefinitions: [],
	};

	it('renders correctly', async () => {
		render(<ViewWorkflowTasks {...defaultProps} />);

		expect(screen.getByText('my-workflow-tasks')).toBeInTheDocument();
		expect(
			await screen.findByRole('button', {name: 'assigned-to-me'})
		).toBeInTheDocument();

		fireEvent.click(
			await screen.findByRole('button', {name: 'assigned-to-me'})
		);

		expect(
			await screen.findByRole('menuitem', {name: 'assigned-to-my-roles'})
		).toBeInTheDocument();

		expect(await screen.findByLabelText('open-x')).toBeInTheDocument();
	});

	it('opens expected link after picking "Assigned to My Roles"', async () => {
		render(<ViewWorkflowTasks {...defaultProps} />);

		fireEvent.click(await screen.findByLabelText('open-x'));

		expect(window.open).toHaveBeenCalledWith(
			defaultProps.myWorkflowTasksURL,
			'_blank'
		);

		fireEvent.click(
			await screen.findByRole('button', {name: 'assigned-to-me'})
		);

		expect(
			await screen.findByRole('menuitem', {name: 'assigned-to-my-roles'})
		).toBeInTheDocument();

		fireEvent.click(
			await screen.findByRole('menuitem', {name: 'assigned-to-my-roles'})
		);

		fireEvent.click(await screen.findByLabelText('open-x'));

		expect(window.open).toHaveBeenCalledWith(
			defaultProps.myRolesWorkflowTasksURL,
			'_blank'
		);
	});
});
