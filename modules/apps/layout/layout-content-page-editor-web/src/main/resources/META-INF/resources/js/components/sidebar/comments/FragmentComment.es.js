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
import React, {useEffect, useState} from 'react';

import useSelector from '../../../store/hooks/useSelector.es';
import {
	deleteFragmentEntryLinkComment,
	editFragmentEntryLinkComment
} from '../../../utils/FragmentsEditorFetchUtils.es';
import InlineConfirm from '../../common/InlineConfirm.es';
import UserIcon from '../../common/UserIcon.es';
import {HIGHLIGHTED_COMMENT_ID_KEY} from '../../edit_mode/EditModeWrapper.es';
import EditCommentForm from './EditCommentForm.es';
import ReplyCommentForm from './ReplyCommentForm.es';
import ResolveButton from './ResolveButton.es';

const FragmentComment = props => {
	const isReply = props.parentCommentId;
	const resolved = props.comment.resolved;

	const [changingResolved, setChangingResolved] = useState(false);
	const [dropDownActive, setDropDownActive] = useState(false);
	const [editing, setEditing] = useState(false);
	const [hidden, setHidden] = useState(false);
	const [highlighted, setHighlighted] = useState(false);
	const [showDeleteMask, setShowDeleteMask] = useState(false);
	const [showResolveMask, setShowResolveMask] = useState(false);

	const showResolvedComments = useSelector(
		state => state.showResolvedComments
	);

	const showModifiedDateTooltip =
		props.comment.edited && props.comment.modifiedDateDescription;

	const commentClassname = classNames({
		'fragments-editor__fragment-comment': true,
		'fragments-editor__fragment-comment--hidden': hidden,
		'fragments-editor__fragment-comment--highlighted': highlighted,
		'fragments-editor__fragment-comment--reply': isReply,
		'fragments-editor__fragment-comment--resolved': resolved,
		'fragments-editor__fragment-comment--with-delete-mask': showDeleteMask,
		'fragments-editor__fragment-comment--with-resolve-mask': showResolveMask,
		small: true
	});

	const handleResolveButtonClick = () => {
		setChangingResolved(true);

		editFragmentEntryLinkComment(
			props.comment.commentId,
			props.comment.body,
			!resolved
		)
			.then(comment => {
				setChangingResolved(false);

				if (showResolvedComments) {
					props.onEdit(comment);
				}
				else if (!resolved) {
					setShowResolveMask(true);
					hideComment(() => props.onEdit(comment));
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
			HIGHLIGHTED_COMMENT_ID_KEY
		);

		if (highlightMessageId === props.comment.commentId) {
			window.sessionStorage.removeItem(HIGHLIGHTED_COMMENT_ID_KEY);

			setHighlighted(true);
		}
	}, [props.comment.commentId]);

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

					<p
						className={classNames('m-0 text-secondary', {
							'lfr-portal-tooltip': showModifiedDateTooltip
						})}
						data-title={
							showModifiedDateTooltip &&
							Liferay.Util.sub(
								Liferay.Language.get('edited-x'),
								props.comment.modifiedDateDescription
							)
						}
					>
						{props.comment.dateDescription}
					</p>
				</div>

				{!isReply && (
					<ResolveButton
						disabled={editing}
						loading={changingResolved}
						onClick={handleResolveButtonClick}
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
					comment={props.comment}
					fragmentEntryLinkId={props.fragmentEntryLinkId}
					onCloseForm={() => setEditing(false)}
				/>
			) : (
				<div
					className="content pb-2 text-secondary"
					dangerouslySetInnerHTML={{__html: props.comment.body}}
				/>
			)}

			{!isReply &&
				props.comment.children &&
				Boolean(props.comment.children.length) && (
					<footer className="fragments-editor__fragment-comment-replies mb-2">
						{props.comment.children &&
							props.comment.children.map(childComment => (
								<FragmentComment
									comment={{...childComment, resolved}}
									fragmentEntryLinkId={
										props.fragmentEntryLinkId
									}
									key={childComment.commentId}
									parentCommentId={props.comment.commentId}
								/>
							))}
					</footer>
				)}

			{!isReply && (
				<ReplyCommentForm
					disabled={editing || resolved}
					fragmentEntryLinkId={props.fragmentEntryLinkId}
					parentCommentId={props.comment.commentId}
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
						deleteFragmentEntryLinkComment(props.comment.commentId)
							.then(() =>
								hideComment(() => props.onDelete(props.comment))
							)
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
};

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
	onEdit: PropTypes.func,
	parentCommentId: PropTypes.string
};

export {FragmentComment};
export default FragmentComment;
