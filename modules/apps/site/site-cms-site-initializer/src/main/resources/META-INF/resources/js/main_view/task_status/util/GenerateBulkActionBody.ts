/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ActionId,
	BulkActionDataDTO,
	BulkActionPostDTO,
	IBulkAction,
	IBulkActionSelectedData,
} from '../TaskStatusType';

function generateBulkActionBody(
	actionId: ActionId,
	selectedData: IBulkActionSelectedData,
	data?: BulkActionDataDTO
): BulkActionPostDTO<ActionId> {
	const initialBody = {
		bulkActionItems: selectedData.items.map((item: any) => ({
			classExternalReferenceCode: item.embedded.externalReferenceCode,
			className: item.entryClassName,
			classPK: item.embedded.id,
			name: item.embedded.title,
		})),
		selectAll: selectedData.selectAll,
		type: actionId,
	};

	const bulkActionParamsMap: Record<ActionId, IBulkAction[ActionId]> = {
		['DeleteBulkAction']: {},
		['KeywordBulkAction']: {
			keywords: data,
		},
		['MoveBulkAction']: {
			objectEntryFolderId: data,
		},
		['PermissionBulkAction']: {
			permission: data,
		},
		['StatusBulkAction']: {
			status: data,
		},
		['TaxonomyCategoryBulkAction']: {
			taxonomyCategoryIds: data,
		},
	};

	const bulkActionParams = bulkActionParamsMap[actionId];

	if (!bulkActionParams) {
		throw new Error('Unrecognized action type: ' + actionId);
	}

	return {...initialBody, ...bulkActionParams};
}

export default generateBulkActionBody;
