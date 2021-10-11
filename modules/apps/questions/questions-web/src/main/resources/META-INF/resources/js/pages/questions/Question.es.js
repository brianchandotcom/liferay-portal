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
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import {useMutation} from 'graphql-hooks';
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';
import {Helmet} from 'react-helmet';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Alert from '../../components/Alert.es';
import Answer from '../../components/Answer.es';
import ArticleBodyRenderer from '../../components/ArticleBodyRenderer.es';
import Breadcrumb from '../../components/Breadcrumb.es';
import CreatorRow from '../../components/CreatorRow.es';
import DefaultQuestionsEditor from '../../components/DefaultQuestionsEditor.es';
import DeleteQuestion from '../../components/DeleteQuestion.es';
import Link from '../../components/Link.es';
import PaginatedList from '../../components/PaginatedList.es';
import Rating from '../../components/Rating.es';
import RelatedQuestions from '../../components/RelatedQuestions.es';
import SectionLabel from '../../components/SectionLabel.es';
import SubscriptionButton from '../../components/SubscriptionButton.es';
import TagList from '../../components/TagList.es';
import {
	createAnswerQuery,
	getMessages,
	getSubscriptionsQuery,
	getThread,
	markAsAnswerMessageBoardMessageQuery,
	subscribeQuery,
	unsubscribeQuery,
} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {
	dateToBriefInternationalHuman,
	deleteCacheKey,
	getContextLink,
	getErrorObject,
	getFullPath,
	historyPushWithSlug,
} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {questionId, sectionTitle},
			url,
		},
	}) => {
		const context = useContext(AppContext);

		const historyPushParser = historyPushWithSlug(history.push);

		const [error, setError] = useState(null);

		const editor = useRef('');

		const [isPostButtonDisable, setIsPostButtonDisable] = useState(true);
		const [showDeleteModalPanel, setShowDeleteModalPanel] = useState(false);

		const [page, setPage] = useState(1);
		const [pageSize, setPageSize] = useState(20);

		const [loading, setLoading] = useState(true);
		const [question, setQuestion] = useState({});
		const [answers, setAnswers] = useState({});

		const fetchMessages = useCallback(() => {
			if (question && question.id) {
				getMessages(question.id, page, pageSize).then(
					({data: {messageBoardThreadMessageBoardMessages}}) => {
						setAnswers(messageBoardThreadMessageBoardMessages);
					}
				);
			}
		}, [question, page, pageSize]);

		useEffect(() => {
			getThread(questionId, context.siteKey)
				.then(
					({data: {messageBoardThreadByFriendlyUrlPath}, error}) => {
						if (error) {
							const errorObject = getErrorObject(
								error.graphQLErrors[0].extensions.exception
									.errno,
								Liferay.Language.get(
									'the-link-you-followed-may-be-broken-or-the-question-no-longer-exists'
								),
								Liferay.Language.get(
									'the-question-is-not-found'
								)
							);
							setError(errorObject);
							setLoading(false);
						}
						else {
							setQuestion(messageBoardThreadByFriendlyUrlPath);
							setLoading(false);
						}
					}
				)
				.catch((error) => {
					if (process.env.NODE_ENV === 'development') {
						console.error(error);
					}
					const errorObject = getErrorObject(
						error.graphQLErrors[0].extensions.exception.errno,
						'the-link-you-followed-may-be-broken-or-the-question-no-longer-exists',
						'the-question-is-not-found'
					);
					setError(errorObject);
					setLoading(false);
				});
		}, [questionId, context.siteKey]);

		sectionTitle =
			sectionTitle || sectionTitle === '0'
				? sectionTitle
				: question.messageBoardSection &&
				  question.messageBoardSection.title;

		useEffect(() => {
			document.title = (question && question.title) || questionId;
		}, [question, questionId]);

		useEffect(() => {
			fetchMessages();
		}, [fetchMessages]);

		const [createAnswer] = useMutation(createAnswerQuery);

		const deleteAnswer = useCallback(
			(answer) => {
				setAnswers({
					...answers,
					items: [
						...answers.items.filter(
							(otherAnswer) => answer.id !== otherAnswer.id
						),
					],
					totalCount: answers.totalCount - 1,
				});
			},
			[answers]
		);

		const [markAsAnswerMessageBoardMessage] = useMutation(
			markAsAnswerMessageBoardMessageQuery
		);

		const answerChange = useCallback(
			(answerId) => {
				const answer = answers.items.find(
					(answer) => answer.showAsAnswer && answer.id !== answerId
				);

				if (answer) {
					markAsAnswerMessageBoardMessage({
						variables: {
							messageBoardMessageId: answer.id,
							showAsAnswer: false,
						},
					}).then(() => {
						fetchMessages();
					});
				}
			},
			[markAsAnswerMessageBoardMessage, answers.items, fetchMessages]
		);

		return (
			<section className="questions-section questions-section-single">
				<Breadcrumb
					section={
						question.messageBoardSection || context.rootTopicId
					}
				/>

				{error && (
					<div className="questions-container row">
						<div className="c-mx-auto c-px-0 col-xl-10">
							<ClayEmptyState
								description={error.message}
								imgSrc={
									context.includeContextPath +
									'/assets/empty_questions_list.png'
								}
								title={error.title}
							>
								<ClayButton
									displayType="primary"
									onClick={() =>
										historyPushParser('/questions')
									}
								>
									{Liferay.Language.get('home')}
								</ClayButton>
							</ClayEmptyState>
						</div>
					</div>
				)}

				<div className="c-mt-5">
					{!loading && !error && (
						<div className="questions-container row">
							<div className="col-md-1 text-md-center">
								<Rating
									aggregateRating={question.aggregateRating}
									disabled={!!question.locked}
									entityId={question.id}
									myRating={
										question.myRating &&
										question.myRating.ratingValue
									}
									type="Thread"
								/>
							</div>

							<div className="col-md-10">
								<div className="align-items-end flex-column-reverse flex-md-row row">
									<div className="c-mt-4 c-mt-md-0 col-md-8">
										{!!question.messageBoardSection &&
											!!question.messageBoardSection
												.numberOfMessageBoardSections && (
												<Link
													to={`/questions/${
														context.useTopicNamesInURL
															? sectionTitle
															: question
																	.messageBoardSection
																	.id
													}`}
												>
													<SectionLabel
														section={
															question.messageBoardSection
														}
													/>
												</Link>
											)}

										<h1
											className={classNames(
												'c-mt-2',
												'question-headline',
												{
													'question-seen':
														question.seen,
												}
											)}
										>
											{question.headline}

											{question.status &&
												question.status !==
													'approved' && (
													<span className="c-ml-2">
														<ClayLabel displayType="info">
															{question.status}
														</ClayLabel>
													</span>
												)}

											{!!question.locked && (
												<span className="c-ml-2">
													<ClayIcon symbol="lock" />
												</span>
											)}
										</h1>

										<p className="c-mb-0 small text-secondary">
											{Liferay.Language.get('asked')}{' '}
											{dateToBriefInternationalHuman(
												question.dateCreated
											)}
											{' - '}
											{Liferay.Language.get(
												'active'
											)}{' '}
											{dateToBriefInternationalHuman(
												question.dateModified
											)}
											{' - '}
											{lang.sub(
												Liferay.Language.get(
													'viewed-x-times'
												),
												[question.viewCount]
											)}
										</p>
									</div>

									{!question.locked && (
										<div className="col-md-4 text-right">
											<ClayButton.Group
												className="questions-actions"
												spaced={true}
											>
												{question.actions.subscribe && (
													<SubscriptionButton
														isSubscribed={
															question.subscribed
														}
														onSubscription={() =>
															deleteCacheKey(
																getSubscriptionsQuery,
																{
																	contentType:
																		'MessageBoardThread',
																}
															)
														}
														queryVariables={{
															messageBoardThreadId:
																question.id,
														}}
														subscribeQuery={
															subscribeQuery
														}
														unsubscribeQuery={
															unsubscribeQuery
														}
													/>
												)}

												{question.actions.delete && (
													<>
														<DeleteQuestion
															deleteModalVisibility={
																showDeleteModalPanel
															}
															question={question}
															setDeleteModalVisibility={
																setShowDeleteModalPanel
															}
														/>

														<ClayButton
															data-tooltip-align="top"
															displayType="secondary"
															onClick={() =>
																setShowDeleteModalPanel(
																	true
																)
															}
															title={Liferay.Language.get(
																'delete'
															)}
														>
															<ClayIcon symbol="trash" />
														</ClayButton>
													</>
												)}

												{question.actions.replace && (
													<Link to={`${url}/edit`}>
														<ClayButton displayType="secondary">
															{Liferay.Language.get(
																'edit'
															)}
														</ClayButton>
													</Link>
												)}
											</ClayButton.Group>
										</div>
									)}
								</div>

								<div className="c-mt-4">
									<ArticleBodyRenderer {...question} />
								</div>

								<div className="c-mt-4">
									<TagList
										sectionTitle={sectionTitle}
										tags={question.keywords}
									/>
								</div>

								<div className="c-mt-4 position-relative questions-creator text-center text-md-right">
									<CreatorRow question={question} />
								</div>

								<h3 className="c-mt-4 text-secondary">
									{answers.totalCount}{' '}
									{Liferay.Language.get('answers')}
								</h3>

								<div className="c-mt-3">
									<PaginatedList
										activeDelta={pageSize}
										activePage={page}
										changeDelta={setPageSize}
										changePage={setPage}
										data={answers}
									>
										{(answer) => (
											<Answer
												answer={answer}
												answerChange={answerChange}
												canMarkAsAnswer={
													!question.locked &&
													!!question.actions.replace
												}
												deleteAnswer={deleteAnswer}
												editable={!question.locked}
												key={answer.id}
											/>
										)}
									</PaginatedList>
								</div>

								{question &&
									question.status !== 'pending' &&
									question.actions &&
									question.actions['reply-to-thread'] && (
										<div className="c-mt-5">
											<DefaultQuestionsEditor
												label={Liferay.Language.get(
													'your-answer'
												)}
												onContentLengthValid={
													setIsPostButtonDisable
												}
												question={question}
												ref={editor}
											/>

											{!question.locked && (
												<ClayButton
													disabled={
														isPostButtonDisable
													}
													displayType="primary"
													onClick={() => {
														createAnswer({
															fetchOptionsOverrides: getContextLink(
																`${sectionTitle}/${questionId}`
															),
															variables: {
																articleBody: editor.current.getContent(),
																messageBoardThreadId:
																	question.id,
															},
														}).then(() => {
															editor.current.clearContent();
															fetchMessages();
														});
													}}
												>
													{context.trustedUser
														? Liferay.Language.get(
																'post-answer'
														  )
														: Liferay.Language.get(
																'submit-for-publication'
														  )}
												</ClayButton>
											)}
										</div>
									)}
							</div>
						</div>
					)}
					{question && question.id && (
						<RelatedQuestions question={question} />
					)}

					<Alert info={error} />
				</div>

				{question && (
					<Helmet>
						<title>{question.headline}</title>
						<link
							href={`${getFullPath(
								context.historyRouterBasePath || 'questions'
							)}${
								context.historyRouterBasePath
									? context.historyRouterBasePath
									: '#'
							}/questions/${sectionTitle}/${questionId}`}
							rel="canonical"
						/>
					</Helmet>
				)}
			</section>
		);
	}
);
