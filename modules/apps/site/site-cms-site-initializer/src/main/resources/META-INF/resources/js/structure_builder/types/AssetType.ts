/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ASSET_TYPE} from '../../main_view/info_panel/util/constants';

export interface IAssetTypeFile {
	externalReferenceCode: string;
	id: number;
	link: {
		href: string;
		label: string;
	};
	name: string;
	thumbnailURL: string;
}

export interface IAssetObjectEntry {
	actions: any;
	dateCreated: string;
	dateModified: string;
	embedded: Partial<{
		creator: any;
		externalReferenceCode: string;
		file: IAssetTypeFile;
		id: number;
		keywords: any[];
		objectEntryFolderExternalReferenceCode: string;
		objectEntryFolderId: number;
		scopeId: number;
		scopeKey: string;
		status: any;
		title: string;
		title_i18n: any;
	}>;
	entryClassName: string;
	score: number;
}

export interface IAssetInformation {
	externalReferenceCode?: string | null;
	icon?: string | null;
	id?: number | null;
	title?: string | null;
	title_i18n?: {
		[key: string]: string;
	} | null;
	type?: string | null;
}

export function getBaseAssetInformation({
	actions: {
		get: {href},
	},
	embedded: {externalReferenceCode, id, title, title_i18n},
}: IAssetObjectEntry): IAssetInformation {
	const baseAssetInfo: IAssetInformation = {
		externalReferenceCode,
		id,
		title,
		title_i18n,
	};

	if (href.includes('object-entry-folders')) {
		baseAssetInfo.icon = 'folder';
		baseAssetInfo.type = ASSET_TYPE.FOLDER;
	}
	else if (
		href.includes('basic-documents') ||
		href.includes('external-videos')
	) {
		baseAssetInfo.icon = 'document-image';
		baseAssetInfo.type = ASSET_TYPE.FILES;
	}
	else if (
		href.includes('basic-web-contents') ||
		href.includes('blogs') ||
		href.includes('knowledge-bases')
	) {
		baseAssetInfo.icon = 'forms';
		baseAssetInfo.type = ASSET_TYPE.CONTENTS;
	}

	return baseAssetInfo;
}
