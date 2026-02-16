/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {dateUtils} from 'frontend-js-web';

import {IAssetObjectEntry} from '../../../common/types/AssetType';
import {ASSET_TYPE, L_CONTENTS, L_FILES} from './constants';

export function formatDate(date: string): string {
	return dateUtils.format(new Date(date), 'P p');
}

export function getAssetType(objectEntry: IAssetObjectEntry): string {
	const {
		objectEntryFolderExternalReferenceCode:
			objectEntryFolderExternalReferenceCode = '',
	} = objectEntry;

	let type = ASSET_TYPE.FOLDER;

	if (objectEntryFolderExternalReferenceCode === L_CONTENTS) {
		type = ASSET_TYPE.CONTENTS;
	}
	else if (objectEntryFolderExternalReferenceCode === L_FILES) {
		type = ASSET_TYPE.FILES;
	}

	return type;
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
