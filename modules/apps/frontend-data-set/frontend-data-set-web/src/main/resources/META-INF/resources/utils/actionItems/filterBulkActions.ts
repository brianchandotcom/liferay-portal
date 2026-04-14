/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBulkActionItem} from '../types';

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
		if (allItemsSelectedActive) {
			return true;
		}

		return !bulkAction.isVisible || bulkAction.isVisible(selectedItems);
	});
};

export default filterBulkActions;
