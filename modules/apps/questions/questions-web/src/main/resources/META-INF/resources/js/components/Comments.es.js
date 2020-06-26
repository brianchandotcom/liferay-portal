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

import {useMutation} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import React, {useCallback, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {createCommentQuery} from '../utils/client.es';
import {getContextLink, stripHTML} from '../utils/utils.es';
import Comment from './Comment.es';
import QuestionsEditor from './QuestionsEditor';
import TextLengthValidation from './TextLengthValidation.es';

export default withRouter(
	({
		comments,
		commentsChange,
		entityId,
		match: {
			params: {questionId, sectionTitle},
		},
		showNewComment,
		showNewCommentChange,
	}) => {
		const [comment, setComment] = useState('');

		const [createComment] = useMutation(createCommentQuery, {
			context: getContextLink(`${sectionTitle}/${questionId}`),
			onCompleted(data) {
				setComment('');
				showNewCommentChange(false);
				commentsChange([
					...comments,
					data.createMessageBoardMessageMessageBoardMessage,
				]);
			},
		});

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
							<QuestionsEditor
								contents={comment}
								onChange={(event) => {
									setComment(event.editor.getData());
								}}
							/>

							<TextLengthValidation text={comment} />

							<ClayButton.Group className="c-mt-3" spaced>
								<ClayButton
									disabled={stripHTML(comment).length < 15}
									displayType="primary"
									onClick={() => {
										createComment({
											variables: {
												articleBody: comment,
												parentMessageBoardMessageId: entityId,
											},
										});
									}}
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
	}
);
