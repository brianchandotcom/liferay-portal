/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addItem, {PageContent} from '../actions/addItem';
import deleteItem from '../actions/deleteItem';
import updateCollectionDisplayCollection from '../actions/updateCollectionDisplayCollection';
import updateEditableValues from '../actions/updateEditableValues';
import updateFragmentEntryLinkConfiguration from '../actions/updateFragmentEntryLinkConfiguration';
import updateItemConfig from '../actions/updateItemConfig';
import updatePageContents from '../actions/updatePageContents';
import updatePreviewImage from '../actions/updatePreviewImage';
import updateRowColumns from '../actions/updateRowColumns';
export default function pageContentsReducer(
	pageContents: PageContent[] | undefined,
	action: ReturnType<
		| typeof addItem
		| typeof deleteItem
		| typeof updateCollectionDisplayCollection
		| typeof updateEditableValues
		| typeof updateFragmentEntryLinkConfiguration
		| typeof updateItemConfig
		| typeof updatePageContents
		| typeof updatePreviewImage
		| typeof updateRowColumns
	>
): (
	| PageContent
	| {
			actions: {
				editImage: {
					previewURL: string;
					editImageURL?: string | undefined;
					fileEntryId?: string | undefined;
				};
				editURL?: string | undefined;
				permissionsURL?: string | undefined;
				viewUsagesURL?: string | undefined;
			};
			className: string;
			classNameId: string;
			classPK: string;
			classTypeId: string;
			externalReferenceCode: string;
			icon: string;
			isRestricted: boolean;
			status: {
				hasApprovedVersion: boolean;
				label: string;
				style: string;
			};
			subtype: string;
			title: string;
			type: string;
			usagesCount: number;
	  }
)[];
