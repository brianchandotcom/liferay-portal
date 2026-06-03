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

const mockHideClientSecret = jest.fn();
const mockOpenToast = jest.fn();
const mockRevealClientSecret = jest.fn();
const mockUseCredential = jest.fn();
const mockWriteText = jest.fn();

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/configuration_form/hooks/useCredential',
	() => ({
		useCredential: () => mockUseCredential(),
	})
);

jest.mock('frontend-js-components-web', () => {
	const React = require('react');

	return {
		FieldBase: ({children, helpMessage, id, label}: any) =>
			React.createElement(
				'div',
				null,
				label && React.createElement('label', {htmlFor: id}, label),
				children,
				helpMessage && React.createElement('small', null, helpMessage)
			),
		openToast: (...args: any[]) => mockOpenToast(...args),
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
		mockHideClientSecret.mockReset();
		mockOpenToast.mockReset();
		mockRevealClientSecret.mockReset();
		mockWriteText.mockReset();
		mockWriteText.mockResolvedValue(undefined);
		mockUseCredential.mockReturnValue({
			clientSecret: null,
			hideClientSecret: mockHideClientSecret,
			revealClientSecret: mockRevealClientSecret,
			revealing: false,
		});

		Object.assign(navigator, {clipboard: {writeText: mockWriteText}});
	});

	afterEach(() => {
		cleanup();
	});

	it('calls hideClientSecret when Hide is clicked', () => {
		mockUseCredential.mockReturnValue({
			clientSecret: 'SECRET_X',
			hideClientSecret: mockHideClientSecret,
			revealClientSecret: mockRevealClientSecret,
			revealing: false,
		});

		render(<CredentialsPanel clientId="CLIENT_X" />);

		fireEvent.click(screen.getByRole('button', {name: 'hide'}));

		expect(mockHideClientSecret).toHaveBeenCalledTimes(1);
	});

	it('calls revealClientSecret when Reveal is clicked', () => {
		render(<CredentialsPanel clientId="CLIENT_X" />);

		fireEvent.click(screen.getByRole('button', {name: 'reveal'}));

		expect(mockRevealClientSecret).toHaveBeenCalledTimes(1);
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

	it('masks the client secret when it is hidden', () => {
		render(<CredentialsPanel clientId="CLIENT_X" />);

		expect(screen.getByDisplayValue('CLIENT_X')).toBeInTheDocument();
		expect(
			screen.getByDisplayValue(SECRET_PLACEHOLDER)
		).toBeInTheDocument();
		expect(
			screen.getByRole('button', {name: 'reveal'})
		).toBeInTheDocument();
	});

	it('shows the client secret when it is revealed', () => {
		mockUseCredential.mockReturnValue({
			clientSecret: 'SECRET_X',
			hideClientSecret: mockHideClientSecret,
			revealClientSecret: mockRevealClientSecret,
			revealing: false,
		});

		render(<CredentialsPanel clientId="CLIENT_X" />);

		expect(screen.getByDisplayValue('SECRET_X')).toBeInTheDocument();
		expect(screen.getByRole('button', {name: 'hide'})).toBeInTheDocument();
	});
});
