/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/layout_data/LayoutData';
import addFragmentEntryLinks from '../actions/addFragmentEntryLinks';
import addItem from '../actions/addItem';
import addRule from '../actions/addRule';
import deleteItem from '../actions/deleteItem';
import deleteRule from '../actions/deleteRule';
import duplicateItem from '../actions/duplicateItem';
import moveItem from '../actions/moveItem';
import updateColSize from '../actions/updateColSize';
import updateCollectionDisplayCollection from '../actions/updateCollectionDisplayCollection';
import updateFormItemConfig from '../actions/updateFormItemConfig';
import updateFragmentEntryLinkConfiguration from '../actions/updateFragmentEntryLinkConfiguration';
import updateItemConfig from '../actions/updateItemConfig';
import updatePreviewImage from '../actions/updatePreviewImage';
import updateRowColumns from '../actions/updateRowColumns';
import updateRule from '../actions/updateRule';
export declare const INITIAL_STATE: LayoutData;
export default function layoutDataReducer(
	layoutData: LayoutData | undefined,
	action:
		| ReturnType<typeof addFragmentEntryLinks>
		| ReturnType<typeof addItem>
		| ReturnType<typeof addRule>
		| ReturnType<typeof deleteItem>
		| ReturnType<typeof deleteRule>
		| ReturnType<typeof duplicateItem>
		| ReturnType<typeof moveItem>
		| ReturnType<typeof updateCollectionDisplayCollection>
		| ReturnType<typeof updateColSize>
		| ReturnType<typeof updateFormItemConfig>
		| ReturnType<typeof updateFragmentEntryLinkConfiguration>
		| ReturnType<typeof updateItemConfig>
		| ReturnType<typeof updatePreviewImage>
		| ReturnType<typeof updateRowColumns>
		| ReturnType<typeof updateRule>
): LayoutData;
