/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const RETURN_TYPES = {
	assetList:
		'com.liferay.item.selector.criteria.InfoListItemSelectorReturnType',
	infoList:
		'com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType',
};

export default function itemSelectorValueToCollection(collection) {
	const {
		classNameId,
		classPK,
		itemSubtype,
		itemType,
		key,
		returnType: type,
		sourceItemType,
		title,
	} = collection;

	switch (type) {
		case RETURN_TYPES.assetList:
			return {
				classNameId,
				classPK,
				itemSubtype,
				itemType,
				title,
				type,
			};
		case RETURN_TYPES.infoList:
			return {
				itemSubtype,
				itemType,
				key,
				sourceItemType,
				title,
				type,
			};

		default:
			return {...collection};
	}
}
