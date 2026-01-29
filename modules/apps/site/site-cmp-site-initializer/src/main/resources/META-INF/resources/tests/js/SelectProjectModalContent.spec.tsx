/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {useResource} from '@clayui/data-provider';
import {fireEvent, render, screen} from '@testing-library/react';
import {navigate} from 'frontend-js-web';
import React from 'react';

import SelectProjectModalContent, {
	Project,
} from '../../js/components/modal/SelectProjectModalContent';

jest.mock('@clayui/data-provider', () => {
	return {
		__esModule: true,
		...((jest.requireActual('@clayui/data-provider') ?? {}) as any),
		useResource: jest.fn(),
	};
});

const mockCloseModal = jest.fn();
const mockNavigate = navigate as jest.Mock;
const mockUseResource = useResource as jest.Mock;

const defaultProps = {
	addProjectURL: 'http://localhost/add-project',
	addTaskURL: 'http://localhost/add-task',
	closeModal: mockCloseModal,
	projectObjectDefinitionId: 'project-id',
};

describe('SelectProjectModalContent', () => {
	beforeAll(() => {
		delete (window as any).location;
		(window as any).location = {
			href: 'http://localhost/redirect-url',
		};
	});

	beforeEach(() => {
		jest.clearAllMocks();

		mockUseResource.mockReturnValue({
			loadMore: jest.fn(),
			resource: {
				items: [
					{
						embedded: {
							id: 123,
							scopeId: 123,
							title: 'project-1',
						},
					},
					{
						embedded: {
							id: 234,
							scopeId: 234,
							title: 'project-2',
						},
					},
				] as Project[],
			},
		});
	});

	afterAll(() => {
		jest.restoreAllMocks();
	});

	describe('rendering', () => {
		it('renders the modal with all elements', () => {
			render(<SelectProjectModalContent {...defaultProps} />);

			expect(screen.getByText('new-task')).toBeInTheDocument();

			expect(screen.getByText('project')).toBeInTheDocument();

			expect(screen.getByText('select-a-project')).toBeInTheDocument();

			expect(screen.getByText('cancel')).toBeInTheDocument();

			expect(screen.getByText('save')).toBeInTheDocument();
		});

		it('fetches projects with correct API parameters', () => {
			render(<SelectProjectModalContent {...defaultProps} />);

			expect(mockUseResource).toHaveBeenCalledWith(
				expect.objectContaining({
					link: expect.stringContaining(
						'/o/search/v1.0/search?emptySearch=true'
					),
				})
			);

			const callArgs = mockUseResource.mock.calls[0][0];

			expect(callArgs.link).toContain(
				`filter=objectDefinitionId eq ${defaultProps.projectObjectDefinitionId}`
			);

			expect(callArgs.link).toContain('nestedFields=embedded');
		});
	});

	describe('when projects exist', () => {
		let picker: HTMLElement;

		beforeEach(() => {
			render(<SelectProjectModalContent {...defaultProps} />);
			picker = screen.getByLabelText('project');
		});

		it('shows project options in the dropdown', () => {
			fireEvent.click(picker);

			expect(screen.getByText('project-1')).toBeInTheDocument();

			expect(screen.getByText('project-2')).toBeInTheDocument();
		});

		it('selects a project from the dropdown', () => {
			fireEvent.click(picker);

			fireEvent.click(screen.getByText('project-1'));

			expect(picker).toHaveTextContent('project-1');
		});

		it('navigates to add task URL with correct parameters when save is clicked', () => {
			fireEvent.click(picker);

			fireEvent.click(screen.getByText('project-1'));

			fireEvent.click(screen.getByText('save'));

			expect(mockNavigate).toHaveBeenCalledTimes(1);

			const navigateArg = mockNavigate.mock.calls[0][0];

			expect(navigateArg).toBeInstanceOf(URL);

			expect(navigateArg.pathname).toBe('/add-task');

			expect(navigateArg.searchParams.get('projectGroupId')).toBe('123');

			expect(navigateArg.searchParams.get('projectId')).toBe('123');

			expect(navigateArg.searchParams.get('redirect')).toBe(
				'http://localhost/redirect-url'
			);
		});
	});

	describe('when no projects exist', () => {
		beforeEach(() => {
			mockUseResource.mockReturnValue({
				loadMore: jest.fn(),
				resource: {
					items: [],
				},
			});
		});

		it('shows "new-project" option in the dropdown', () => {
			render(<SelectProjectModalContent {...defaultProps} />);

			const picker = screen.getByLabelText('project');

			fireEvent.click(picker);

			expect(screen.getByText('no-projects-created')).toBeInTheDocument();

			expect(screen.getByText('new-project')).toBeInTheDocument();
		});

		it('navigates to add project URL when new-project button is clicked', () => {
			render(<SelectProjectModalContent {...defaultProps} />);

			const picker = screen.getByLabelText('project');

			fireEvent.click(picker);

			const newProjectBtn = screen.getByText('new-project');

			fireEvent.click(newProjectBtn);

			expect(mockNavigate).toHaveBeenCalledTimes(1);

			const navigateArg = mockNavigate.mock.calls[0][0];

			expect(navigateArg).toBeInstanceOf(URL);

			expect(navigateArg.pathname).toBe('/add-project');

			expect(navigateArg.searchParams.get('redirect')).toBe(
				'http://localhost/redirect-url'
			);
		});
	});

	describe('modal controls', () => {
		it('calls closeModal when cancel button is clicked', () => {
			render(<SelectProjectModalContent {...defaultProps} />);

			fireEvent.click(screen.getByText('cancel'));

			expect(mockCloseModal).toHaveBeenCalledTimes(1);
		});

		it('does not navigate when save is clicked without project selection', () => {
			render(<SelectProjectModalContent {...defaultProps} />);

			fireEvent.click(screen.getByText('save'));

			expect(mockNavigate).not.toHaveBeenCalled();
		});
	});
});
