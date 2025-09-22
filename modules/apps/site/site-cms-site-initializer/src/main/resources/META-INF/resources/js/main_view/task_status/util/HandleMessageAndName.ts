/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ActionId, IBulkActionSelectedData} from '../TaskStatusType';

function handleMessageAndName(
	actionId: ActionId,
	selectedData?: IBulkActionSelectedData
) {
	const bulkAction: Record<
		ActionId,
		{
			infoMessage: string;
			name: string;
		}
	> = {
		DeleteBulkAction: {
			infoMessage:
				selectedData?.items.length === 1
					? Liferay.Language.get(
							'asset-delete-action-started-for-x-asset'
						)
					: Liferay.Language.get(
							'asset-delete-action-started-for-x-assets'
						),
			name: Liferay.Language.get('assets-deletion'),
		},
		KeywordBulkAction: {
			infoMessage:
				selectedData?.items.length === 1
					? Liferay.Language.get(
							'tags-update-action-started-for-x-tag'
						)
					: Liferay.Language.get(
							'tags-update-action-started-for-x-tags'
						),
			name: Liferay.Language.get('assets-tagging'),
		},
		MoveBulkAction: {
			infoMessage:
				selectedData?.items.length === 1
					? Liferay.Language.get(
							'assets-movement-action-started-for-x-asset'
						)
					: Liferay.Language.get(
							'assets-movement-action-started-for-x-assets'
						),
			name: Liferay.Language.get('assets-movement'),
		},
		PermissionBulkAction: {
			infoMessage:
				selectedData?.items.length === 1
					? Liferay.Language.get(
							'permission-update-action-started-for-x-permission'
						)
					: Liferay.Language.get(
							'permission-update-action-started-for-x-permissions'
						),
			name: Liferay.Language.get('assets-permissioning'),
		},
		StatusBulkAction: {
			infoMessage: Liferay.Language.get(''),
			name: Liferay.Language.get(''),
		},
		TaxonomyCategoryBulkAction: {
			infoMessage:
				selectedData?.items.length === 1
					? Liferay.Language.get(
							'categories-update-action-started-for-x-category'
						)
					: Liferay.Language.get(
							'categories-update-action-started-for-x-categories'
						),
			name: Liferay.Language.get('assets-categorization'),
		},
	};

	return bulkAction[actionId];
}

export default handleMessageAndName;
