/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IItemsActions} from '../../../src/main/resources/META-INF/resources';
import filterItemActions from '../../../src/main/resources/META-INF/resources/utils/actionItems/filterItemActions';

const testActionsWithPermissionKey: IItemsActions[] = [
	{
		data: {
			permissionKey: 'DELETE',
			title: 'Link action sample',
		},
		href: '/o/data-test-endpoint/{id}',
		label: 'Link action sample',
		target: 'link',
	},
	{
		data: {
			permissionKey: 'POST',
			title: 'Another one',
		},
		href: '/home',
		label: 'Another one',
		target: 'link',
	},
];

const testActionsWithRandomCasePermissionKey: IItemsActions[] = [
	{
		data: {
			permissionKey: 'STANDALONEACTION',
			title: 'Stand Alone Action',
		},
		href: '/o/data-test-endpoint/{id}',
		label: 'Stand Alone Action',
		target: 'headless',
	},
];

const testActionsWithoutPermissionKey: IItemsActions[] = [
	{
		href: '/o/data-test-endpoint/{id}',
		label: 'Link action sample',
		target: 'link',
	},
	{
		href: '/home',
		label: 'Another one',
		target: 'link',
	},
];

const availableItemData = {
	actions: {
		delete: {
			href: 'http://someurl/o/data-test-endpoint/fields/38212',
			method: 'DELETE',
		},
		get: {
			href: 'http://someurl/o/data-test-endpoint/fields/38212',
			method: 'GET',
		},
		permissions: {
			href: 'http://someurl/o/data-test-endpoint/fields/38212/permissions',
			method: 'GET',
		},
		replace: {
			href: 'http://someurl/o/data-test-endpoint/fields/38212',
			method: 'PUT',
		},
		standAloneAction: {
			href: 'http://someurl/o/data-test-endpoint/fields/38212',
			method: 'POST',
		},
		update: {
			href: 'http://someurl/o/data-test-endpoint/fields/38212',
			method: 'PATCH',
		},
	},
	creator: {
		additionalName: '',
		contentType: 'UserAccount',
		familyName: 'Test',
		givenName: 'Test',
		id: 2222,
		name: 'Test Test',
	},
	id: 38212,
	label: 'id',
	label_i18n: {
		en_US: 'id',
	},
	name: 'id',
	renderer: 'default',
	sortable: true,
	type: 'integer',
};

describe('filterItemActions', () => {
	describe('when permissionKey is defined for an action', () => {
		it('returns the actions where the permissionKey matches the itemData.actions key', () => {
			const filteredActions = filterItemActions(
				testActionsWithPermissionKey,
				availableItemData
			);

			expect(filteredActions.length).toBeLessThan(
				testActionsWithPermissionKey.length
			);
		});

		it('returns the actions where the permissionKey matches the itemData.actions key regardless of letter case', () => {
			const filteredActions = filterItemActions(
				testActionsWithRandomCasePermissionKey,
				availableItemData
			);

			expect(filteredActions[0].data).toMatchObject(
				testActionsWithRandomCasePermissionKey[0].data
			);
		});
	});

	describe('when permissionKey is not defined for an item', () => {
		it('returns all the actions', () => {
			const filteredActions = filterItemActions(
				testActionsWithoutPermissionKey,
				availableItemData
			);

			expect(filteredActions).toMatchObject(
				testActionsWithoutPermissionKey
			);
		});
	});
});
