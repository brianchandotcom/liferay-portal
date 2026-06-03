/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {
	cleanup,
	fireEvent,
	render,
	screen,
	waitFor,
} from '@testing-library/react';
import React from 'react';

import CredentialsPanel from '../../../src/main/resources/META-INF/resources/js/configuration_form/CredentialsPanel';

const SECRET_PLACEHOLDER = '••••••••••••••••••••••••';

const mockGetCredential = jest.fn();
const mockOpenToast = jest.fn();
const mockWriteText = jest.fn();

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/configuration_form/services/ConfigurationService',
	() => ({
		getCredential: (...args: any[]) => mockGetCredential(...args),
	})
);

jest.mock('@liferay/object-js-components-web', () => ({
	openToast: (...args: any[]) => mockOpenToast(...args),
}));

jest.mock('frontend-js-components-web', () => {
	const React = require('react');

	return {
		FieldBase: ({children, id, label}: any) =>
			React.createElement(
				'div',
				null,
				label && React.createElement('label', {htmlFor: id}, label),
				children
			),
	};
});

(global as any).Liferay = {
	Icons: {spritemap: 'icons.svg'},
	Language: {
		get: (key: string) => key,
	},
};

describe('CredentialsPanel', () => {
	beforeEach(() => {
		mockGetCredential.mockReset();
		mockOpenToast.mockReset();
		mockWriteText.mockReset();
		mockWriteText.mockResolvedValue(undefined);

		Object.assign(navigator, {clipboard: {writeText: mockWriteText}});
	});

	afterEach(() => {
		cleanup();
	});

	it('copies the client ID to the clipboard', async () => {
		render(<CredentialsPanel clientId="CLIENT_X" />);

		fireEvent.click(
			screen.getAllByRole('button', {name: 'copy-to-clipboard'})[0]
		);

		await waitFor(() => {
			expect(mockWriteText).toHaveBeenCalledWith('CLIENT_X');
		});

		expect(mockOpenToast).toHaveBeenCalledWith(
			expect.objectContaining({
				message: 'copied-to-clipboard',
				type: 'success',
			})
		);
	});

	it('exposes the help text on a focusable control', () => {
		render(<CredentialsPanel clientId="CLIENT_X" />);

		expect(
			screen.getByRole('button', {name: 'api-credentials-help'})
		).toBeInTheDocument();
	});

	it('hides the secret again after it is revealed', async () => {
		mockGetCredential.mockResolvedValueOnce({clientSecret: 'SECRET_X'});

		render(<CredentialsPanel clientId="CLIENT_X" />);

		fireEvent.click(screen.getByRole('button', {name: 'reveal'}));

		await waitFor(() => {
			expect(screen.getByDisplayValue('SECRET_X')).toBeInTheDocument();
		});

		fireEvent.click(screen.getByRole('button', {name: 'hide'}));

		expect(
			screen.getByDisplayValue(SECRET_PLACEHOLDER)
		).toBeInTheDocument();
		expect(
			screen.getByRole('button', {name: 'reveal'})
		).toBeInTheDocument();
	});

	it('masks the client secret until it is revealed', () => {
		render(<CredentialsPanel clientId="CLIENT_X" />);

		expect(screen.getByDisplayValue('CLIENT_X')).toBeInTheDocument();
		expect(
			screen.getByDisplayValue(SECRET_PLACEHOLDER)
		).toBeInTheDocument();
		expect(
			screen.getByRole('button', {name: 'reveal'})
		).toBeInTheDocument();
		expect(mockGetCredential).not.toHaveBeenCalled();
	});

	it('reveals the client secret when Reveal is clicked', async () => {
		mockGetCredential.mockResolvedValueOnce({clientSecret: 'SECRET_X'});

		render(<CredentialsPanel clientId="CLIENT_X" />);

		fireEvent.click(screen.getByRole('button', {name: 'reveal'}));

		await waitFor(() => {
			expect(screen.getByDisplayValue('SECRET_X')).toBeInTheDocument();
		});

		expect(mockGetCredential).toHaveBeenCalledTimes(1);
		expect(screen.getByRole('button', {name: 'hide'})).toBeInTheDocument();
	});

	it('shows an error toast when revealing fails', async () => {
		mockGetCredential.mockRejectedValueOnce(new Error('Boom'));

		render(<CredentialsPanel clientId="CLIENT_X" />);

		fireEvent.click(screen.getByRole('button', {name: 'reveal'}));

		await waitFor(() => {
			expect(mockOpenToast).toHaveBeenCalledWith(
				expect.objectContaining({
					message: 'failed-to-load-credentials',
					type: 'danger',
				})
			);
		});

		expect(
			screen.getByDisplayValue(SECRET_PLACEHOLDER)
		).toBeInTheDocument();
	});
});
