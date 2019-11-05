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

import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import CommentForm from './CommentForm';

function editFragmentEntryLinkComment() {
	throw new Error('Not implemented');
}

export default function EditCommentForm({
	comment,
	onCloseForm,
	onEdit = () => {}
}) {
	const [editingComment, setEditingComment] = useState(false);
	const [textareaContent, setTextareaContent] = useState(comment.body);

	const _handleCommentButtonClick = () => {
		setEditingComment(true);

		editFragmentEntryLinkComment(comment.commentId, textareaContent)
			.then(comment => {
				setEditingComment(false);

				onEdit(comment);
				onCloseForm();
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'the-comment-could-not-be-edited'
					),
					title: Liferay.Language.get('error'),
					type: 'danger'
				});

				setEditingComment(false);
			});
	};

	return (
		<CommentForm
			autoFocus
			id={`pageEditorCommentEditor_${comment.commentId}`}
			loading={editingComment}
			onCancelButtonClick={() => onCloseForm()}
			onSubmitButtonClick={_handleCommentButtonClick}
			onTextareaChange={content => setTextareaContent(content)}
			showButtons
			submitButtonLabel={Liferay.Language.get('update')}
			textareaContent={textareaContent}
		/>
	);
}

EditCommentForm.propTypes = {
	comment: PropTypes.shape({
		body: PropTypes.string.isRequired,
		commentId: PropTypes.string.isRequired
	}),
	onCloseForm: PropTypes.func.isRequired,
	onEdit: PropTypes.func
};
