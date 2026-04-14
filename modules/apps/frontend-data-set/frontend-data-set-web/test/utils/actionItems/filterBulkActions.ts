/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import filterBulkActions from '../../../src/main/resources/META-INF/resources/utils/actionItems/filterBulkActions';
import {IBulkActionItem} from '../../../src/main/resources/META-INF/resources/utils/types';

describe('filterBulkActions', () => {
	const selectedItems = [
		{color: 'blue', id: 1, status: 'active'},
		{color: 'red', id: 2, status: 'inactive'},
	];

	describe('when bulkActions is missing or empty', () => {
		it('returns an empty array', () => {
			expect(
				filterBulkActions({
					allItemsSelectedActive: false,
					bulkActions: null as unknown as IBulkActionItem[],
					selectedItems,
				})
			).toEqual([]);
		});
	});

	describe('when neither visibilityFilters nor isVisible are defined', () => {
		it('returns all actions', () => {
			const bulkActions: IBulkActionItem[] = [
				{href: '/action1'},
				{href: '/action2'},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				selectedItems,
			});

			expect(result).toHaveLength(2);
			expect(result).toEqual(bulkActions);
		});
	});

	describe('when visibilityFilters are defined', () => {
		it('returns only the actions where every selected item matches all visibility filters', () => {
			const localSelectedItems = [
				{color: 'blue', status: 'active', type: 'document'},
				{color: 'red', status: 'inactive', type: 'document'},
			];

			const bulkActions: IBulkActionItem[] = [
				{
					data: {
						id: 'match-all',
						visibilityFilters: {type: 'document'},
					},
				},
				{
					data: {
						id: 'match-none',
						visibilityFilters: {type: 'image'},
					},
				},
				{
					data: {
						id: 'match-partial',
						visibilityFilters: {color: 'blue'},
					},
				},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				selectedItems: localSelectedItems,
			});

			expect(result).toHaveLength(1);
			expect(result[0].data?.id).toBe('match-all');
		});

		it('includes actions even if visibilityFilters do not match, when allItemsSelectedActive is true', () => {
			const bulkActions: IBulkActionItem[] = [
				{
					data: {id: 'nomatch', visibilityFilters: {color: 'yellow'}},
				},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: true,
				bulkActions,
				selectedItems,
			});

			expect(result).toHaveLength(1);
			expect(result[0].data?.id).toBe('nomatch');
		});
	});

	describe('when isVisible is defined', () => {
		it('filters actions based on the isVisible callback when allItemsSelectedActive is false', () => {
			const isVisibleFn = jest.fn().mockReturnValue(false);
			const bulkActions: IBulkActionItem[] = [
				{
					data: {id: 'hidden-action'},
					isVisible: isVisibleFn,
				},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				selectedItems,
			});

			expect(result).toHaveLength(0);
			expect(isVisibleFn).toHaveBeenCalledWith(selectedItems);
		});

		it('bypasses the isVisible check and includes the action when allItemsSelectedActive is true', () => {
			const isVisibleFn = jest.fn().mockReturnValue(false);
			const bulkActions: IBulkActionItem[] = [
				{
					data: {id: 'hidden-action-but-all-selected'},
					isVisible: isVisibleFn,
				},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: true,
				bulkActions,
				selectedItems,
			});

			expect(result).toHaveLength(1);
			expect(result[0].data?.id).toBe('hidden-action-but-all-selected');
			expect(isVisibleFn).not.toHaveBeenCalled();
		});
	});
});
