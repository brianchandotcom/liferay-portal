/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';
import {sub} from 'frontend-js-web';

import {IBulkActionFDSData} from '../../../common/types/BulkActionTask';
import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../../../common/utils/constants';
import {openActionNotAllowedModal} from '../../../common/utils/openActionNotAllowedModal';
import FolderItemSelectorModalContent from '../../modal/FolderItemSelectorModalContent';
import {AdditionalProps} from '../AssetsFDSPropsTransformer';

/**
 * Common logic for folder item selector bulk actions (move/copy).
 */
const folderItemSelectorBulkAction = ({
	action,
	additionalProps,
	apiURL,
	dataSetId,
	selectedData,
}: {
	action: 'copy' | 'move';
	additionalProps: AdditionalProps;
	apiURL?: string;
	dataSetId?: string;
	selectedData: Required<IBulkActionFDSData>;
}) => {
	const title =
		selectedData.items.length === 1
			? selectedData.items[0].title
			: sub(Liferay.Language.get('x-items'), [selectedData.items.length]);

	const hasFileOrFolder = selectedData.items.some(
		(item) =>
			item.entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME ||
			item.embedded?.objectEntryFolderExternalReferenceCode === 'L_FILES'
	);

	const hasContent = selectedData.items.some(
		(item) =>
			item.embedded?.objectEntryFolderExternalReferenceCode ===
			'L_CONTENTS'
	);

	if (hasFileOrFolder && hasContent) {
		const moveMessage = Liferay.Language.get(
			'assets-with-different-content-types-cannot-be-moved-together.-select-assets-with-the-same-content-type-and-try-again'
		);
		const copyMessage = Liferay.Language.get(
			'assets-with-different-content-types-cannot-be-copied-together.-select-assets-with-the-same-content-type-and-try-again'
		);

		const message = action === 'move' ? moveMessage : copyMessage;

		return openActionNotAllowedModal({message});
	}

	return render(

		// @ts-ignore

		FolderItemSelectorModalContent,
		{
			action,
			apiURL,
			assetLibraries: additionalProps.assetLibraries,
			dataSetId,
			isBulk: true,
			itemData: {
				...selectedData,
				embedded: {
					...(selectedData.items[0]?.embedded || {}),
				},
				title,
			},
			objectEntryFolderExternalReferenceCode:
				additionalProps.objectEntryFolderExternalReferenceCode,
			rootObjectEntryFolderExternalReferenceCode:
				additionalProps.rootObjectEntryFolderExternalReferenceCode ||
				additionalProps.parentObjectEntryFolderExternalReferenceCode,
			selectedData,
		},
		document.createElement('div')
	);
};

export default folderItemSelectorBulkAction;
