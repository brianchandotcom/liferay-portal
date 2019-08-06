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

import classNames from 'classnames';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayDropDown from '@clayui/drop-down';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ReplyCommentForm from './ReplyCommentForm.es';
import {
	deleteFragmentEntryLinkComment,
	editFragmentEntryLinkComment
} from '../../../utils/FragmentsEditorFetchUtils.es';
import EditCommentForm from './EditCommentForm.es';
import InlineConfirm from '../../common/InlineConfirm.es';
import UserIcon from '../../common/UserIcon.es';
import ResolveButton from './ResolveButton.es';

const FragmentComment = props => {
	const isReply = props.parentCommentId;
	const [resolved, setResolved] = useState(false);

	const [dropDownActive, setDropDownActive] = useState(false);
	const [editing, setEditing] = useState(false);
	const [hidden, setHidden] = useState(false);
	const [showDeleteMask, setShowDeleteMash] = useState(false);
	const [showResolveMask, setShowResolveMask] = useState(false);

	const dateDescriptionProps = {
		className: 'm-0 text-secondary'
	};

	if (props.comment.edited && props.comment.modifiedDateDescription) {
		dateDescriptionProps.className += ' lfr-portal-tooltip';

		dateDescriptionProps['data-title'] = Liferay.Util.sub(
			Liferay.Language.get('edited-x'),
			props.comment.modifiedDateDescription
		);
	}

	const commentClassname = classNames({
		'fragments-editor__fragment-comment': true,
		'fragments-editor__fragment-comment--hidden': hidden,
		'fragments-editor__fragment-comment--reply': isReply,
		'fragments-editor__fragment-comment--resolved': resolved,
		'fragments-editor__fragment-comment--with-delete-mask': showDeleteMask,
		'fragments-editor__fragment-comment--with-resolve-mask': showResolveMask,
		'px-3': !isReply,
		small: true
	});

	const hideComment = () => {
		setHidden(true);

		setTimeout(() => {
			setShowDeleteMash(false);
			setShowResolveMask(false);
			props.onDelete(props.comment);
		}, 1000);
	};

	return (
		<article className={commentClassname}>
			<div className="d-flex mb-2">
				<UserIcon {...props.comment.author} />

				<div className="flex-grow-1 overflow-hidden pl-2">
					<p className="m-0 text-truncate">
						<strong
							className="lfr-portal-tooltip"
							data-title={props.comment.author.fullName}
						>
							{props.comment.author.fullName}
						</strong>
					</p>

					<p {...dateDescriptionProps}>
						{props.comment.dateDescription}
					</p>
				</div>

				{!isReply && (
					<ResolveButton
						loading={showResolveMask}
						onClick={() => {
							if (resolved) {
								setResolved(false);
							} else {
								setResolved(true);
								setShowResolveMask(true);

								editFragmentEntryLinkComment(
									props.comment.commentId,
									props.comment.commentBody,
									true
								).then(hideComment);
							}
						}}
						resolved={resolved}
					/>
				)}

				{Liferay.ThemeDisplay.getUserId() ===
					props.comment.author.userId && (
					<ClayDropDown
						active={dropDownActive}
						onActiveChange={setDropDownActive}
						trigger={
							<ClayButton
								borderless
								disabled={editing}
								displayType="secondary"
								monospaced
								outline
								small
							>
								<ClayIcon symbol="ellipsis-v" />
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Item
								onClick={() => {
									setDropDownActive(false);
									setEditing(true);
								}}
							>
								{Liferay.Language.get('edit')}
							</ClayDropDown.Item>

							<ClayDropDown.Item
								onClick={() => {
									setDropDownActive(false);
									setShowDeleteMash(true);
								}}
							>
								{Liferay.Language.get('delete')}
							</ClayDropDown.Item>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				)}
			</div>

			{editing ? (
				<EditCommentForm
					comment={props.comment}
					fragmentEntryLinkId={props.fragmentEntryLinkId}
					onCloseForm={() => setEditing(false)}
					onEdit={props.onEdit}
				/>
			) : (
				<p
					className="content text-secondary"
					dangerouslySetInnerHTML={{__html: props.comment.body}}
				/>
			)}

			{!isReply && (
				<React.Fragment>
					<footer className="fragments-editor__fragment-comment-replies">
						{props.comment.children &&
							props.comment.children.map(childComment => (
								<FragmentComment
									comment={childComment}
									fragmentEntryLinkId={
										props.fragmentEntryLinkId
									}
									key={childComment.commentId}
									onDelete={props.onDelete}
									onEdit={props.onEditReply(
										props.comment.commentId
									)}
									onEditReply={props.onEditReply}
									parentCommentId={props.comment.commentId}
								/>
							))}
					</footer>

					<ReplyCommentForm
						fragmentEntryLinkId={props.fragmentEntryLinkId}
						parentCommentId={props.comment.commentId}
					/>
				</React.Fragment>
			)}

			{showDeleteMask && (
				<InlineConfirm
					cancelButtonLabel={Liferay.Language.get('cancel')}
					confirmButtonLabel={Liferay.Language.get('delete')}
					message={Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this-comment'
					)}
					onCancelButtonClick={() => setShowDeleteMash(false)}
					onConfirmButtonClick={() =>
						deleteFragmentEntryLinkComment(
							props.comment.commentId
						).then(hideComment)
					}
				/>
			)}

			{showResolveMask && (
				<div className="resolve-mask">
					<ClayIcon symbol="check-circle" />
				</div>
			)}
		</article>
	);
};

FragmentComment.propTypes = {
	comment: PropTypes.shape({
		author: PropTypes.shape({
			fullName: PropTypes.string,
			portraitURL: PropTypes.string
		}),

		commentId: PropTypes.string.isRequired,
		body: PropTypes.string,
		dateDescription: PropTypes.string
	}),

	fragmentEntryLinkId: PropTypes.string.isRequired,
	onDelete: PropTypes.func,
	onEdit: PropTypes.func,
	onEditReply: PropTypes.func,
	parentCommentId: PropTypes.string
};

export {FragmentComment};
export default FragmentComment;
