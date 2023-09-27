/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classnames from 'classnames';
import {useMutation} from 'graphql-hooks';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import FlagsContainer from '../pages/questions/components/FlagsContainer';
import {
	deleteMessageQuery,
	markAsAnswerMessageBoardMessageQuery,
} from '../utils/client.es';
import ArticleBodyRenderer from './ArticleBodyRenderer.es';
import Comments from './Comments.es';
import EditedTimestamp from './EditedTimestamp.es';
import Link from './Link.es';
import Modal from './Modal.es';
import Rating from './Rating.es';
import UserRow from './UserRow.es';

export default withRouter(
	({
		answer,
		answerChange,
		canMarkAsAnswer,
		deleteAnswer,
		display,
		editable = true,
		match: {url},
		onSubscription,
		question,
		showItems = true,
		showSignature,
		styledItems = false,
	}) => {
		const context = useContext(AppContext);
		const [comments, setComments] = useState(
			answer.messageBoardMessages.items
		);
		const [showAsAnswer, setShowAsAnswer] = useState(answer.showAsAnswer);
		const [showNewComment, setShowNewComment] = useState(false);
		const [showDeleteAnswerModal, setShowDeleteAnswerModal] = useState(
			false
		);

		const [deleteMessage] = useMutation(deleteMessageQuery);

		const _commentsChange = useCallback((comments) => {
			setComments([...comments]);
		}, []);

		const [markAsAnswerMessageBoardMessage] = useMutation(
			markAsAnswerMessageBoardMessageQuery
		);

		useEffect(() => {
			setShowAsAnswer(answer.showAsAnswer);
		}, [answer.showAsAnswer]);

		return (
			<>
				<div
					className={classnames('questions-answer c-py-2', {
						'c-px-3': showAsAnswer && !display?.preview,
						'questions-answer': styledItems,
						'questions-answer-success': showAsAnswer,
					})}
					data-testid="mark-as-answer-style"
				>
					<div className="d-flex row">
						{showItems && (
							<div className="c-ml-auto c-ml-md-1 c-ml-sm-auto order-1 order-md-0 text-md-center text-right">
								<Rating
									aggregateRating={answer.aggregateRating}
									disabled={!editable}
									entityId={answer.id}
									myRating={
										answer.myRating &&
										answer.myRating.ratingValue
									}
									type="Message"
								/>
							</div>
						)}

						<div className="c-mb-4 c-mb-md-0 c-ml-3 col-lg-11 col-md-10 col-sm-12 col-xl-11">
							<div
								className={classnames('d-flex', {
									'flex-column':
										showAsAnswer && !display?.preview,
									'flex-row-reverse':
										showAsAnswer && display?.preview,
									'justify-content-between': display?.preview,
								})}
							>
								{showAsAnswer && (
									<div
										className={classnames('d-flex', {
											'justify-content-end':
												display?.preview,
										})}
									>
										<p
											className="c-mb-0 font-weight-bold text-success"
											data-testid="mark-as-answer-check"
										>
											<span className="c-mr-2">
												{Liferay.Language.get(
													'chosen-answer'
												)}
											</span>

											<ClayIcon
												aria-label={Liferay.Language.get(
													'chosen-answer'
												)}
												symbol="check-circle-full"
											/>
										</p>
									</div>
								)}

								<span className="text-secondary">
									<EditedTimestamp
										creator={answer.creator.name}
										dateCreated={answer.dateCreated}
										dateModified={answer.dateModified}
										operationText={Liferay.Language.get(
											'answered'
										)}
										styledTimeStamp={styledItems}
									/>
								</span>

								{answer.modified && (
									<span className="question-edited">
										({Liferay.Language.get('edited')})
									</span>
								)}
							</div>

							{answer.status && answer.status !== 'approved' && (
								<span className="c-ml-2 text-secondary">
									<ClayLabel displayType="info">
										{answer.status}
									</ClayLabel>
								</span>
							)}

							<div>
								<ArticleBodyRenderer {...answer} />
							</div>

							<div>
								<div>
									{editable && (
										<div
											className={classnames(
												'font-weight-bold text-secondary',
												{
													'font-weight-bold text-secondary d-flex': styledItems,
												}
											)}
										>
											{answer.actions[
												'reply-to-message'
											] &&
												answer.status !== 'pending' &&
												!comments.length && (
													<ClayButton
														aria-label={Liferay.Language.get(
															'add-comment'
														)}
														className={classnames(
															'btn-sm c-mr-2 c-px-2 c-py-1',
															{
																'text-2': styledItems,
															}
														)}
														onClick={() =>
															setShowNewComment(
																true
															)
														}
													>
														{Liferay.Language.get(
															'add-comment'
														)}
													</ClayButton>
												)}

											{answer.actions.delete && (
												<>
													<ClayButton
														aria-label={Liferay.Language.get(
															'delete'
														)}
														className={classnames(
															'btn-sm c-mr-2 c-px-2 c-py-1',
															{
																'text-2': styledItems,
															}
														)}
														displayType="secondary"
														onClick={() => {
															setShowDeleteAnswerModal(
																true
															);
														}}
													>
														{Liferay.Language.get(
															'delete'
														)}
													</ClayButton>
													<Modal
														body={Liferay.Language.get(
															'do-you-want-to-delete–this-answer'
														)}
														callback={() => {
															deleteMessage({
																variables: {
																	messageBoardMessageId:
																		answer.id,
																},
															}).then(() => {
																if (
																	comments.length
																) {
																	Promise.all(
																		comments.map(
																			({
																				id,
																			}) =>
																				deleteMessage(
																					{
																						variables: {
																							messageBoardMessageId: id,
																						},
																					}
																				)
																		)
																	).then(
																		() => {
																			deleteAnswer(
																				answer
																			);
																		}
																	);
																}
																else {
																	deleteAnswer(
																		answer
																	);
																}
															});
														}}
														onClose={() => {
															setShowDeleteAnswerModal(
																false
															);
														}}
														status="warning"
														textPrimaryButton={Liferay.Language.get(
															'delete'
														)}
														title={Liferay.Language.get(
															'delete-answer'
														)}
														visible={
															showDeleteAnswerModal
														}
													/>
												</>
											)}

											{canMarkAsAnswer && (
												<ClayButton
													aria-label={
														showAsAnswer
															? Liferay.Language.get(
																	'unmark-as-answer'
															  )
															: Liferay.Language.get(
																	'mark-as-answer'
															  )
													}
													className={classnames(
														'btn-sm c-mr-2 c-px-2 c-py-1',
														{
															'text-2': styledItems,
														}
													)}
													data-testid="mark-as-answer-button"
													displayType="secondary"
													onClick={() => {
														markAsAnswerMessageBoardMessage(
															{
																variables: {
																	messageBoardMessageId:
																		answer.id,
																	showAsAnswer: !showAsAnswer,
																},
															}
														).then(() => {
															setShowAsAnswer(
																!showAsAnswer
															);
															if (answerChange) {
																answerChange(
																	answer.id
																);
															}
														});
													}}
												>
													{showAsAnswer
														? Liferay.Language.get(
																'unmark-as-answer'
														  )
														: Liferay.Language.get(
																'mark-as-answer'
														  )}
												</ClayButton>
											)}

											{display?.flags && (
												<FlagsContainer
													btnProps={{
														className:
															'c-mr-2 c-px-2 c-py-1 btn btn-secondary',
														small: true,
													}}
													content={answer}
													context={context}
													onlyIcon={false}
													showIcon={false}
												/>
											)}

											{editable &&
												answer.actions.replace &&
												showItems && (
													<ClayButton
														aria-label={Liferay.Language.get(
															'edit'
														)}
														className="btn-sm c-mr-2 c-px-2 c-py-1"
														displayType="secondary"
													>
														<Link
															className="text-reset"
															to={`${url}/answers/${answer.friendlyUrlPath}/edit`}
														>
															{Liferay.Language.get(
																'edit'
															)}
														</Link>
													</ClayButton>
												)}
										</div>
									)}
								</div>

								{showItems && (
									<div className="c-ml-md-auto c-ml-sm-2 c-mr-lg-2 c-mr-md-4 c-mr-xl-2 d-flex justify-content-end">
										<UserRow
											companyName={context.companyName}
											creator={answer.creator}
											hasCompanyMx={answer.hasCompanyMx}
											statistics={
												answer.creatorStatistics
											}
										/>
									</div>
								)}
							</div>
						</div>
					</div>
				</div>

				<div className="row">
					<div className="col-md-9 offset-md-1">
						<Comments
							comments={comments}
							commentsChange={_commentsChange}
							editable={editable}
							entityId={answer.id}
							hasCompanyMx={comments.hasCompanyMx}
							onSubscription={onSubscription}
							question={question}
							showNewComment={showNewComment}
							showNewCommentChange={(value) =>
								setShowNewComment(value)
							}
							showSignature={showSignature}
							styledItems={styledItems}
						/>
					</div>
				</div>
				<div className="c-my-2 offset-md-1">
					{editable && !!comments.length && !showNewComment && (
						<ClayButton.Group
							className="font-weight-bold text-secondary"
							spaced
						>
							{answer.actions['reply-to-message'] &&
								answer.status !== 'pending' && (
									<ClayButton
										aria-label={Liferay.Language.get(
											'add-comment'
										)}
										className="btn-sm c-px-2 c-py-1"
										onClick={() => setShowNewComment(true)}
									>
										{Liferay.Language.get('add-comment')}
									</ClayButton>
								)}
						</ClayButton.Group>
					)}
				</div>
			</>
		);
	}
);
