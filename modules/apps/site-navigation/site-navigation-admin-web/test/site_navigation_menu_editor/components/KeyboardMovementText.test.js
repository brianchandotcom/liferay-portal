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

import {getMovementText} from '../../../src/main/resources/META-INF/resources/site_navigation_menu_editor/components/KeyboardMovementText';
import getFlatItems from '../../../src/main/resources/META-INF/resources/site_navigation_menu_editor/utils/getFlatItems';

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	sub: jest.fn((langKey, arg) => langKey.replace('x', arg)),
}));

const ITEMS = getFlatItems([
	{
		children: [
			{
				children: [],
				parentSiteNavigationMenuItemId: 'parent',
				siteNavigationMenuItemId: 'child1',
				title: 'Child 1',
			},
			{
				children: [],
				parentSiteNavigationMenuItemId: 'parent',
				siteNavigationMenuItemId: 'child2',
				title: 'Child 2',
			},
			{
				children: [],
				parentSiteNavigationMenuItemId: 'parent',
				siteNavigationMenuItemId: 'child3',
				title: 'Child 3',
			},
			{
				children: [],
				parentSiteNavigationMenuItemId: 'parent',
				siteNavigationMenuItemId: 'child4',
				title: 'Child 4',
			},
		],
		parentSiteNavigationMenuItemId: '0',
		siteNavigationMenuItemId: 'parent',
		title: 'Parent',
	},
]);

describe('KeyboardMovementText', () => {
	it('explains how to use the keys when activating it', () => {
		const text = getMovementText(
			{
				eventKey: 'Enter',
				menuItemTitle: 'pagina',
				menuItemType: 'layout',
			},
			ITEMS
		);

		expect(text).toBe(
			'use-up-and-down-arrows-to-move-pagina (layout)-and-press-enter-to-place-it-in-desired-position'
		);
	});

	it('explains that is it going to be placed as a child of another item', () => {
		const text = getMovementText(
			{
				menuItemTitle: 'pagina',
				menuItemType: 'layout',
				parentSiteNavigationMenuItemId: 'child1',
			},
			ITEMS
		);

		expect(text).toBe('targeting-inside-of-Child 1');
	});

	it('explains that is it going to be placed on top of another item', () => {
		const text = getMovementText(
			{
				menuItemTitle: 'pagina',
				menuItemType: 'layout',
				order: 0,
				parentSiteNavigationMenuItemId: 'parent',
			},
			ITEMS
		);

		expect(text).toBe('targeting-top,Child 1-of-x');
	});

	it('explains that is it going to be placed on the bottom of another item', () => {
		const text = getMovementText(
			{
				menuItemTitle: 'pagina',
				menuItemType: 'layout',
				order: 1,
				parentSiteNavigationMenuItemId: 'parent',
			},
			ITEMS
		);

		expect(text).toBe('targeting-bottom,Child 1-of-x');
	});
});
