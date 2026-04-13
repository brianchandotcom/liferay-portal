/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {dateUtils} from 'frontend-js-web';

import {IAssetObjectEntry} from '../../../common/types/AssetType';
import {
	ASSET_TYPE,
	ASSET_TYPE_ERC,
	L_CMS_CONTENT_STRUCTURES,
	L_CMS_FILE_TYPES,
} from './constants';

export function formatDate(date: string): string {
	return dateUtils.format(new Date(date), 'P p');
}

export function getAssetType(objectEntry: IAssetObjectEntry): string {
	const {
		systemProperties: {
			objectDefinitionBrief: {
				objectFolderExternalReferenceCode:
					objectFolderExternalReferenceCode = '',
			} = {},
		} = {},
	} = objectEntry;

	if (
		objectFolderExternalReferenceCode ===
			ASSET_TYPE_ERC.BASIC_WEB_CONTENT ||
		objectFolderExternalReferenceCode === L_CMS_CONTENT_STRUCTURES
	) {
		return ASSET_TYPE.CONTENTS;
	}
	else if (
		objectFolderExternalReferenceCode === ASSET_TYPE_ERC.BASIC_DOCUMENT ||
		objectFolderExternalReferenceCode === L_CMS_FILE_TYPES
	) {
		return ASSET_TYPE.FILES;
	}

	return ASSET_TYPE.FOLDER;
}

export function getAssetLanguages(
	title_i18n: {[key: string]: string} = {}
): string[] {
	const assetLanguages = Object.keys(title_i18n);

	if (assetLanguages.length) {
		return Object.keys(title_i18n).map((key) => key.replace('_', '-'));
	}

	return [];
}
