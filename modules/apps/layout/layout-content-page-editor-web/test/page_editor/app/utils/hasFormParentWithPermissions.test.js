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

import {hasFormParentWithPermissions} from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/hasFormParentWithPermissions';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			formTypes: [
				{
					hasPermission: true,
					label: 'Form Type 1',
					value: '11111',
				},
				{
					hasPermission: false,
					label: 'Form Type 2',
					value: '22222',
				},
			],
		},
	})
);

const DEFAULT_LAYOUT_DATA = {
	items: {
		form: {
			children: ['fragment'],
			config: {
				classNameId: '11111',
				classTypeId: '0',
			},
			itemId: 'form',
			parentId: '',
			type: 'form',
		},
		fragment1: {
			children: [],
			config: {},
			itemId: 'fragment1',
			parentId: 'form',
			type: 'fragment',
		},
		fragment2: {
			children: [],
			config: {},
			itemId: 'fragment2',
			parentId: 'fragment1',
			type: 'fragment',
		},
	},
};

describe('hasFormParentWithPermissions', () => {
	it('checks if the item has a form parent mapped to a item with permissions', () => {
		expect(
			hasFormParentWithPermissions(
				DEFAULT_LAYOUT_DATA.items.fragment1,
				DEFAULT_LAYOUT_DATA
			)
		).toBe(true);

		expect(
			hasFormParentWithPermissions(
				DEFAULT_LAYOUT_DATA.items.fragment2,
				DEFAULT_LAYOUT_DATA.items
			)
		).toBe(true);

		expect(
			hasFormParentWithPermissions(DEFAULT_LAYOUT_DATA.items.fragment1, {
				items: {
					...DEFAULT_LAYOUT_DATA.items,
					form: {
						...DEFAULT_LAYOUT_DATA.items.form,
						config: {
							classNameId: '22222',
							classTypeId: '0',
						},
					},
				},
			})
		).toBe(false);
	});
});
