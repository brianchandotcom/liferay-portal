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
import {HTML5Backend} from 'react-dnd-html5-backend';

import {ColumnWithControls} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items';
import {updateNewLayoutDataContext} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items/ColumnWithControls';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {
	ControlsProvider,
	useSelectItem,
} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';

const LAYOUT_DATA = {
	deletedItems: [],
	items: {
		'column-1': {
			config: {
				mobile: {},
				size: 4,
				tablet: {},
			},
		},
		'column-2': {
			config: {
				mobile: {},
				size: 4,
				tablet: {},
			},
		},
		'column-3': {
			config: {
				mobile: {},
				size: 4,
				tablet: {},
			},
		},
	},
};

const renderColumn = ({
	activeItemId = 'row',
	columnIndex = 1,
	hasUpdatePermissions = true,
	lockedExperience = false,
} = {}) => {
	const columnA = {
		children: [],
		config: {},
		itemId: 'columnA',
		parentId: 'row',
		type: LAYOUT_DATA_ITEM_TYPES.column,
	};

	const columnB = {
		children: [],
		config: {},
		itemId: 'columnB',
		parentId: 'row',
		type: LAYOUT_DATA_ITEM_TYPES.column,
	};

	const row = {
		children: ['columnA', 'columnB'],
		config: {},
		itemId: 'row',
		parentId: null,
		type: LAYOUT_DATA_ITEM_TYPES.row,
	};

	const layoutData = {
		items: {columnA, columnB, row},
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
						selectedViewportSize: VIEWPORT_SIZES.desktop,
					})}
				>
					<AutoSelect />
					<ColumnWithControls
						item={columnIndex ? columnB : columnA}
					/>
				</StoreAPIContextProvider>
			</ControlsProvider>
		</DndProvider>
	);
};

describe('ColumnWithControls', () => {
	it('hides resize handler if parent row is not active', () => {
		const {queryByTitle} = renderColumn({activeItemId: 'nothing'});

		expect(queryByTitle('resize-column')).toBe(null);
	});

	it('hides resize handler if user has no permissions', () => {
		const {queryByTitle} = renderColumn({hasUpdatePermissions: false});

		expect(queryByTitle('resize-column')).toBe(null);
	});

	it('hides resize handler for first column', () => {
		const {queryByTitle} = renderColumn({columnIndex: 0});

		expect(queryByTitle('resize-column')).toBe(null);
	});

	describe('updateNewLayoutDataContext', () => {
		afterEach(() => {
			cleanup();
		});

		it('allows changing module width for a viewport', async () => {
			const columnConfig = {
				'column-1': {
					config: {
						mobile: {},
						size: 4,
						tablet: {},
					},
					size: 7,
				},
				'column-2': {
					config: {
						mobile: {},
						size: 4,
						tablet: {},
					},
					size: 1,
				},
			};

			expect(
				updateNewLayoutDataContext(LAYOUT_DATA, columnConfig, 'desktop')
			).toEqual({
				...LAYOUT_DATA,
				items: {
					...LAYOUT_DATA.items,
					'column-1': {
						config: {
							mobile: {},
							size: 7,
							tablet: {},
						},
					},
					'column-2': {
						config: {
							mobile: {},
							size: 1,
							tablet: {},
						},
					},
				},
			});
		});

		it('allows changing module without affecting previous viewports', async () => {
			const columnConfig = {
				'column-1': {
					config: {
						mobile: {},
						size: 4,
						tablet: {size: 8},
					},
					size: 6,
				},
				'column-2': {
					config: {
						mobile: {},
						size: 4,
						tablet: {size: 2},
					},
					size: 3,
				},
			};

			expect(
				updateNewLayoutDataContext(LAYOUT_DATA, columnConfig, 'mobile')
			).toEqual({
				...LAYOUT_DATA,
				items: {
					...LAYOUT_DATA.items,
					'column-1': {
						config: {
							mobile: {size: 6},
							size: 4,
							tablet: {size: 8},
						},
					},
					'column-2': {
						config: {
							mobile: {size: 3},
							size: 4,
							tablet: {size: 2},
						},
					},
				},
			});
		});

		it('allows changing previously modified module without affecting the rest of viewports', async () => {
			const columnConfig = {
				'column-1': {
					config: {
						mobile: {size: 3},
						size: 4,
						tablet: {size: 2},
					},
					size: 1,
				},
				'column-2': {
					config: {
						mobile: {size: 6},
						size: 4,
						tablet: {size: 8},
					},
					size: 2,
				},
			};

			expect(
				updateNewLayoutDataContext(LAYOUT_DATA, columnConfig, 'tablet')
			).toEqual({
				...LAYOUT_DATA,
				items: {
					...LAYOUT_DATA.items,
					'column-1': {
						config: {
							mobile: {size: 3},
							size: 4,
							tablet: {size: 1},
						},
					},
					'column-2': {
						config: {
							mobile: {size: 6},
							size: 4,
							tablet: {size: 2},
						},
					},
				},
			});
		});
	});
});
