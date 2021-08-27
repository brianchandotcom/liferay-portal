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

import deleteItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteItem';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/InfoItemService',
	() => ({
		getPageContents: jest.fn(() => Promise.resolve()),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService',
	() => ({markItemForDeletion: jest.fn(() => Promise.resolve())})
);

const STATE = {
	fragmentEntryLinks: {
		38685: {
			editableValues: {
				portletId: 'com_liferay_blogs_web_portlet_BlogsPortlet',
			},
			fragmentEntryLinkId: '38685',
			name: 'Blogs',
		},
		38687: {
			editableValues: {
				portletId:
					'com_liferay_microblogs_web_portlet_MicroblogsPortlet',
			},
			fragmentEntryLinkId: '38687',
			name: 'Microblogs',
		},
		38688: {
			editableValues: {
				instanceId: '2411',
				portletId: 'com_liferay_microblogs_web_portlet_AnotherPortlet',
			},
			fragmentEntryLinkId: '38688',
			name: 'AnotherPortlet',
		},
	},
	layoutData: {
		items: {
			child1: {
				children: [],
				config: {
					fragmentEntryLinkId: '38687',
				},
				itemId: 'child1',
				parentId: 'container',
				type: 'fragment',
			},

			child2: {
				children: [],
				config: {
					fragmentEntryLinkId: '38685',
				},
				itemId: 'child2',
				parentId: 'container',
				type: 'fragment',
			},
			child3: {
				children: [],
				config: {
					fragmentEntryLinkId: '38688',
				},
				itemId: 'child3',
				parentId: 'container',
				type: 'fragment',
			},
			container: {
				children: ['child1', 'child2', 'child3'],
				config: {},
				itemId: 'id-4',
				parentId: 'id-2',
				type: 'container',
			},

			root: {
				children: ['container'],
				config: {},
				itemId: 'root',
				parentId: '',
				type: 'root',
			},
		},
		rootItems: {
			dropZone: '',
			main: 'root',
		},
		version: 1,
	},
};

describe('deleteItem', () => {
	it('dispatches the delete item action with the portletIds of the removed portlets, if any', async () => {
		const dispatch = jest.fn();

		await deleteItem({itemId: 'container', store: STATE})(
			dispatch,
			() => STATE
		);

		expect(dispatch).toBeCalledWith(
			expect.objectContaining({
				portletIds: [
					'com_liferay_microblogs_web_portlet_MicroblogsPortlet',
					'com_liferay_blogs_web_portlet_BlogsPortlet',
				],
			})
		);
	});
});
