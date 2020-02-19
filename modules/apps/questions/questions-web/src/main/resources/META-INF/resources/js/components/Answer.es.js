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
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import React, {useCallback, useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import {
	deleteMessage,
	markAsAnswerMessageBoardMessage
} from '../utils/client.es';
import ArticleBodyRenderer from './ArticleBodyRenderer.es';
import Comments from './Comments.es';
import Rating from './Rating.es';
import UserRow from './UserRow.es';

export default ({answer, answerChange, deleteAnswer}) => {
	const [comments, setComments] = useState(answer.messageBoardMessages.items);
	const [showAsAnswer, setShowAsAnswer] = useState(answer.showAsAnswer);
	const [showNewComment, setShowNewComment] = useState(false);

	const _deleteAnswer = () =>
		deleteMessage(answer).then(() => deleteAnswer(answer));

	const _commentsChange = useCallback(comments => {
		setComments([...comments]);
	}, []);

	const _answerChange = () =>
		markAsAnswerMessageBoardMessage(answer.id, !showAsAnswer).then(() => {
			setShowAsAnswer(!showAsAnswer);
			if (answerChange) {
				answerChange(answer.id);
			}
		});

	const _ratingChange = useCallback(
		ratingValue => {
			answer.aggregateRating = {...answer.aggregateRating, ratingValue};
		},
		[answer]
	);

	useEffect(() => {
		setShowAsAnswer(answer.showAsAnswer);
	}, [answer.showAsAnswer]);

	return (
		<>
			<div
				className={classnames(
					'autofit-row',
					'autofit-padded',
					'question-answer',
					{'question-accepted-answer': showAsAnswer}
				)}
			>
				<div className="autofit-col">
					<Rating
						aggregateRating={answer.aggregateRating}
						entityId={answer.id}
						myRating={
							answer.myRating && answer.myRating.ratingValue
						}
						ratingChange={_ratingChange}
						type={'Message'}
					/>
				</div>

				<div className="autofit-col autofit-col-expand">
					<div className="autofit-section">
						{showAsAnswer && (
							<p className="question-accepted-message">
								<ClayIcon symbol="check-circle-full" />{' '}
								<strong>
									{Liferay.Language.get('chosen-answer')}
								</strong>
							</p>
						)}

						<ArticleBodyRenderer {...answer} />

						<ClayButton.Group spaced={true}>
							{answer.actions.create && (
								<ClayButton
									displayType="unstyled"
									onClick={() => setShowNewComment(true)}
								>
									{Liferay.Language.get('reply')}
								</ClayButton>
							)}
							{answer.actions.delete && (
								<ClayButton
									displayType="unstyled"
									onClick={_deleteAnswer}
								>
									{Liferay.Language.get('delete')}
								</ClayButton>
							)}
							{answer.actions.replace && (
								<>
									<ClayButton
										displayType="unstyled"
										onClick={_answerChange}
									>
										{Liferay.Language.get(
											showAsAnswer
												? 'unmark as answer'
												: 'mark as answer'
										)}
									</ClayButton>
									<ClayButton displayType="unstyled">
										<Link to={`/answers/${answer.id}/edit`}>
											<span>
												{Liferay.Language.get('edit')}
											</span>
										</Link>
									</ClayButton>
								</>
							)}
						</ClayButton.Group>
					</div>
				</div>
				<div className="autofit-col">
					<UserRow creator={answer.creator} />
				</div>
			</div>
			<Comments
				comments={comments}
				commentsChange={_commentsChange}
				entityId={answer.id}
				showNewComment={showNewComment}
				showNewCommentChange={value => setShowNewComment(value)}
			/>
		</>
	);
};
