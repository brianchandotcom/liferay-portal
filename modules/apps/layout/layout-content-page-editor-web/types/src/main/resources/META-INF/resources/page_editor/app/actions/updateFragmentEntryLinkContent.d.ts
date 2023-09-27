/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function updateFragmentEntryLinkContent({
	collectionContentId,
	content,
	fragmentEntryLinkId,
}: {
	collectionContentId?: string;
	content: string;
	fragmentEntryLinkId: string;
}): {
	readonly collectionContentId: string | undefined;
	readonly content: string;
	readonly fragmentEntryLinkId: string;
	readonly type: 'UPDATE_FRAGMENT_ENTRY_LINK_CONTENT';
};
