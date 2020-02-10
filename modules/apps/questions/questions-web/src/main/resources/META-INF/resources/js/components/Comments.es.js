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
import React, {useCallback, useState} from 'react';

import {createComment} from '../utils/client.es';
import lang from '../utils/lang.es';
import Comment from './Comment.es';

export default ({
	comments,
	commentsChange,
	entityId,
	showNewComment,
	showNewCommentChange
}) => {
	const [comment, setComment] = useState('');

	const postComment = () => {
		return createComment(comment, entityId).then(data => {
			setComment('');
			showNewCommentChange(false);
			commentsChange([...comments, data]);
		});
	};

	const _commentChange = useCallback(
		comment => {
			if (commentsChange) {
				return commentsChange([
					...comments.filter(o => o.id !== comment.id)
				]);
			}

			return null;
		},
		[commentsChange, comments]
	);

	return (
		<div>
			{comments.map(comment => (
				<Comment
					comment={comment}
					commentChange={_commentChange}
					key={comment.id}
				/>
			))}

			{showNewComment && (
				<div
					className="autofit-padded autofit-row"
					style={{paddingLeft: '5em'}}
				>
					<div className="autofit-col autofit-col-expand">
						<hr className="question-comment-separator" />

						<div>
							<ClayForm.Group className="form-group-sm">
								<textarea
									className="form-control"
									onChange={event =>
										setComment(event.target.value)
									}
									value={comment}
								/>
							</ClayForm.Group>
						</div>

						<div className="autofit-row">
							<ClayButton.Group spaced={true}>
								<ClayButton
									disabled={comment.length < 15}
									displayType="primary"
									onClick={postComment}
									small={true}
								>
									{Liferay.Language.get('reply')}
								</ClayButton>
								<ClayButton
									displayType="secondary"
									onClick={() => showNewCommentChange(false)}
									small={true}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							</ClayButton.Group>

							<div className="autofit-col autofit-col-expand question-comment-validation">
								{comment.length < 15 && (
									<span>
										{lang.sub(
											Liferay.Language.get(
												'x-characters-left'
											),
											[15 - comment.length]
										)}
									</span>
								)}
							</div>
						</div>
					</div>
				</div>
			)}
		</div>
	);
};
