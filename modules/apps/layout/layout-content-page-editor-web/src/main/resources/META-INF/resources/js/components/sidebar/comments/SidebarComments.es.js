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

import useSelector from '../../../store/hooks/useSelector.es';
import {getItemPath} from '../../../utils/FragmentsEditorGetUtils.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import {FragmentComments} from './FragmentComments.es';
import {FragmentEntryLinksWithComments} from './FragmentEntryLinksWithComments.es';

const SidebarComments = () => {
	const activeItemId = useSelector(state => state.activeItemId);
	const activeItemType = useSelector(state => state.activeItemType);
	const structure = useSelector(state => state.layoutData.structure);
	const activeFragmentEntryLink = getItemPath(
		activeItemId,
		activeItemType,
		structure
	).find(
		activeItem =>
			activeItem.itemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment
	);
	const fragmentEntryLink = useSelector(state =>
		activeFragmentEntryLink
			? state.fragmentEntryLinks[activeFragmentEntryLink.itemId]
			: null
	);

	if (fragmentEntryLink) {
		return <FragmentComments fragmentEntryLink={fragmentEntryLink} />;
	}

	return <FragmentEntryLinksWithComments />;
};

export {SidebarComments};
export default SidebarComments;
