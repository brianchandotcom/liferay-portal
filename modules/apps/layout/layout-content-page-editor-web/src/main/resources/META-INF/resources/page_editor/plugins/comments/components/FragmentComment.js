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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState, useContext} from 'react';

import {StoreContext} from '../../../app/store/index';
import InlineConfirm from '../../../common/components/InlineConfirm';
import UserIcon from '../../../common/components/UserIcon';
import EditCommentForm from './EditCommentForm';
import ReplyCommentForm from './ReplyCommentForm';
import ResolveButton from './ResolveButton';

function deleteFragmentEntryLinkComment() {
	throw new Error('Not implemented');
}

function editFragmentEntryLinkComment() {
	throw new Error('Not implemented');
}

export default function FragmentComment({
	comment,
	fragmentEntryLinkId,
	onDelete,
	onEdit,
	onEditReply,
	parentCommentId
}) {
	const {
		author,
		body,
		commentId,
		dateDescription,
		edited,
		modifiedDateDescription,
		resolved
	} = comment;

	const [changingResolved, setChangingResolved] = useState(false);
	const [dropDownActive, setDropDownActive] = useState(false);
	const [editing, setEditing] = useState(false);
	const [hidden, setHidden] = useState(false);
	const [highlighted, setHighlighted] = useState(false);
	const [showDeleteMask, setShowDeleteMask] = useState(false);
	const [showResolveMask, setShowResolveMask] = useState(false);

	const {showResolvedComments} = useContext(StoreContext);
	const showModifiedDateTooltip = !!(edited && modifiedDateDescription);

	const commentClassname = classNames('small', {
		'fragments-editor__fragment-comment': true,
		'fragments-editor__fragment-comment--hidden': hidden,
		'fragments-editor__fragment-comment--highlighted': highlighted,
		'fragments-editor__fragment-comment--reply': !!parentCommentId,
		'fragments-editor__fragment-comment--resolved': resolved,
		'fragments-editor__fragment-comment--with-delete-mask': showDeleteMask,
		'fragments-editor__fragment-comment--with-resolve-mask': showResolveMask
	});

	const handleResolveButtonClick = () => {
		setChangingResolved(true);

		editFragmentEntryLinkComment(commentId, body, !resolved)
			.then(comment => {
				setChangingResolved(false);

				if (showResolvedComments) {
					onEdit(comment);
				} else if (!resolved) {
					setShowResolveMask(true);
					hideComment(() => onEdit(comment));
				}
			})
			.catch(() => {
				openToast({
					message: resolved
						? Liferay.Language.get(
								'the-comment-could-not-be-unresolved'
						  )
						: Liferay.Language.get(
								'the-comment-could-not-be-resolved'
						  ),
					title: Liferay.Language.get('error'),
					type: 'danger'
				});

				setChangingResolved(false);
			});
	};

	const isMounted = useIsMounted();

	const hideComment = onHide => {
		setHidden(true);

		setTimeout(() => {
			if (isMounted()) {
				setShowDeleteMask(false);
				setShowResolveMask(false);
				onHide();
			}
		}, 1000);
	};

	useEffect(() => {
		const highlightMessageId = window.sessionStorage.getItem(
			'HIGHLIGHTED_COMMENT_ID_KEY'
		);

		if (highlightMessageId === commentId) {
			window.sessionStorage.removeItem('HIGHLIGHTED_COMMENT_ID_KEY');

			setHighlighted(true);
		}
	}, [commentId]);

	return (
		<article className={commentClassname}>
			<div className="d-flex mb-2">
				<UserIcon {...author} />

				<div className="flex-grow-1 overflow-hidden pl-2">
					<p className="m-0 text-truncate">
						<strong
							className="lfr-portal-tooltip"
							data-title={author.fullName}
						>
							{author.fullName}
						</strong>
					</p>

					<p
						className={classNames('m-0 text-secondary', {
							'lfr-portal-tooltip': showModifiedDateTooltip
						})}
						data-title={
							showModifiedDateTooltip &&
							Liferay.Util.sub(
								Liferay.Language.get('edited-x'),
								modifiedDateDescription
							)
						}
					>
						{dateDescription}
					</p>
				</div>

				{!parentCommentId && (
					<ResolveButton
						disabled={editing}
						loading={changingResolved}
						onClick={handleResolveButtonClick}
						resolved={resolved}
					/>
				)}

				{Liferay.ThemeDisplay.getUserId() === author.userId && (
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
								disabled={resolved}
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
									setShowDeleteMask(true);
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
					comment={comment}
					fragmentEntryLinkId={fragmentEntryLinkId}
					onCloseForm={() => setEditing(false)}
					onEdit={onEdit}
				/>
			) : (
				<div
					className="content pb-2 text-secondary"
					dangerouslySetInnerHTML={{__html: body}}
				/>
			)}

			{!parentCommentId &&
				comment.children &&
				Boolean(comment.children.length) && (
					<footer className="fragments-editor__fragment-comment-replies mb-2">
						{comment.children &&
							comment.children.map(childComment => (
								<FragmentComment
									comment={{...childComment, resolved}}
									fragmentEntryLinkId={fragmentEntryLinkId}
									key={childComment.commentId}
									onDelete={onDelete}
									onEdit={onEditReply(commentId)}
									onEditReply={onEditReply}
									parentCommentId={commentId}
								/>
							))}
					</footer>
				)}

			{!parentCommentId && (
				<ReplyCommentForm
					disabled={editing || resolved}
					fragmentEntryLinkId={fragmentEntryLinkId}
					parentCommentId={commentId}
				/>
			)}

			{showDeleteMask && (
				<InlineConfirm
					cancelButtonLabel={Liferay.Language.get('cancel')}
					confirmButtonLabel={Liferay.Language.get('delete')}
					message={Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this-comment'
					)}
					onCancelButtonClick={() => setShowDeleteMask(false)}
					onConfirmButtonClick={() =>
						deleteFragmentEntryLinkComment(commentId)
							.then(() => hideComment(() => onDelete(comment)))
							.catch(() => {
								openToast({
									message: Liferay.Language.get(
										'the-comment-could-not-be-deleted'
									),
									title: Liferay.Language.get('error'),
									type: 'danger'
								});
							})
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
}

FragmentComment.propTypes = {
	comment: PropTypes.shape({
		author: PropTypes.shape({
			fullName: PropTypes.string,
			portraitURL: PropTypes.string
		}),
		body: PropTypes.string,
		commentId: PropTypes.string.isRequired,
		dateDescription: PropTypes.string
	}),

	fragmentEntryLinkId: PropTypes.string.isRequired,
	onDelete: PropTypes.func,
	onEdit: PropTypes.func,
	onEditReply: PropTypes.func,
	parentCommentId: PropTypes.string
};
