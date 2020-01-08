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

import React, {useCallback, useState} from 'react';

import {createComment} from '../utils/client.es';
import lang from '../utils/lang.es';
import Comment from './Comment.es';

export default ({comments, commentsChange, entityId, showNewComment}) => {
	const [comment, setComment] = useState('');

	const postComment = () => {
		return createComment(comment, entityId).then(data => {
			setComment('');
			showNewComment = false;
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
				<>
					<div className="autofit-padded autofit-row">
						<div className="autofit-col autofit-col-expand">
							<textarea
								onChange={event =>
									setComment(event.target.value)
								}
								value={comment}
							/>
						</div>
						<div className="autofit-col">
							<button
								className="btn btn-primary"
								disabled={comment.length < 15}
								onClick={postComment}
							>
								{Liferay.Language.get('add-comment')}
							</button>
						</div>
					</div>
					<div className="autofit-padded autofit-row">
						<div className="autofit-col">
							{comment.length < 15 && (
								<span>
									{lang.sub(
										Liferay.Language.get(
											'enter-at-least-x-characters'
										),
										[15 - comment.length]
									)}
								</span>
							)}
						</div>
					</div>
				</>
			)}
		</div>
	);
};
