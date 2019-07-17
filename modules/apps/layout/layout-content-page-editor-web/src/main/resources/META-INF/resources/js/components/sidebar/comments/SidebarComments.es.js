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

import PropTypes from 'prop-types';
import React from 'react';

import {NoCommentsMessage} from './NoCommentsMessage.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import {ConnectedFragmentComments} from './FragmentComments.es';
import {getConnectedReactComponent} from '../../../store/ConnectedComponent.es';
import {getItemPath} from '../../../utils/FragmentsEditorGetUtils.es';

const SidebarComments = props => {
	const activeFragmentEntryLink = getItemPath(
		props.activeItemId,
		props.activeItemType,
		props.structure
	).find(
		activeItem =>
			activeItem.itemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment
	);

	return activeFragmentEntryLink ? (
		<ConnectedFragmentComments
			fragmentEntryLinkId={activeFragmentEntryLink.itemId}
		/>
	) : (
		<NoCommentsMessage />
	);
};

SidebarComments.propTypes = {
	activeItemId: PropTypes.string,
	activeItemType: PropTypes.string,
	structure: PropTypes.array
};

const ConnectedSidebarComments = getConnectedReactComponent(
	state => ({
		activeItemId: state.activeItemId,
		activeItemType: state.activeItemType,
		structure: state.layoutData.structure
	}),
	() => ({})
)(SidebarComments);

export {ConnectedSidebarComments, SidebarComments};
export default ConnectedSidebarComments;
