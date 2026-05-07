/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import EmailAddress from '../../js/EmailAddress/EmailAddress';

import type {LocalizedValue} from 'dynamic-data-mapping-form-field-type';

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

jest.mock('@clayui/autocomplete', () => {
	const React = require('react');

	const DropDown = ({active, children}: any) =>
		active
			? React.createElement('div', {'data-testid': 'dropdown'}, children)
			: null;

	const Item = ({children, onClick}: any) =>
		React.createElement(
			'button',
			{'data-testid': 'domain-option', onClick},
			children
		);

	const ClayAutocomplete = Object.assign(
		React.forwardRef(({children}: any, _ref: any) =>
			React.createElement('div', {ref: _ref}, children)
		),
		{DropDown, Item}
	);

	return {__esModule: true, default: ClayAutocomplete};
});

jest.mock('@clayui/drop-down', () => {
	const React = require('react');

	return {
		__esModule: true,
		default: {
			ItemList: ({children}: any) =>
				React.createElement('div', null, children),
		},
	};
});

jest.mock('@clayui/form', () => {
	const React = require('react');

	return {
		ClayInput: Object.assign(
			({disabled, onBlur, onChange, onFocus, type, value}: any) =>
				React.createElement('input', {
					'data-testid': 'email-input',
					disabled,
					onBlur,
					onChange,
					onFocus,
					type,
					value,
				}),
			{
				Group: ({children}: any) =>
					React.createElement('div', null, children),
				GroupItem: ({children}: any) =>
					React.createElement('div', null, children),
			}
		),
	};
});

jest.mock('dynamic-data-mapping-form-field-type/api', () => {
	const React = require('react');

	return {
		ReactFieldBase: ({children, displayErrors, errorMessage, valid}: any) =>
			React.createElement(
				'div',
				null,
				children,
				displayErrors && valid === false && errorMessage
					? React.createElement(
							'div',
							{'data-testid': 'error-message'},
							errorMessage
						)
					: null
			),
	};
});

jest.mock('dynamic-data-mapping-form-field-type', () => {
	const React = require('react');

	return {
		LocalesDropdown: () =>
			React.createElement('div', {'data-testid': 'locales-dropdown'}),
	};
});

jest.mock('data-engine-js-components-web', () => ({
	useFormState: jest.fn(() => ({
		availableLocales: [
			{displayName: 'English (United States)', id: 'en_US'},
		],
		defaultLanguageId: 'en_US',
		editingLanguageId: 'en_US',
	})),
}));

describe('Email', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('clears the format validation error when the value becomes valid', () => {
		render(<EmailAddress name="email" onChange={jest.fn()} />);

		fireEvent.change(screen.getByTestId('email-input'), {
			target: {value: 'notanemail'},
		});

		fireEvent.blur(screen.getByTestId('email-input'));

		expect(screen.getByTestId('error-message')).toBeInTheDocument();

		fireEvent.change(screen.getByTestId('email-input'), {
			target: {value: 'user@example.com'},
		});

		expect(screen.queryByTestId('error-message')).not.toBeInTheDocument();
	});

	it('disables the input when readOnly is true', () => {
		render(<EmailAddress name="email" onChange={jest.fn()} readOnly />);

		expect(screen.getByTestId('email-input')).toBeDisabled();
	});

	it('does not show the autocomplete dropdown when autocompleteEnabled is false', () => {
		render(
			<EmailAddress
				autocompleteDomains="gmail.com"
				autocompleteEnabled={false}
				name="email"
				onChange={jest.fn()}
			/>
		);

		fireEvent.change(screen.getByTestId('email-input'), {
			target: {value: 'test@gmail'},
		});

		expect(screen.queryByTestId('dropdown')).not.toBeInTheDocument();
	});

	it('fills the input with the selected domain without doubling @', () => {
		render(
			<EmailAddress
				autocompleteDomains="gmail.com"
				autocompleteEnabled
				name="email"
				onChange={jest.fn()}
			/>
		);

		fireEvent.change(screen.getByTestId('email-input'), {
			target: {value: 'test@gm'},
		});

		fireEvent.click(screen.getByTestId('domain-option'));

		expect(screen.getByTestId('email-input')).toHaveValue('test@gmail.com');
	});

	it('filters domain suggestions by the typed fragment', () => {
		render(
			<EmailAddress
				autocompleteDomains="liferay.com,gmail.com"
				autocompleteEnabled
				name="email"
				onChange={jest.fn()}
			/>
		);

		fireEvent.change(screen.getByTestId('email-input'), {
			target: {value: 'test@li'},
		});

		const options = screen.getAllByTestId('domain-option');

		expect(options).toHaveLength(1);
		expect(options[0]).toHaveTextContent('test@liferay.com');
	});

	it('renders a text input', () => {
		render(<EmailAddress name="email" onChange={jest.fn()} />);

		expect(screen.getByTestId('email-input')).toBeInTheDocument();
	});

	it('renders the locale dropdown when localizedObjectField is true', () => {
		render(
			<EmailAddress
				localizedObjectField
				name="email"
				onChange={jest.fn()}
				value={{en_US: 'test@example.com'} as LocalizedValue<string>}
			/>
		);

		expect(screen.getByTestId('locales-dropdown')).toBeInTheDocument();
	});

	it('shows a format validation error after blur with an invalid email', () => {
		render(<EmailAddress name="email" onChange={jest.fn()} />);

		fireEvent.change(screen.getByTestId('email-input'), {
			target: {value: 'notanemail'},
		});

		fireEvent.blur(screen.getByTestId('email-input'));

		expect(screen.getByTestId('error-message')).toHaveTextContent(
			'please-enter-a-valid-email-address'
		);
	});

	it('shows blocked domain error after blur', () => {
		render(
			<EmailAddress
				blockedDomains="@yahoo.com"
				name="email"
				onChange={jest.fn()}
			/>
		);

		fireEvent.change(screen.getByTestId('email-input'), {
			target: {value: 'user@yahoo.com'},
		});

		fireEvent.blur(screen.getByTestId('email-input'));

		expect(screen.getByTestId('error-message')).toHaveTextContent(
			'the-email-address-domain-is-not-allowed-enter-an-email-address-with-a-different-domain'
		);
	});

	it('shows blocked domain error for localizable fields after blur', () => {
		render(
			<EmailAddress
				blockedDomains="@yahoo.com"
				localizedObjectField
				name="email"
				onChange={jest.fn()}
				value={{en_US: ''} as LocalizedValue<string>}
			/>
		);

		fireEvent.change(screen.getByTestId('email-input'), {
			target: {value: 'user@yahoo.com'},
		});

		fireEvent.blur(screen.getByTestId('email-input'));

		expect(screen.getByTestId('error-message')).toHaveTextContent(
			'the-email-address-domain-is-not-allowed-enter-an-email-address-with-a-different-domain'
		);
	});

	it('shows the autocomplete dropdown as soon as the user starts typing', () => {
		render(
			<EmailAddress
				autocompleteDomains="gmail.com"
				autocompleteEnabled
				name="email"
				onChange={jest.fn()}
			/>
		);

		fireEvent.change(screen.getByTestId('email-input'), {
			target: {value: 'test'},
		});

		expect(screen.getByTestId('dropdown')).toBeInTheDocument();
	});

	it('shows the full email suggestion before the @ is typed', () => {
		render(
			<EmailAddress
				autocompleteDomains="gmail.com"
				autocompleteEnabled
				name="email"
				onChange={jest.fn()}
			/>
		);

		fireEvent.change(screen.getByTestId('email-input'), {
			target: {value: 'test'},
		});

		expect(screen.getByTestId('domain-option')).toHaveTextContent(
			'test@gmail.com'
		);
	});

	it('strips leading @ from admin-configured domain values', () => {
		render(
			<EmailAddress
				autocompleteDomains="@gmail.com,@liferay.com"
				autocompleteEnabled
				name="email"
				onChange={jest.fn()}
			/>
		);

		fireEvent.change(screen.getByTestId('email-input'), {
			target: {value: 'test@'},
		});

		const options = screen.getAllByTestId('domain-option');

		expect(options).toHaveLength(2);
		expect(options[0]).toHaveTextContent('test@gmail.com');
		expect(options[1]).toHaveTextContent('test@liferay.com');
	});
});
