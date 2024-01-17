/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {FragmentEntryLink} from './addFragmentEntryLinks';
export default function updateEditableValues({
	content,
	editableValues,
	fragmentEntryLinkId,
	segmentsExperienceId,
}: {
	content: string;
	editableValues: FragmentEntryLink['editableValues'];
	fragmentEntryLinkId: string;
	segmentsExperienceId: string;
}): {
	readonly content: string;
	readonly editableValues: {
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
	readonly fragmentEntryLinkId: string;
	readonly segmentsExperienceId: string;
	readonly type: 'UPDATE_EDITABLE_VALUES';
};
