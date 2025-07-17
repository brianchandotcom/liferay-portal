/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, render, screen} from '@testing-library/react';
import React from 'react';

import SpaceService from '../../../../src/main/resources/META-INF/resources/js/common/services/SpaceService';
import {Space} from '../../../../src/main/resources/META-INF/resources/js/common/types/Space';
import {
	AddSpaceMembers,
	AddSpaceMembersProps,
} from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/AddSpaceMembers';

const mockLearnResources = {
	'site-cms-site-initializer': {
		'add-space-members': {
			en_US: {
				message: 'Test Message',
				url: 'https://learn.liferay.com/test-url',
			},
		},
	},
};

describe('AddSpaceMembers', () => {
	const testSpace = {
		id: '123',
		name: 'Test Space',
	};

	const testUsersResponse = {
		items: [],
		lastPage: 1,
		page: 1,
		totalCount: 0,
	};

	const testUserGroupsResponse = {
		items: [],
		lastPage: 1,
		page: 1,
		totalCount: 0,
	};

	const props: AddSpaceMembersProps = {
		assetLibraryCreatorUserId: '0',
		assetLibraryId: testSpace.id,
		assetLibraryName: testSpace.name,
		baseAssetLibraryURL: '/web/cms/e/space/28632',
		learnResources: mockLearnResources,
	};

	let getSpaceSpy: jest.SpyInstance;
	let getSpaceUsersSpy: jest.SpyInstance;
	let getSpaceUserGroupsSpy: jest.SpyInstance;

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

		global.IntersectionObserver = jest.fn().mockImplementation(() => ({
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
		jest.restoreAllMocks();
		delete (global as any).IntersectionObserver;
	});

	it('renders with correct title, description, buttons', async () => {
		await act(async () => render(<AddSpaceMembers {...props} />));

		expect(
			screen.getByRole('heading', {
				name: `add-members-to-x`,
			})
		).toBeInTheDocument();
		expect(
			screen.getByText(
				'add-team-members-to-this-space-to-start-collaborating'
			)
		).toBeInTheDocument();

		expect(
			screen.getByRole('button', {
				name: 'continue-without-members',
			})
		).toBeInTheDocument();
	});
});
