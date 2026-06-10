/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen, waitFor, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import DesignLibraryManageMembersModal from '../../src/main/resources/META-INF/resources/js/modal/DesignLibraryManageMembersModal';
import MemberService from '../../src/main/resources/META-INF/resources/js/services/MemberService';

jest.mock(
	'../../src/main/resources/META-INF/resources/js/services/MemberService',
	() => ({
		__esModule: true,
		default: {
			addUser: jest.fn(),
			addUserGroup: jest.fn(),
			getUserGroupMembers: jest.fn(),
			getUserMembers: jest.fn(),
		},
	})
);

const mockOpenToast = jest.fn();

jest.mock('frontend-js-components-web', () => ({
	...(jest.requireActual('frontend-js-components-web') as any),
	openToast: (...args: any[]) => mockOpenToast(...args),
}));

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as any),
	fetch: jest.fn(() => {
		const headers = new Headers();

		headers.set('Content-Type', 'application/json');

		return Promise.resolve({
			headers,
			json: () =>
				Promise.resolve({
					items: [
						{
							emailAddress: 'newuser@liferay.com',
							externalReferenceCode: 'new-user-erc',
							id: 99,
							name: 'New User',
						},
					],
					lastPage: 1,
					page: 1,
				}),
			ok: true,
			status: 200,
		});
	}),
}));

const mockMemberService = MemberService as jest.Mocked<typeof MemberService>;

async function inviteCandidateUser() {
	await userEvent.click(screen.getByPlaceholderText('enter-name-or-email'));

	await userEvent.click(await screen.findByText(/New User/));

	await userEvent.click(screen.getByRole('button', {name: 'invite'}));
}

describe('DesignLibraryManageMembersModal', () => {
	const {ResizeObserver: originalResizeObserver} = window;

	let originalEscapeHTML: unknown;

	beforeAll(() => {
		const liferay = (globalThis as any).Liferay;

		liferay.Util = liferay.Util || {};
		originalEscapeHTML = liferay.Util.escapeHTML;
		liferay.Util.escapeHTML = (str: string) => str;

		window.ResizeObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));
	});

	afterAll(() => {
		(globalThis as any).Liferay.Util.escapeHTML = originalEscapeHTML;
		window.ResizeObserver = originalResizeObserver;
	});

	beforeEach(() => {
		jest.clearAllMocks();

		mockMemberService.getUserMembers.mockResolvedValue({
			items: [
				{
					externalReferenceCode: 'u1',
					id: 1,
					name: 'Alice Adams',
					roles: [],
					type: 'user',
				},
			],
		});

		mockMemberService.getUserGroupMembers.mockResolvedValue({
			items: [
				{
					externalReferenceCode: 'g1',
					id: 2,
					name: 'Marketing',
					roles: [],
					type: 'userGroup',
				},
			],
		});
	});

	it('renders the current members in the who has access list', async () => {
		render(
			<DesignLibraryManageMembersModal externalReferenceCode="lib-erc" />
		);

		await waitFor(() =>
			expect(screen.getByText('Alice Adams')).toBeInTheDocument()
		);

		expect(screen.getByText('Marketing')).toBeInTheDocument();
		expect(mockMemberService.getUserMembers).toHaveBeenCalledWith(
			'lib-erc'
		);
		expect(mockMemberService.getUserGroupMembers).toHaveBeenCalledWith(
			'lib-erc'
		);
	});

	it('invites a member and adds it to the list', async () => {
		mockMemberService.addUser.mockResolvedValue({} as any);

		render(
			<DesignLibraryManageMembersModal externalReferenceCode="lib-erc" />
		);

		await waitFor(() =>
			expect(screen.getByText('Alice Adams')).toBeInTheDocument()
		);

		await inviteCandidateUser();

		await waitFor(() =>
			expect(mockMemberService.addUser).toHaveBeenCalledWith(
				'lib-erc',
				'new-user-erc'
			)
		);

		expect(
			within(screen.getByRole('list')).getByText('New User')
		).toBeInTheDocument();

		expect(mockOpenToast).toHaveBeenCalledWith(
			expect.objectContaining({type: 'success'})
		);
	});

	it('shows an error toast when adding a member fails', async () => {
		mockMemberService.addUser.mockRejectedValue({title: 'Boom'});

		render(
			<DesignLibraryManageMembersModal externalReferenceCode="lib-erc" />
		);

		await waitFor(() =>
			expect(screen.getByText('Alice Adams')).toBeInTheDocument()
		);

		await inviteCandidateUser();

		await waitFor(() =>
			expect(mockOpenToast).toHaveBeenCalledWith(
				expect.objectContaining({type: 'danger'})
			)
		);

		expect(
			within(screen.getByRole('list')).queryByText('New User')
		).not.toBeInTheDocument();
	});

	it('shows an error toast when loading members fails', async () => {
		mockMemberService.getUserMembers.mockRejectedValue({
			title: 'Load failed',
		});

		render(
			<DesignLibraryManageMembersModal externalReferenceCode="lib-erc" />
		);

		await waitFor(() =>
			expect(mockOpenToast).toHaveBeenCalledWith(
				expect.objectContaining({type: 'danger'})
			)
		);
	});
});
