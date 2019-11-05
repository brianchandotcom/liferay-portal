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
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState, useContext} from 'react';

import AppContext from '../../../core/AppContext';
import CommentForm from './CommentForm';

function addFragmentEntryLinkCommentReply() {
	throw new Error('Not implemented');
}

export default function ReplyCommentForm({
	disabled,
	fragmentEntryLinkId,
	parentCommentId
}) {
	const [addingComment, setAddingComment] = useState(false);
	const [showForm, setShowForm] = useState(false);
	const [textareaContent, setTextareaContent] = useState('');
	const {dispatch} = useContext(AppContext);

	const handleReplyButtonClick = () => {
		setAddingComment(true);

		addFragmentEntryLinkCommentReply(
			fragmentEntryLinkId,
			parentCommentId,
			textareaContent
		)
			.then(comment => {
				dispatch({
					comment,
					fragmentEntryLinkId,
					parentCommentId,
					type: 'addReply'
				});

				setAddingComment(false);
				setShowForm(false);
				setTextareaContent('');
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'the-reply-could-not-be-saved'
					),
					title: Liferay.Language.get('error'),
					type: 'danger'
				});

				setAddingComment(false);
			});
	};

	return (
		<div className="mr-3 pb-2">
			{showForm ? (
				<CommentForm
					autoFocus
					id={`pageEditorCommentReplyEditor_${parentCommentId}`}
					loading={addingComment}
					onCancelButtonClick={() => {
						setShowForm(false);
						setTextareaContent('');
					}}
					onSubmitButtonClick={handleReplyButtonClick}
					onTextareaChange={content =>
						content && setTextareaContent(content)
					}
					showButtons={true}
					submitButtonLabel={Liferay.Language.get('reply')}
					textareaContent={textareaContent}
				/>
			) : (
				<ClayButton
					borderless
					disabled={disabled}
					displayType="secondary"
					onClick={() => setShowForm(true)}
					small
				>
					{Liferay.Language.get('reply')}
				</ClayButton>
			)}
		</div>
	);
}

ReplyCommentForm.propTypes = {
	disabled: PropTypes.bool,
	fragmentEntryLinkId: PropTypes.string.isRequired,
	parentCommentId: PropTypes.string.isRequired
};
