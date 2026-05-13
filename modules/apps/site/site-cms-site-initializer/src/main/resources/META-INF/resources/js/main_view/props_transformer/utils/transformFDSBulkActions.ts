/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBulkActionItem} from '@liferay/frontend-data-set-web';

const BULK_ACTION_PERMISSION_KEYS: Record<string, string> = {
	'assign-default-workflow': 'update',
	'copy-to': 'update',
	'default-permissions': 'permissions',
	'delete': 'delete',
	'download': 'get',
	'duplicate': 'duplicate',
	'edit-categories': 'update',
	'edit-default-permissions-by-role': 'permissions',
	'edit-permissions-by-role': 'permissions',
	'edit-tags': 'update',
	'expire': 'expire',
	'export-for-translation': 'get',
	'move-to': 'update',
	'permissions': 'permissions',
	'reset-to-default-permissions': 'permissions',
	'restore': 'restore',
};

export default function transformFDSBulkActions(
	bulkActions: Array<IBulkActionItem>
): Array<IBulkActionItem> {
	return bulkActions.map((action: IBulkActionItem) => {
		const key = action?.data?.id as string;
		const permissionKey = key && BULK_ACTION_PERMISSION_KEYS[key];

		if (!permissionKey) {
			return action;
		}

		return {
			...action,
			isVisible: ({
				allItemsSelectedActive,
				selectedItems,
			}: {
				allItemsSelectedActive?: boolean;
				selectedItems?: Array<any>;
			} = {}): boolean => {
				if (allItemsSelectedActive) {
					return key !== 'download' && key !== 'duplicate';
				}

				if (key === 'download') {
					return (
						selectedItems?.every((item: any) =>
							Boolean(item?.embedded?.file?.link?.href)
						) ?? false
					);
				}

				return (
					selectedItems?.every(
						(item: any) => item?.actions?.[permissionKey]
					) ?? false
				);
			},
		};
	});
}
