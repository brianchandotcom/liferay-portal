/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import RecipientUserAutocomplete from '../../../../src/main/resources/META-INF/resources/js/components/digital-signature-form/RecipientUserAutocomplete';

describe('RecipientUserAutocomplete', () => {
	afterAll(() => {
		delete window.ResizeObserver;
	});

	beforeAll(() => {
		window.ResizeObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));
	});

	beforeEach(() => {
		fetch.mockResponse(JSON.stringify({items: []}));
	});

	it('clears an email that matches no user when the field is left', async () => {
		const onSelectUser = jest.fn();

		fetch.mockResponse(
			JSON.stringify({
				items: [
					{
						emailAddress: 'test1@liferay.com',
						id: 1,
						name: 'Test Test',
					},
				],
			})
		);

		render(
			<RecipientUserAutocomplete
				id="recipients[0].email"
				label="recipient-email"
				onSelectUser={onSelectUser}
			/>
		);

		const input = screen.getByLabelText('recipient-email');

		await userEvent.type(input, 'test');
		await userEvent.click(
			await screen.findByText('Test Test (test1@liferay.com)')
		);
		await userEvent.type(input, 'x');
		await userEvent.tab();

		expect(onSelectUser).toHaveBeenLastCalledWith({
			emailAddress: '',
			name: '',
		});
		expect(input).toHaveValue('');
	});

	it('clears the selected user when the email is edited', async () => {
		const onSelectUser = jest.fn();

		render(
			<RecipientUserAutocomplete
				id="recipients[0].email"
				label="recipient-email"
				onSelectUser={onSelectUser}
				value="test1@liferay.com"
			/>
		);

		await userEvent.type(screen.getByLabelText('recipient-email'), 'x');

		expect(onSelectUser).toHaveBeenCalledTimes(1);
		expect(onSelectUser).toHaveBeenCalledWith({emailAddress: '', name: ''});
		expect(screen.getByLabelText('recipient-email')).toHaveValue(
			'test1@liferay.comx'
		);
	});

	it('keeps the user chosen from the list', async () => {
		const onSelectUser = jest.fn();
		const userAccount = {
			emailAddress: 'test1@liferay.com',
			id: 1,
			name: 'Test Test',
		};

		fetch.mockResponse(JSON.stringify({items: [userAccount]}));

		render(
			<RecipientUserAutocomplete
				id="recipients[0].email"
				label="recipient-email"
				onSelectUser={onSelectUser}
			/>
		);

		await userEvent.type(screen.getByLabelText('recipient-email'), 'test');
		await userEvent.click(
			await screen.findByText('Test Test (test1@liferay.com)')
		);

		expect(onSelectUser).toHaveBeenCalledTimes(1);
		expect(onSelectUser).toHaveBeenCalledWith(
			expect.objectContaining({
				emailAddress: userAccount.emailAddress,
				name: userAccount.name,
			})
		);
		expect(screen.getByLabelText('recipient-email')).toHaveValue(
			'test1@liferay.com'
		);
	});

	it('keeps the user chosen with the keyboard', async () => {
		const onSelectUser = jest.fn();
		const userAccount = {
			emailAddress: 'test1@liferay.com',
			id: 1,
			name: 'Test Test',
		};

		fetch.mockResponse(JSON.stringify({items: [userAccount]}));

		render(
			<RecipientUserAutocomplete
				id="recipients[0].email"
				label="recipient-email"
				onSelectUser={onSelectUser}
			/>
		);

		await userEvent.type(screen.getByLabelText('recipient-email'), 'test');
		await screen.findByText('Test Test (test1@liferay.com)');
		await userEvent.keyboard('{ArrowDown}{Enter}');

		expect(onSelectUser).toHaveBeenCalledTimes(1);
		expect(onSelectUser).toHaveBeenCalledWith(
			expect.objectContaining({
				emailAddress: userAccount.emailAddress,
				name: userAccount.name,
			})
		);
		expect(screen.getByLabelText('recipient-email')).toHaveValue(
			'test1@liferay.com'
		);
	});

	it('searches the user accounts by the typed text', async () => {
		render(
			<RecipientUserAutocomplete
				id="recipients[0].email"
				label="recipient-email"
				onSelectUser={jest.fn()}
			/>
		);

		await userEvent.type(
			screen.getByLabelText('recipient-email'),
			'test user'
		);

		await waitFor(() => expect(fetch).toHaveBeenCalled());

		const url = new URL(fetch.mock.calls[0][0], 'http://localhost');

		expect(url.pathname).toBe('/o/headless-admin-user/v1.0/user-accounts');
		expect(url.searchParams.get('search')).toBe('test user');
	});

	it('shows the email of the recipient in its row', () => {
		const {rerender} = render(
			<RecipientUserAutocomplete
				id="recipients[0].email"
				label="recipient-email"
				onSelectUser={jest.fn()}
				value="test1@liferay.com"
			/>
		);

		rerender(
			<RecipientUserAutocomplete
				id="recipients[0].email"
				label="recipient-email"
				onSelectUser={jest.fn()}
				value="test2@liferay.com"
			/>
		);

		expect(screen.getByLabelText('recipient-email')).toHaveValue(
			'test2@liferay.com'
		);
	});
});
