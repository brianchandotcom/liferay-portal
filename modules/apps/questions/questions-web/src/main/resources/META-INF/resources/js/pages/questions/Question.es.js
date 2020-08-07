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

import {useMutation, useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayNavigationBar from '@clayui/navigation-bar';
import classNames from 'classnames';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Answer from '../../components/Answer.es';
import ArticleBodyRenderer from '../../components/ArticleBodyRenderer.es';
import Breadcrumb from '../../components/Breadcrumb.es';
import CreatorRow from '../../components/CreatorRow.es';
import DeleteQuestion from '../../components/DeleteQuestion.es';
import Link from '../../components/Link.es';
import PaginatedList from '../../components/PaginatedList.es';
import QuestionsEditor from '../../components/QuestionsEditor';
import Rating from '../../components/Rating.es';
import RelatedQuestions from '../../components/RelatedQuestions.es';
import SectionLabel from '../../components/SectionLabel.es';
import Subscription from '../../components/Subscription.es';
import TagList from '../../components/TagList.es';
import TextLengthValidation from '../../components/TextLengthValidation.es';
import useQueryParams from '../../hooks/useQueryParams.es';
import {
	createAnswerQuery,
	getMessagesQuery,
	getThreadQuery,
	markAsAnswerMessageBoardMessageQuery,
} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {
	dateToBriefInternationalHuman,
	getContextLink,
	stripHTML,
} from '../../utils/utils.es';

export default withRouter(
	({
		location,
		match: {
			params: {questionId, sectionTitle},
			url,
		},
	}) => {
		const context = useContext(AppContext);

		const queryParams = useQueryParams(location);

		const sort = queryParams.get('sort') || 'active';

		const [articleBody, setArticleBody] = useState();
		const [showDeleteModalPanel, setShowDeleteModalPanel] = useState(false);

		const [page, setPage] = useState(1);
		const [pageSize, setPageSize] = useState(20);

		const {
			loading,
			data: {messageBoardThreadByFriendlyUrlPath: question = {}} = {},
		} = useQuery(getThreadQuery, {
			context: {
				uri: '/o/graphql?nestedFields=lastPostDate',
			},
			variables: {
				friendlyUrlPath: questionId,
				siteKey: context.siteKey,
			},
		});

		const {
			data: {messageBoardThreadMessageBoardMessages = {}} = {},
			refetch,
		} = useQuery(getMessagesQuery, {
			context: {
				uri: '/o/graphql?nestedFields=lastPostDate',
			},
			skip: !question || !question.id,
			variables: {
				messageBoardThreadId: question.id,
				page: sort === 'votes' ? 1 : page,
				pageSize: sort === 'votes' ? 100 : pageSize,
				sort:
					sort === 'votes' || sort === 'active'
						? 'dateModified:desc'
						: 'dateCreated:desc',
			},
		});

		const [answers, setAnswers] = useState({});

		useEffect(() => {
			if (messageBoardThreadMessageBoardMessages.totalCount) {
				if (sort !== 'votes') {
					setAnswers({...messageBoardThreadMessageBoardMessages});
				}
				else {
					const items = [
						...[
							...messageBoardThreadMessageBoardMessages.items,
						].sort((answer1, answer2) => {
							if (answer2.showAsAnswer) {
								return 1;
							}
							if (answer1.showAsAnswer) {
								return -1;
							}

							const ratingValue1 =
								(answer1.aggregateRating &&
									answer1.aggregateRating.ratingValue) ||
								0;
							const ratingValue2 =
								(answer2.aggregateRating &&
									answer2.aggregateRating.ratingValue) ||
								0;

							return ratingValue2 - ratingValue1;
						}),
					];

					setAnswers({
						...messageBoardThreadMessageBoardMessages,
						items,
					});
				}
			}
		}, [messageBoardThreadMessageBoardMessages, pageSize, sort]);

		const [createAnswer] = useMutation(createAnswerQuery, {
			context: getContextLink(`${sectionTitle}/${questionId}`),
			onCompleted() {
				setArticleBody('');
				refetch();
			},
		});

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
			markAsAnswerMessageBoardMessageQuery,
			{
				onCompleted() {
					refetch();
				},
			}
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
					});
				}
			},
			[markAsAnswerMessageBoardMessage, answers.items]
		);

		return (
			<section className="questions-section questions-section-single">
				<Breadcrumb section={question.messageBoardSection} />

				<div className="c-mt-5 questions-container">
					{!loading && (
						<div className="row">
							<div className="col-md-1 text-md-center">
								<Rating
									aggregateRating={question.aggregateRating}
									entityId={question.id}
									myRating={
										question.myRating &&
										question.myRating.ratingValue
									}
									type={'Thread'}
								/>
							</div>

							<div className="col-md-10">
								<div className="align-items-end flex-column-reverse flex-md-row row">
									<div className="c-mt-4 c-mt-md-0 col-md-8">
										{!!question.messageBoardSection
											.numberOfMessageBoardSections && (
											<Link
												to={`/questions/${question.messageBoardSection.title}`}
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

									<div className="col-md-4 text-right">
										<ClayButton.Group
											className="questions-actions"
											spaced={true}
										>
											{question.actions.subscribe && (
												<Subscription
													question={question}
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
														displayType="secondary"
														onClick={() =>
															setShowDeleteModalPanel(
																true
															)
														}
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
								</div>

								<div className="c-mt-4">
									<ArticleBodyRenderer {...question} />
								</div>

								<div className="c-mt-4">
									<TagList
										sectionTitle={
											question.messageBoardSection &&
											question.messageBoardSection.title
										}
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

								{!!answers.totalCount && (
									<div className="border-bottom c-mt-3">
										<ClayNavigationBar triggerLabel="Active">
											<ClayNavigationBar.Item
												active={sort === 'active'}
											>
												<Link
													className="link-unstyled nav-link"
													to={`${url}?sort=active`}
												>
													{Liferay.Language.get(
														'active'
													)}
												</Link>
											</ClayNavigationBar.Item>

											<ClayNavigationBar.Item
												active={sort === 'oldest'}
											>
												<Link
													className="link-unstyled nav-link"
													to={`${url}?sort=oldest`}
												>
													{Liferay.Language.get(
														'oldest'
													)}
												</Link>
											</ClayNavigationBar.Item>

											<ClayNavigationBar.Item
												active={sort === 'votes'}
											>
												<Link
													className="link-unstyled nav-link"
													to={`${url}?sort=votes`}
												>
													{Liferay.Language.get(
														'votes'
													)}
												</Link>
											</ClayNavigationBar.Item>
										</ClayNavigationBar>
									</div>
								)}

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
													!!question.actions.replace
												}
												deleteAnswer={deleteAnswer}
												key={answer.id}
											/>
										)}
									</PaginatedList>
								</div>

								{question &&
									question.actions &&
									question.actions['reply-to-thread'] && (
										<div className="c-mt-5">
											<ClayForm>
												<ClayForm.Group className="form-group-sm">
													<label htmlFor="basicInput">
														{Liferay.Language.get(
															'your-answer'
														)}

														<span className="c-ml-2 reference-mark">
															<ClayIcon symbol="asterisk" />
														</span>
													</label>

													<div className="c-mt-2">
														<QuestionsEditor
															contents={
																articleBody
															}
															onChange={(
																event
															) => {
																setArticleBody(
																	event.editor.getData()
																);
															}}
														/>
													</div>

													<ClayForm.FeedbackGroup>
														<ClayForm.FeedbackItem>
															<TextLengthValidation
																text={
																	articleBody
																}
															/>
														</ClayForm.FeedbackItem>
													</ClayForm.FeedbackGroup>
												</ClayForm.Group>
											</ClayForm>

											<ClayButton
												disabled={
													!articleBody ||
													stripHTML(articleBody)
														.length < 15
												}
												displayType="primary"
												onClick={() => {
													createAnswer({
														variables: {
															articleBody,
															messageBoardThreadId:
																question.id,
														},
													});
												}}
											>
												{Liferay.Language.get(
													'post-answer'
												)}
											</ClayButton>
										</div>
									)}
							</div>
						</div>
					)}
					{question && question.id && (
						<RelatedQuestions question={question} />
					)}
				</div>
			</section>
		);
	}
);
