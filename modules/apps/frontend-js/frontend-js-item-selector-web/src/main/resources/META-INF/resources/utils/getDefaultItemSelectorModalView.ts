/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TView} from '@liferay/frontend-data-set-web';

import {
	assetLibraryViews,
	documentsAndMediaViews,
	userViews,
} from './defaultViews';

export enum EItemSelectorModalViewsConfig {
	ASSET_LIBRARY = 'assets',
	DOCUMENTS_AND_MEDIA = 'documents',
	USERS = 'users',
}

const getDefaultItemSelectorModalViews = ({
	viewsConfig,
}: {
	viewsConfig: `${EItemSelectorModalViewsConfig}` | TView[];
}): any => {
	if (typeof viewsConfig === 'object') {
		return viewsConfig;
	}
	else if (viewsConfig === EItemSelectorModalViewsConfig.ASSET_LIBRARY) {
		return assetLibraryViews;
	}
	else if (
		viewsConfig === EItemSelectorModalViewsConfig.DOCUMENTS_AND_MEDIA
	) {
		return documentsAndMediaViews;
	}
	else if (viewsConfig === EItemSelectorModalViewsConfig.USERS) {
		return userViews;
	}
};

export default getDefaultItemSelectorModalViews;
