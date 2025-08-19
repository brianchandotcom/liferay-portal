/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, render, screen, waitFor, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {openToast} from 'frontend-js-components-web';
import React from 'react';

import AdminUserService from '../../../../src/main/resources/META-INF/resources/js/common/services/AdminUserService';
import SpaceService from '../../../../src/main/resources/META-INF/resources/js/common/services/SpaceService';
import {Space} from '../../../../src/main/resources/META-INF/resources/js/common/types/Space';
import {
	UserAccount,
	UserGroup,
} from '../../../../src/main/resources/META-INF/resources/js/common/types/UserAccount';
import {SelectOptions} from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceMembersInputWithSelect';
import {SPACE_MEMBER_ROLE_NAME} from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceMembersPermissionSelect';
import {
	SpaceMembersWithList,
	SpaceMembersWithListProps,
} from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceMembersWithList';
import {mockFetch} from '../../__mocks__/frontend-js-web';

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as any),
	sub: (str: string, arg: string) => str.replace('x', arg),
}));

jest.mock('frontend-js-components-web', () => ({
	openToast: jest.fn(),
}));

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/services/AdminUserService'
);

describe('SpaceMembersWithList', () => {
	const testSpace = {
		id: '123',
		name: 'Test Space',
	};

	const mockRoles = [
		{
			externalReferenceCode: '1',
			id: 100,
			name: SPACE_MEMBER_ROLE_NAME,
			name_i18n: {'en-US': 'Space Member'},
		},
		{
			externalReferenceCode: '2',
			id: 101,
			name: 'Role 1',
			name_i18n: {'en-US': 'Role 1'},
		},
	];

	const testUsers = [
		{
			emailAddress: 'john.doe@example.com',
			id: '1',
			image: '/image/user_portrait',
			imageId: '1',
			name: 'John Doe',
			roles: [{id: 100, name: SPACE_MEMBER_ROLE_NAME}],
		},
		{
			emailAddress: 'jane.smith@example.com',
			id: '2',
			image: '/image/user_portrait',
			imageId: '1',
			name: 'Jane Smith',
			roles: [{id: 100, name: SPACE_MEMBER_ROLE_NAME}],
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
			roles: [{id: 100, name: SPACE_MEMBER_ROLE_NAME}],
		},
		{
			id: '2',
			name: 'Group 2',
			roles: [{id: 100, name: SPACE_MEMBER_ROLE_NAME}],
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
		hasAssignMembersPermission: true,
	};

	const {ResizeObserver: ResizeObserverOriginal} = window;

	let consoleErrorSpy: jest.SpyInstance;
	let getSpaceUserGroupsSpy: jest.SpyInstance;
	let getSpaceUsersSpy: jest.SpyInstance;
	let intersectionObserverMock: jest.Mock;
	const mockedOpenToast = openToast as jest.Mock;

	beforeEach(() => {
		jest.spyOn(SpaceService, 'getSpace').mockResolvedValue(
			testSpace as unknown as Space
		);
		getSpaceUsersSpy = jest
			.spyOn(SpaceService, 'getSpaceUsers')
			.mockResolvedValue(testUsersResponse);
		getSpaceUserGroupsSpy = jest
			.spyOn(SpaceService, 'getSpaceUserGroups')
			.mockResolvedValue(testUserGroupsResponse);

		jest.spyOn(AdminUserService, 'getUserRoles').mockResolvedValue({
			items: mockRoles,
			lastPage: 1,
			page: 1,
			pageSize: 1,
			totalCount: mockRoles.length,
		});

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
		jest.restoreAllMocks();
		mockFetch.mockReset();
		mockedOpenToast.mockClear();

		const alerts = document.body.querySelectorAll('[role="alert"]');
		alerts.forEach((alert) => alert.remove());
	});

	afterAll(() => {
		window.ResizeObserver = ResizeObserverOriginal;
		delete (global as any).IntersectionObserver;
	});

	it('lists users from a space', async () => {
		render(<SpaceMembersWithList {...props} />);

		const usersList = await screen.findByLabelText('who-has-access');
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
				roles: [{id: 1, name: 'Admin'}],
			},
		];
		const moreUsersResponse = {
			items: moreUsers,
			lastPage: 2,
			page: 2,
			totalCount: 1,
		};

		getSpaceUsersSpy.mockImplementation(
			jest
				.fn()
				.mockResolvedValueOnce({
					...testUsersResponse,
					lastPage: 2,
				})
				.mockResolvedValueOnce(moreUsersResponse)
		);

		render(<SpaceMembersWithList {...props} />);

		await waitFor(() => {
			expect(
				within(screen.getByLabelText('who-has-access')).getAllByRole(
					'listitem'
				)
			).toHaveLength(testUsers.length);
		});

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
		const moreGroups = [
			{id: '3', name: 'Group Three', roles: [{id: 1, name: 'Admin'}]},
		];
		const moreGroupsResponse = {
			items: moreGroups,
			lastPage: 2,
			page: 2,
			totalCount: 1,
		};

		getSpaceUserGroupsSpy.mockImplementation(
			jest
				.fn()
				.mockResolvedValueOnce({
					...testUserGroupsResponse,
					lastPage: 2,
				})
				.mockResolvedValueOnce(moreGroupsResponse)
		);

		render(<SpaceMembersWithList {...props} />);

		await userEvent.selectOptions(
			screen.getByRole('combobox', {name: 'add-people-to-collaborate'}),
			'groups'
		);

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

		expect(screen.getByText('no-members-yet')).toBeInTheDocument();
		expect(
			screen.getByText('add-members-to-this-space')
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

		expect(screen.getByText('no-members-yet')).toBeInTheDocument();
		expect(
			screen.getByText('add-members-to-this-space')
		).toBeInTheDocument();

		await userEvent.selectOptions(
			screen.getByRole('combobox', {name: 'add-people-to-collaborate'}),
			SelectOptions.GROUPS
		);

		expect(screen.getByText('no-members-yet')).toBeInTheDocument();
		expect(
			screen.getByText('add-members-to-this-space')
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

			expect(
				within(screen.getByLabelText('who-has-access')).getByText(
					'New User'
				)
			).toBeInTheDocument();

			await waitFor(() => {
				expect(linkSpy).toHaveBeenCalledWith({
					spaceId: testSpace.id,
					userId: newUser.id,
				});
			});

			expect(mockedOpenToast).toHaveBeenCalledWith({
				message: `user-<strong>${newUser.name}</strong>-successfully-added-to-space`,
				type: 'success',
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

			await waitFor(() => expect(linkSpy).toHaveBeenCalled());

			expect(mockedOpenToast).toHaveBeenCalledWith({
				message: `failed-to-add-user-<strong>${newUser.name}</strong>-to-space`,
				type: 'danger',
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

			expect(userItem).not.toBeInTheDocument();

			await waitFor(() => {
				expect(unlinkSpy).toHaveBeenCalledWith({
					spaceId: testSpace.id,
					userId: testUsers[1].id,
				});
			});

			expect(mockedOpenToast).toHaveBeenCalledWith({
				message: `user-<strong>${testUsers[1].name}</strong>-successfully-removed-from-space`,
				type: 'success',
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

			await waitFor(() => expect(unlinkSpy).toHaveBeenCalled());

			expect(mockedOpenToast).toHaveBeenCalledWith({
				message: `unable-to-remove-user-<strong>${testUsers[1].name}</strong>-from-space`,
				type: 'danger',
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

			await waitFor(() => expect(unlinkSpy).toHaveBeenCalled());

			expect(mockedOpenToast).toHaveBeenCalledWith({
				message: `unable-to-remove-group-<strong>${testUserGroups[0].name}</strong>-from-space`,
				type: 'danger',
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

	describe('When hasAssignMembersPermission is false', () => {
		it('renders the add members input as disabled', () => {
			render(
				<SpaceMembersWithList
					{...props}
					hasAssignMembersPermission={false}
				/>
			);

			expect(
				screen.getByRole('combobox', {
					name: 'add-people-to-collaborate',
				})
			).toBeInTheDocument();

			expect(
				screen.getByPlaceholderText('enter-name-or-email')
			).toBeDisabled();
		});

		it('does not render the remove button for members', async () => {
			render(
				<SpaceMembersWithList
					{...props}
					hasAssignMembersPermission={false}
				/>
			);

			await waitFor(() => {
				expect(screen.getByText(testUsers[1].name)).toBeInTheDocument();
			});

			expect(
				screen.queryByRole('button', {name: /remove/i})
			).not.toBeInTheDocument();
		});
	});

	describe('When updating user member roles', () => {
		it("updates a user's roles and shows a success toast", async () => {
			const updateUserRolesSpy = jest
				.spyOn(SpaceService, 'updateUserRoles')
				.mockResolvedValue({
					data: {
						roleNames: [SPACE_MEMBER_ROLE_NAME, 'Role 1'],
						spaceId: testSpace.id,
						userId: testUsers[1].id,
					},
					error: null,
				});

			render(<SpaceMembersWithList {...props} />);

			const userItem = await screen.findByText(testUsers[1].name);
			const permissionSelect = within(userItem.closest('li')!).getByRole(
				'button',
				{
					name: 'Space Member',
				}
			);

			await userEvent.click(permissionSelect);

			const roleCheckbox = await screen.findByLabelText('Role 1');
			await userEvent.click(roleCheckbox);

			await waitFor(() => {
				expect(updateUserRolesSpy).toHaveBeenCalledWith({
					roleNames: [SPACE_MEMBER_ROLE_NAME, 'Role 1'],
					spaceId: testSpace.id,
					userId: testUsers[1].id,
				});
			});

			expect(mockedOpenToast).toHaveBeenCalledWith({
				message: `<strong>${testUsers[1].name}</strong>-role-was-successfully-updated`,
				type: 'success',
			});

			expect(
				within(userItem.closest('li')!).getByRole('button', {
					name: 'Space Member, Role 1',
				})
			).toBeInTheDocument();
		});

		it('shows an error toast and reverts roles when update fails', async () => {
			const updateUserRolesSpy = jest
				.spyOn(SpaceService, 'updateUserRoles')
				.mockResolvedValue({
					data: null,
					error: 'Update failed',
				});

			render(<SpaceMembersWithList {...props} />);

			const userItem = await screen.findByText(testUsers[1].name);
			const permissionSelect = within(userItem.closest('li')!).getByRole(
				'button',
				{
					name: 'Space Member',
				}
			);

			await userEvent.click(permissionSelect);

			const roleCheckbox = await screen.findByLabelText('Role 1');
			await userEvent.click(roleCheckbox);

			await waitFor(() => expect(updateUserRolesSpy).toHaveBeenCalled());

			expect(mockedOpenToast).toHaveBeenCalledWith({
				message: `unable-to-update-roles-for-user-<strong>${testUsers[1].name}</strong>`,
				type: 'danger',
			});

			expect(permissionSelect).toHaveTextContent('Space Member');
			expect(permissionSelect).not.toHaveTextContent('Role 1');
		});
	});

	describe('When updating user group member roles', () => {
		it("updates a user group's roles and shows a success toast", async () => {
			const updateUserGroupRolesSpy = jest
				.spyOn(SpaceService, 'updateUserGroupRoles')
				.mockResolvedValue({
					data: {
						roleNames: [SPACE_MEMBER_ROLE_NAME, 'Role 1'],
						spaceId: testSpace.id,
						userGroupId: testUserGroups[0].id,
					},
					error: null,
				});

			render(<SpaceMembersWithList {...props} />);

			await userEvent.selectOptions(
				screen.getByRole('combobox', {
					name: 'add-people-to-collaborate',
				}),
				SelectOptions.GROUPS
			);

			const groupItem = await screen.findByText(testUserGroups[0].name);
			const permissionSelect = within(groupItem.closest('li')!).getByRole(
				'button',
				{
					name: 'Space Member',
				}
			);

			await userEvent.click(permissionSelect);

			const menu = await screen.findByRole('menu');
			const roleCheckbox = await within(menu).findByLabelText('Role 1');
			await userEvent.click(roleCheckbox);

			await waitFor(() => {
				expect(updateUserGroupRolesSpy).toHaveBeenCalledWith({
					roleNames: [SPACE_MEMBER_ROLE_NAME, 'Role 1'],
					spaceId: testSpace.id,
					userGroupId: testUserGroups[0].id,
				});
			});

			expect(mockedOpenToast).toHaveBeenCalledWith({
				message: `<strong>${testUserGroups[0].name}</strong>-role-was-successfully-updated`,
				type: 'success',
			});

			expect(
				within(groupItem.closest('li')!).getByRole('button', {
					name: 'Space Member, Role 1',
				})
			).toBeInTheDocument();
		});

		it('shows an error toast and reverts roles when update fails', async () => {
			const updateUserGroupRolesSpy = jest
				.spyOn(SpaceService, 'updateUserGroupRoles')
				.mockResolvedValue({
					data: null,
					error: 'Update failed',
				});

			render(<SpaceMembersWithList {...props} />);

			await userEvent.selectOptions(
				screen.getByRole('combobox', {
					name: 'add-people-to-collaborate',
				}),
				SelectOptions.GROUPS
			);

			const groupItem = await screen.findByText(testUserGroups[0].name);
			const permissionSelect = within(groupItem.closest('li')!).getByRole(
				'button',
				{
					name: 'Space Member',
				}
			);

			await userEvent.click(permissionSelect);

			const menu = await screen.findByRole('menu');
			const roleCheckbox = await within(menu).findByLabelText('Role 1');
			await userEvent.click(roleCheckbox);

			await waitFor(() =>
				expect(updateUserGroupRolesSpy).toHaveBeenCalled()
			);

			expect(mockedOpenToast).toHaveBeenCalledWith({
				message: `unable-to-update-roles-for-group-<strong>${testUserGroups[0].name}</strong>`,
				type: 'danger',
			});

			expect(permissionSelect).toHaveTextContent('Space Member');
			expect(permissionSelect).not.toHaveTextContent('Role 1');
		});
	});
});
