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

import getLayoutDataItemLabel from '../../utils/getLayoutDataItemLabel';

/**
 * Obtain the name associated to the undo action,
 * for those cases where there is not name associated, returns null
 *
 * @param {object} options
 * @param {object} options.action
 * @param {object} options.state
 * @return {string|null}
 */
export function getItemNameFromAction({action, state}) {
	const fragmentEntryLinks = action.fragmentEntryLinks
		? Object.values(action.fragmentEntryLinks).reduce(
				(acc, fragmentEntryLink) => {
					acc[
						fragmentEntryLink.fragmentEntryLinkId
					] = fragmentEntryLink;

					return acc;
				},
				{}
		  )
		: state.fragmentEntryLinks;

	const item =
		state.layoutData?.items[action.itemId] ||
		action.layoutData?.items[action.itemId] ||
		Object.values(state.layoutData?.items ?? {}).find(
			(item) =>
				item.config.fragmentEntryLinkId === action.fragmentEntryLinkId
		) ||
		Object.values(action.layoutData?.items ?? {}).find(
			(item) =>
				item.config.fragmentEntryLinkId === action.fragmentEntryLinkId
		);

	if (!item) {
		return null;
	}

	return getLayoutDataItemLabel(item, fragmentEntryLinks);
}
