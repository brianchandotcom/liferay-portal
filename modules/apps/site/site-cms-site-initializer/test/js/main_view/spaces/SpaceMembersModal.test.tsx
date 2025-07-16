/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import SpaceService from '../../../../src/main/resources/META-INF/resources/js/common/services/SpaceService';
import {UserAccount} from '../../../../src/main/resources/META-INF/resources/js/common/types/UserAccount';
import SpaceMembersModal from '../../../../src/main/resources/META-INF/resources/js/main_view/spaces/SpaceMembersModal';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/services/SpaceService'
);

const mockUsers = [
	{
		id: '1',
		name: 'John Doe',
	},
	{
		id: '2',
		name: 'Jane Smith',
	},
] as UserAccount[];

describe('SpaceMembersModal', () => {
	const props = {
		assetLibraryCreatorUserId: '1',
		assetLibraryId: '123',
	};

	const {IntersectionObserver: IntersectionObserverOriginal} = window;
	const {ResizeObserver: ResizeObserverOriginal} = window;
	let getSpaceUsersSpy: jest.SpyInstance;

	beforeAll(() => {
		window.IntersectionObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));
		window.ResizeObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));
	});

	afterAll(() => {
		window.IntersectionObserver = IntersectionObserverOriginal;
		window.ResizeObserver = ResizeObserverOriginal;
		jest.restoreAllMocks();
	});

	beforeEach(() => {
		jest.spyOn(SpaceService, 'getSpaceUserGroups').mockResolvedValue({
			items: [],
			lastPage: 1,
			page: 1,
			totalCount: 0,
		});

		getSpaceUsersSpy = jest
			.spyOn(SpaceService, 'getSpaceUsers')
			.mockResolvedValue({
				items: mockUsers,
				lastPage: 1,
				page: 1,
				totalCount: mockUsers.length,
			});
	});

	afterEach(() => {
		jest.clearAllMocks();
	});

	it('renders the modal with a header and the members list', async () => {
		render(<SpaceMembersModal {...props} />);

		await waitFor(() => {
			expect(getSpaceUsersSpy).toHaveBeenCalledTimes(1);
		});

		expect(screen.getByText('all-members')).toBeInTheDocument();
		expect(screen.getByLabelText('who-has-access')).toBeInTheDocument();
	});

	it('displays the members list correctly from the service', async () => {
		render(<SpaceMembersModal {...props} />);

		await waitFor(() => {
			expect(screen.getByText('John Doe')).toBeInTheDocument();
			expect(screen.getByText('Jane Smith')).toBeInTheDocument();
		});
	});

	it('shows a "no members" message when the space is empty', async () => {
		getSpaceUsersSpy.mockResolvedValue({
			items: [],
		});

		render(<SpaceMembersModal {...props} />);

		await waitFor(() => {
			expect(
				screen.getByText('this-space-has-no-user-yet')
			).toBeInTheDocument();
		});
	});
});
