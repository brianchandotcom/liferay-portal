/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_EDITABLE_VALUES} from './types';

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
}) {
	return {
		content,
		editableValues,
		fragmentEntryLinkId,
		pageContents,
		segmentsExperienceId,
		type: UPDATE_EDITABLE_VALUES,
	} as const;
}
