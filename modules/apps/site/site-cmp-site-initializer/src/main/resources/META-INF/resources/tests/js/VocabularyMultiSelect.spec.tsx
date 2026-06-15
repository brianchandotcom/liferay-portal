/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import VocabularyMultiSelect from '../../js/components/VocabularyMultiSelect';

jest.mock('../../js/utils/api', () => ({
	getVocabularyByExternalReferenceCode: jest
		.fn()
		.mockResolvedValue({data: {id: 42}}),
}));

beforeEach(() => {
	global.fetch = jest.fn().mockResolvedValue({
		json: () => Promise.resolve({id: 42}),
	} as Response);
});

const mockCategories = [
	{id: 1, name: 'Decision Maker'},
	{id: 2, name: 'Champion'},
];

describe('VocabularyMultiSelect', () => {
	it('renders initial selected categories as chips', () => {
		const {getByText} = render(
			<VocabularyMultiSelect
				disabled={false}
				label="Personas"
				onChange={jest.fn()}
				placeholder="Add Personas"
				value={mockCategories}
				vocabularyERC="L_CMP_PERSONAS"
			/>
		);

		expect(getByText('Decision Maker')).toBeInTheDocument();
		expect(getByText('Champion')).toBeInTheDocument();
	});

	it('renders the label', () => {
		const {getByText} = render(
			<VocabularyMultiSelect
				disabled={false}
				label="Personas"
				onChange={jest.fn()}
				placeholder="Add Personas"
				value={[]}
				vocabularyERC="L_CMP_PERSONAS"
			/>
		);

		expect(getByText('Personas')).toBeInTheDocument();
	});

	it('calls onChange with remaining items when a chip is removed', () => {
		const onChange = jest.fn();

		const {getAllByLabelText} = render(
			<VocabularyMultiSelect
				disabled={false}
				label="Personas"
				onChange={onChange}
				placeholder="Add Personas"
				value={mockCategories}
				vocabularyERC="L_CMP_PERSONAS"
			/>
		);

		fireEvent.click(getAllByLabelText('remove')[0]);

		expect(onChange).toHaveBeenCalledWith([{id: 2, name: 'Champion'}]);
	});

	it('disables remove buttons when disabled is true', () => {
		const {getAllByLabelText} = render(
			<VocabularyMultiSelect
				disabled={true}
				label="Personas"
				onChange={jest.fn()}
				placeholder="Add Personas"
				value={mockCategories}
				vocabularyERC="L_CMP_PERSONAS"
			/>
		);

		getAllByLabelText('remove').forEach((button) => {
			expect(button).toBeDisabled();
		});
	});
});
