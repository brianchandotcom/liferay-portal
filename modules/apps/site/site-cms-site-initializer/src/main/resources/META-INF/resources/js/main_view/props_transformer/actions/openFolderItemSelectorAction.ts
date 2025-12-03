/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';

import {AssetLibrary} from '../../../common/types/AssetLibrary';
import FolderItemSelectorModalContent from '../../modal/FolderItemSelectorModalContent';

export default function openFolderItemSelectorAction(
	action: 'copy' | 'move',
	assetLibraries: AssetLibrary[],
	itemData: ItemData,
	loadData: () => {},
	objectEntryFolderExternalReferenceCode: string,
	rootObjectEntryFolderExternalReferenceCode: string
) {
	return render(

		// @ts-ignore

		FolderItemSelectorModalContent,
		{
			action,
			assetLibraries,
			itemData,
			loadData,
			objectEntryFolderExternalReferenceCode,
			rootObjectEntryFolderExternalReferenceCode,
		},
		document.createElement('div')
	);
}
