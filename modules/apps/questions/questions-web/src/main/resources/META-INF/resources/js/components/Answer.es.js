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
import {withRouter} from 'react-router-dom';

import {
	deleteMessage,
	markAsAnswerMessageBoardMessage,
} from '../utils/client.es';
import ArticleBodyRenderer from './ArticleBodyRenderer.es';
import Comments from './Comments.es';
import Link from './Link.es';
import Rating from './Rating.es';
import UserRow from './UserRow.es';

export default withRouter(
	({answer, answerChange, deleteAnswer, match: {url}}) => {
		const [comments, setComments] = useState(
			answer.messageBoardMessages.items
		);
		const [showAsAnswer, setShowAsAnswer] = useState(answer.showAsAnswer);
		const [showNewComment, setShowNewComment] = useState(false);

		const _deleteAnswer = () =>
			deleteMessage(answer).then(() => deleteAnswer(answer));

		const _commentsChange = useCallback(comments => {
			setComments([...comments]);
		}, []);

		const _answerChange = () =>
			markAsAnswerMessageBoardMessage(answer.id, !showAsAnswer).then(
				() => {
					setShowAsAnswer(!showAsAnswer);
					if (answerChange) {
						answerChange(answer.id);
					}
				}
			);

		const _ratingChange = useCallback(
			ratingValue => {
				answer.aggregateRating = {
					...answer.aggregateRating,
					ratingValue,
				};
			},
			[answer]
		);

		useEffect(() => {
			setShowAsAnswer(answer.showAsAnswer);
		}, [answer.showAsAnswer]);

		return (
			<>
				<div
					className={classnames('questions-answer c-p-3', {
						'questions-answer-success': showAsAnswer,
					})}
				>
					<div className="align-items-center align-items-md-start row">
						<div className="col-6 col-md-1 order-1 order-md-0 text-md-center text-right">
							<Rating
								aggregateRating={answer.aggregateRating}
								entityId={answer.id}
								myRating={
									answer.myRating &&
									answer.myRating.ratingValue
								}
								ratingChange={_ratingChange}
								type={'Message'}
							/>
						</div>

						<div className="c-mb-4 c-mb-md-0 col-lg-9 col-md-8">
							{showAsAnswer && (
								<p className="c-mb-0 font-weight-bold text-success">
									<ClayIcon symbol="check-circle-full" />

									<span className="c-ml-3">
										{Liferay.Language.get('chosen-answer')}
									</span>
								</p>
							)}

							<div className="c-mt-2">
								<ArticleBodyRenderer {...answer} />
							</div>

							<ClayButton.Group
								className="font-weight-bold text-secondary"
								spaced={true}
							>
								{answer.actions['reply-to-message'] && (
									<ClayButton
										className="text-reset"
										displayType="unstyled"
										onClick={() => setShowNewComment(true)}
									>
										{Liferay.Language.get('reply')}
									</ClayButton>
								)}

								{answer.actions.delete && (
									<ClayButton
										className="text-reset"
										displayType="unstyled"
										onClick={_deleteAnswer}
									>
										{Liferay.Language.get('delete')}
									</ClayButton>
								)}

								{answer.actions.replace && (
									<ClayButton
										className="text-reset"
										displayType="unstyled"
										onClick={_answerChange}
									>
										{Liferay.Language.get(
											showAsAnswer
												? 'Unmark as answer'
												: 'Mark as answer'
										)}
									</ClayButton>
								)}

								{/* this is an extra double check, remove it without creating 2 clay-group-item */}
								{answer.actions.replace && (
									<ClayButton
										className="text-reset"
										displayType="unstyled"
									>
										<Link
											className="text-reset"
											to={`${url}/answers/${answer.friendlyUrlPath}/edit`}
										>
											{Liferay.Language.get('edit')}
										</Link>
									</ClayButton>
								)}
							</ClayButton.Group>
						</div>

						<div className="col-6 col-lg-2 col-md-3">
							<UserRow
								creator={answer.creator}
								statistics={answer.creatorStatistics}
							/>
						</div>
					</div>
				</div>

				<div className="row">
					<div className="col-md-9 offset-md-1">
						<Comments
							comments={comments}
							commentsChange={_commentsChange}
							entityId={answer.id}
							showNewComment={showNewComment}
							showNewCommentChange={value =>
								setShowNewComment(value)
							}
						/>
					</div>
				</div>
			</>
		);
	}
);
