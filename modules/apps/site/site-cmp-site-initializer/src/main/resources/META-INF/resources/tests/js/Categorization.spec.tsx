/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import Categorization from '../../js/components/project/Categorization';

beforeEach(() => {
	global.fetch = jest.fn().mockResolvedValue({
		json: () => Promise.resolve({id: 42}),
	} as Response);
});

const mockPersonas = [
	{id: 1, name: 'Decision Maker'},
	{id: 2, name: 'Champion'},
];

const mockFunnelStages = [
	{id: 3, name: 'Awareness'},
	{id: 4, name: 'Consideration'},
];

describe('Categorization', () => {
	it('renders the categorization header', () => {
		const {getByText} = render(
			<Categorization
				cmsGroupId={0}
				funnelStagesVocabularyERC="L_CMP_FUNNEL_STAGE"
				hasUpdatePermission={true}
				objectEntryKeywords={[]}
				personasVocabularyERC="L_CMP_PERSONAS"
				selectedFunnelStageCategories={[]}
				selectedPersonaCategories={[]}
			/>
		);

		expect(getByText('categorization')).toBeInTheDocument();
	});

	it('renders initial persona and funnel stage categories as chips', () => {
		const {getByText} = render(
			<Categorization
				cmsGroupId={0}
				funnelStagesVocabularyERC="L_CMP_FUNNEL_STAGE"
				hasUpdatePermission={true}
				objectEntryKeywords={[]}
				personasVocabularyERC="L_CMP_PERSONAS"
				selectedFunnelStageCategories={mockFunnelStages}
				selectedPersonaCategories={mockPersonas}
			/>
		);

		expect(getByText('Decision Maker')).toBeInTheDocument();
		expect(getByText('Champion')).toBeInTheDocument();
		expect(getByText('Awareness')).toBeInTheDocument();
		expect(getByText('Consideration')).toBeInTheDocument();
	});

	it('renders one hidden input per category ID from both selectors', () => {
		const {container} = render(
			<Categorization
				cmsGroupId={0}
				funnelStagesVocabularyERC="L_CMP_FUNNEL_STAGE"
				hasUpdatePermission={true}
				objectEntryKeywords={[]}
				personasVocabularyERC="L_CMP_PERSONAS"
				selectedFunnelStageCategories={mockFunnelStages}
				selectedPersonaCategories={mockPersonas}
			/>
		);

		const hiddenInputs = container.querySelectorAll(
			'input[name="assetCategoryIds"]'
		);

		expect(hiddenInputs).toHaveLength(4);

		const values = Array.from(hiddenInputs).map(
			(input) => (input as HTMLInputElement).value
		);

		expect(values).toEqual(['1', '2', '3', '4']);
	});

	it('renders no hidden inputs when no categories are selected', () => {
		const {container} = render(
			<Categorization
				cmsGroupId={0}
				funnelStagesVocabularyERC="L_CMP_FUNNEL_STAGE"
				hasUpdatePermission={true}
				objectEntryKeywords={[]}
				personasVocabularyERC="L_CMP_PERSONAS"
				selectedFunnelStageCategories={[]}
				selectedPersonaCategories={[]}
			/>
		);

		const hiddenInputs = container.querySelectorAll(
			'input[name="assetCategoryIds"]'
		);

		expect(hiddenInputs).toHaveLength(0);
	});
});
