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

import {useCallback, useEffect} from 'react';

import switchSidebarPanel from '../actions/switchSidebarPanel';
import {HIGHLIGHTED_COMMENT_ID_KEY} from '../config/constants/highlightedCommentIdKey';
import {useSelectItem} from '../contexts/ControlsContext';
import {useDispatch, useSelector} from '../contexts/StoreContext';

export default function URLParser() {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
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

			const {itemId} = Object.values(layoutData.items).find(
				(item) =>
					item.config.fragmentEntryLinkId === fragmentEntryLinkId
			) || {itemId: null};

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
		[dispatch, fragmentEntryLinks, layoutData.items, selectItem]
	);

	useEffect(() => {
		const url = new URL(window.location.href);

		if (url.searchParams.has('messageId')) {
			window.sessionStorage.setItem(
				HIGHLIGHTED_COMMENT_ID_KEY,
				url.searchParams.get('messageId')
			);

			selectFragment(url.searchParams.get('messageId'));
			url.searchParams.delete('messageId');

			let skipLoadPopstate;

			if (Liferay.SPA && Liferay.SPA.app) {
				skipLoadPopstate = Liferay.SPA.app.skipLoadPopstate;
				Liferay.SPA.app.skipLoadPopstate = true;
			}

			history.replaceState(null, document.head.title, url.href);

			requestAnimationFrame(() => {
				if (
					Liferay.SPA &&
					Liferay.SPA.app &&
					typeof skipLoadPopstate === 'boolean'
				) {
					Liferay.SPA.app.skipLoadPopstate = skipLoadPopstate;
				}
			});
		}
	}, [selectFragment]);

	return null;
}
