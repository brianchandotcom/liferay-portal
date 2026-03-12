/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ISearchAssetObjectEntry} from '../../../common/types/AssetType';
import {StickerConfig} from '../../../common/types/StickerConfig';
import {getFileMimeTypeObjectDefinitionStickerValue} from '../../props_transformer/utils/transformViewsItemProps';
import {ReplaceItem} from '../contexts/FindAndReplaceContext';

export function enrichItem({
	fdsItem,
	replaceItem,
	stickerConfig,
}: {
	fdsItem: ISearchAssetObjectEntry;
	replaceItem: ReplaceItem;
	stickerConfig: StickerConfig;
}) {
	const {
		fileMimeTypeCssClasses,
		fileMimeTypeIcons,
		objectDefinitionCssClasses,
		objectDefinitionIcons,
	} = stickerConfig;

	const stickerClassName = getFileMimeTypeObjectDefinitionStickerValue(
		fileMimeTypeCssClasses,
		objectDefinitionCssClasses,
		fdsItem
	);

	const stickerSymbol =
		getFileMimeTypeObjectDefinitionStickerValue(
			fileMimeTypeIcons,
			objectDefinitionIcons,
			fdsItem
		) || 'documents-and-media';

	return {
		...replaceItem,
		className: fdsItem.entryClassName,
		stickerClassName,
		stickerSymbol,
		title: getTitle(replaceItem),
	};
}

function getTitle(item: ReplaceItem) {
	const field = item.fields.find((field) => field.name === 'title');

	return field?.value_i18n![
		Liferay.ThemeDisplay.getDefaultLanguageId()
	] as string;
}
