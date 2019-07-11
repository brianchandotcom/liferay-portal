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

/* eslint no-unused-vars: "warn" */

import PropTypes from 'prop-types';
import React from 'react';

import {NoCommentsMessage} from './NoCommentsMessage.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import {ConnectedFragmentComments} from './FragmentComments.es';
import {getConnectedReactComponent} from '../../../store/ConnectedComponent.es';

const SidebarComments = props => {
	const fragmentIsSelected =
		props.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment &&
		props.activeItemId;

	return fragmentIsSelected ? (
		<ConnectedFragmentComments fragmentEntryLinkId={props.activeItemId} />
	) : (
		<NoCommentsMessage />
	);
};

SidebarComments.propTypes = {
	activeItemId: PropTypes.string,
	activeItemType: PropTypes.string
};

const ConnectedSidebarComments = getConnectedReactComponent(
	state => ({
		activeItemId: state.activeItemId,
		activeItemType: state.activeItemType
	}),
	() => ({})
)(SidebarComments);

export {ConnectedSidebarComments, SidebarComments};
export default ConnectedSidebarComments;
