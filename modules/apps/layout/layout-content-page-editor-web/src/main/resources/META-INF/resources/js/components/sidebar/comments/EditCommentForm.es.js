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

import PropTypes from 'prop-types';
import React, {useState} from 'react';

import CommentForm from './CommentForm.es';

const EditCommentForm = props => {
	const [editingComment, setEditingComment] = useState(false);
	const [textareaContent, setTextareaContent] = useState(props.body);

	const _handleCommentButtonClick = () => {
		setEditingComment(true);

		setTimeout(() => {
			setEditingComment(false);
			props.onCloseForm();
		}, 2000);
	};

	return (
		<CommentForm
			loading={editingComment}
			onCancelButtonClick={() => props.onCloseForm()}
			onSubmitButtonClick={_handleCommentButtonClick}
			onTextareaChange={event => setTextareaContent(event.target.value)}
			showButtons
			submitButtonLabel={Liferay.Language.get('update')}
			textareaContent={textareaContent}
		/>
	);
};

EditCommentForm.propTypes = {
	body: PropTypes.string.isRequired,
	commentId: PropTypes.string.isRequired,
	onCloseForm: PropTypes.func.isRequired
};

export {EditCommentForm};
export default EditCommentForm;
