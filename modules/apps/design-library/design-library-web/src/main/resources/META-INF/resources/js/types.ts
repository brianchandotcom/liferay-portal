/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface ActionItem {
	data: {id: string};
	href?: string;
}

export interface DesignLibraryItem {
	creatorUserId: number;
	dateModified: string;
	description: string;
	externalReferenceCode: string;
	id: number;
	name: string;
}

export interface DesignLibrary {
	assetLibraryKey: string;
	creatorUserId: string;
	description: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	settings?: DesignLibrarySettings;
	siteId: number;
}

export interface DesignLibrarySettings {
	availableLanguageIds?: string[];
	defaultLanguageId?: string;
	logoColor?:
		| 'outline-0'
		| 'outline-1'
		| 'outline-2'
		| 'outline-3'
		| 'outline-4'
		| 'outline-5'
		| 'outline-6'
		| 'outline-7'
		| 'outline-8'
		| 'outline-9';
	sharingEnabled?: boolean;
	trashEnabled?: boolean;
	trashEntriesMaxAge?: number;
	useCustomLanguages?: boolean;
}

export interface Site {
	descriptiveName: string;
	externalReferenceCode: string;
	id: string;
	logo: string;
	name: string;
	searchable: boolean;
}
