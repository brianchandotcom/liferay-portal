/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import ItemSelector from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/ItemSelector';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			infoItemSelectorUrl: 'infoItemSelectorUrl',
			portletNamespace: 'portletNamespace',
		},
	})
);

function renderItemSelector({selectedItemTitle = ''}) {
	const state = {
		mappedInfoItems: [],
	};

	return render(
		<StoreAPIContextProvider dispatch={() => {}} getState={() => state}>
			<ItemSelector
				label="itemSelectorLabel"
				onItemSelect={() => {}}
				selectedItemTitle={selectedItemTitle}
			/>
		</StoreAPIContextProvider>
	);
}

describe('ItemSelector', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders correctly', () => {
		const {getByText} = renderItemSelector({});

		expect(getByText('itemSelectorLabel')).toBeInTheDocument();
	});

	it('shows selected item title correctly when receiving it in props', () => {
		const selectedItemTitle = 'itemTitle';

		const {getByLabelText} = renderItemSelector({
			selectedItemTitle,
		});

		expect(getByLabelText('itemSelectorLabel')).toHaveValue(
			selectedItemTitle
		);
	});

	it('does not show any title when not receiving it in props', () => {
		const {getByLabelText} = renderItemSelector({});

		expect(getByLabelText('itemSelectorLabel')).toBeEmpty();
	});
});
