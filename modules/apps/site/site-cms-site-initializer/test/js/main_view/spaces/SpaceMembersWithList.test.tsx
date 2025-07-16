/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, render, screen, waitFor, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import SpaceService from '../../../../src/main/resources/META-INF/resources/js/common/services/SpaceService';
import {Space} from '../../../../src/main/resources/META-INF/resources/js/common/types/Space';
import {
	UserAccount,
	UserGroup,
} from '../../../../src/main/resources/META-INF/resources/js/common/types/UserAccount';
import {SelectOptions} from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceMembersInputWithSelect';
import {
	SpaceMembersWithList,
	SpaceMembersWithListProps,
} from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceMembersWithList';
import {mockFetch} from '../../__mocks__/frontend-js-web';

describe('SpaceMembersWithList', () => {
	const testSpace = {
		id: '123',
		name: 'Test Space',
	};

	const testUsers = [
		{
			emailAddress: 'john.doe@example.com',
			id: '1',
			image: '/image/user_portrait',
			name: 'John Doe',
		},
		{
			emailAddress: 'jane.smith@example.com',
			id: '2',
			image: '/image/user_portrait',
			name: 'Jane Smith',
		},
	] as UserAccount[];

	const testUsersResponse = {
		items: testUsers,
		lastPage: 1,
		page: 1,
		totalCount: testUsers.length,
	};

	const testUserGroups = [
		{
			id: '1',
			name: 'Group 1',
		},
		{
			id: '2',
			name: 'Group 2',
		},
	] as UserGroup[];

	const testUserGroupsResponse = {
		items: testUserGroups,
		lastPage: 1,
		page: 1,
		totalCount: testUserGroups.length,
	};

	const props: SpaceMembersWithListProps = {
		assetLibraryCreatorUserId: testUsers[0].id,
		assetLibraryId: testSpace.id,
	};

	const {ResizeObserver: ResizeObserverOriginal} = window;

	let consoleErrorSpy: jest.SpyInstance;
	let getSpaceSpy: jest.SpyInstance;
	let getSpaceUserGroupsSpy: jest.SpyInstance;
	let getSpaceUsersSpy: jest.SpyInstance;
	let intersectionObserverMock: jest.Mock;

	beforeEach(() => {
		getSpaceSpy = jest
			.spyOn(SpaceService, 'getSpace')
			.mockResolvedValue(testSpace as unknown as Space);
		getSpaceUsersSpy = jest
			.spyOn(SpaceService, 'getSpaceUsers')
			.mockResolvedValue(testUsersResponse);
		getSpaceUserGroupsSpy = jest
			.spyOn(SpaceService, 'getSpaceUserGroups')
			.mockResolvedValue(testUserGroupsResponse);

		consoleErrorSpy = jest.spyOn(console, 'error').mockImplementation();

		intersectionObserverMock = jest.fn((callback) => {
			(intersectionObserverMock as any).mockCallback = callback;

			return {
				disconnect: jest.fn(),
				observe: jest.fn(),
				unobserve: jest.fn(),
			};
		});
		global.IntersectionObserver = intersectionObserverMock;
	});

	beforeAll(() => {
		window.ResizeObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));
	});

	afterEach(() => {
		getSpaceSpy.mockClear();
		getSpaceUsersSpy.mockClear();
		getSpaceUserGroupsSpy.mockClear();
		consoleErrorSpy.mockRestore();

		jest.clearAllMocks();

		const alerts = document.body.querySelectorAll('[role="alert"]');
		alerts.forEach((alert) => alert.remove());
	});

	afterAll(() => {
		window.ResizeObserver = ResizeObserverOriginal;
		delete (global as any).IntersectionObserver;
		jest.restoreAllMocks();
	});

	it('lists users from a space', async () => {
		render(<SpaceMembersWithList {...props} />);

		const usersList = screen.getByLabelText('who-has-access');
		expect(usersList).toBeInTheDocument();

		await waitFor(() => {
			const usersListItems = within(usersList).getAllByRole('listitem');
			expect(usersListItems).toHaveLength(testUsers.length);

			usersListItems.forEach((item, index) => {
				expect(item).toHaveTextContent(testUsers[index].name);
			});
		});
	});

	it('lists user groups from a space', async () => {
		render(<SpaceMembersWithList {...props} />);

		await userEvent.selectOptions(
			screen.getByRole('combobox', {name: 'add-people-to-collaborate'}),
			SelectOptions.GROUPS
		);

		const userGroupsList = screen.getByLabelText('who-has-access');
		expect(userGroupsList).toBeInTheDocument();

		await waitFor(() => {
			const userGroupsListItems =
				within(userGroupsList).getAllByRole('listitem');
			expect(userGroupsListItems).toHaveLength(testUserGroups.length);

			userGroupsListItems.forEach((item, index) => {
				expect(item).toHaveTextContent(testUserGroups[index].name);
			});
		});
	});

	it('loads more users when scrolling down', async () => {
		const moreUsers = [
			{
				emailAddress: 'user3@example.com',
				id: '3',
				name: 'User Three',
			},
		];
		const moreUsersResponse = {
			items: moreUsers,
			lastPage: 2,
			page: 2,
			totalCount: 1,
		};

		getSpaceUsersSpy.mockResolvedValueOnce({
			...testUsersResponse,
			lastPage: 2,
		});

		render(<SpaceMembersWithList {...props} />);

		await waitFor(() => {
			expect(
				within(screen.getByLabelText('who-has-access')).getAllByRole(
					'listitem'
				)
			).toHaveLength(testUsers.length);
		});

		getSpaceUsersSpy.mockResolvedValueOnce(moreUsersResponse);

		act(() => {
			(intersectionObserverMock as any).mockCallback([
				{isIntersecting: true},
			]);
		});

		await waitFor(() => {
			expect(getSpaceUsersSpy).toHaveBeenCalledTimes(2);
			expect(getSpaceUsersSpy).toHaveBeenLastCalledWith(
				expect.objectContaining({page: 2})
			);
		});

		await waitFor(() => {
			expect(
				within(screen.getByLabelText('who-has-access')).getAllByRole(
					'listitem'
				)
			).toHaveLength(testUsers.length + moreUsers.length);
		});

		expect(screen.queryByRole('status')).not.toBeInTheDocument();
	});

	it('loads more user groups when scrolling down', async () => {
		const moreGroups = [{id: '3', name: 'Group Three'}];
		const moreGroupsResponse = {
			items: moreGroups,
			lastPage: 2,
			page: 2,
			totalCount: 1,
		};

		getSpaceUserGroupsSpy.mockResolvedValueOnce({
			...testUserGroupsResponse,
			lastPage: 3,
		});

		render(<SpaceMembersWithList {...props} />);

		await userEvent.selectOptions(
			screen.getByRole('combobox', {name: 'add-people-to-collaborate'}),
			'groups'
		);

		getSpaceUserGroupsSpy.mockResolvedValueOnce(moreGroupsResponse);

		act(() => {
			(intersectionObserverMock as any).mockCallback([
				{isIntersecting: true},
			]);
		});

		await waitFor(() => {
			expect(getSpaceUserGroupsSpy).toHaveBeenCalledTimes(2);
			expect(getSpaceUserGroupsSpy).toHaveBeenLastCalledWith(
				expect.objectContaining({page: 2})
			);
		});

		await waitFor(() => {
			expect(
				within(screen.getByLabelText('who-has-access')).getAllByRole(
					'listitem'
				)
			).toHaveLength(testUserGroups.length + moreGroups.length);
		});
	});

	it('handles failure when loading initial members', async () => {
		getSpaceUsersSpy.mockRejectedValueOnce(new Error('Fetch failed'));

		render(<SpaceMembersWithList {...props} />);

		await waitFor(() => {
			expect(consoleErrorSpy).toHaveBeenCalledWith(
				new Error('Fetch failed')
			);
		});

		expect(
			screen.getByText('this-space-has-no-user-yet')
		).toBeInTheDocument();
	});

	it('shows a "no members" message when the space is empty', async () => {
		getSpaceUsersSpy.mockResolvedValueOnce({
			...testUsersResponse,
			items: [],
		});
		getSpaceUserGroupsSpy.mockResolvedValueOnce({
			...testUserGroupsResponse,
			items: [],
		});

		await act(async () => {
			render(<SpaceMembersWithList {...props} />);
		});

		expect(
			screen.getByText('this-space-has-no-user-yet')
		).toBeInTheDocument();

		await userEvent.selectOptions(
			screen.getByRole('combobox', {name: 'add-people-to-collaborate'}),
			SelectOptions.GROUPS
		);

		expect(
			screen.getByText('this-space-has-no-group-yet')
		).toBeInTheDocument();
	});

	describe('When linking to a space', () => {
		it('adds a new user to the list and shows a success toast', async () => {
			const newUser = {
				emailAddress: 'new@user.com',
				id: '3',
				name: 'New User',
			};
			mockFetch.mockResolvedValue({
				json: async () => ({items: [newUser]}),
			} as Response);
			const linkSpy = jest
				.spyOn(SpaceService, 'linkUserToSpace')
				.mockResolvedValue({data: null, error: null});

			render(<SpaceMembersWithList {...props} />);

			const input = screen.getByPlaceholderText('enter-name-or-email');

			await userEvent.type(input, 'New');

			await userEvent.click(
				await screen.findByRole('option', {name: /New User/})
			);

			await waitFor(() => {
				expect(linkSpy).toHaveBeenCalledWith({
					spaceId: testSpace.id,
					userId: newUser.id,
				});

				expect(
					within(screen.getByLabelText('who-has-access')).getByText(
						'New User'
					)
				).toBeInTheDocument();

				const alert = screen.getByRole('alert');
				expect(alert).toHaveTextContent(
					`user-${newUser.name}-successfully-added-to-space`
				);
			});
		});

		it('shows an error toast when linking a user fails', async () => {
			const newUser = {
				emailAddress: 'fail@user.com',
				id: '3',
				name: 'Fail User',
			};

			mockFetch.mockResolvedValue({
				json: async () => ({items: [newUser]}),
			} as Response);

			const linkSpy = jest
				.spyOn(SpaceService, 'linkUserToSpace')
				.mockResolvedValue({data: null, error: 'Link failed'});

			render(<SpaceMembersWithList {...props} />);

			await userEvent.click(
				screen.getByPlaceholderText('enter-name-or-email')
			);

			await userEvent.click(
				await screen.findByRole('option', {name: /Fail User/})
			);

			await waitFor(() => {
				expect(linkSpy).toHaveBeenCalled();

				const alert = screen.getByRole('alert');
				expect(alert).toHaveTextContent(
					`failed-to-add-user-${newUser.name}-to-space`
				);
			});
		});
	});

	describe('When unlinking from a space', () => {
		it('removes a user from the list and shows a success toast', async () => {
			getSpaceUsersSpy.mockResolvedValueOnce({
				...testUsersResponse,
				items: testUsers,
			});
			const unlinkSpy = jest
				.spyOn(SpaceService, 'unlinkUserFromSpace')
				.mockResolvedValue({data: null, error: null});

			render(<SpaceMembersWithList {...props} />);

			const userItem = await screen.findByText(testUsers[1].name);

			const removeButton = within(userItem.closest('li')!).getByRole(
				'button',
				{name: /remove-user/}
			);
			await userEvent.click(removeButton);

			await waitFor(() => {
				expect(unlinkSpy).toHaveBeenCalledWith({
					spaceId: testSpace.id,
					userId: testUsers[1].id,
				});
				expect(removeButton).not.toBeInTheDocument();

				const alert = screen.getByRole('alert');
				expect(alert).toHaveTextContent(
					`user-${testUsers[1].name}-successfully-removed-from-space`
				);
			});
		});

		it('shows an error toast when unlinking a user fails', async () => {
			getSpaceUsersSpy.mockResolvedValueOnce({
				...testUsersResponse,
				items: testUsers,
			});
			const unlinkSpy = jest
				.spyOn(SpaceService, 'unlinkUserFromSpace')
				.mockResolvedValue({data: null, error: 'Unlink failed'});

			render(<SpaceMembersWithList {...props} />);

			const userItem = await screen.findByText(testUsers[1].name);
			const removeButton = within(userItem.closest('li')!).getByRole(
				'button',
				{name: /remove-user/}
			);
			await userEvent.click(removeButton);

			await waitFor(() => {
				expect(unlinkSpy).toHaveBeenCalled();

				const alert = screen.getByRole('alert');
				expect(alert).toHaveTextContent(
					`unable-to-remove-user-${testUsers[1].name}-from-space`
				);
			});
		});

		it('shows an error toast when unlinking a group fails', async () => {
			getSpaceUserGroupsSpy.mockResolvedValueOnce({
				...testUserGroupsResponse,
				items: [testUserGroups[0]],
			});

			const unlinkSpy = jest
				.spyOn(SpaceService, 'unlinkUserGroupFromSpace')
				.mockResolvedValue({data: null, error: 'Unlink failed'});

			render(<SpaceMembersWithList {...props} />);

			await userEvent.selectOptions(
				screen.getByRole('combobox', {
					name: 'add-people-to-collaborate',
				}),
				SelectOptions.GROUPS
			);

			const groupItem = await screen.findByText(testUserGroups[0].name);
			const removeButton = within(groupItem.closest('li')!).getByRole(
				'button',
				{name: /remove-group/}
			);
			await userEvent.click(removeButton);

			await waitFor(() => {
				expect(unlinkSpy).toHaveBeenCalled();

				const alert = screen.getByRole('alert');
				expect(alert).toHaveTextContent(
					`unable-to-remove-group-${testUserGroups[0].name}-from-space-x`
				);
			});
		});
	});

	it('prevents adding a user that is already in the list', async () => {
		const newUser = {
			emailAddress: 'new@user.com',
			id: '3',
			name: 'New User',
		};

		mockFetch.mockResolvedValue({
			json: async () => ({items: [newUser]}),
		} as Response);

		const linkSpy = jest
			.spyOn(SpaceService, 'linkUserToSpace')
			.mockResolvedValue({data: null, error: null});

		render(<SpaceMembersWithList {...props} />);

		const input = screen.getByPlaceholderText('enter-name-or-email');

		await userEvent.type(input, 'New');

		await userEvent.click(
			await screen.findByRole('option', {name: /New User/})
		);

		await waitFor(() => {
			expect(linkSpy).toHaveBeenCalledTimes(1);
		});

		await userEvent.type(input, 'New');
		await userEvent.click(
			await screen.findByRole('option', {name: /New User/})
		);

		expect(linkSpy).toHaveBeenCalledTimes(1);
	});
});
