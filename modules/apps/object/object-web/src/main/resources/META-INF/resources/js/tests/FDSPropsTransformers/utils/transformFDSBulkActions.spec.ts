/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBulkActionItem} from '@liferay/frontend-data-set-web';

import transformFDSBulkActions from '../../../components/FDSPropsTransformer/utils/transformFDSBulkActions';

const findBulkAction = (
	bulkActions: Array<IBulkActionItem>,
	id: string
): IBulkActionItem | undefined =>
	bulkActions.find((action) => action?.data?.id === id);

const transformBulkAction = (id: string): Array<IBulkActionItem> =>
	transformFDSBulkActions([{data: {id}}]);

const deleteBulkAction = findBulkAction(
	transformBulkAction('delete'),
	'delete'
);

describe('transformFDSBulkActions', () => {
	describe('bulk action isVisible', () => {
		it('attaches an isVisible callback to a mapped bulk action', () => {
			expect(typeof deleteBulkAction?.isVisible).toBe('function');
		});

		it('hides the action when allItemsSelectedActive is true and selected items lack the permission', () => {
			expect(
				deleteBulkAction?.isVisible?.({
					allItemsSelectedActive: true,
					selectedItems: [
						{actions: {get: {}}, id: 1},
						{actions: {get: {}}, id: 2},
					],
				})
			).toBe(false);
		});

		it('hides the action when any selected item lacks the permission', () => {
			expect(
				deleteBulkAction?.isVisible?.({
					allItemsSelectedActive: false,
					selectedItems: [
						{actions: {delete: {}}, id: 1},
						{actions: {get: {}}, id: 2},
					],
				})
			).toBe(false);
		});

		it('hides the action when no items are selected', () => {
			expect(
				deleteBulkAction?.isVisible?.({
					allItemsSelectedActive: false,
					selectedItems: [],
				})
			).toBe(false);
		});

		it('hides the action when no selected item has the permission', () => {
			expect(
				deleteBulkAction?.isVisible?.({
					allItemsSelectedActive: false,
					selectedItems: [
						{actions: {get: {}}, id: 1},
						{actions: {get: {}}, id: 2},
					],
				})
			).toBe(false);
		});

		it('shows the action when every selected item has the permission', () => {
			expect(
				deleteBulkAction?.isVisible?.({
					allItemsSelectedActive: false,
					selectedItems: [
						{actions: {delete: {}}, id: 1},
						{actions: {delete: {}}, id: 2},
					],
				})
			).toBe(true);
		});
	});

	describe('unmapped action id', () => {
		it('returns the action unchanged when the id is not in the permission map', () => {
			const [action] = transformBulkAction('unmapped-action');

			expect(action?.isVisible).toBeUndefined();
		});
	});
});
