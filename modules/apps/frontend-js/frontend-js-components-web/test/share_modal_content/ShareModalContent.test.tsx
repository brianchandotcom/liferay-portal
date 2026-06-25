/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {act, fireEvent, render, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ShareModalContent from '../../src/main/resources/META-INF/resources/share_modal_content/ShareModalContent';
import {PermissionOption} from '../../src/main/resources/META-INF/resources/share_modal_content/types';

jest.useFakeTimers();

jest.mock(
	'../../src/main/resources/META-INF/resources/toast/openToast',
	() => ({
		__esModule: true,
		default: jest.fn(),
	})
);

jest.mock('frontend-js-web', () => ({
	dateUtils: {
		getFirstDayOfWeek: jest.fn(() => 0),
	},
	fetch: (...args: any[]) => (global.fetch as any)(...args),
	sub: jest.fn((str) => str),
}));

const mockCloseModal = jest.fn();
const mockOnCollaboratorsUpdate = jest.fn(() => Promise.resolve({error: null}));

const DEFAULT_PERMISSION_OPTIONS: PermissionOption[] = [
	{label: 'view-and-download', value: 'VIEW'},
	{label: 'view-download-and-comment', value: 'ADD_DISCUSSION,VIEW'},
	{
		label: 'view-download-comment-and-update',
		value: 'ADD_DISCUSSION,UPDATE,VIEW',
	},
];

const FOLDER_PERMISSION_OPTIONS: PermissionOption[] = [
	{label: 'view-and-download', value: 'VIEW'},
	{label: 'view-download-and-update', value: 'UPDATE,VIEW'},
];

const DEFAULT_PROPS = {
	autocompleteURL: '/search',
	closeModal: mockCloseModal,
	creator: {
		contentType: 'UserAccount',
		id: '1',
		name: 'Test1 Test1',
	},
	initialCollaborators: [
		{
			actionIds: 'VIEW',
			share: false,
			type: 'User' as const,
			user: {
				id: '2',
				name: 'Test2 Test2',
			},
		},
	],
	onCollaboratorsUpdate: mockOnCollaboratorsUpdate,
	permissionOptions: DEFAULT_PERMISSION_OPTIONS,
	title: 'Test Document',
};

const renderComponent = (
	props: React.ComponentProps<typeof ShareModalContent> = DEFAULT_PROPS
) => {
	return render(<ShareModalContent {...props} />);
};

describe('ShareModalContent', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		window.ResizeObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));

		global.fetch = jest.fn().mockImplementation(() => {
			return Promise.resolve({
				json: () => Promise.resolve({items: []}),
				ok: true,
			});
		});
	});

	it('checks the accessibility of the share content', async () => {
		const {container} = renderComponent();

		await act(async () => {
			checkAccessibility({bestPractices: true, context: container});
		});
	});

	it('renders the modal header and collaborators header', () => {
		const {getByText} = renderComponent();

		expect(getByText('share-x')).toBeInTheDocument();
		expect(getByText('add-people-to-collaborate')).toBeInTheDocument();
		expect(getByText('who-has-access (x-users)')).toBeInTheDocument();
	});

	it('renders the supplied collaborators list title', () => {
		const {getByText, queryByText} = renderComponent({
			...DEFAULT_PROPS,
			collaboratorsListTitle: 'who-can-see-this-view',
		});

		expect(
			getByText('who-can-see-this-view (x-users)')
		).toBeInTheDocument();
		expect(queryByText('who-has-access (x-users)')).not.toBeInTheDocument();
	});

	it('renders a custom autocomplete label without a help icon by default', () => {
		const {container, getByText} = renderComponent({
			...DEFAULT_PROPS,
			autocompleteLabel: 'add-people',
		});

		expect(getByText('add-people')).toBeInTheDocument();
		expect(
			container.querySelector('svg.lexicon-icon-question-circle-full')
		).not.toBeInTheDocument();
	});

	it('renders the expiration date selector by default', () => {
		const {getByLabelText} = renderComponent();

		expect(getByLabelText('set-expiration-date')).toBeInTheDocument();
	});

	it('replaces the more-options dropdown with a remove button when showAllowResharing is false', () => {
		const {container, getByLabelText, queryByLabelText} = renderComponent({
			...DEFAULT_PROPS,
			showAllowResharing: false,
		});

		expect(queryByLabelText('more-options')).not.toBeInTheDocument();

		const removeButton = getByLabelText('remove-access');

		expect(removeButton).toBeInTheDocument();
		expect(
			container.querySelector('svg.lexicon-icon-times-circle')
		).toBeInTheDocument();
	});

	it('hides the expiration date selector when showExpirationDate is false', () => {
		const {queryByLabelText} = renderComponent({
			...DEFAULT_PROPS,
			showExpirationDate: false,
		});

		expect(queryByLabelText('set-expiration-date')).not.toBeInTheDocument();
	});

	it('renders the help icon when autocompleteHelpText is supplied', () => {
		const {container} = renderComponent({
			...DEFAULT_PROPS,
			autocompleteHelpText: 'share-view-autocomplete-help',
		});

		const helpIcon = container.querySelector(
			'svg.lexicon-icon-question-circle-full'
		);

		expect(helpIcon).toBeInTheDocument();
		expect(helpIcon).toHaveAttribute(
			'data-title',
			'share-view-autocomplete-help'
		);
	});

	it('renders the list of users with access', () => {
		const {container} = renderComponent();

		expect(
			container.querySelectorAll<HTMLInputElement>(
				'li.list-group-item .autofit-col-expand'
			)[0]
		).toHaveTextContent('Test2 Test2');

		expect(
			container.querySelectorAll<HTMLInputElement>(
				'li.list-group-item .autofit-col-expand'
			)[1]
		).toHaveTextContent('Test1 Test1');
		expect(
			container.querySelectorAll<HTMLInputElement>(
				'li.list-group-item'
			)[1]
		).toHaveTextContent(/owner/);
	});

	it('renders the cancel and share buttons', () => {
		const {getByText} = renderComponent();

		expect(getByText('cancel')).toBeInTheDocument();

		const shareButton = getByText('share');

		expect(shareButton).toBeInTheDocument();
		expect(shareButton).toBeDisabled();
	});

	it('calls search when the autocomplete input changes', async () => {
		const {container, getByRole} = renderComponent();

		const input = container.querySelector<HTMLInputElement>(
			'input#collaboratorAutocomplete'
		)!;

		await act(async () => {
			fireEvent.change(input, {
				target: {value: 'Test3'},
			});
		});

		await act(async () => {
			jest.advanceTimersByTime(300);
		});

		expect(global.fetch).toHaveBeenCalledWith(
			expect.stringContaining('search?search=Test3'),
			expect.objectContaining({
				method: 'GET',
			}),
			undefined
		);

		await waitFor(() => {
			expect(getByRole('listbox')).toBeInTheDocument();
		});
	});

	it('keeps the typed value in the autocomplete input when a special character is entered', async () => {
		const user = userEvent.setup({
			advanceTimers: jest.advanceTimersByTime,
		});

		const {container} = renderComponent();

		const input = container.querySelector<HTMLInputElement>(
			'input#collaboratorAutocomplete'
		)!;

		await user.type(input, 'Test3,');

		expect(input).toHaveValue('Test3,');
	});

	it('calls submission when share is clicked', async () => {
		const {getByLabelText, getByRole, getByText} = renderComponent();

		fireEvent.click(getByLabelText('more-options'));

		waitFor(() => {
			expect(
				getByRole('menuitem', {name: 'allow-resharing'})
			).toBeInTheDocument();
		});

		await act(async () => {
			fireEvent.click(getByRole('menuitem', {name: 'allow-resharing'}));
		});

		waitFor(() => {
			expect(getByText('share')).toBeEnabled();
		});

		await act(async () => {
			fireEvent.click(getByText('share'));
		});

		expect(mockOnCollaboratorsUpdate).toHaveBeenCalledWith([
			{
				actionIds: 'VIEW',
				share: true,
				type: 'User',
				user: {id: '2', name: 'Test2 Test2'},
			},
		]);

		expect(mockCloseModal).toHaveBeenCalledTimes(1);
	});

	it('renders the supplied permission options', () => {
		const {getByLabelText, getByRole} = renderComponent();

		fireEvent.click(getByLabelText('edit-permissions'));

		expect(
			getByRole('option', {name: 'view-and-download'})
		).toBeInTheDocument();
		expect(
			getByRole('option', {name: 'view-download-and-comment'})
		).toBeInTheDocument();
		expect(
			getByRole('option', {name: 'view-download-comment-and-update'})
		).toBeInTheDocument();
	});

	it('hides the permission selector when only one option is provided', () => {
		const {queryByLabelText} = renderComponent({
			...DEFAULT_PROPS,
			permissionOptions: [{label: 'view', value: 'VIEW'}],
		});

		expect(queryByLabelText('edit-permissions')).not.toBeInTheDocument();
	});

	it('renders the permission as read-only text when the user cannot manage collaborators', () => {
		const {getByText, queryByLabelText} = renderComponent({
			...DEFAULT_PROPS,
			canManageCollaborators: false,
		});

		expect(queryByLabelText('edit-permissions')).not.toBeInTheDocument();
		expect(getByText('view-and-download')).toBeInTheDocument();
	});

	it('renders only the permission options the caller provides', () => {
		const folderProps = {
			...DEFAULT_PROPS,
			permissionOptions: FOLDER_PERMISSION_OPTIONS,
		};

		const {getByLabelText, getByRole, queryByText} =
			renderComponent(folderProps);

		fireEvent.click(getByLabelText('edit-permissions'));

		expect(
			getByRole('option', {name: 'view-and-download'})
		).toBeInTheDocument();
		expect(
			getByRole('option', {name: 'view-download-and-update'})
		).toBeInTheDocument();

		expect(
			queryByText('view-download-and-comment')
		).not.toBeInTheDocument();
	});
});
