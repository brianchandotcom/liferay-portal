/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {useSessionState} from 'frontend-js-components-web';
import {openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {HIGHLIGHTED_COMMENT_ID_KEY} from '../../../app/config/constants/highlightedCommentIdKey';
import {useDispatch, useSelector} from '../../../app/contexts/StoreContext';
import FragmentService from '../../../app/services/FragmentService';
import deleteFragmentComment from '../../../app/thunks/deleteFragmentComment';
import InlineConfirm from '../../../common/components/InlineConfirm';
import UserIcon from '../../../common/components/UserIcon';
import EditCommentForm from './EditCommentForm';
import ReplyCommentForm from './ReplyCommentForm';
import ResolveButton from './ResolveButton';

export default function FragmentComment({
	comment,
	fragmentEntryLinkId,
	onEdit,
	parentCommentId,
}) {
	const {
		author,
		body,
		commentId,
		dateDescription,
		edited,
		modifiedDateDescription,
		resolved,
	} = comment;

	const [changingResolved, setChangingResolved] = useState(false);
	const [dropDownActive, setDropDownActive] = useState(false);
	const [editing, setEditing] = useState(false);
	const [hidden, setHidden] = useState(false);
	const [highlighted, setHighlighted] = useState(false);
	const [highlightedMessageId, setHighlightedMessageId] = useSessionState(
		HIGHLIGHTED_COMMENT_ID_KEY
	);
	const [showDeleteMask, setShowDeleteMask] = useState(false);
	const [showResolveMask, setShowResolveMask] = useState(false);

	const showResolvedComments = useSelector(
		(state) => state.showResolvedComments
	);
	const dispatch = useDispatch();

	const showModifiedDateTooltip = !!(edited && modifiedDateDescription);

	const commentClassname = classNames('small', {
		'page-editor__fragment-comment': true,
		'page-editor__fragment-comment--hidden': hidden,
		'page-editor__fragment-comment--highlighted': highlighted,
		'page-editor__fragment-comment--reply': !!parentCommentId,
		'page-editor__fragment-comment--resolved': resolved,
		'page-editor__fragment-comment--with-delete-mask': showDeleteMask,
		'page-editor__fragment-comment--with-resolve-mask': showResolveMask,
	});

	const isMounted = useIsMounted();

	const hideComment = (onHide) => {
		setHidden(true);

		setTimeout(() => {
			if (isMounted()) {
				setShowDeleteMask(false);
				setShowResolveMask(false);
				onHide();
			}
		}, 1000);
	};

	const handleResolveButtonClick = () => {
		setChangingResolved(true);

		FragmentService.editComment({
			body,
			commentId,
			onNetworkStatus: dispatch,
			resolved: !resolved,
		})
			.then((comment) => {
				setChangingResolved(false);

				if (showResolvedComments) {
					onEdit(comment);
				}
				else if (!resolved) {
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
					type: 'danger',
				});

				setChangingResolved(false);
			});
	};

	useEffect(() => {
		if (highlightedMessageId === commentId) {
			setHighlighted(true);
			setHighlightedMessageId(null);
		}
	}, [commentId, highlightedMessageId, setHighlightedMessageId]);

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
							'lfr-portal-tooltip': showModifiedDateTooltip,
						})}
						data-title={
							showModifiedDateTooltip &&
							sub(
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
						menuElementAttrs={{
							containerProps: {
								className: 'cadmin',
							},
						}}
						onActiveChange={setDropDownActive}
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get('options')}
								borderless
								disabled={editing}
								displayType="secondary"
								monospaced
								outline
								size="sm"
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
					<footer className="mb-2 page-editor__fragment-comment-replies">
						{comment.children &&
							comment.children.map((childComment) => (
								<FragmentComment
									comment={{
										...childComment,
										parentCommentId: comment.commentId,
										resolved,
									}}
									fragmentEntryLinkId={fragmentEntryLinkId}
									key={childComment.commentId}
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
						dispatch(
							deleteFragmentComment({
								commentId,
								fragmentEntryLinkId,
								parentCommentId,
							})
						).catch(() => {
							openToast({
								message: Liferay.Language.get(
									'the-comment-could-not-be-deleted'
								),
								type: 'danger',
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
			portraitURL: PropTypes.string,
		}),
		body: PropTypes.string,
		commentId: PropTypes.string.isRequired,
		dateDescription: PropTypes.string,
		parentCommentId: PropTypes.string,
	}),

	fragmentEntryLinkId: PropTypes.string.isRequired,
	onEdit: PropTypes.func,
	parentCommentId: PropTypes.string,
};
