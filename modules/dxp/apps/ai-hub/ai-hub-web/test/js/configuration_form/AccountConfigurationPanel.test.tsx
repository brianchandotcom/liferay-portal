/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import AccountConfigurationPanel from '../../../src/main/resources/META-INF/resources/js/configuration_form/AccountConfigurationPanel';

const mockSetField = jest.fn();

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
	Language: {
		get: (key: string) => key,
	},
};

const defaultProps = {
	setField: mockSetField,
	values: {
		environmentURLs: '',
		externalReferenceCode: '',
		recipientEmailAddress: '',
	},
};

describe('AccountConfigurationPanel', () => {
	beforeEach(() => {
		mockSetField.mockReset();
	});

	afterEach(() => {
		cleanup();
	});

	it('calls setField when the Environment URL changes', () => {
		render(<AccountConfigurationPanel {...defaultProps} />);

		fireEvent.change(screen.getByLabelText(/^environment-url/), {
			target: {value: 'https://www.example.com'},
		});

		expect(mockSetField).toHaveBeenCalledWith(
			'environmentURLs',
			'https://www.example.com'
		);
	});

	it('calls setField when the Notification Email changes', () => {
		render(<AccountConfigurationPanel {...defaultProps} />);

		fireEvent.change(screen.getByLabelText(/^notification-email/), {
			target: {value: 'test@example.com'},
		});

		expect(mockSetField).toHaveBeenCalledWith(
			'recipientEmailAddress',
			'test@example.com'
		);
	});

	it('renders the Environment URL and Notification Email fields', () => {
		render(<AccountConfigurationPanel {...defaultProps} />);

		expect(screen.getByLabelText(/^environment-url/)).toBeInTheDocument();

		expect(
			screen.getByLabelText(/^notification-email/)
		).toBeInTheDocument();
	});
});
