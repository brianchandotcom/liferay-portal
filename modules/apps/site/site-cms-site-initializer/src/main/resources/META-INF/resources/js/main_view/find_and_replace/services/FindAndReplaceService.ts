/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locale} from 'frontend-js-components-web';

import ApiHelper from '../../../common/services/ApiHelper';
import {ISearchAssetObjectEntry} from '../../../common/types/AssetType';
import {StickerConfig} from '../../../common/types/StickerConfig';
import {triggerAssetBulkAction} from '../../props_transformer/actions/triggerAssetBulkAction';
import {ReplaceItem} from '../contexts/FindAndReplaceContext';
import {enrichItem} from '../utils/enrichItem';
import {getReplaceData} from '../utils/getReplaceData';

async function getReplaceItems({
	fdsItems,
	stickerConfig,
}: {
	fdsItems: ISearchAssetObjectEntry[];
	stickerConfig: StickerConfig;
}) {
	const formData = new FormData();

	const objectEntries = fdsItems.map((item) => ({
		className: item.entryClassName,
		objectEntryId: item.embedded.id,
	}));

	formData.append('objectEntries', JSON.stringify(objectEntries));

	const response = await ApiHelper.postFormData<ReplaceItem[]>(
		formData,
		`${Liferay.ThemeDisplay.getPathMain()}/cms/get_object_entries_values`
	);

	if (response.error || !response.data) {
		return response;
	}

	const fdsItemsMap = new Map<string, ISearchAssetObjectEntry>();

	for (const fdsItem of fdsItems) {
		fdsItemsMap.set(String(fdsItem.embedded.id), fdsItem);
	}

	const data = response.data.map((replaceItem) => {
		const fdsItem = fdsItemsMap.get(replaceItem.id);

		if (!fdsItem) {
			return replaceItem;
		}

		return enrichItem({fdsItem, replaceItem, stickerConfig});
	});

	return {
		...response,
		data,
	};
}

function performBulkReplace({
	dataSetId,
	items: replaceItems,
	localeId,
	replacement,
	search,
}: {
	dataSetId: string;
	items: ReplaceItem[];
	localeId: Locale['id'] | 'all';
	replacement: string;
	search: string;
}) {
	const {items, values} = getReplaceData({
		localeId,
		replaceItems,
		replacement,
		search,
	});

	triggerAssetBulkAction<'UpdateObjectValuesBulkSelectionAction'>({
		additionalData: {
			replacement,
			search,
		},
		apiURL: '/o/bulk/v1.0/bulk-action',
		dataSetId,
		keyValues: {
			values,
		},
		selectedData: {
			items: items as unknown as ISearchAssetObjectEntry[],
			selectAll: false,
		},
		type: 'UpdateObjectValuesBulkSelectionAction',
	});
}

function performSingleReplace({
	item,
	localeId,
	replacement,
	search,
}: {
	item: ReplaceItem;
	localeId: Locale['id'] | 'all';
	replacement: string;
	search: string;
}) {
	const {items, values} = getReplaceData({
		localeId,
		replaceItems: [item],
		replacement,
		search,
	});

	return ApiHelper.post('/o/bulk/v1.0/bulk-action', {
		bulkActionItems: items.map(({className, id}) => ({
			className,
			classPK: id,
		})),
		type: 'UpdateObjectValuesBulkSelectionAction',
		values,
	});
}

export default {getReplaceItems, performBulkReplace, performSingleReplace};
