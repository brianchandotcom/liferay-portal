/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ISearchAssetObjectEntry,
	ISearchAssetTypeInformation,
} from '../../../common/types/AssetType';
import {ASSET_TYPE, L_CONTENTS, L_FILES} from './constants';

export function getBaseAssetInformation({
	actions: {
		get: {href},
	},
	embedded: {
		externalReferenceCode,
		id,
		objectEntryFolderExternalReferenceCode,
		title,
		title_i18n,
	},
}: ISearchAssetObjectEntry): ISearchAssetTypeInformation {
	const baseAssetInfo: ISearchAssetTypeInformation = {
		externalReferenceCode,
		id,
		objectEntryFolderExternalReferenceCode,
		title,
		title_i18n,
	};

	if (href.includes('object-entry-folders')) {
		baseAssetInfo.icon = 'folder';
		baseAssetInfo.type = ASSET_TYPE.FOLDER;
	}
	else if (objectEntryFolderExternalReferenceCode === L_FILES) {
		baseAssetInfo.icon = 'document-image';
		baseAssetInfo.type = ASSET_TYPE.FILES;
	}
	else if (objectEntryFolderExternalReferenceCode === L_CONTENTS) {
		baseAssetInfo.icon = 'forms';
		baseAssetInfo.type = ASSET_TYPE.CONTENTS;
	}

	return baseAssetInfo;
}
