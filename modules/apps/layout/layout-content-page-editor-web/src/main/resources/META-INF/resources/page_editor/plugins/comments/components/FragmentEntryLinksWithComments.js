/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';
import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {
	useHoverItem,
	useSelectItem,
} from '../../../app/contexts/ControlsContext';
import {useSelector} from '../../../app/contexts/StoreContext';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import NoCommentsMessage from './NoCommentsMessage';
import ResolvedCommentsToggle from './ResolvedCommentsToggle';

export default function FragmentEntryLinksWithComments() {
	const itemsWithComments = useSelector((state) =>
		Object.values(state.layoutData.items)
			.filter((item) => item.type === LAYOUT_DATA_ITEM_TYPES.fragment)
			.map((item) => [
				item,
				state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
			])
			.map(([item, fragmentEntryLink]) => [
				item,
				{
					...fragmentEntryLink,
					comments: (fragmentEntryLink.comments || []).filter(
						({resolved}) =>
							(state.showResolvedComments && resolved) ||
							!resolved
					),
				},
			])
			.filter(
				([, fragmentEntryLink]) => fragmentEntryLink.comments.length
			)
	);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('comments')}
			</SidebarPanelHeader>

			<ResolvedCommentsToggle />

			{itemsWithComments.length ? (
				<nav className="list-group mb-0 overflow-auto page-editor__fragments-with-comments">
					{itemsWithComments.map(([item, fragmentEntryLink]) => (
						<FragmentEntryLinkWithComments
							fragmentEntryLink={fragmentEntryLink}
							item={item}
							key={fragmentEntryLink.fragmentEntryLinkId}
						/>
					))}
				</nav>
			) : (
				<NoCommentsMessage />
			)}
		</>
	);
}

function FragmentEntryLinkWithComments({fragmentEntryLink, item}) {
	const selectItem = useSelectItem();
	const hoverItem = useHoverItem();

	return (
		<button
			aria-label={Liferay.Language.get('show-comments')}
			className="border-0 flex-shrink-0 list-group-item list-group-item-action"
			onClick={() => selectItem(item.itemId)}
			onFocus={() => hoverItem(item.itemId)}
			onMouseOut={() => hoverItem(null)}
			onMouseOver={() => hoverItem(item.itemId)}
			type="button"
		>
			<strong className="d-block text-dark">
				{fragmentEntryLink.name}
			</strong>

			<span className="text-secondary">
				{sub(
					fragmentEntryLink.comments.length === 1
						? Liferay.Language.get('x-comment')
						: Liferay.Language.get('x-comments'),
					fragmentEntryLink.comments.length
				)}
			</span>
		</button>
	);
}
