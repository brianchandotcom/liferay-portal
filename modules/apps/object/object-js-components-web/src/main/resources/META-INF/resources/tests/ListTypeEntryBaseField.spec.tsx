/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {ListTypeEntryBaseField} from '../components/BaseEntryFields/ListTypeEntryBaseField';

const baseProps = {
	creationLanguageId: 'en_US' as Liferay.Language.Locale,
	label: 'default-value',
	onChange: () => {},
	placeholder: 'choose-an-option',
	required: true,
};

const colorsPicklistItems = [
	{
		externalReferenceCode: 'blue-erc',
		id: 11,
		key: 'blue',
		listTypeDefinitionId: 1,
		name: 'Blue',
		name_i18n: {en_US: 'Blue'},
	},
];

const sizesPicklistItems = [
	{
		externalReferenceCode: 'small-erc',
		id: 21,
		key: 'small',
		listTypeDefinitionId: 2,
		name: 'Small',
		name_i18n: {en_US: 'Small'},
	},
];

describe('ListTypeEntryBaseField', () => {
	it('clears the selected value when switching to a different picklist', () => {
		const {rerender} = render(
			<ListTypeEntryBaseField
				{...baseProps}
				picklistItems={colorsPicklistItems}
				selectedPicklistItemKey="blue"
			/>
		);

		expect(screen.getByRole('combobox')).toHaveTextContent('Blue');

		rerender(
			<ListTypeEntryBaseField
				{...baseProps}
				picklistItems={sizesPicklistItems}
				selectedPicklistItemKey="blue"
			/>
		);

		expect(screen.getByRole('combobox')).not.toHaveTextContent('Blue');
		expect(screen.getByRole('combobox')).toHaveTextContent(
			'choose-an-option'
		);
	});

	it('clears the selected value when the picklist items become empty', () => {
		const {rerender} = render(
			<ListTypeEntryBaseField
				{...baseProps}
				picklistItems={colorsPicklistItems}
				selectedPicklistItemKey="blue"
			/>
		);

		expect(screen.getByRole('combobox')).toHaveTextContent('Blue');

		rerender(
			<ListTypeEntryBaseField
				{...baseProps}
				picklistItems={[]}
				selectedPicklistItemKey="blue"
			/>
		);

		expect(screen.getByRole('combobox')).not.toHaveTextContent('Blue');
		expect(screen.getByRole('combobox')).toHaveTextContent(
			'choose-an-option'
		);
	});

	it('renders an empty select when the picklist has no items', () => {
		render(<ListTypeEntryBaseField {...baseProps} picklistItems={[]} />);

		expect(screen.getByRole('combobox')).toBeInTheDocument();
		expect(screen.getByText('default-value')).toBeInTheDocument();
	});

	it('shows the selected value when the picklist has items', () => {
		render(
			<ListTypeEntryBaseField
				{...baseProps}
				picklistItems={colorsPicklistItems}
				selectedPicklistItemKey="blue"
			/>
		);

		expect(screen.getByRole('combobox')).toHaveTextContent('Blue');
	});
});
