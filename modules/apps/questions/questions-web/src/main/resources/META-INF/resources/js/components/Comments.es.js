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
import {useMutation} from 'graphql-hooks';
import React, {useCallback, useContext, useRef, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {createCommentQuery} from '../utils/client.es';
import {getContextLink} from '../utils/utils.es';
import Comment from './Comment.es';
import DefaultQuestionsEditor from './DefaultQuestionsEditor.es';

export default withRouter(
	({
		comments,
		commentsChange,
		editable = true,
		entityId,
		match: {
			params: {questionId, sectionTitle},
		},
		showNewComment,
		showNewCommentChange,
	}) => {
		const context = useContext(AppContext);

		const editor = useRef('');

		const [isReplyButtonDisable, setIsReplyButtonDisable] = useState(false);

		const [createComment] = useMutation(createCommentQuery);

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
						editable={editable}
						key={comment.id}
					/>
				))}

				{editable && showNewComment && (
					<>
						<ClayForm.Group small>
							<DefaultQuestionsEditor
								label={Liferay.Language.get('your-answer')}
								onContentLengthValid={setIsReplyButtonDisable}
								ref={editor}
							/>

							<ClayButton.Group className="c-mt-3" spaced>
								<ClayButton
									disabled={isReplyButtonDisable}
									displayType="primary"
									onClick={() => {
										createComment({
											fetchOptionsOverrides: getContextLink(
												`${sectionTitle}/${questionId}`
											),
											variables: {
												articleBody: editor.current.getContent(),
												parentMessageBoardMessageId: entityId,
											},
										}).then(({data}) => {
											editor.current.clearContent();
											showNewCommentChange(false);
											commentsChange([
												...comments,
												data.createMessageBoardMessageMessageBoardMessage,
											]);
										});
									}}
								>
									{context.trustedUser
										? Liferay.Language.get('reply')
										: Liferay.Language.get(
												'submit-for-publication'
										  )}
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
