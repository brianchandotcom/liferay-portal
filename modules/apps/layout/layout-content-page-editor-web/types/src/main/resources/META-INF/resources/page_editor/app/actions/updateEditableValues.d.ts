/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {FragmentEntryLink} from './addFragmentEntryLinks';
import type {PageContent} from './addItem';
export default function updateEditableValues({
	content,
	editableValues,
	fragmentEntryLinkId,
	pageContents,
	segmentsExperienceId,
}: {
	content: string;
	editableValues: FragmentEntryLink['editableValues'];
	fragmentEntryLinkId: string;
	pageContents: PageContent[];
	segmentsExperienceId: string;
}): {
	readonly content: string;
	readonly editableValues: {
		'com.liferay.fragment.entry.processor.background.image.BackgroundImageFragmentEntryProcessor': {
			[x: string]: unknown;
		};
		'com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor': {
			[x: string]: unknown;
		};
		'com.liferay.fragment.entry.processor.freemarker.FreeMarkerFragmentEntryProcessor': {
			[x: string]: unknown;
		};
	};
	readonly fragmentEntryLinkId: string;
	readonly pageContents: PageContent[];
	readonly segmentsExperienceId: string;
	readonly type: 'UPDATE_EDITABLE_VALUES';
};
