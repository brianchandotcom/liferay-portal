/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizedValue} from '../getLocalizedValue';
import {IActionsDataFilter, IBulkActionItem} from '../types';

const matchesVisibilityFilters = (
	action: IBulkActionItem,
	selectedItemsData: Array<any>
): boolean => {
	if (!action?.data?.visibilityFilters) {
		return true;
	}

	const visibilityFilters: IActionsDataFilter =
		action?.data?.visibilityFilters;

	return selectedItemsData.every((itemData: any) => {
		return Object.keys(visibilityFilters).every(
			(key: string) =>
				getLocalizedValue(itemData, key)?.value ===
				visibilityFilters[key]
		);
	});
};

const filterBulkActions = ({
	allItemsSelectedActive,
	bulkActions,
	selectedItems,
}: {
	allItemsSelectedActive: boolean;
	bulkActions: Array<IBulkActionItem>;
	selectedItems: Array<any>;
}): Array<IBulkActionItem> => {
	if (!bulkActions) {
		return [];
	}

	return bulkActions.filter((bulkAction) => {
		if (
			!allItemsSelectedActive &&
			!matchesVisibilityFilters(bulkAction, selectedItems)
		) {
			return false;
		}

		if (
			allItemsSelectedActive ||
			!bulkAction.isVisible ||
			bulkAction.isVisible(selectedItems)
		) {
			return bulkAction;
		}
	});
};

export default filterBulkActions;
