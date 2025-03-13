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

const testActionsWithPermissionKeyAndFilters: IItemsActions[] = [
	{
		data: {
			filters: [{key: 'color', value: 'green'}],
			permissionKey: 'UPDATE',
		},
		href: '/o/data-test-endpoint/{id}',
		label: 'Action with color filter & permissionKey',
		target: 'link',
	},
	{
		data: {
			filters: [{key: 'color', value: 'blue'}],
			permissionKey: 'UPDATE',
		},
		href: '/o/data-test-endpoint/{id}',
		label: 'Action with color filter & permissionKey',
		target: 'link',
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
	color: 'blue',
	creator: {
		additionalName: '',
		contentType: 'UserAccount',
		familyName: 'Test',
		givenName: 'Test',
		id: 2222,
		name: 'Test Test',
	},
	dateCreated: '2025-03-13T08:27:46Z',
	id: 38212,
	label: 'id',
	label_i18n: {
		en_US: 'id',
	},
	name: 'id',
	rating: 3,
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
				testActionsWithRandomCasePermissionKey[0].data!
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

	describe('when permissionKey and action filters are defined for an action item', () => {
		it('returns only the action that matches the permissionKey and the action filter criteria', () => {
			const filteredActions = filterItemActions(
				testActionsWithPermissionKeyAndFilters,
				availableItemData
			);

			expect(filteredActions.length).toEqual(1);
			expect(filteredActions[0]).toMatchObject(
				testActionsWithPermissionKeyAndFilters[1]
			);
		});
	});

	describe('when only action filters are defined for an action item', () => {
		it('returns only the action that matches the action filter criteria', () => {
			const testActionsWithOnlyFilters: IItemsActions[] =
				testActionsWithPermissionKeyAndFilters.map((action) => {
					delete action.data?.permissionKey;

					return {
						...action,
					};
				});

			const filteredActions = filterItemActions(
				testActionsWithOnlyFilters,
				availableItemData
			);

			expect(filteredActions.length).toEqual(1);

			expect(filteredActions[0]).toMatchObject(
				testActionsWithOnlyFilters[1]
			);
		});

		it('returns only the actions that matches all action filters criteria', () => {
			const testActionsWithOnlyFilters: IItemsActions[] =
				testActionsWithPermissionKeyAndFilters.map((action) => {
					delete action.data?.permissionKey;

					const newActionFilter = {key: 'type', value: 'boolean'};

					return {
						...action,
						data: {
							filters:
								action.data?.filters?.concat(newActionFilter),
						},
					};
				});

			const filteredActions = filterItemActions(
				testActionsWithOnlyFilters,
				availableItemData
			);

			expect(filteredActions.length).toEqual(0);
		});

		it('returns actions if the filter criteria key includes a composed field name', () => {
			const testActionsWithOnlyFilters: IItemsActions[] =
				testActionsWithPermissionKeyAndFilters.map((action) => {
					delete action.data?.permissionKey;

					const newActionFilter = {
						key: 'creator.name',
						value: 'Test Test',
					};

					return {
						...action,
						data: {
							filters:
								action.data?.filters?.concat(newActionFilter),
						},
					};
				});

			const filteredActions = filterItemActions(
				testActionsWithOnlyFilters,
				availableItemData
			);

			expect(filteredActions.length).toEqual(1);
			expect(filteredActions[0]).toMatchObject(
				testActionsWithOnlyFilters[1]
			);
		});

		it('returns actions if the filter criteria is based on a boolean value', () => {
			const testActionsWithOnlyFilters: IItemsActions[] =
				testActionsWithPermissionKeyAndFilters.map((action) => {
					delete action.data?.permissionKey;

					const newActionFilter = {key: 'sortable', value: true};

					return {
						...action,
						data: {
							filters:
								action.data?.filters?.concat(newActionFilter),
						},
					};
				});

			const filteredActions = filterItemActions(
				testActionsWithOnlyFilters,
				availableItemData
			);

			expect(filteredActions.length).toEqual(1);
		});
	});

	describe('when only isVisible action callback is defined for item actions', () => {
		it('returns the actions that match the action isVisible callback criteria', () => {
			const isVisibleFn = {
				isPopular(item: any) {
					return item.type === 'integer' && item.rating > 3;
				},
			};

			const spyCallback = jest.spyOn(isVisibleFn, 'isPopular');

			const testActionsWithIsVisibleCallback: IItemsActions[] =
				testActionsWithoutPermissionKey.map((action) => {
					return {
						...action,
						isVisible: isVisibleFn.isPopular,
					};
				});

			const filteredActions = filterItemActions(
				testActionsWithIsVisibleCallback,
				availableItemData
			);

			expect(spyCallback).toHaveBeenCalledTimes(2);
			expect(filteredActions.length).toEqual(0);
		});
	});
});
