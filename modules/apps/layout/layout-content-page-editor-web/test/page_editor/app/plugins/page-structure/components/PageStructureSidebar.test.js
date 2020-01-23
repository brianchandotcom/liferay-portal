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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemDefaultConfigurations';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import PageStructureSidebar from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-structure/components/PageStructureSidebar';

import '@testing-library/jest-dom/extend-expect';

const fragmentEntryLinks = {
	'001': {
		content: {
			value: {
				content: '<div>001</div>'
			}
		},
		editableValues: {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {}
		},
		fragmentEntryLinkId: '001',
		name: 'Fragment 1'
	}
};

const layoutData = {
	items: {
		'00-main': {
			children: ['01-container'],
			config: {...LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS.root},
			itemId: '00-main',
			parentId: null,
			type: LAYOUT_DATA_ITEM_TYPES.root
		},
		'01-container': {
			children: ['02-row'],
			config: {},
			itemId: '01-container',
			parentId: 'main',
			type: LAYOUT_DATA_ITEM_TYPES.container
		},
		'02-row': {
			children: ['03-column'],
			config: {},
			itemId: '02-row',
			parentId: '01-container',
			type: LAYOUT_DATA_ITEM_TYPES.row
		},
		'03-column': {
			children: ['04-fragment'],
			config: {},
			itemId: '03-column',
			parentId: '02-row',
			type: LAYOUT_DATA_ITEM_TYPES.column
		},
		'04-fragment': {
			children: [],
			config: {
				fragmentEntryLinkId: '001'
			},
			itemId: '04-fragment',
			parentId: '03-column',
			type: LAYOUT_DATA_ITEM_TYPES.fragment
		}
	},

	rootItems: {main: '00-main'},
	version: 1
};

const RenderPageStructureSidebar = () => {
	return (
		<StoreAPIContextProvider
			getState={() => ({fragmentEntryLinks, layoutData})}
		>
			<PageStructureSidebar />
		</StoreAPIContextProvider>
	);
};

describe('PageStructureSidebar', () => {
	afterEach(cleanup);

	it('renders the first child of main collapsed', () => {
		const {getByText} = render(<RenderPageStructureSidebar />);

		expect(getByText(LAYOUT_DATA_ITEM_TYPES.container)).toBeInTheDocument();
	});
});
