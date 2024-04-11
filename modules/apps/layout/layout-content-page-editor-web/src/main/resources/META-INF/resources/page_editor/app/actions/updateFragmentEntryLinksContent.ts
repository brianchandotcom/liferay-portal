/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_FRAGMENT_ENTRY_LINKS_CONTENT} from './types';

export default function updateFragmentEntryLinkContent({
	fragmentEntryLinksContent,
}: {
	fragmentEntryLinksContent: Array<{
		content: string;
		fragmentEntryLinkId: string;
	}>;
}) {
	return {
		fragmentEntryLinksContent,
		type: UPDATE_FRAGMENT_ENTRY_LINKS_CONTENT,
	} as const;
}
