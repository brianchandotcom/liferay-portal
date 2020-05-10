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
import ClayForm from '@clayui/form';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useCallback, useContext, useState} from 'react';

import {AppContext} from '../AppContext.es';
import {createComment} from '../utils/client.es';
import lang from '../utils/lang.es';
import {getCKEditorConfig, onBeforeLoadCKEditor} from '../utils/utils.es';
import Comment from './Comment.es';

export default ({
	comments,
	commentsChange,
	entityId,
	showNewComment,
	showNewCommentChange,
}) => {
	const [comment, setComment] = useState('');

	const context = useContext(AppContext);

	const postComment = () => {
		return createComment(comment, entityId).then((data) => {
			setComment('');
			showNewCommentChange(false);
			commentsChange([...comments, data]);
		});
	};

	const _commentChange = useCallback(
		(comment) => {
			if (commentsChange) {
				return commentsChange([
					...comments.filter((o) => o.id !== comment.id),
				]);
			}

			return null;
		},
		[commentsChange, comments]
	);

	return (
		<div>
			{comments.map((comment) => (
				<Comment
					comment={comment}
					commentChange={_commentChange}
					key={comment.id}
				/>
			))}

			{showNewComment && (
				<>
					<ClayForm.Group small>
						<Editor
							config={getCKEditorConfig()}
							data={comment}
							onBeforeLoad={(editor) =>
								onBeforeLoadCKEditor(
									editor,
									context.imageBrowseURL
								)
							}
							onChange={(event) =>
								setComment(event.editor.getData())
							}
						/>

						{comment.length < 15 && (
							<p className="float-right small text-secondary">
								{lang.sub(
									Liferay.Language.get('x-characters-left'),
									[15 - comment.length]
								)}
							</p>
						)}

						<ClayButton.Group className="c-mt-3" spaced>
							<ClayButton
								disabled={comment.length < 15}
								displayType="primary"
								onClick={postComment}
							>
								{Liferay.Language.get('reply')}
							</ClayButton>

							<ClayButton
								displayType="secondary"
								onClick={() => showNewCommentChange(false)}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ClayButton.Group>
					</ClayForm.Group>
				</>
			)}
		</div>
	);
};
