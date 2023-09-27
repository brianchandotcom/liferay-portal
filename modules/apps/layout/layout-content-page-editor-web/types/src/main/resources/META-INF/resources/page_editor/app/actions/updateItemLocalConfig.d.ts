/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function updateItemLocalConfig({
	disableUndo,
	itemConfig,
	itemId,
	overridePreviousConfig,
}: {
	disableUndo?: boolean;
	itemConfig: {
		loading?: boolean;
		showMessagePreview?: boolean;
		showPreview?: boolean;
	};
	itemId: string;
	overridePreviousConfig?: boolean;
}): {
	readonly disableUndo: boolean;
	readonly itemConfig: {
		loading?: boolean | undefined;
		showMessagePreview?: boolean | undefined;
		showPreview?: boolean | undefined;
	};
	readonly itemId: string;
	readonly overridePreviousConfig: boolean;
	readonly type: 'UPDATE_ITEM_LOCAL_CONFIG';
};
