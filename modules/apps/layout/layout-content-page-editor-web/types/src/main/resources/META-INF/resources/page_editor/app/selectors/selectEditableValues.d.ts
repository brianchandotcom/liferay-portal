/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
export default function selectEditableValues(
	{
		fragmentEntryLinks,
	}: {
		fragmentEntryLinks: FragmentEntryLinkMap;
	},
	fragmentEntryLinkId: string
): {
	'com.liferay.fragment.entry.processor.background.image.BackgroundImageFragmentEntryProcessor': {
		[
			x: string
		]: import('../../types/editables/EditableValue').EditableValue;
	};
	'com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor': {
		[
			x: string
		]: import('../../types/editables/EditableValue').EditableValue;
	};
	'com.liferay.fragment.entry.processor.freemarker.FreeMarkerFragmentEntryProcessor': {
		[
			x: string
		]: import('../../types/editables/EditableValue').EditableValue;
	};
};
