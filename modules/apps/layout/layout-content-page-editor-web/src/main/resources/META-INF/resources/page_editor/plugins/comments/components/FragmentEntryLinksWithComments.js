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

import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {
	useHoverItem,
	useSelectItem,
} from '../../../app/contexts/ControlsContext';
import {useSelector} from '../../../app/contexts/StoreContext';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
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

			<SidebarPanelContent padded={false}>
				<ResolvedCommentsToggle />

				{itemsWithComments.length ? (
					<nav className="list-group">
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
			</SidebarPanelContent>
		</>
	);
}

function FragmentEntryLinkWithComments({fragmentEntryLink, item}) {
	const selectItem = useSelectItem();
	const hoverItem = useHoverItem();

	return (
		<a
			className="border-0 list-group-item list-group-item-action"
			href={`#${fragmentEntryLink.fragmentEntryLinkId}`}
			onClick={() => selectItem(item.itemId)}
			onFocus={() => hoverItem(item.itemId)}
			onMouseOut={() => hoverItem(null)}
			onMouseOver={() => hoverItem(item.itemId)}
		>
			<strong className="d-block text-dark">
				{fragmentEntryLink.name}
			</strong>

			<span className="text-secondary">
				{Liferay.Util.sub(
					fragmentEntryLink.comments.length === 1
						? Liferay.Language.get('x-comment')
						: Liferay.Language.get('x-comments'),
					fragmentEntryLink.comments.length
				)}
			</span>
		</a>
	);
}
