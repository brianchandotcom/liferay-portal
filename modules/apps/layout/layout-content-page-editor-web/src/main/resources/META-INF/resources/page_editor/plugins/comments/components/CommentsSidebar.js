/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSessionState} from 'frontend-js-components-web';
import React from 'react';

import {HIGHLIGHTED_COMMENT_ID_KEY} from '../../../app/config/constants/highlightedCommentIdKey';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useActiveItemId} from '../../../app/contexts/ControlsContext';
import {useSelectorCallback} from '../../../app/contexts/StoreContext';
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
