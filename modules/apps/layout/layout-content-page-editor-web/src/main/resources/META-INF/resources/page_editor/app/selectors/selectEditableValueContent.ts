/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LanguageId} from '../../types/layout_data/BaseLayoutDataItem';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/editableFragmentEntryProcessor';
import {getEditableLocalizedValue} from '../utils/getEditableLocalizedValue';
import selectEditableValue from './selectEditableValue';

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
	processorType = EDITABLE_FRAGMENT_ENTRY_PROCESSOR
) {
	return getEditableLocalizedValue(
		selectEditableValue(
			{fragmentEntryLinks},
			fragmentEntryLinkId,
			editableId,
			processorType
		),
		languageId
	);
}
