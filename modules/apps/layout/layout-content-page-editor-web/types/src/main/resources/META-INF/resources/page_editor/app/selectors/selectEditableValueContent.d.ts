/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LanguageId} from '../../types/layout_data/BaseLayoutDataItem';
import type {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
export default function selectEditableValueContent(
	{
		fragmentEntryLinks,
		languageId,
	}: {
		fragmentEntryLinks: FragmentEntryLinkMap;
		languageId: LanguageId;
	},
	fragmentEntryLinkId: string,
	editableId: string,
	processorType?: 'com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor'
):
	| string
	| import('../../types/editables/EditableValue').EditableValue
	| undefined;
