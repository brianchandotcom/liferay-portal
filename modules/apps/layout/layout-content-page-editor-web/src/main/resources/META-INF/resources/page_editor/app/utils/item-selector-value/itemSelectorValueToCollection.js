/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const RETURN_TYPES = {
	assetList:
		'com.liferay.item.selector.criteria.InfoListItemSelectorReturnType',
	infoItemRelatedList:
		'com.liferay.info.list.provider.item.selector.criterion.InfoItemRelatedListProviderItemSelectorReturnType',
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

		case RETURN_TYPES.infoItemRelatedList:
			return {
				itemType,
				key,
				sourceItemType,
				title,
				type,
			};
		case RETURN_TYPES.infoList:
			return {
				itemSubtype,
				itemType,
				key,
				title,
				type,
			};

		default:
			return {...collection};
	}
}
