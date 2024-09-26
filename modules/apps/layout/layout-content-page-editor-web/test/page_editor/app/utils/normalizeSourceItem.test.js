/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import normalizeSourceItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/normalizeSourceItem';

const layoutData = {
	items: {
		item01: {
			config: {},
			itemId: 'item01',
			type: 'container',
		},
		item02: {
			config: {
				fragmentEntryLinkId: 'fragmentEntryLink01',
			},
			itemId: 'item02',
			type: 'container',
		},
		item03: {
			config: {
				fragmentEntryLinkId: 'fragmentEntryLink02',
			},
			itemId: 'item03',
			type: 'fragment',
		},
	},
};

const fragmentEntryLinks = {
	fragmentEntryLink01: {
		portletId:
			'com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet',
	},

	fragmentEntryLink02: {
		fieldTypes: ['stepper'],
	},
};

describe('normalizeSourceItem', () => {
	it('normalizes source item', () => {
		expect(
			normalizeSourceItem(
				layoutData.items['item01'],
				layoutData,
				fragmentEntryLinks
			)
		).toStrictEqual({
			config: {},
			fieldTypes: [],
			fragmentEntryType: null,
			isWidget: false,
			itemId: 'item01',
			name: 'container',
			type: 'container',
		});
	});

	it('normalizes source item if the item is a widget', () => {
		expect(
			normalizeSourceItem(
				layoutData.items['item02'],
				layoutData,
				fragmentEntryLinks
			)
		).toStrictEqual(
			expect.objectContaining({
				config: {fragmentEntryLinkId: 'fragmentEntryLink01'},
				fieldTypes: [],
				fragmentEntryType: null,
				isWidget: true,
			})
		);
	});

	it('normalizes source item if the item has field types', () => {
		expect(
			normalizeSourceItem(
				layoutData.items['item03'],
				layoutData,
				fragmentEntryLinks
			)
		).toStrictEqual(
			expect.objectContaining({
				config: {fragmentEntryLinkId: 'fragmentEntryLink02'},
				fieldTypes: ['stepper'],
				fragmentEntryType: null,
				isWidget: false,
			})
		);
	});
});
