/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';

import '@testing-library/jest-dom';
import {act, render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import ListTypeDefaultValueSelect from '../../../components/ObjectField/DefaultValueFields/ListTypeDefaultValueSelect';

const baseProps = {
	creationLanguageId: 'en_US' as Liferay.Language.Locale,
	label: 'default-value',
	onSubmit: () => {},
	required: true,
	setValues: () => {},
};

const colorsListTypeEntries = [
	{
		externalReferenceCode: 'blue-erc',
		id: 11,
		key: 'blue',
		listTypeDefinitionId: 1,
		name: 'Blue',
		name_i18n: {en_US: 'Blue'},
	},
];

const sizesListTypeEntries = [
	{
		externalReferenceCode: 'small-erc',
		id: 21,
		key: 'small',
		listTypeDefinitionId: 2,
		name: 'Small',
		name_i18n: {en_US: 'Small'},
	},
];

describe('ListTypeDefaultValueSelect', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('clears the previous default value when switching to a different picklist', async () => {
		jest.spyOn(
			API,
			'getListTypeDefinitionListTypeEntries'
		).mockImplementation((listTypeDefinitionId) =>
			Promise.resolve(
				(listTypeDefinitionId === 1
					? colorsListTypeEntries
					: sizesListTypeEntries) as any
			)
		);

		const {rerender} = render(
			<ListTypeDefaultValueSelect
				{...baseProps}
				defaultValue="blue"
				values={{listTypeDefinitionId: 1}}
			/>
		);

		await waitFor(() => {
			expect(screen.getByRole('combobox')).toHaveTextContent('Blue');
		});

		rerender(
			<ListTypeDefaultValueSelect
				{...baseProps}
				defaultValue="blue"
				values={{listTypeDefinitionId: 2}}
			/>
		);

		await waitFor(() => {
			expect(screen.getByRole('combobox')).not.toHaveTextContent('Blue');
		});

		expect(screen.getByRole('combobox')).toHaveTextContent(
			'choose-an-option'
		);
	});

	it('clears the previous default value when switching to a picklist without items', async () => {
		jest.spyOn(
			API,
			'getListTypeDefinitionListTypeEntries'
		).mockImplementation((listTypeDefinitionId) =>
			Promise.resolve(
				(listTypeDefinitionId === 1 ? colorsListTypeEntries : []) as any
			)
		);

		const {rerender} = render(
			<ListTypeDefaultValueSelect
				{...baseProps}
				defaultValue="blue"
				values={{listTypeDefinitionId: 1}}
			/>
		);

		await waitFor(() => {
			expect(screen.getByRole('combobox')).toHaveTextContent('Blue');
		});

		rerender(
			<ListTypeDefaultValueSelect
				{...baseProps}
				defaultValue="blue"
				values={{listTypeDefinitionId: 2}}
			/>
		);

		await waitFor(() => {
			expect(screen.getByRole('combobox')).not.toHaveTextContent('Blue');
		});

		expect(screen.getByRole('combobox')).toHaveTextContent(
			'choose-an-option'
		);
	});

	it('discards results from a superseded picklist request', async () => {
		let resolveColors!: (items: ListTypeEntry[]) => void;

		jest.spyOn(
			API,
			'getListTypeDefinitionListTypeEntries'
		).mockImplementation((listTypeDefinitionId) =>
			listTypeDefinitionId === 1
				? (new Promise((resolve) => {
						resolveColors = resolve;
					}) as any)
				: (Promise.resolve(sizesListTypeEntries) as any)
		);

		const {rerender} = render(
			<ListTypeDefaultValueSelect
				{...baseProps}
				defaultValue="blue"
				values={{listTypeDefinitionId: 1}}
			/>
		);

		rerender(
			<ListTypeDefaultValueSelect
				{...baseProps}
				defaultValue="blue"
				values={{listTypeDefinitionId: 2}}
			/>
		);

		await waitFor(() => {
			expect(screen.getByRole('combobox')).toBeInTheDocument();
		});

		await act(async () => {
			resolveColors(colorsListTypeEntries);
		});

		expect(screen.getByRole('combobox')).not.toHaveTextContent('Blue');
	});

	it('renders the required field and its error when the picklist has no items', async () => {
		jest.spyOn(
			API,
			'getListTypeDefinitionListTypeEntries'
		).mockResolvedValue([]);

		render(
			<ListTypeDefaultValueSelect
				{...baseProps}
				error="this-field-is-required"
				values={{listTypeDefinitionId: 1}}
			/>
		);

		await waitFor(() => {
			expect(screen.getByRole('combobox')).toBeInTheDocument();
		});

		expect(screen.getByText('default-value')).toBeInTheDocument();
		expect(screen.getByText('this-field-is-required')).toBeInTheDocument();
	});

	it('shows the selected default value when the picklist has items', async () => {
		jest.spyOn(
			API,
			'getListTypeDefinitionListTypeEntries'
		).mockResolvedValue(colorsListTypeEntries as any);

		render(
			<ListTypeDefaultValueSelect
				{...baseProps}
				defaultValue="blue"
				values={{listTypeDefinitionId: 1}}
			/>
		);

		await waitFor(() => {
			expect(screen.getByRole('combobox')).toHaveTextContent('Blue');
		});
	});
});
