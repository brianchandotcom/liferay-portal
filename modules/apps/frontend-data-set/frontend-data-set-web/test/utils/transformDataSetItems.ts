/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import transformDataSetItems from '../../src/main/resources/META-INF/resources/utils/transformDataSetItems';

describe('transformDataSetItems utility', () => {
	it('copies embedded.actions to a top-level actions property', () => {
		const actions = {
			delete: {href: '/o/delete', method: 'DELETE'},
			versions: {href: '/o/versions', method: 'GET'},
		};

		const [item] = transformDataSetItems([
			{embedded: {actions, id: 1}, score: 1},
		]);

		expect(item.actions).toBe(actions);
	});

	it('leaves items without embedded.actions unchanged in shape', () => {
		const [item] = transformDataSetItems([{embedded: {id: 1}, score: 1}]);

		expect(item).toEqual({embedded: {id: 1}, score: 1});
		expect(item).not.toHaveProperty('actions');
	});

	it('does not mutate the original items or their embedded objects', () => {
		const actions = {
			versions: {href: '/o/versions', method: 'GET'},
		};
		const original = {embedded: {actions, id: 1}, score: 1};

		transformDataSetItems([original]);

		expect(original.embedded.actions).toBe(actions);
		expect(original).not.toHaveProperty('actions');
	});

	it('is idempotent: running twice produces the same shape', () => {
		const actions = {
			versions: {href: '/o/versions', method: 'GET'},
		};
		const input = [{embedded: {actions, id: 1}, score: 1}];

		const once = transformDataSetItems(input);
		const twice = transformDataSetItems(input);

		expect(once).toEqual(twice);
		expect(once[0].actions).toBe(actions);
		expect(twice[0].actions).toBe(actions);
	});
});
