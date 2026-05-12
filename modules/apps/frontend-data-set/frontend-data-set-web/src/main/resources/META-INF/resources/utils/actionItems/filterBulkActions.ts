/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBaseFilterState, IBulkActionItem, IFDSState} from '../types';

const filterBulkActions = ({
	allItemsSelectedActive,
	bulkActions,
	globalFDSState,
	selectedItems,
}: {
	allItemsSelectedActive: boolean;
	bulkActions: Array<IBulkActionItem>;
	globalFDSState: IFDSState;
	selectedItems: Array<any>;
}): Array<IBulkActionItem> => {
	if (!bulkActions) {
		return [];
	}

	const bulkActionCallbackContext = {
		activeFilters: globalFDSState?.filters.filter(
			(filter: IBaseFilterState) => filter?.active
		),
		activeSearch: globalFDSState.search,
		allItemsSelectedActive,
		selectedItems,
	};

	return bulkActions
		.filter(
			(bulkAction) =>
				!bulkAction.isVisible ||
				bulkAction.isVisible(bulkActionCallbackContext)
		)
		.map((bulkAction) => {
			if (!bulkAction.isDisabled) {
				return bulkAction;
			}

			return {
				...bulkAction,
				data: {
					...bulkAction.data,
					disabled: bulkAction.isDisabled(bulkActionCallbackContext),
				},
			};
		});
};

export default filterBulkActions;
