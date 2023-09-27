/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION} from './types';

import type {LayoutData} from '../../types/LayoutData';
import type {FragmentEntryLink} from './addFragmentEntryLinks';
import type {PageContent} from './addItem';

export default function updateFragmentEntryLinkConfiguration({
	fragmentEntryLink,
	fragmentEntryLinkId,
	layoutData,
	pageContents,
}: {
	fragmentEntryLink: FragmentEntryLink;
	fragmentEntryLinkId: string;
	layoutData: LayoutData;
	pageContents: PageContent[];
}) {
	return {
		fragmentEntryLink,
		fragmentEntryLinkId,
		layoutData,
		pageContents,
		type: UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	} as const;
}
