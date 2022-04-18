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
import {act, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import {HideFromSearchField} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/fragment-configuration-fields/HideFromSearchField';
import {useSelectItem} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import updateItemConfig from '../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';

const DEFAULT_ITEM = {
	config: {},
	itemId: 'item-id',
	parentId: 'parent-id',
};

const DEFAULT_LAYOUT_DATA = {
	items: {},
};

const LAYOUT_DATA_WITH_HIDDEN_PARENT = {
	items: {
		'parent-id': {
			config: {
				indexed: false,
			},
			itemId: 'parent-id',
		},
	},
};

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => jest.fn()
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext',
	() => {
		const selectItem = jest.fn();

		return {
			useSelectItem: () => selectItem,
		};
	}
);

const renderComponent = ({
	item = DEFAULT_ITEM,
	layoutData = DEFAULT_LAYOUT_DATA,
} = {}) =>
	render(
		<StoreAPIContextProvider
			dispatch={() => {}}
			getState={() => ({
				layoutData,
			})}
		>
			<HideFromSearchField item={item} layoutData={layoutData} />
		</StoreAPIContextProvider>
	);

describe('TextField', () => {
	it('calls dispatch method with selected value for Hide From Search checkbox', async () => {
		renderComponent();

		const checkbox = screen.getByLabelText('hide-from-site-search-results');

		await act(async () => {
			fireEvent.click(checkbox);
		});

		expect(updateItemConfig).toBeCalledWith(
			expect.objectContaining({
				itemConfig: {
					indexed: false,
				},
			})
		);
	});

	it('renders checkbox disabled and checked when parent is hidden', async () => {
		renderComponent({layoutData: LAYOUT_DATA_WITH_HIDDEN_PARENT});

		const checkbox = screen.getByLabelText('hide-from-site-search-results');

		expect(checkbox).toBeDisabled();
		expect(checkbox).toBeChecked();
	});

	it('allows going to parent fragment when parent is hidden', async () => {
		renderComponent({layoutData: LAYOUT_DATA_WITH_HIDDEN_PARENT});

		const button = screen.getByText('go-to-parent-fragment-to-edit');
		const selectItem = useSelectItem();

		fireEvent.click(button);

		expect(selectItem).toBeCalledWith('parent-id');
	});
});
