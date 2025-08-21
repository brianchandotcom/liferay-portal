/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum AssetType {
	Blog = 'BLOG',
	CustomStructure = 'CUSTOMSTRUCTURE',
	Document = 'DOCUMENT',
	JournalArticle = 'JOURNALARTICLE',
	KnowledgeTransfer = 'KNOWLEDGETRANSFER',
	WebContent = 'WEBCONTENT',
}

export type AssetTypeIcon = {
	color: string;
	symbol: string;
};

export const AssetTypeIcons: Record<AssetType, AssetTypeIcon> = {

	// TODO: Define correct symbols and colors

	[AssetType.JournalArticle]: {color: '#5c9531', symbol: 'blogs'},
	[AssetType.Blog]: {color: '', symbol: ''},
	[AssetType.Document]: {color: '', symbol: ''},
	[AssetType.CustomStructure]: {color: '', symbol: ''},
	[AssetType.KnowledgeTransfer]: {color: '#e82092', symbol: 'wiki'},
	[AssetType.WebContent]: {color: '#2080ff', symbol: 'web-content'},
};
