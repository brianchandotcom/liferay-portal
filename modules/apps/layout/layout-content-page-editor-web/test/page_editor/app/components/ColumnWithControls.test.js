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
import {cleanup} from '@testing-library/react';

import {updateNewLayoutDataContext} from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items/ColumnWithControls';

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
