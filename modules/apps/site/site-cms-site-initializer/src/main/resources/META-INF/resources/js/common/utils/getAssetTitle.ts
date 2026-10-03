/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IAssetTitleSource {
	embedded?: {
		id?: number;
		title?: string;
	};
	title?: string;
}

export function getAssetTitle(item?: IAssetTitleSource): string {
	if (item?.embedded?.title) {
		return item.embedded.title;
	}

	if (!item?.title || item.title === String(item.embedded?.id)) {
		return '';
	}

	return item.title;
}
