/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type ListStyle =
	| 'Bordered List (Collection Provider)'
	| 'Bulleted List (Collection Provider)'
	| 'Bulleted List (Journal)';

type Props = {
	classPK?: number;
	collectionConfig?: CollectionConfig;
	id: string;
	listStyle?: ListStyle;
	pageElements?: PageElement[];
	provider?: 'Recent Content';
};

const DEFAULT_CONFIG = {
	numberOfColumns: 1,
	numberOfItems: 50,
};

const COLLECTION_PROVIDERS = {
	'Recent Content':
		'com.liferay.asset.internal.info.collection.provider.RecentContentInfoCollectionProvider',
};

const LIST_STYLES = {
	'Bordered List (Collection Provider)':
		'com.liferay.asset.info.internal.list.renderer.AssetEntryBorderedBasicInfoListRenderer',
	'Bulleted List (Collection Provider)':
		'com.liferay.asset.info.internal.list.renderer.BulletedAssetEntryBasicInfoListRenderer',
	'Bulleted List (Journal)':
		'com.liferay.journal.web.internal.info.list.renderer.BulletedJournalArticleBasicInfoListRenderer',
};

export default function getCollectionDefinition({
	classPK,
	id,
	listStyle,
	pageElements,
	provider,
}: Props): PageElement {
	return {
		definition: {
			...DEFAULT_CONFIG,
			collectionConfig: {
				collectionReference: {
					className: classPK
						? 'com.liferay.asset.list.model.AssetListEntry'
						: COLLECTION_PROVIDERS[provider],
					classPK,
				},
				collectionType: classPK ? 'Collection' : 'CollectionProvider',
			},
			listStyle: LIST_STYLES[listStyle],
		},
		id,
		pageElements,
		type: 'Collection',
	};
}
