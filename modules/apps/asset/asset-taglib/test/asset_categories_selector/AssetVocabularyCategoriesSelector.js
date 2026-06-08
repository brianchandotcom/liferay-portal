/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {useResource} from '@clayui/data-provider';
import {render} from '@testing-library/react';
import React from 'react';

import AssetVocabularyCategoriesSelector from '../../src/main/resources/META-INF/resources/js/asset_categories_selector/AssetVocabularyCategoriesSelector';

const DEFAULT_PROPS = {
	eventName: 'selectCategory',
	groupIds: [],
	inputName: '',
	portletURL: '',
};

let capturedProps;

jest.mock('@clayui/multi-select', () => ({
	__esModule: true,
	default: (props) => {
		capturedProps = props;

		return null;
	},
}));

jest.mock('@clayui/data-provider', () => {
	const originalModule = jest.requireActual('@clayui/data-provider');

	return {
		__esModule: true,
		...originalModule,
		useResource: jest.fn().mockImplementation(() => ({
			refetch: jest.fn(),
			resource: [],
		})),
	};
});

describe('AssetVocabularyCategoriesSelector', () => {
	beforeEach(() => {
		capturedProps = undefined;
	});

	it('refetch is not called in the first component render', () => {
		render(<AssetVocabularyCategoriesSelector {...DEFAULT_PROPS} />);

		const {refetch} = useResource();

		expect(refetch).not.toHaveBeenCalled();
	});

	it('gives the combobox an accessible name via aria-labelledby', () => {
		const {container} = render(
			<AssetVocabularyCategoriesSelector
				{...DEFAULT_PROPS}
				inputName="assetCategoryIds_42"
				label="My Vocabulary"
			/>
		);

		const labelId = 'assetCategoryIds_42_MultiSelectLabel';

		expect(capturedProps['aria-labelledby']).toBe(labelId);

		const label = container.querySelector(`#${labelId}`);

		expect(label).toBeInTheDocument();
		expect(label.tagName).toBe('LABEL');
		expect(label).toHaveAttribute('for', 'assetCategoryIds_42_MultiSelect');
		expect(label).toHaveTextContent('My Vocabulary');
	});

	it('does not set aria-labelledby when there is no label', () => {
		render(<AssetVocabularyCategoriesSelector {...DEFAULT_PROPS} />);

		expect(capturedProps['aria-labelledby']).toBeUndefined();
	});
});
