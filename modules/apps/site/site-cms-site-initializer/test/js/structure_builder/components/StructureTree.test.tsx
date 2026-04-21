/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	flatItemIds,
	getRangeItems,
} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/components/StructureTree';
import {Uuid} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/types/Uuid';

const uuid = (id: string) => id as Uuid;

const ROOT = uuid('root');

const items = [
	{
		children: [
			{id: uuid('a')},
			{
				children: [{id: uuid('b1')}, {id: uuid('b2')}],
				id: uuid('b'),
			},
			{id: uuid('c')},
		],
		id: ROOT,
	},
];

beforeEach(() => {
	(globalThis as any).Liferay = {
		...(globalThis as any).Liferay,
		Browser: {isMac: () => false},
	};
});

it('flatItemIds flattens ids in depth-first order including nested children', () => {
	expect(flatItemIds(items)).toEqual([
		ROOT,
		uuid('a'),
		uuid('b'),
		uuid('b1'),
		uuid('b2'),
		uuid('c'),
	]);
});

it('getRangeItems returns items between the anchor and the target going downwards', () => {
	expect(
		getRangeItems({
			items,
			rootId: ROOT,
			selection: [uuid('a')],
			targetId: uuid('b2'),
		})
	).toEqual([uuid('a'), uuid('b'), uuid('b1'), uuid('b2')]);
});

it('getRangeItems returns items between the anchor and the target when the target is above the anchor', () => {
	expect(
		getRangeItems({
			items,
			rootId: ROOT,
			selection: [uuid('b2')],
			targetId: uuid('a'),
		})
	).toEqual([uuid('a'), uuid('b'), uuid('b1'), uuid('b2')]);
});

it('getRangeItems uses the last selected item as the anchor', () => {
	expect(
		getRangeItems({
			items,
			rootId: ROOT,
			selection: [uuid('c'), uuid('a')],
			targetId: uuid('b'),
		})
	).toEqual([uuid('a'), uuid('b')]);
});

it('getRangeItems returns only the target when anchor and target are the same', () => {
	expect(
		getRangeItems({
			items,
			rootId: ROOT,
			selection: [uuid('b1')],
			targetId: uuid('b1'),
		})
	).toEqual([uuid('b1')]);
});
