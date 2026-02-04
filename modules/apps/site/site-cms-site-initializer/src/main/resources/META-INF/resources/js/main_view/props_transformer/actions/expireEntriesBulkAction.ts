/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBulkActionFDSData} from '../../../common/types/BulkActionTask';
import {triggerAssetBulkAction} from './triggerAssetBulkAction';

export default function expireEntriesBulkAction({
	apiURL = '',
	dataSetId = '',
	selectedData,
}: {
	apiURL?: string;
	dataSetId?: string;
	selectedData: IBulkActionFDSData;
}): void {
	triggerAssetBulkAction({
		apiURL,
		dataSetId,
		selectedData,
		type: 'ExpireBulkAction',
	});
}
