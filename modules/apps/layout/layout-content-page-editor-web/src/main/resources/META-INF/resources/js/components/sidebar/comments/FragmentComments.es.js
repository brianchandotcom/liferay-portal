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

import AddCommentForm from './AddCommentForm.es';
import {deleteFragmentEntryLinkCommentAction} from '../../../actions/deleteFragmentEntryLinkComment.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import {updateFragmentEntryLinkCommentAction} from '../../../actions/updateFragmentEntryLinkComment.es';
import {updateFragmentEntryLinkCommentReplyAction} from '../../../actions/updateFragmentEntryLinkCommentReply.es';
import FragmentComment from './FragmentComment.es';
import useSelector from '../../../store/hooks/useSelector.es';
import useDispatch from '../../../store/hooks/useDispatch.es';
import {CLEAR_ACTIVE_ITEM} from '../../../actions/actions.es';

const FragmentComments = props => {
	const fragmentEntryLink = useSelector(
		state => state.fragmentEntryLinks[props.fragmentEntryLinkId]
	);
	const fragmentEntryLinkComments = fragmentEntryLink.comments || [];
	const dispatch = useDispatch();

	const {
		clearActiveItem,
		deleteComment,
		editComment,
		editCommentReply
	} = getActions(dispatch, props);

	return (
		<>
			<h1 className="align-items-center d-flex h4 py-4">
				<ClayButton
					borderless
					className="pl-3 text-dark"
					onClick={clearActiveItem}
					small
				>
					<ClayIcon symbol="angle-left" />
				</ClayButton>

				{fragmentEntryLink.name}
			</h1>

			<div
				data-fragments-editor-item-id={props.fragmentEntryLinkId}
				data-fragments-editor-item-type={
					FRAGMENTS_EDITOR_ITEM_TYPES.fragment
				}
			>
				<AddCommentForm
					fragmentEntryLinkId={props.fragmentEntryLinkId}
				/>

				{[...fragmentEntryLinkComments].reverse().map(comment => (
					<FragmentComment
						comment={comment}
						fragmentEntryLinkId={props.fragmentEntryLinkId}
						key={comment.commentId}
						onDelete={deleteComment}
						onEdit={editComment}
						onEditReply={editCommentReply}
					/>
				))}
			</div>
		</>
	);
};

FragmentComments.propTypes = {
	fragmentEntryLinkId: PropTypes.string.isRequired
};

const getActions = (dispatch, ownProps) => ({
	clearActiveItem: () =>
		dispatch({
			type: CLEAR_ACTIVE_ITEM
		}),
	deleteComment: comment =>
		dispatch(
			deleteFragmentEntryLinkCommentAction(
				ownProps.fragmentEntryLinkId,
				comment
			)
		),
	editComment: comment =>
		dispatch(
			updateFragmentEntryLinkCommentAction(
				ownProps.fragmentEntryLinkId,
				comment
			)
		),
	editCommentReply: parentCommentId => comment =>
		dispatch(
			updateFragmentEntryLinkCommentReplyAction(
				ownProps.fragmentEntryLinkId,
				parentCommentId,
				comment
			)
		)
});

export {FragmentComments};
export default FragmentComments;
