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

import {HIGHLIGHTED_COMMENT_ID_KEY} from '../../../app/config/constants/highlightedCommentIdKey';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useActiveItemId} from '../../../app/contexts/ControlsContext';
import {useSelectorCallback} from '../../../app/contexts/StoreContext';
import {useSessionState} from '../../../core/hooks/useSessionState';
import FragmentComments from './FragmentComments';
import FragmentEntryLinksWithComments from './FragmentEntryLinksWithComments';

export default function CommentsSidebar() {
	const activeItemId = useActiveItemId();
	const [highlightedMessageId] = useSessionState(HIGHLIGHTED_COMMENT_ID_KEY);

	const activeFragmentEntryLink = useSelectorCallback(
		(state) => {
			const getActiveFragmentEntryLink = (itemId) => {
				const item = state.layoutData.items[itemId];

				if (item) {
					if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
						return (
							state.fragmentEntryLinks[
								item.config.fragmentEntryLinkId
							] || null
						);
					}
					else if (item.parentId) {
						return getActiveFragmentEntryLink(item.parentId);
					}
				}

				return null;
			};

			return getActiveFragmentEntryLink(activeItemId);
		},
		[activeItemId, highlightedMessageId]
	);

	return (
		<div
			className="d-flex flex-column"
			onMouseDown={(event) =>
				event.nativeEvent.stopImmediatePropagation()
			}
		>
			{activeFragmentEntryLink ? (
				<FragmentComments fragmentEntryLink={activeFragmentEntryLink} />
			) : (
				<FragmentEntryLinksWithComments />
			)}
		</div>
	);
}
