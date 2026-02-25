/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';
import {sub} from 'frontend-js-web';

import FolderItemSelectorModalContent from '../../modal/FolderItemSelectorModalContent';
import {AdditionalProps} from '../AssetsFDSPropsTransformer';

const copyBulkAction = ({
	additionalProps,
	apiURL,
	dataSetId,
	selectedData,
}: {
	additionalProps: AdditionalProps;
	apiURL?: string;
	dataSetId?: string;
	selectedData: any;
}) => {
	const title =
		selectedData.items.length === 1
			? selectedData.items[0].title
			: sub(Liferay.Language.get('x-items'), [selectedData.items.length]);

	return render(

		// @ts-ignore

		FolderItemSelectorModalContent,
		{
			action: 'copy',
			apiURL,
			assetLibraries: additionalProps.assetLibraries,
			isBulk: true,
			itemData: {
				...selectedData,
				embedded: {
					...selectedData.items[0].embedded,
				},
				title,
			},
			objectEntryFolderExternalReferenceCode:
				additionalProps.objectEntryFolderExternalReferenceCode,
			rootObjectEntryFolderExternalReferenceCode:
				additionalProps.rootObjectEntryFolderExternalReferenceCode,
			selectedData: {
				...selectedData,
				id: dataSetId,
			},
		},
		document.createElement('div')
	);
};

export default copyBulkAction;
