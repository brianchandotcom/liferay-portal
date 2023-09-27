/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_ITEM_LOCAL_CONFIG} from './types';

export default function updateItemLocalConfig({
	disableUndo = false,
	itemConfig,
	itemId,
	overridePreviousConfig = false,
}: {
	disableUndo?: boolean;
	itemConfig: {
		loading?: boolean;
		showMessagePreview?: boolean;
		showPreview?: boolean;
	};
	itemId: string;
	overridePreviousConfig?: boolean;
}) {
	return {
		disableUndo,
		itemConfig,
		itemId,
		overridePreviousConfig,
		type: UPDATE_ITEM_LOCAL_CONFIG,
	} as const;
}
