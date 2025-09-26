/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {fetch} from 'frontend-js-web';
import React from 'react';

import {
	SelectOptions,
	SpaceMembersInputWithSelect,
} from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceMembersInputWithSelect';

jest.mock('frontend-js-web');

const mockFetch = fetch as jest.Mock<Promise<Response>>;

describe('SpaceMembersInputWithSelect', () => {
	const {ResizeObserver: ResizeObserverOriginal} = window;

	const mockUserApiResponse = {
		items: [
			{
				emailAddress: 'john.doe@example.com',
				externalReferenceCode: 'john.doe',
				id: '1',
				image: '/image/user_portrait',
				imageId: 'john.doe.image',
				name: 'John Doe',
			},
			{
				emailAddress: 'jane.smith@example.com',
				externalReferenceCode: 'jane.smith',
				id: '2',
				image: '/image/user_portrait',
				imageId: 'jane.smith.image',
				name: 'Jane Smith',
			},
		],
	};

	const mockGroupApiResponse = {
		items: [
			{
				externalReferenceCode: 'group.1',
				id: '1',
				name: 'Group 1',
				roles: [],
				usersCount: 5,
			},
			{
				externalReferenceCode: 'group.2',
				id: '2',
				name: 'Group 2',
				roles: [],
				usersCount: 10,
			},
		],
	};

	beforeAll(() => {
		window.ResizeObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));
	});

	afterEach(() => {
		jest.clearAllMocks();
		mockFetch.mockClear();
	});

	afterAll(() => {
		window.ResizeObserver = ResizeObserverOriginal;
		jest.restoreAllMocks();
		mockFetch.mockReset();
	});

	it('accepts a custom className', () => {
		const customClass = 'custom-class';

		const {container} = render(
			<SpaceMembersInputWithSelect
				className={customClass}
				disabled={false}
			/>
		);

		expect(container.getElementsByClassName(customClass)).toHaveLength(1);
	});

	it('renders with initial value for select', () => {
		const selectValue = SelectOptions.USERS;

		render(
			<SpaceMembersInputWithSelect
				disabled={false}
				selectValue={selectValue}
			/>
		);

		const typeSelect = screen.getByRole('combobox', {
			name: 'add-people-to-collaborate',
		});
		expect(typeSelect).toBeInTheDocument();
		expect(typeSelect).toHaveValue(selectValue);
	});

	it('calls "onSelectChange" callback when changing value for input', async () => {
		const onSelectChange = jest.fn();

		render(
			<SpaceMembersInputWithSelect
				disabled={false}
				onSelectChange={onSelectChange}
			/>
		);

		expect(onSelectChange).not.toHaveBeenCalled();

		await userEvent.selectOptions(
			screen.getByRole('combobox', {name: 'add-people-to-collaborate'}),
			SelectOptions.GROUPS
		);

		expect(onSelectChange).toHaveBeenCalledTimes(1);
		expect(onSelectChange).toHaveBeenCalledWith(SelectOptions.GROUPS);
	});

	it('displays a list of users when the select value is "users"', async () => {
		mockFetch.mockResolvedValue({
			headers: new Headers([['Content-Type', 'application/json']]),
			json: async () => mockUserApiResponse,
		} as Response);

		render(
			<SpaceMembersInputWithSelect
				disabled={false}
				selectValue={SelectOptions.USERS}
			/>
		);

		await userEvent.click(
			screen.getByPlaceholderText('enter-name-or-email')
		);

		await waitFor(() => {
			expect(screen.getAllByRole('option')).toHaveLength(2);
		});

		expect(
			screen.getByRole('option', {name: /John Doe \(john.doe\)/})
		).toBeInTheDocument();

		expect(
			screen.getByRole('option', {name: /Jane Smith \(jane.smith\)/})
		).toBeInTheDocument();
	});

	it('displays a list of groups when the select value is "groups"', async () => {
		mockFetch.mockResolvedValue({
			headers: new Headers([['Content-Type', 'application/json']]),
			json: async () => mockGroupApiResponse,
		} as Response);

		render(
			<SpaceMembersInputWithSelect
				disabled={false}
				selectValue={SelectOptions.GROUPS}
			/>
		);

		await userEvent.click(
			screen.getByPlaceholderText('enter-name-or-email')
		);

		await waitFor(() => {
			expect(screen.getAllByRole('option')).toHaveLength(2);
		});

		const group1 = screen.getByRole('option', {name: /Group 1/});
		expect(group1).toBeInTheDocument();
		expect(group1).toHaveTextContent('(5-members)');

		const group2 = screen.getByRole('option', {name: /Group 2/});
		expect(group2).toBeInTheDocument();
		expect(group2).toHaveTextContent('(10-members)');
	});

	it('displays a group with 0 members if usersCount is not provided', async () => {
		mockFetch.mockResolvedValue({
			headers: new Headers([['Content-Type', 'application/json']]),
			json: async () => ({
				items: [{id: '1', name: 'Group 1'}],
			}),
		} as Response);

		render(
			<SpaceMembersInputWithSelect
				disabled={false}
				selectValue={SelectOptions.GROUPS}
			/>
		);

		await userEvent.click(
			screen.getByPlaceholderText('enter-name-or-email')
		);

		await waitFor(() => {
			screen.getByRole('option', {name: /Group 1/});
		});

		const group1 = screen.getByRole('option', {name: /Group 1/});
		expect(group1).toBeInTheDocument();
		expect(group1).toHaveTextContent('(0-members)');
	});

	it('displays "no results found" message when search returns no items', async () => {
		mockFetch.mockResolvedValue({
			headers: new Headers([['Content-Type', 'application/json']]),
			json: async () => ({items: []}),
		} as Response);

		render(
			<SpaceMembersInputWithSelect
				disabled={false}
				selectValue={SelectOptions.USERS}
			/>
		);

		const input = screen.getByPlaceholderText('enter-name-or-email');

		await userEvent.type(input, 'non-existent');

		await waitFor(() => {
			expect(screen.getByText('no-results-found')).toBeInTheDocument();
		});
	});

	it('calls "onAutocompleteItemSelected" callback when a user is selected', async () => {
		mockFetch.mockResolvedValue({
			headers: new Headers([['Content-Type', 'application/json']]),
			json: async () => mockUserApiResponse,
		} as Response);

		const onAutocompleteItemSelected = jest.fn();

		render(
			<SpaceMembersInputWithSelect
				disabled={false}
				onAutocompleteItemSelected={onAutocompleteItemSelected}
				selectValue={SelectOptions.USERS}
			/>
		);

		await userEvent.click(
			screen.getByPlaceholderText('enter-name-or-email')
		);

		await waitFor(() => {
			expect(
				screen.getByRole('option', {name: /John Doe \(john.doe\)/})
			).toBeInTheDocument();
		});

		await userEvent.click(
			screen.getByRole('option', {name: /John Doe \(john.doe\)/})
		);

		await waitFor(async () => {
			expect(onAutocompleteItemSelected).toHaveBeenCalledTimes(1);
		});

		expect(onAutocompleteItemSelected).toHaveBeenCalledWith({
			emailAddress: 'john.doe@example.com',
			externalReferenceCode: 'john.doe',
			id: '1',
			image: '/image/user_portrait',
			imageId: 'john.doe.image',
			name: 'John Doe',
			roles: [],
		});

		await waitFor(() => {
			expect(
				screen.getByPlaceholderText('enter-name-or-email')
			).toHaveValue('');
		});
	});

	it('calls "onAutocompleteItemSelected" callback when a group is selected', async () => {
		mockFetch.mockResolvedValue({
			headers: new Headers([['Content-Type', 'application/json']]),
			json: async () => mockGroupApiResponse,
		} as Response);

		const onAutocompleteItemSelected = jest.fn();

		render(
			<SpaceMembersInputWithSelect
				disabled={false}
				onAutocompleteItemSelected={onAutocompleteItemSelected}
				selectValue={SelectOptions.GROUPS}
			/>
		);

		const input = screen.getByPlaceholderText('enter-name-or-email');
		await userEvent.click(input);

		await waitFor(() => {
			expect(
				screen.getByRole('option', {name: /Group 1/})
			).toBeInTheDocument();
		});

		await userEvent.click(screen.getByRole('option', {name: /Group 1/}));

		await waitFor(async () => {
			expect(onAutocompleteItemSelected).toHaveBeenCalledTimes(1);
		});

		expect(onAutocompleteItemSelected).toHaveBeenCalledWith({
			externalReferenceCode: 'group.1',
			id: '1',
			name: 'Group 1',
			numberOfUserAccounts: '5',
			roles: [],
		});

		await waitFor(() => expect(input).toHaveValue(''));
	});

	it('renders a disabled input when disabled is true', async () => {
		render(
			<SpaceMembersInputWithSelect
				disabled
				selectValue={SelectOptions.USERS}
			/>
		);

		const input = screen.getByPlaceholderText('enter-name-or-email');

		expect(input).toBeDisabled();

		await waitFor(() => expect(mockFetch).not.toHaveBeenCalled());
	});
});
