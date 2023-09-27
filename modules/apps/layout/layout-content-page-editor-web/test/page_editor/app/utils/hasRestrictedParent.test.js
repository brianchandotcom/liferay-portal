/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {hasRestrictedParent} from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/hasRestrictedParent';

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

describe('hasRestrictedParent', () => {
	it('checks if the item has a form parent mapped to a item with permissions', () => {
		expect(
			hasRestrictedParent(
				DEFAULT_LAYOUT_DATA.items.fragment1,
				DEFAULT_LAYOUT_DATA
			)
		).toBe(true);

		expect(
			hasRestrictedParent(
				DEFAULT_LAYOUT_DATA.items.fragment2,
				DEFAULT_LAYOUT_DATA
			)
		).toBe(true);

		expect(
			hasRestrictedParent(DEFAULT_LAYOUT_DATA.items.fragment1, {
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
