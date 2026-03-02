/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {readConfigFromURL} from '@liferay/frontend-data-set-web';
import {openConfirmModal} from '@liferay/layout-js-components-web';

import {ISearchAssetObjectEntry} from '../../../common/types/AssetType';
import {StickerConfig} from '../../../common/types/StickerConfig';
import {openFindAndReplaceModal} from './openFindAndReplaceModal';

export async function handleFindAndReplace({
	availableLocales,
	dataSetId,
	fdsItems,
	stickerConfig,
}: {
	availableLocales: Locale[];
	dataSetId?: string;
	fdsItems: ISearchAssetObjectEntry[];
	stickerConfig: StickerConfig;
}) {
	if (!dataSetId) {
		return;
	}

	const search = getSearchFromURL(dataSetId);

	if (!search) {
		showWarning({
			text: Liferay.Language.get(
				'to-use-find-and-replace-you-need-to-perform-a-search-first'
			),
			title: Liferay.Language.get('search-required'),
		});

		return;
	}

	openFindAndReplaceModal({
		availableLocales,
		dataSetId,
		fdsItems,
		search: normalizeSearch(search),
		stickerConfig,
	});
}

function getSearchFromURL(dataSetId: string): string | null {
	if (!dataSetId) {
		return null;
	}

	const config = readConfigFromURL(dataSetId);

	if (!config || !config.q) {
		return null;
	}

	return config.q || null;
}

function normalizeSearch(search: string): string {
	if (search.startsWith('"') && search.endsWith('"')) {
		return search.slice(1, -1);
	}

	return search;
}

function showWarning({text, title}: {text: string; title: string}) {
	openConfirmModal({
		buttonLabel: Liferay.Language.get('ok'),
		center: true,
		status: 'warning',
		text,
		title,
	});
}
