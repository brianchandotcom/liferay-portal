/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addFragmentEntryLinkComment from '../actions/addFragmentEntryLinkComment';
import addFragmentEntryLinks, {
	FragmentEntryLinkMap,
} from '../actions/addFragmentEntryLinks';
import addItem from '../actions/addItem';
import changeMasterLayout from '../actions/changeMasterLayout';
import deleteFragmentEntryLinkComment from '../actions/deleteFragmentEntryLinkComment';
import deleteItem from '../actions/deleteItem';
import duplicateItem from '../actions/duplicateItem';
import editFragmentEntryLinkComment from '../actions/editFragmentEntryLinkComment';
import updateCollectionDisplayCollection from '../actions/updateCollectionDisplayCollection';
import updateEditableValues from '../actions/updateEditableValues';
import updateFormItemConfig from '../actions/updateFormItemConfig';
import updateFragmentEntryLinkConfiguration from '../actions/updateFragmentEntryLinkConfiguration';
import updateFragmentEntryLinkContent from '../actions/updateFragmentEntryLinkContent';
import updatePreviewImage from '../actions/updatePreviewImage';
export declare const INITIAL_STATE: FragmentEntryLinkMap;
export default function fragmentEntryLinksReducer(
	fragmentEntryLinks: FragmentEntryLinkMap | undefined,
	action: ReturnType<
		| typeof addItem
		| typeof addFragmentEntryLinks
		| typeof addFragmentEntryLinkComment
		| typeof changeMasterLayout
		| typeof deleteItem
		| typeof deleteFragmentEntryLinkComment
		| typeof duplicateItem
		| typeof editFragmentEntryLinkComment
		| typeof updateCollectionDisplayCollection
		| typeof updateEditableValues
		| typeof updateFormItemConfig
		| typeof updateFragmentEntryLinkConfiguration
		| typeof updateFragmentEntryLinkContent
		| typeof updatePreviewImage
	>
): FragmentEntryLinkMap;
