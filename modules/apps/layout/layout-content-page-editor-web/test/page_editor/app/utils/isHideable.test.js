/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isHideable from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/isHideable';

const layoutData = {
	items: {
		form01: {
			children: ['input01', 'heading01'],
			itemId: 'form01',
			parentId: 'root',
			type: 'form',
		},
		heading01: {
			children: [],
			config: {
				fragmentEntryLinkId: 'fragmentEntryLink02',
			},
			itemId: 'heading01',
			parentId: 'form01',
			type: 'fragment',
		},
		input01: {
			children: [],
			config: {
				fragmentEntryLinkId: 'fragmentEntryLink01',
			},
			itemId: 'input01',
			parentId: 'form01',
			type: 'fragment',
		},
	},
};

const fragmentEntryLinks = {
	fragmentEntryLink01: {
		fragmentEntryType: 'input',
	},
	fragmentEntryLink02: {
		fragmentEntryType: 'component',
	},
};

describe('isHideable', () => {
	it('item of type other than "fragment"', () => {
		expect(
			isHideable(
				layoutData.items['form01'],
				fragmentEntryLinks,
				layoutData
			)
		).toBe(true);
	});

	it('fragment type item with fragmentEntryLinks of type "component" is hideable', () => {
		expect(
			isHideable(
				layoutData.items['heading01'],
				fragmentEntryLinks,
				layoutData
			)
		).toBe(true);
	});

	it('fragment type item with fragmentEntryLinks of type "input" is not hideable', () => {
		expect(
			isHideable(
				layoutData.items['input01'],
				fragmentEntryLinks,
				layoutData
			)
		).toBe(false);
	});
});
