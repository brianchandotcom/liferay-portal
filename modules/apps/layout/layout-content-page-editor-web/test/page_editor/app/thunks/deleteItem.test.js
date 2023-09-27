/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	() => ({
		markItemForDeletion: jest.fn(() =>
			Promise.resolve({
				layoutData: {
					items: {container: {children: []}},
					rootItems: {main: 'container'},
				},
			})
		),
	})
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
					'com_liferay_microblogs_web_portlet_AnotherPortlet_INSTANCE_2411',
				],
			})
		);
	});
});
