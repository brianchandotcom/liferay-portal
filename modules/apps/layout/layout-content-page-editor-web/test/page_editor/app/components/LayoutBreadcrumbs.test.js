/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {LayoutBreadcrumbs} from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/LayoutBreadcrumbs';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {
	ControlsProvider,
	useSelectItem,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

const AutoSelect = ({itemId}) => {
	useSelectItem()(itemId);

	return null;
};

const renderComponent = ({activeItemId}) => {
	return render(
		<StoreMother.Component
			getState={() => ({
				fragmentEntryLinks: {
					'item-1-fragment': {
						name: 'Item 1',
					},
					'item-2-fragment': {
						name: 'Item 2',
					},
					'item-3-fragment': {
						name: 'Item 3',
					},
					'item-4-fragment': {
						name: 'Item 4',
					},
				},
				layoutData: {
					deletedItems: [],
					items: {
						'column': {
							children: ['item-4'],
							config: {},
							itemId: 'column',
							parentId: 'grid',
							type: LAYOUT_DATA_ITEM_TYPES.column,
						},
						'grid': {
							children: ['column'],
							config: {},
							itemId: 'grid',
							type: LAYOUT_DATA_ITEM_TYPES.row,
						},
						'item-1': {
							children: ['item-2'],
							config: {
								fragmentEntryLinkId: 'item-1-fragment',
							},
							itemId: 'item-1',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						'item-2': {
							children: ['item-3'],
							config: {
								fragmentEntryLinkId: 'item-2-fragment',
							},
							itemId: 'item-2',
							parentId: 'item-1',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						'item-3': {
							children: [],
							config: {
								fragmentEntryLinkId: 'item-3-fragment',
							},
							itemId: 'item-3',
							parentId: 'item-2',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						'item-4': {
							children: [],
							config: {
								fragmentEntryLinkId: 'item-4-fragment',
							},
							itemId: 'item-4',
							parentId: 'column',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
					},
				},
			})}
		>
			<ControlsProvider>
				<AutoSelect itemId={activeItemId} />

				<LayoutBreadcrumbs />
			</ControlsProvider>
		</StoreMother.Component>
	);
};

describe('LayoutBreadcrumbs', () => {
	beforeAll(() => {
		const wrapper = document.createElement('div');
		wrapper.setAttribute('id', 'wrapper');
		document.body.appendChild(wrapper);
	});

	afterAll(() => {
		const wrapper = document.getElementById('wrapper');
		document.body.removeChild(wrapper);
	});

	it('renders item in breadcrumbs when selecting it', () => {
		renderComponent({activeItemId: 'item-1'});

		expect(screen.queryAllByText('Item 1')[0]).toBeInTheDocument();
	});

	it('renders ancestors in breadcrumbs when selecting an item', () => {
		renderComponent({activeItemId: 'item-3'});

		expect(screen.queryAllByText('Item 3')[0]).toBeInTheDocument();

		expect(screen.queryAllByText('Item 2')[0]).toBeInTheDocument();

		expect(screen.queryAllByText('Item 1')[0]).toBeInTheDocument();
	});

	it('does not render children in breadcrumbs when selecting an item', () => {
		renderComponent({activeItemId: 'item-2'});

		expect(screen.queryByText('Item 3')).not.toBeInTheDocument();
	});

	it('does not render columns in breadcrumbs even if they are in the path', () => {
		renderComponent({activeItemId: 'item-4'});

		expect(screen.queryAllByText('Item 4')[0]).toBeInTheDocument();
		expect(screen.queryAllByText('grid')[0]).toBeInTheDocument();

		expect(screen.queryByText('column')).not.toBeInTheDocument();
	});
});
