/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';

import {ISearchAssetObjectEntry} from '../../../common/types/AssetType';
import {StickerConfig} from '../../../common/types/StickerConfig';
import FindAndReplaceModal from '../components/FindAndReplaceModal';

type Props = {
	availableLocales: Locale[];
	dataSetId: string;
	fdsItems: ISearchAssetObjectEntry[];
	stickerConfig: StickerConfig;
};

export function openFindAndReplaceModal(props: Props) {

	// @ts-ignore

	return render(FindAndReplaceModal, props, document.createElement('div'));
}
