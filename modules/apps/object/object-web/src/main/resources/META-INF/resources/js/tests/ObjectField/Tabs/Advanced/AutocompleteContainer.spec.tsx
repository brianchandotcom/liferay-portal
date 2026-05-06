/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import {AutocompleteContainer} from '../../../../components/ObjectField/Tabs/Advanced/AutocompleteContainer';

jest.mock('@clayui/multi-select', () => ({
	__esModule: true,
	default: ({items, onItemsChange, placeholder}: any) => (
		<div>
			{items.map((item: any) => (
				<span data-testid="tag" key={item.value}>
					{item.label}
				</span>
			))}

			<input
				data-testid="domain-input"
				onKeyDown={(event) => {
					if (event.key === 'Enter') {
						const input = event.target as HTMLInputElement;

						onItemsChange([
							...items,
							{label: input.value, value: input.value},
						]);
					}
				}}
				placeholder={placeholder}
			/>

			<button data-testid="clear-all" onClick={() => onItemsChange([])}>
				clear
			</button>
		</div>
	),
}));

beforeAll(() => {
	(global as any).Liferay = {
		Language: {
			get: (key: string) => key,
		},
	};
});

const setValues = jest.fn();

const defaultValues: Partial<ObjectField> = {
	businessType: 'EmailAddress',
	objectFieldSettings: [],
};

const renderComponent = (values: Partial<ObjectField> = defaultValues) =>
	render(
		<AutocompleteContainer
			onSubmit={jest.fn()}
			setValues={setValues}
			values={values}
		/>
	);

describe('AutocompleteContainer', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('renders the Enable Autocomplete toggle', () => {
		renderComponent();

		expect(screen.getByText('enable-autocomplete')).toBeInTheDocument();
	});

	it('toggle is off by default when no autocompleteEnabled setting is present', () => {
		renderComponent();

		expect(screen.getByRole('switch')).not.toBeChecked();
	});

	it('toggle is on when autocompleteEnabled is true in settings', () => {
		renderComponent({
			...defaultValues,
			objectFieldSettings: [{name: 'autocompleteEnabled', value: true}],
		});

		expect(screen.getByRole('switch')).toBeChecked();
	});

	it('hides the domain input when the toggle is off', () => {
		renderComponent();

		expect(screen.queryByTestId('domain-input')).not.toBeInTheDocument();
	});

	it('shows the domain input when the toggle is on', () => {
		renderComponent({
			...defaultValues,
			objectFieldSettings: [{name: 'autocompleteEnabled', value: true}],
		});

		expect(screen.getByTestId('domain-input')).toBeInTheDocument();
	});

	it('calls setValues with autocompleteEnabled true when toggle is turned on', () => {
		renderComponent();

		fireEvent.click(screen.getByRole('switch'));

		expect(setValues).toHaveBeenCalledWith(
			expect.objectContaining({
				objectFieldSettings: expect.arrayContaining([
					expect.objectContaining({
						name: 'autocompleteEnabled',
						value: true,
					}),
				]),
			})
		);
	});

	it('calls setValues removing autocompleteEnabled and autocompleteDomains when toggle is turned off', () => {
		renderComponent({
			...defaultValues,
			objectFieldSettings: [
				{name: 'autocompleteEnabled', value: true},
				{name: 'autocompleteDomains', value: 'gmail.com'},
			],
		});

		fireEvent.click(screen.getByRole('switch'));

		expect(setValues).toHaveBeenCalledWith({objectFieldSettings: []});
	});

	it('renders existing autocomplete domains as tags when enabled', () => {
		renderComponent({
			...defaultValues,
			objectFieldSettings: [
				{name: 'autocompleteEnabled', value: true},
				{name: 'autocompleteDomains', value: 'gmail.com,outlook.com'},
			],
		});

		const tags = screen.getAllByTestId('tag');

		expect(tags).toHaveLength(2);
		expect(tags[0]).toHaveTextContent('gmail.com');
		expect(tags[1]).toHaveTextContent('outlook.com');
	});

	it('calls setValues with updated autocompleteDomains when a domain is added', () => {
		renderComponent({
			...defaultValues,
			objectFieldSettings: [{name: 'autocompleteEnabled', value: true}],
		});

		const input = screen.getByTestId('domain-input');

		fireEvent.keyDown(input, {
			key: 'Enter',
			target: {value: 'liferay.com'},
		});

		expect(setValues).toHaveBeenCalledWith(
			expect.objectContaining({
				objectFieldSettings: expect.arrayContaining([
					expect.objectContaining({
						name: 'autocompleteDomains',
						value: 'liferay.com',
					}),
				]),
			})
		);
	});

	it('calls setValues removing autocompleteDomains when all domains are cleared', () => {
		renderComponent({
			...defaultValues,
			objectFieldSettings: [
				{name: 'autocompleteEnabled', value: true},
				{name: 'autocompleteDomains', value: 'gmail.com'},
			],
		});

		fireEvent.click(screen.getByTestId('clear-all'));

		expect(setValues).toHaveBeenCalledWith(
			expect.objectContaining({
				objectFieldSettings: expect.not.arrayContaining([
					expect.objectContaining({name: 'autocompleteDomains'}),
				]),
			})
		);
	});
});
