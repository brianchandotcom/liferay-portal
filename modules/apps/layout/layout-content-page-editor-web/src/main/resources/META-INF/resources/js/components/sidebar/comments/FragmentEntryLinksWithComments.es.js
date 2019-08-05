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

import useDispatch from '../../../store/hooks/useDispatch.es';
import {
	UPDATE_ACTIVE_ITEM,
	UPDATE_HOVERED_ITEM
} from '../../../actions/actions.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import useSelector from '../../../store/hooks/useSelector.es';

const FragmentEntryLinksWithComments = () => {
	const dispatch = useDispatch();

	const fragmentEntryLinksWithComments = useSelector(state =>
		Object.values(state.fragmentEntryLinks).filter(
			fragmentEntryLink => (fragmentEntryLink.comments || []).length
		)
	);

	const setActiveFragmentEntryLink = fragmentEntryLinkId => () => {
		dispatch({
			activeItemId: fragmentEntryLinkId,
			activeItemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
			type: UPDATE_ACTIVE_ITEM
		});

		const fragmentEntryLinkElement = document.querySelector(
			`.fragment-entry-link-list [data-fragments-editor-item-id="${fragmentEntryLinkId}"][data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.fragment}"]`
		);

		if (fragmentEntryLinkElement) {
			fragmentEntryLinkElement.scrollIntoView({
				behavior: 'smooth',
				block: 'center'
			});
		}
	};

	const setHoveredFragmentEntryLink = fragmentEntryLinkId => () => {
		dispatch({
			activeItemId: fragmentEntryLinkId,
			activeItemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
			type: UPDATE_HOVERED_ITEM
		});
	};

	return (
		<nav className="list-group">
			{fragmentEntryLinksWithComments.map(fragmentEntryLink => (
				<a
					className="border-0 list-group-item list-group-item-action"
					href={`#${fragmentEntryLink.fragmentEntryLinkId}`}
					key={fragmentEntryLink.fragmentEntryLinkId}
					onClick={setActiveFragmentEntryLink(
						fragmentEntryLink.fragmentEntryLinkId
					)}
					onFocus={setHoveredFragmentEntryLink(
						fragmentEntryLink.fragmentEntryLinkId
					)}
					onMouseOver={setHoveredFragmentEntryLink(
						fragmentEntryLink.fragmentEntryLinkId
					)}
				>
					<strong className="d-block text-dark">{name}</strong>

					<span className="text-secondary">
						{Liferay.Util.sub(
							Liferay.Language.get('x-comments'),
							fragmentEntryLink.comments.length
						)}
					</span>
				</a>
			))}
		</nav>
	);
};

export {FragmentEntryLinksWithComments};
export default FragmentEntryLinksWithComments;
