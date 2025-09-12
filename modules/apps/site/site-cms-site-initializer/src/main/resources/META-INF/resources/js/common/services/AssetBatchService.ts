/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper from './ApiHelper';

async function deleteAssetEntries({
	items,
	selectAll,
}: {
	items: {className: string; classPK: string; name: string}[];
	selectAll: boolean;
}) {
	return await ApiHelper.post('/o/headless-cms/v1.0/bulk-action', {
		bulkActionItems: items,
		selectAll,
		type: 'DeleteBulkAction',
	});
}

export default {
	deleteAssetEntries,
};
