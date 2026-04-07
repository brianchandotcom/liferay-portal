/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ISearchAssetObjectEntry} from '../../../common/types/AssetType';
import {StickerConfig} from '../../../common/types/StickerConfig';
import {openFindAndReplaceModal} from './openFindAndReplaceModal';

const EXPIRED_STATUS_CODE = 3;

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

	openFindAndReplaceModal({
		availableLocales,
		dataSetId,
		fdsItems: normalizeFdsItems(fdsItems),
		stickerConfig,
	});
}

function normalizeFdsItems(fdsItems: ISearchAssetObjectEntry[]) {
	return fdsItems.filter(
		(item) => item.embedded.status.code !== EXPIRED_STATUS_CODE
	);
}
