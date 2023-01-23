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

import {hasRestrictedFormParent} from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/hasRestrictedFormParent';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			formTypes: [
				{
					isRestricted: true,
					label: 'Form Type 1',
					value: '11111',
				},
				{
					isRestricted: false,
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

describe('hasRestrictedFormParent', () => {
	it('checks if the item has a form parent mapped to a item with permissions', () => {
		expect(
			hasRestrictedFormParent(
				DEFAULT_LAYOUT_DATA.items.fragment1,
				DEFAULT_LAYOUT_DATA
			)
		).toBe(true);

		expect(
			hasRestrictedFormParent(
				DEFAULT_LAYOUT_DATA.items.fragment2,
				DEFAULT_LAYOUT_DATA
			)
		).toBe(true);

		expect(
			hasRestrictedFormParent(DEFAULT_LAYOUT_DATA.items.fragment1, {
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
