/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
}): {
	readonly fragmentEntryLink: FragmentEntryLink<string, string>;
	readonly fragmentEntryLinkId: string;
	readonly layoutData: LayoutData;
	readonly pageContents: PageContent[];
	readonly type: 'UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION';
};
