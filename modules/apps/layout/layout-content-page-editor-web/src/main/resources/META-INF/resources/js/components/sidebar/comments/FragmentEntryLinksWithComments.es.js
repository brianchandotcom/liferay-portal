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

import {getConnectedReactComponent} from '../../../store/ConnectedComponent.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import {
	UPDATE_ACTIVE_ITEM,
	UPDATE_HOVERED_ITEM
} from '../../../actions/actions.es';

const FragmentEntryLinksWithComments = props => (
	<nav className="list-group">
		{props.fragmentEntryLinksWithComments.map(fragmentEntryLink => {
			const {comments, fragmentEntryLinkId, name} = fragmentEntryLink;

			const setActiveFragmentEntryLink = () =>
				props.setActiveFragmentEntryLink(fragmentEntryLinkId);

			const setHoveredFragmentEntryLink = () =>
				props.setHoveredFragmentEntryLink(fragmentEntryLinkId);

			return (
				<a
					className="border-0 list-group-item list-group-item-action"
					href={`#${fragmentEntryLinkId}`}
					key={fragmentEntryLinkId}
					onClick={setActiveFragmentEntryLink}
					onFocus={setHoveredFragmentEntryLink}
					onMouseOver={setHoveredFragmentEntryLink}
				>
					<strong className="d-block text-dark">{name}</strong>

					<span className="text-secondary">
						{Liferay.Util.sub(
							Liferay.Language.get('x-comments'),
							comments.length
						)}
					</span>
				</a>
			);
		})}
	</nav>
);

FragmentEntryLinksWithComments.propTypes = {
	fragmentEntryLinksWithComments: PropTypes.array,
	hoveredItemId: PropTypes.string,
	hoveredItemType: PropTypes.string,
	setActiveFragmentEntryLink: PropTypes.func,
	setHoveredFragmentEntryLink: PropTypes.func
};

const ConnectedFragmentEntryLinksWithComments = getConnectedReactComponent(
	state => ({
		fragmentEntryLinksWithComments: Object.values(
			state.fragmentEntryLinks
		).filter(
			fragmentEntryLink => (fragmentEntryLink.comments || []).length
		),

		hoveredItemId: state.hoveredItemId,
		hoveredItemType: state.hoveredItemType
	}),

	dispatch => ({
		setActiveFragmentEntryLink: fragmentEntryLinkId =>
			dispatch({
				activeItemId: fragmentEntryLinkId,
				activeItemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
				type: UPDATE_ACTIVE_ITEM
			}),

		setHoveredFragmentEntryLink: fragmentEntryLinkId =>
			dispatch({
				hoveredItemId: fragmentEntryLinkId,
				hoveredItemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
				type: UPDATE_HOVERED_ITEM
			})
	})
)(FragmentEntryLinksWithComments);

export {
	ConnectedFragmentEntryLinksWithComments,
	FragmentEntryLinksWithComments
};

export default ConnectedFragmentEntryLinksWithComments;
