/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBulkActionFDSData} from '../../../common/types/BulkActionTask';
import {triggerAssetBulkAction} from './triggerAssetBulkAction';

/**
 * Executes the bulk expire action.
 */
export function executeBulkExpireAction(
	apiURL: string,
	dataSetId: string,
	selectedData: IBulkActionFDSData,
	processClose?: () => void
): void {
	triggerAssetBulkAction({
		apiURL,
		dataSetId,
		onCreateSuccess: () => {
			processClose?.();
		},
		selectedData,
		type: 'ExpireBulkAction',
	});
}

/**
 * Handles bulk expiration logic and modal display based on trash status of spaces.
 */
async function handleBulkExpiration({
	apiURL,
	dataSetId,
	selectedData,
}: {
	apiURL: string;
	dataSetId: string;
	selectedData: IBulkActionFDSData;
}): Promise<void> {
	executeBulkExpireAction(apiURL, dataSetId, selectedData);
}

/**
 * Entry point for bulk expire action.
 */
export default async function expireEntriesBulkAction({
	apiURL = '',
	dataSetId = '',
	selectedData,
}: {
	apiURL?: string;
	dataSetId?: string;
	selectedData: IBulkActionFDSData;
}): Promise<void> {
	await handleBulkExpiration({apiURL, dataSetId, selectedData});
}
