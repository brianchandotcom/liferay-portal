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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import {CLEAR_ACTIVE_ITEM} from '../../../actions/actions.es';
import {deleteFragmentEntryLinkCommentAction} from '../../../actions/deleteFragmentEntryLinkComment.es';
import {updateFragmentEntryLinkCommentAction} from '../../../actions/updateFragmentEntryLinkComment.es';
import {updateFragmentEntryLinkCommentReplyAction} from '../../../actions/updateFragmentEntryLinkCommentReply.es';
import useDispatch from '../../../store/hooks/useDispatch.es';
import useGetComments from '../../../store/hooks/useGetComments.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import SidebarHeader from '../SidebarHeader.es';
import AddCommentForm from './AddCommentForm.es';
import FragmentComment from './FragmentComment.es';
import ResolvedCommentsToggle from './ResolvedCommentsToggle.es';

const FragmentComments = props => {
	const fragmentEntryLink = props.fragmentEntryLink;
	const getComments = useGetComments();
	const fragmentEntryLinkComments = getComments(fragmentEntryLink);
	const dispatch = useDispatch();

	const {
		clearActiveItem,
		deleteComment,
		editComment,
		editCommentReply
	} = getActions(dispatch, props);

	return (
		<>
			<SidebarHeader className="comments-sidebar-title">
				<ClayButton
					borderless
					className="text-dark"
					onClick={clearActiveItem}
					small
				>
					<ClayIcon symbol="angle-left" />
				</ClayButton>

				<span>{fragmentEntryLink.name}</span>
			</SidebarHeader>

			<ResolvedCommentsToggle />

			<div
				data-fragments-editor-item-id={
					fragmentEntryLink.fragmentEntryLinkId
				}
				data-fragments-editor-item-type={
					FRAGMENTS_EDITOR_ITEM_TYPES.fragment
				}
			>
				<AddCommentForm
					fragmentEntryLinkId={fragmentEntryLink.fragmentEntryLinkId}
				/>

				{fragmentEntryLinkComments.map((_, i) => {
					const comment =
						fragmentEntryLinkComments[
							fragmentEntryLinkComments.length - 1 - i
						];
					return (
						<FragmentComment
							comment={comment}
							fragmentEntryLinkId={
								fragmentEntryLink.fragmentEntryLinkId
							}
							key={comment.commentId}
							onDelete={deleteComment}
							onEdit={editComment}
							onEditReply={editCommentReply}
						/>
					);
				})}
			</div>
		</>
	);
};

FragmentComments.propTypes = {
	fragmentEntryLink: PropTypes.object.isRequired
};

const getActions = (dispatch, ownProps) => ({
	clearActiveItem: () =>
		dispatch({
			type: CLEAR_ACTIVE_ITEM
		}),
	deleteComment: comment =>
		dispatch(
			deleteFragmentEntryLinkCommentAction(
				ownProps.fragmentEntryLink.fragmentEntryLinkId,
				comment
			)
		),
	editComment: comment =>
		dispatch(
			updateFragmentEntryLinkCommentAction(
				ownProps.fragmentEntryLink.fragmentEntryLinkId,
				comment
			)
		),
	editCommentReply: parentCommentId => comment =>
		dispatch(
			updateFragmentEntryLinkCommentReplyAction(
				ownProps.fragmentEntryLink.fragmentEntryLinkId,
				parentCommentId,
				comment
			)
		)
});

export {FragmentComments};
export default FragmentComments;
