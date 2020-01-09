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
import parser from 'bbcode-to-react';
import classnames from 'classnames';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {AppContext} from '../AppContext.es';
import {
	deleteMessage,
	markAsAnswerMessageBoardMessage
} from '../utils/client.es';
import Comments from './Comments.es';
import Rating from './Rating.es';
import UserRow from './UserRow.es';

export default ({answer, answerChange, creatorId, deleteAnswer}) => {
	const context = useContext(AppContext);

	const [comments, setComments] = useState(answer.messageBoardMessages.items);
	const [showAsAnswer, setShowAsAnswer] = useState(answer.showAsAnswer);
	const [showNewComment, setShowNewComment] = useState(false);

	const _canAccept = () =>
		context.userId === creatorId || context.isOmniAdmin;
	const _canEdit = () =>
		context.userId === answer.creator.id || context.isOmniAdmin;

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

						<p>{parser.toReact(answer.articleBody)}</p>

						<ClayButton.Group spaced={true}>
							<ClayButton
								displayType="unstyled"
								onClick={() => setShowNewComment(true)}
							>
								{Liferay.Language.get('reply')}
							</ClayButton>
							{_canEdit() && (
								<ClayButton
									displayType="unstyled"
									onClick={_deleteAnswer}
								>
									{Liferay.Language.get('delete')}
								</ClayButton>
							)}
							{_canAccept() && (
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
