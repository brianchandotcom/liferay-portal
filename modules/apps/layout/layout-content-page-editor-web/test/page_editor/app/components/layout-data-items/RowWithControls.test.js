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
import {cleanup, queryByText, render} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {RowWithControls} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {
	ControlsProvider,
	useSelectItem,
} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			frontendTokens: {},
		},
	})
);

const renderRow = ({
	activeItemId = 'row',
	columnConfiguration = [],
	hasUpdatePermissions = true,
	lockedExperience = false,
	viewportSize = VIEWPORT_SIZES.desktop,
} = {}) => {
	const childrenItems = {};

	columnConfiguration.forEach(({size, children = []}, index) => {
		const itemId = `column${index}`;

		childrenItems[itemId] = {
			children,
			config: {
				size,
				styles: {},
			},
			itemId,
			parentId: 'row',
			type: LAYOUT_DATA_ITEM_TYPES.col,
		};
	});

	const row = {
		children: Object.keys(childrenItems),
		config: {styles: {}},
		itemId: 'row',
		parentId: null,
		type: LAYOUT_DATA_ITEM_TYPES.row,
	};

	const child = {
		children: [],
		config: {styles: {}},
		itemId: 'child',
		parentId: null,
		type: LAYOUT_DATA_ITEM_TYPES.container,
	};

	const layoutData = {
		items: {child, row, ...childrenItems},
	};

	const AutoSelect = () => {
		useSelectItem()(activeItemId);

		return null;
	};

	return render(
		<DndProvider backend={HTML5Backend}>
			<ControlsProvider>
				<StoreAPIContextProvider
					getState={() => ({
						layoutData,
						permissions: {
							LOCKED_SEGMENTS_EXPERIMENT: lockedExperience,
							UPDATE: hasUpdatePermissions,
						},
						selectedViewportSize: viewportSize,
					})}
				>
					<AutoSelect />
					<RowWithControls item={row} layoutData={layoutData} />
				</StoreAPIContextProvider>
			</ControlsProvider>
		</DndProvider>
	);
};

describe('RowWithControls', () => {
	afterEach(cleanup);

	it('does not add row class if user has no permissions', () => {
		const {baseElement} = renderRow({hasUpdatePermissions: false});

		expect(baseElement.querySelector('.page-editor__row')).toBe(null);
	});

	it('does not allow deleting or duplicating the row if user has no permissions', () => {
		const {baseElement} = renderRow({hasUpdatePermissions: false});

		expect(queryByText(baseElement, 'delete')).not.toBeInTheDocument();
		expect(queryByText(baseElement, 'duplicate')).not.toBeInTheDocument();
	});

	it('does include the empty class when having one row and columns are empty', () => {
		const {baseElement} = renderRow({
			columnConfiguration: [{size: 4}, {size: 4}, {size: 4}],
		});

		expect(
			baseElement.querySelector('.page-editor__row.empty')
		).toBeInTheDocument();
	});

	it('does not include the empty class when having one row and one column is not empty', () => {
		const {baseElement} = renderRow({
			columnConfiguration: [
				{children: ['child'], size: 4},
				{size: 4},
				{size: 4},
			],
		});

		expect(baseElement.querySelector('.page-editor__row.empty')).toBe(null);
	});

	it('does not include the empty class when having several rows and some columns from one row are empty', () => {
		const {baseElement} = renderRow({
			columnConfiguration: [
				{children: ['child'], size: 6},
				{size: 6},
				{size: 4},
				{size: 4},
				{size: 4},
			],
		});

		expect(
			baseElement.querySelector('.page-editor__row.empty')
		).toBeInTheDocument();
	});
});
