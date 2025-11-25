/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {triggerAssetBulkAction} from './triggerAssetBulkAction';

export default function executeResetPermissionBulkAction({
	apiURL,
	selectedData,
}: {
	apiURL?: string;
	selectedData: any;
}) {
	try {
		triggerAssetBulkAction({
			apiURL,
			selectedData,
			type: 'ResetPermissionBulkAction',
		});
	}
	catch (error) {
		console.error('Error resetting permissions:', error);

		Liferay.Util.openToast({
			message: Liferay.Language.get('an-error-occurred'),
			type: 'danger',
		});
	}
}
