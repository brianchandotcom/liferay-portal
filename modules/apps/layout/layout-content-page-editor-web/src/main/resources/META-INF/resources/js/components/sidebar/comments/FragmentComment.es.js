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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayDropDown from '@clayui/drop-down';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ClayButton from '@clayui/button';
import Button from '../../common/Button.es';
import {deleteFragmentEntryLinkComment} from '../../../utils/FragmentsEditorFetchUtils.es';
import EditCommentForm from './EditCommentForm.es';
import UserIcon from '../../common/UserIcon.es';

const FragmentComment = props => {
	const [deletingComment, setDeletingComment] = useState(false);
	const [deleteRequested, setDeleteRequested] = useState(false);
	const [dropDownActive, setDropDownActive] = useState(false);
	const [editing, setEditing] = useState(false);

	const _handleActuallyDeleteButtonClick = () => {
		setDeletingComment(true);

		deleteFragmentEntryLinkComment(props.commentId).then(() => {
			setDeletingComment(false);
			setDeleteRequested(false);
			props.onDelete();
		});
	};

	const _handleEditButtonClick = () => {
		setDropDownActive(false);
		setEditing(true);
	};

	return (
		<article className="fragments-editor__fragment-comment small">
			<div className="d-flex mb-2">
				<UserIcon {...props.author} />

				<div className="flex-grow-1 pl-2">
					<p className="m-0 text-truncate">
						<strong>{props.author.fullName}</strong>
					</p>

					<p className="m-0 text-secondary">
						{props.dateDescription}

						{props.edited && (
							<span
								className="lfr-portal-tooltip ml-1 text-lowercase"
								data-title={props.modifiedDateDescription}
							>
								({Liferay.Language.get('edited')})
							</span>
						)}
					</p>
				</div>

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
							onClick={_handleEditButtonClick}
						>
							{Liferay.Language.get('edit')}
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
				/>
			) : (
				<p
					className="text-secondary"
					dangerouslySetInnerHTML={{__html: props.body}}
				/>
			)}

			<Button
				disabled={deleteRequested}
				displayType="link"
				onClick={() => setDeleteRequested(true)}
				small
				type="button"
			>
				{Liferay.Language.get('delete')}
			</Button>
			{deleteRequested && (
				<div
					className="delete-confirmation"
					ref={node => node && node.querySelector('.delete').focus()}
					role="alertdialog"
				>
					<p className="text-center text-secondary">
						<strong>
							{Liferay.Language.get(
								'are-you-sure-you-want-to-delete-this-comment'
							)}
						</strong>
					</p>
					<ClayButton.Group spaced>
						<Button
							className="delete"
							displayType="primary"
							loading={deletingComment}
							onClick={_handleActuallyDeleteButtonClick}
							small
						>
							{Liferay.Language.get('delete')}
						</Button>

						<Button
							disabled={deletingComment}
							displayType="secondary"
							onClick={() => setDeleteRequested(false)}
							small
							type="button"
						>
							{Liferay.Language.get('keep')}
						</Button>
					</ClayButton.Group>
				</div>
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
	onDelete: PropTypes.func
};

export {FragmentComment};
export default FragmentComment;
