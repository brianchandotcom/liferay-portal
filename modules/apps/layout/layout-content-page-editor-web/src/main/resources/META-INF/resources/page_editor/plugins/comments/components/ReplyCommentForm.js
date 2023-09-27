/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useDispatch} from '../../../app/contexts/StoreContext';
import addFragmentComment from '../../../app/thunks/addFragmentComment';
import CommentForm from './CommentForm';

export default function ReplyCommentForm({
	disabled,
	fragmentEntryLinkId,
	parentCommentId,
}) {
	const [addingComment, setAddingComment] = useState(false);
	const [showForm, setShowForm] = useState(false);
	const [textareaContent, setTextareaContent] = useState('');
	const dispatch = useDispatch();

	const handleReplyButtonClick = () => {
		setAddingComment(true);

		dispatch(
			addFragmentComment({
				body: textareaContent,
				fragmentEntryLinkId,
				parentCommentId,
			})
		)
			.then(() => {
				setAddingComment(false);
				setShowForm(false);
				setTextareaContent('');
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'the-reply-could-not-be-saved'
					),
					type: 'danger',
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
					onTextareaChange={(content) =>
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
					size="sm"
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
	parentCommentId: PropTypes.string.isRequired,
};
