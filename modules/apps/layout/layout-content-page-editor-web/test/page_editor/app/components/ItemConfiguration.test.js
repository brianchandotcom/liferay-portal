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
import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import ItemConfiguration from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/ItemConfiguration';
import {ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {useSelectItem} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext',
	() => {
		const selectItem = jest.fn();

		return {
			useSelectItem: () => selectItem,
		};
	}
);

const STATE = {
	fragmentEntryLinks: {
		fragmentEntryLinkId1: {
			editableTypes: {
				editableId1: 'text',
			},
		},
	},
	layoutData: {
		items: {
			fragmentItemId1: {
				children: [],
				config: {
					fragmentEntryLinkId: 'fragmentEntryLinkId1',
				},
				itemId: 'fragmentItemId1',
				type: LAYOUT_DATA_ITEM_TYPES.fragment,
			},
		},
	},
	permissions: {
		UPDATE: true,
	},
};

const renderComponent = ({activeItemId, activeItemType}) =>
	render(
		<StoreAPIContextProvider dispatch={() => {}} getState={() => STATE}>
			<ItemConfiguration
				activeItemId={activeItemId}
				activeItemType={activeItemType}
			/>
		</StoreAPIContextProvider>
	);

describe('ItemConfiguration', () => {
	it('shows back button when active item is an editable', async () => {
		renderComponent({
			activeItemId: 'fragmentEntryLinkId1-editableId1',
			activeItemType: ITEM_TYPES.editable,
		});

		expect(
			screen.getByTitle('back-to-parent-configuration')
		).toBeInTheDocument();
	});

	it('select parent fragment when back button is clicked', async () => {
		renderComponent({
			activeItemId: 'fragmentEntryLinkId1-editableId1',
			activeItemType: ITEM_TYPES.editable,
		});

		const backButton = screen.getByTitle('back-to-parent-configuration');

		const selectItem = useSelectItem();

		fireEvent.click(backButton);

		expect(selectItem).toBeCalledWith('fragmentItemId1');
	});

	it('does not show back button when active item is not an editable', async () => {
		renderComponent({
			activeItemId: 'fragmentItemId1',
			activeItemType: LAYOUT_DATA_ITEM_TYPES.fragment,
		});

		expect(
			screen.queryByTitle('back-to-parent-configuration')
		).not.toBeInTheDocument();
	});
});
