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
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import {
	ControlsProvider,
	useSelectItem,
} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/Controls';
import {CollectionWithControls} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/store';

const renderCollection = ({
	isActive = true,
	viewportSize = VIEWPORT_SIZES.desktop,
	lockedSegment = false,
	hasUpdatePermission = true,
} = {}) => {
	const collection = {
		children: [],
		config: {},
		itemId: 'collection',
		parentId: null,
		type: LAYOUT_DATA_ITEM_TYPES.collection,
	};

	const layoutData = {
		items: {collection},
	};

	const AutoSelector = () => {
		useSelectItem()(isActive ? 'collection' : null);

		return null;
	};

	return render(
		<DndProvider backend={HTML5Backend}>
			<ControlsProvider>
				<StoreAPIContextProvider
					dispatch={() => {}}
					getState={() => ({
						permissions: {
							LOCKED_SEGMENTS_EXPERIMENT: lockedSegment,
							UPDATE: hasUpdatePermission,
						},
						selectedViewportSize: viewportSize,
					})}
				>
					<AutoSelector />
					<CollectionWithControls
						item={collection}
						layoutData={layoutData}
					/>
				</StoreAPIContextProvider>
			</ControlsProvider>
		</DndProvider>
	);
};

describe('CollectionWithControls', () => {
	afterEach(cleanup);

	it('shows a no-collection-selected message', () => {
		const {getByText} = renderCollection();

		expect(getByText('no-collection-selected-yet')).toBeInTheDocument();
	});

	it('hides FloatingToolbar if item is not active', async () => {
		const {queryByTitle} = renderCollection({isActive: false});

		expect(queryByTitle('duplicate')).toBe(null);
		expect(queryByTitle('collection-display-configuration')).toBe(null);
	});

	it('removes all buttons if user has no permissions', () => {
		const {queryByTitle} = renderCollection({hasUpdatePermission: false});

		expect(queryByTitle('duplicate')).toBe(null);
		expect(queryByTitle('collection-display-configuration')).toBe(null);
	});

	it('removes all buttons if experience is locked', () => {
		const {queryByTitle} = renderCollection({lockedSegment: true});

		expect(queryByTitle('duplicate')).toBe(null);
		expect(queryByTitle('collection-display-configuration')).toBe(null);
	});

	it('removes duplicate button if viewport is not desktop', () => {
		const {queryByTitle} = renderCollection({
			viewportSize: "Sandro's Phone",
		});

		expect(queryByTitle('duplicate')).toBe(null);
	});
});
