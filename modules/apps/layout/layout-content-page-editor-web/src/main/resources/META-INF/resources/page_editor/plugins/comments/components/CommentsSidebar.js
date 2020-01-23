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

import React from 'react';

import {useActiveItemId} from '../../../app/components/Controls';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useSelector} from '../../../app/store/index';
import FragmentComments from './FragmentComments';
import FragmentEntryLinksWithComments from './FragmentEntryLinksWithComments';

function getActiveFragmentEntryLink(itemId, fragmentEntryLinks, layoutData) {
	const item = layoutData.items[itemId];

	if (item) {
		if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
			return fragmentEntryLinks[item.config.fragmentEntryLinkId];
		} else if (item.parentId) {
			return getActiveFragmentEntryLink(
				item.parentId,
				fragmentEntryLinks,
				layoutData
			);
		}
	}

	return null;
}

export default function CommentsSidebar() {
	const fragmentEntryLinks = useSelector(state => state.fragmentEntryLinks);
	const layoutData = useSelector(state => state.layoutData);

	const activeItemId = useActiveItemId();

	const activeFragmentEntryLink = getActiveFragmentEntryLink(
		activeItemId,
		fragmentEntryLinks,
		layoutData
	);

	return (
		<div
			onMouseDown={event => event.nativeEvent.stopImmediatePropagation()}
		>
			{activeFragmentEntryLink ? (
				<FragmentComments fragmentEntryLink={activeFragmentEntryLink} />
			) : (
				<FragmentEntryLinksWithComments />
			)}
		</div>
	);
}
