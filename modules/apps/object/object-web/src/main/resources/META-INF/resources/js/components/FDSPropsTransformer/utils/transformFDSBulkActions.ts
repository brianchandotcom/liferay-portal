/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBulkActionItem} from '@liferay/frontend-data-set-web';

const BULK_ACTION_PERMISSION_KEYS: Partial<Record<string, keyof Actions>> = {
	delete: 'delete',
};

export default function transformFDSBulkActions<
	TItem extends {actions?: Actions},
>(bulkActions: Array<IBulkActionItem>): Array<IBulkActionItem> {
	return bulkActions.map((action) => {
		const id = action?.data?.id;
		const permissionKey = id && BULK_ACTION_PERMISSION_KEYS[id];

		if (!permissionKey) {
			return action;
		}

		return {
			...action,
			isVisible: ({
				selectedItems,
			}: {
				selectedItems?: Array<TItem>;
			} = {}): boolean => {
				if (!selectedItems?.length) {
					return false;
				}

				return selectedItems.every((item) =>
					Boolean(item?.actions?.[permissionKey])
				);
			},
		};
	});
}
