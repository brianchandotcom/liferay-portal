/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
