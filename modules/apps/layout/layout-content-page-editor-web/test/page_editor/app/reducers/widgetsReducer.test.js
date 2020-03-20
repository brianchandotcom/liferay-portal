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

import {
	ADD_FRAGMENT_ENTRY_LINKS,
	DELETE_WIDGETS,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/types';
import widgetsReducer from '../../../../src/main/resources/META-INF/resources/page_editor/app/reducers/widgetsReducer';

const widgets = [
	{
		portlets: [
			{
				instanceable: false,
				portletId: 'Blogs',
				title: 'Blogs',
				used: false,
			},
			{
				instanceable: false,
				portletId: 'BlogsAggregator',
				title: 'Blogs Aggregator',
				used: false,
			},
			{
				instanceable: true,
				portletId: 'BlogsCollection',
				title: 'Blogs Collection',
				used: false,
			},
			{
				instanceable: false,
				portletId: 'Images',
				title: 'Image Collection',
				used: true,
			},
		],
		title: 'Collaboration',
	},
];

describe('widgetsReducer', () => {
	it('set used to true to the fragmentEntryLinks that are non-instanciable portlet when adding them', () => {
		const fragmentEntryLinks = [
			{
				editableValues: {
					portletId: 'Blogs',
				},
			},
			{
				editableValues: {
					portletId: 'BlogsAggregator',
				},
			},
			{},
		];

		const nextWidgets = widgetsReducer(widgets, {
			fragmentEntryLinks,
			type: ADD_FRAGMENT_ENTRY_LINKS,
		});

		expect(nextWidgets[0].portlets[0].used).toBe(true);
		expect(nextWidgets[0].portlets[1].used).toBe(true);
		expect(nextWidgets[0].portlets[2].used).toBe(false);
		expect(nextWidgets[0].portlets[3].used).toBe(true);
	});

	it('set used to false to the fragmentEntryLinks that are non-instanciable portlet when removing them', () => {
		const fragmentEntryLinks = [
			{
				editableValues: {
					portletId: 'Images',
				},
			},
			{},
		];

		const nextWidgets = widgetsReducer(widgets, {
			fragmentEntryLinks,
			type: DELETE_WIDGETS,
		});

		expect(nextWidgets[0].portlets[0].used).toBe(false);
		expect(nextWidgets[0].portlets[1].used).toBe(false);
		expect(nextWidgets[0].portlets[2].used).toBe(false);
		expect(nextWidgets[0].portlets[3].used).toBe(false);
	});
});
