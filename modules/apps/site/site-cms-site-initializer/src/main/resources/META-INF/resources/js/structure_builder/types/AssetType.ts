/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ASSET_TYPE} from '../../main_view/info_panel/util/constants';

export interface IAssetFile {
	externalReferenceCode: string;
	id: number;
	link: {
		href: string;
		label: string;
	};
	name: string;
	previewURL: string;
	thumbnailURL: string;
}

export interface IAssetObjectEntry {
	actions: any;
	creator: any;
	dateCreated: string;
	dateModified: string;
	expirationDate: string;
	externalReferenceCode: string;
	file?: IAssetFile;
	friendlyUrlPath: string;
	id: number;
	keywords: any[];
	objectEntryFolderExternalReferenceCode: string;
	objectEntryFolderId: number;
	reviewDate: string;
	scopeId: number;
	scopeKey: string;
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	systemProperties: IAssetVersion;
	title: string;
	title_i18n: any;
}

export interface IAssetVersion {
	version: {
		number: number;
	};
}

export interface ISearchAssetObjectEntry {
	actions: any;
	dateCreated: string;
	dateModified: string;
	embedded: Partial<IAssetObjectEntry>;
	entryClassName: string;
	score: number;
}

export interface ISearchAssetTypeInformation {
	externalReferenceCode?: string | null;
	icon?: string | null;
	id?: number | null;
	title?: string | null;
	title_i18n?:
		| {
				[key: string]: string;
		  }
		| {};
	type?: string | null;
}

export function getBaseAssetInformation({
	actions: {
		get: {href},
	},
	embedded: {externalReferenceCode, id, title, title_i18n},
}: ISearchAssetObjectEntry): ISearchAssetTypeInformation {
	const baseAssetInfo: ISearchAssetTypeInformation = {
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
