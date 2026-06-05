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

import ConfigurationForm from '../../../src/main/resources/META-INF/resources/js/configuration_form/ConfigurationForm';

const mockGetConfiguration = jest.fn();
const mockPutConfiguration = jest.fn();
const mockOpenToast = jest.fn();

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/configuration_form/services/ConfigurationService',
	() => ({
		getConfiguration: (...args: any[]) => mockGetConfiguration(...args),
		putConfiguration: (...args: any[]) => mockPutConfiguration(...args),
	})
);

jest.mock('frontend-js-components-web', () => {
	const React = require('react');

	return {
		FieldBase: ({
			children,
			errorMessage,
			helpMessage,
			id,
			label,
			required,
		}: any) =>
			React.createElement(
				'div',
				null,
				label &&
					React.createElement(
						'label',
						{htmlFor: id},
						label,
						required && '*'
					),
				children,
				errorMessage && React.createElement('div', null, errorMessage),
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

const defaultProps = {
	accountEntryId: 12345,
	backURL: '/back',
	externalReferenceCode: '',
};

describe('ConfigurationForm', () => {
	beforeEach(() => {
		mockGetConfiguration.mockReset();
		mockPutConfiguration.mockReset();
		mockOpenToast.mockReset();
	});

	afterEach(() => {
		cleanup();
	});

	it('exposes a Cancel link that points at backURL', () => {
		render(<ConfigurationForm {...defaultProps} backURL="/back-here" />);

		expect(screen.getByRole('link', {name: 'cancel'})).toHaveAttribute(
			'href',
			'/back-here'
		);
	});

	it('hydrates the inputs after the fetch resolves in edit mode', async () => {
		mockGetConfiguration.mockResolvedValueOnce({
			environmentURLs: 'https://test.example.com',
			externalReferenceCode: 'CONFIG_X',
			recipientEmailAddress: 'test@example.com',
		});

		render(
			<ConfigurationForm
				{...defaultProps}
				externalReferenceCode="CONFIG_X"
			/>
		);

		await waitFor(() => {
			expect(
				screen.getByDisplayValue('https://test.example.com')
			).toBeInTheDocument();
		});

		expect(
			screen.getByDisplayValue('test@example.com')
		).toBeInTheDocument();
	});

	it('renders the Environment URL and Notification Email fields', () => {
		render(<ConfigurationForm {...defaultProps} />);

		expect(screen.getByLabelText(/^environment-url/)).toBeInTheDocument();

		expect(
			screen.getByLabelText(/^notification-email/)
		).toBeInTheDocument();
	});

	it('submits the values with the account relationship and shows a success toast', async () => {
		mockGetConfiguration.mockResolvedValueOnce({
			environmentURLs: '',
			externalReferenceCode: 'CONFIG_X',
			recipientEmailAddress: '',
		});

		mockPutConfiguration.mockResolvedValueOnce({});

		render(
			<ConfigurationForm
				{...defaultProps}
				externalReferenceCode="CONFIG_X"
			/>
		);

		await waitFor(() => {
			expect(
				screen.getByLabelText(/^environment-url/)
			).toBeInTheDocument();
		});

		fireEvent.change(screen.getByLabelText(/^environment-url/), {
			target: {value: 'https://www.example.com'},
		});

		fireEvent.change(screen.getByLabelText(/^notification-email/), {
			target: {value: 'test@example.com'},
		});

		fireEvent.click(screen.getByRole('button', {name: 'save'}));

		await waitFor(() => {
			expect(mockPutConfiguration).toHaveBeenCalledWith(
				'CONFIG_X',
				expect.objectContaining({
					environmentURLs: 'https://www.example.com',
					r_accountToAIHubConfigurations_accountEntryId: 12345,
					recipientEmailAddress: 'test@example.com',
				})
			);
		});

		await waitFor(() => {
			expect(mockOpenToast).toHaveBeenCalledWith(
				expect.objectContaining({type: 'success'})
			);
		});
	});
});
