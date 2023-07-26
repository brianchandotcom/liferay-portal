/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSessionState} from 'frontend-js-components-web';
import {COOKIE_TYPES} from 'frontend-js-web';
import {useCallback, useEffect} from 'react';

import switchSidebarPanel from '../../actions/switchSidebarPanel';
import {HIGHLIGHTED_COMMENT_ID_KEY} from '../../config/constants/highlightedCommentIdKey';
import {useSelectItem} from '../../contexts/ControlsContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import getFragmentItem from '../../utils/getFragmentItem';

export default function useURLParser() {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const [, setHighlightedCommentId] = useSessionState(
		HIGHLIGHTED_COMMENT_ID_KEY,
		undefined,
		COOKIE_TYPES.NECESSARY
	);
	const layoutData = useSelector((state) => state.layoutData);
	const dispatch = useDispatch();
	const selectItem = useSelectItem();

	const selectFragment = useCallback(
		(messageId) => {
			const {fragmentEntryLinkId} = Object.values(
				fragmentEntryLinks
			).find((fragmentEntryLink) =>
				fragmentEntryLink.comments.find(
					(comment) =>
						comment.commentId === messageId ||
						comment.children.find(
							(childComment) =>
								childComment.commentId === messageId
						)
				)
			) || {fragmentEntryLinkId: null};

			const {itemId} =
				getFragmentItem(layoutData, fragmentEntryLinkId) || {};

			if (itemId) {
				selectItem(itemId);

				dispatch(
					switchSidebarPanel({
						sidebarOpen: true,
						sidebarPanelId: 'comments',
					})
				);
			}
		},
		[dispatch, fragmentEntryLinks, layoutData, selectItem]
	);

	useEffect(() => {
		const url = new URL(window.location.href);

		if (url.searchParams.has('messageId')) {
			setHighlightedCommentId(url.searchParams.get('messageId'));
			selectFragment(url.searchParams.get('messageId'));
			url.searchParams.delete('messageId');

			history.replaceState(null, document.head.title, url.href);
		}

		if (url.searchParams.has('itemId')) {
			selectItem(url.searchParams.get('itemId'));

			url.searchParams.delete('itemId');

			history.replaceState(null, document.head.title, url.href);
		}
	}, [selectFragment, setHighlightedCommentId, selectItem]);
}
