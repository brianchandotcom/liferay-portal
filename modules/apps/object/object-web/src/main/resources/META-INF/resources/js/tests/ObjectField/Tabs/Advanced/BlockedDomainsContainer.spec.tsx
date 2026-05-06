/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import {BlockedDomainsContainer} from '../../../../components/ObjectField/Tabs/Advanced/BlockedDomainsContainer';

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
		<BlockedDomainsContainer
			onSubmit={jest.fn()}
			setValues={setValues}
			values={values}
		/>
	);

describe('BlockedDomainsContainer', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('calls setValues removing the blockedDomains setting when all domains are cleared', () => {
		renderComponent({
			...defaultValues,
			objectFieldSettings: [{name: 'blockedDomains', value: '@aol.com'}],
		});

		fireEvent.click(screen.getByTestId('clear-all'));

		expect(setValues).toHaveBeenCalledWith({
			objectFieldSettings: [],
		});
	});

	it('calls setValues with the updated blockedDomains setting when a domain is added', () => {
		renderComponent();

		const input = screen.getByTestId('domain-input');

		fireEvent.keyDown(input, {
			key: 'Enter',
			target: {value: '@spam.com'},
		});

		expect(setValues).toHaveBeenCalledWith({
			objectFieldSettings: [{name: 'blockedDomains', value: '@spam.com'}],
		});
	});

	it('renders domains label', () => {
		renderComponent();

		expect(screen.getByText('domains')).toBeInTheDocument();
	});

	it('renders existing blocked domains as tags', () => {
		renderComponent({
			...defaultValues,
			objectFieldSettings: [
				{name: 'blockedDomains', value: '@aol.com,@hotmail.com'},
			],
		});

		const tags = screen.getAllByTestId('tag');

		expect(tags).toHaveLength(2);
		expect(tags[0]).toHaveTextContent('@aol.com');
		expect(tags[1]).toHaveTextContent('@hotmail.com');
	});
});
