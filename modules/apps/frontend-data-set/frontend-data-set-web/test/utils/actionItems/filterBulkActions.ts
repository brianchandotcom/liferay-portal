/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import filterBulkActions from '../../../src/main/resources/META-INF/resources/utils/actionItems/filterBulkActions';
import {
	IBaseFilterState,
	IBulkActionItem,
	IFDSState,
} from '../../../src/main/resources/META-INF/resources/utils/types';

describe('filterBulkActions', () => {
	const globalFDSState: IFDSState = {
		filters: [] as Array<IBaseFilterState>,
		search: {query: ''},
	};
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
					globalFDSState,
					selectedItems,
				})
			).toEqual([]);
		});
	});

	describe('when isVisible is not defined', () => {
		it('returns all actions', () => {
			const bulkActions: IBulkActionItem[] = [
				{href: '/action1'},
				{href: '/action2'},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				globalFDSState,
				selectedItems,
			});

			expect(result).toHaveLength(2);
			expect(result).toEqual(bulkActions);
		});
	});

	describe('when isVisible is defined', () => {
		it('filters actions based on the isVisible callback', () => {
			const isVisibleFn = jest.fn(
				({selectedItems = []}: {selectedItems?: Array<any>}) => {
					return selectedItems.every((item: any) => {
						return item.status === 'inactive';
					});
				}
			);

			const bulkActions: IBulkActionItem[] = [
				{
					data: {id: 'hidden-action'},
					isVisible: isVisibleFn,
				},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				globalFDSState,
				selectedItems,
			});

			expect(result).toHaveLength(0);
			expect(isVisibleFn).toHaveBeenCalledWith({
				activeFilters: [],
				activeSearch: {
					query: '',
				},
				allItemsSelectedActive: false,
				selectedItems,
			});
		});

		it('evaluates isVisible callback and includes the action when allItemsSelectedActive is true if it returns true', () => {
			const isVisibleFn = jest.fn().mockReturnValue(true);
			const bulkActions: IBulkActionItem[] = [
				{
					data: {id: 'visible-action-but-all-selected'},
					isVisible: isVisibleFn,
				},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: true,
				bulkActions,
				globalFDSState,
				selectedItems,
			});

			expect(result).toHaveLength(1);
			expect(result[0].data?.id).toBe('visible-action-but-all-selected');
			expect(isVisibleFn).toHaveBeenCalledWith({
				activeFilters: [],
				activeSearch: {
					query: '',
				},
				allItemsSelectedActive: true,
				selectedItems,
			});
		});
	});

	describe('when isDisabled is not defined', () => {
		it('returns the action without stamping data.disabled', () => {
			const bulkActions: IBulkActionItem[] = [
				{data: {highlighted: true}},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				globalFDSState,
				selectedItems,
			});

			expect(result).toHaveLength(1);
			expect(result[0].data).not.toHaveProperty('disabled');
		});
	});

	describe('when isDisabled is defined', () => {
		it('evaluates isDisabled callback and stamps data.disabled if it returns true', () => {
			const isDisabledFn = jest.fn().mockReturnValue(true);
			const bulkActions: IBulkActionItem[] = [
				{
					data: {highlighted: true, id: 'update'},
					isDisabled: isDisabledFn,
				},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				globalFDSState,
				selectedItems,
			});

			expect(result).toHaveLength(1);
			expect(result[0].data).toEqual({
				disabled: true,
				highlighted: true,
				id: 'update',
			});
			expect(isDisabledFn).toHaveBeenCalledWith({
				activeFilters: [],
				activeSearch: {
					query: '',
				},
				allItemsSelectedActive: false,
				selectedItems,
			});
		});

		it('stamps data.disabled to false when isDisabled returns false', () => {
			const bulkActions: IBulkActionItem[] = [
				{
					data: {id: 'evaluated-action'},
					isDisabled: () => false,
				},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				globalFDSState,
				selectedItems,
			});

			expect(result[0].data?.disabled).toBe(false);
		});

		it('drops a not-visible action without invoking isDisabled', () => {
			const isDisabledFn = jest.fn().mockReturnValue(true);
			const bulkActions: IBulkActionItem[] = [
				{
					data: {id: 'hidden'},
					isDisabled: isDisabledFn,
					isVisible: () => false,
				},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				globalFDSState,
				selectedItems,
			});

			expect(result).toHaveLength(0);
			expect(isDisabledFn).not.toHaveBeenCalled();
		});

		it('does not mutate the input bulkActions array or its data objects', () => {
			const original: IBulkActionItem = {
				data: {id: 'mutable-check'},
				isDisabled: () => true,
			};
			const originalDataReference = original.data;
			const bulkActions: IBulkActionItem[] = [original];

			filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				globalFDSState,
				selectedItems,
			});

			expect(original.data).toBe(originalDataReference);
			expect(original.data).not.toHaveProperty('disabled');
		});
	});
});
