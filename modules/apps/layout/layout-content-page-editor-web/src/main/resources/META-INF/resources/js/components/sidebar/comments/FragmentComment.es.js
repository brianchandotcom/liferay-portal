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

import {ConnectedReplyCommentForm} from './ReplyCommentForm.es';
import {
	deleteFragmentEntryLinkComment,
	editFragmentEntryLinkComment
} from '../../../utils/FragmentsEditorFetchUtils.es';
import EditCommentForm from './EditCommentForm.es';
import InlineConfirm from '../../common/InlineConfirm.es';
import UserIcon from '../../common/UserIcon.es';
import Loader from '../../common/Loader.es';

const FragmentComment = props => {
	const [deleteRequested, setDeleteRequested] = useState(false);
	const [dropDownActive, setDropDownActive] = useState(false);
	const [editing, setEditing] = useState(false);
	const [resolving, setResolving] = useState(false);
	const [resolved, setResolved] = useState(false);

	const dateDescriptionProps = {
		className: 'm-0 text-secondary'
	};

	if (props.edited && props.modifiedDateDescription) {
		dateDescriptionProps.className += ' lfr-portal-tooltip';

		dateDescriptionProps['data-title'] = `${Liferay.Language.get(
			'edited'
		)} ${props.modifiedDateDescription}`;
	}

	const commentClassname = classNames({
		'fragments-editor__fragment-comment': true,
		'fragments-editor__fragment-comment--deleting': deleteRequested,
		'fragments-editor__fragment-comment--resolved': resolved,
		'fragments-editor__fragment-comment--resolving': resolving,
		small: true
	});

	return (
		<article className={commentClassname}>
			<div className="d-flex mb-2">
				<UserIcon {...props.author} />

				<div className="flex-grow-1 pl-2">
					<p className="m-0 text-truncate">
						<strong>{props.author.fullName}</strong>
					</p>

					<p {...dateDescriptionProps}>{props.dateDescription}</p>
				</div>

				<ClayButton
					className="text-secondary btn-monospaced btn-sm"
					disabled={resolving}
					displayType="unstyled"
					onClick={() => {
						setResolving(true);
						editFragmentEntryLinkComment(
							props.commentId,
							props.body,
							true
						).then(() => {
							setResolved(true);
							setTimeout(props.onDelete, 1000);
						});
					}}
				>
					{resolving ? (
						<Loader />
					) : (
						<span
							className="lfr-portal-tooltip ml-1 text-lowercase"
							data-title={Liferay.Language.get('resolve')}
						>
							<ClayIcon symbol="check-circle" />
						</span>
					)}
				</ClayButton>

				<ClayDropDown
					active={dropDownActive}
					onActiveChange={setDropDownActive}
					trigger={
						<ClayButton
							className="text-secondary btn-monospaced btn-sm"
							disabled={editing}
							displayType="unstyled"
						>
							<ClayIcon symbol="ellipsis-v" />
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						<ClayDropDown.Item
							href="#edit"
							onClick={() => {
								setDropDownActive(false);
								setEditing(true);
							}}
						>
							{Liferay.Language.get('edit')}
						</ClayDropDown.Item>

						<ClayDropDown.Item
							href="#delete"
							onClick={() => {
								setDropDownActive(false);
								setDeleteRequested(true);
							}}
						>
							{Liferay.Language.get('delete')}
						</ClayDropDown.Item>
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</div>

			{editing ? (
				<EditCommentForm
					body={props.body}
					commentId={props.commentId}
					fragmentEntryLinkId={props.fragmentEntryLinkId}
					onCloseForm={() => setEditing(false)}
					onEdit={props.onEdit}
				/>
			) : (
				<p
					className="content text-secondary"
					dangerouslySetInnerHTML={{__html: props.body}}
				/>
			)}

			{deleteRequested && (
				<InlineConfirm
					cancelButtonLabel={Liferay.Language.get('keep')}
					confirmButtonLabel={Liferay.Language.get('delete')}
					message={Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this-comment'
					)}
					onCancelButtonClick={() => setDeleteRequested(false)}
					onConfirmButtonClick={() =>
						deleteFragmentEntryLinkComment(props.commentId).then(
							() => {
								setDeleteRequested(false);
								props.onDelete();
							}
						)
					}
				/>
			)}

			{resolved && (
				<div className="resolved">
					<ClayIcon symbol="check-circle" />
				</div>
			)}

			{!props.parentCommentId && (
				<ConnectedReplyCommentForm
					fragmentEntryLinkId={props.fragmentEntryLinkId}
					parentCommentId={props.commentId}
				/>
			)}
		</article>
	);
};

FragmentComment.propTypes = {
	author: PropTypes.shape({
		fullName: PropTypes.string,
		portraitURL: PropTypes.string
	}),

	commentId: PropTypes.string.isRequired,
	body: PropTypes.string,
	dateDescription: PropTypes.string,
	fragmentEntryLinkId: PropTypes.string.isRequired,
	onDelete: PropTypes.func,
	onEdit: PropTypes.func,
	parentCommentId: PropTypes.string
};

export {FragmentComment};
export default FragmentComment;
