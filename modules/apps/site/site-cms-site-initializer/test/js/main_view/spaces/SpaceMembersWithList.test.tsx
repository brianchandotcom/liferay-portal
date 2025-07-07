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
import {
	AddSpaceMembers,
	AddSpaceMembersProps,
} from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/AddSpaceMembers';

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

	const props: AddSpaceMembersProps = {
		assetLibraryCreatorUserId: testUsers[0].id,
		assetLibraryId: testSpace.id,
		assetLibraryName: testSpace.name,
		baseAssetLibraryURL: '/web/cms/e/space/28632',
		learnResources: {},
	};

	const LiferayOriginal = global.Liferay;
	const {ResizeObserver: ResizeObserverOriginal} = window;

	let getSpaceSpy: jest.SpyInstance;
	let getSpaceUsersSpy: jest.SpyInstance;
	let getSpaceUserGroupsSpy: jest.SpyInstance;
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
		global.Liferay = {
			Language: {
				get: jest.fn((key) => key),
			},
			ThemeDisplay: {
				...LiferayOriginal.ThemeDisplay,
				getUserId: jest.fn(() => '1'),
			},
		} as any;

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

		jest.clearAllMocks();
	});

	afterAll(() => {
		window.ResizeObserver = ResizeObserverOriginal;
		delete (global as any).IntersectionObserver;
		jest.restoreAllMocks();
	});

	it('lists users from a space', async () => {
		await act(async () => render(<AddSpaceMembers {...props} />));

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
		await act(async () => render(<AddSpaceMembers {...props} />));

		await userEvent.click(
			screen.getByRole('combobox', {name: 'add-people-to-collaborate'})
		);

		await userEvent.click(screen.getByRole('option', {name: 'groups'}));

		const userGroupsList = screen.getByLabelText('who-has-access');
		expect(userGroupsList).toBeInTheDocument();

		await waitFor(() => {
			const userGroupsListItems =
				within(userGroupsList).getAllByRole('listitem');
			expect(userGroupsListItems).toHaveLength(testUsers.length);

			userGroupsListItems.forEach((item, index) => {
				expect(item).toHaveTextContent(testUsers[index].name);
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

		await act(async () => render(<AddSpaceMembers {...props} />));

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
			lastPage: 2,
		});

		await act(async () => render(<AddSpaceMembers {...props} />));

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
});
