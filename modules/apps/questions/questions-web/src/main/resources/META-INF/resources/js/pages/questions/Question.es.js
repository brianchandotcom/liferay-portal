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
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Answer from '../../components/Answer.es';
import ArticleBodyRenderer from '../../components/ArticleBodyRenderer.es';
import CreatorRow from '../../components/CreatorRow.es';
import Link from '../../components/Link.es';
import Modal from '../../components/Modal.es';
import Rating from '../../components/Rating.es';
import RelatedQuestions from '../../components/RelatedQuestions.es';
import SectionLabel from '../../components/SectionLabel.es';
import Subscription from '../../components/Subscription.es';
import TagList from '../../components/TagList.es';
import {
	createAnswer,
	deleteMessageBoardThread,
	getMessages,
	getThread,
	markAsAnswerMessageBoardMessage,
} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {
	dateToBriefInternationalHuman,
	getCKEditorConfig,
	onBeforeLoadCKEditor,
} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		location: key,
		match: {
			params: {questionId},
			url,
		},
	}) => {
		const context = useContext(AppContext);

		const [answers, setAnswers] = useState([]);
		const [articleBody, setArticleBody] = useState();
		const [deleteModalVisible, setDeleteModalVisible] = useState(false);
		const [page, setPage] = useState(1);
		const [question, setQuestion] = useState();
		const [sectionTitle, setSectionTitle] = useState('');
		const [filter, setFilter] = useState('active');

		useEffect(() => {
			loadThread();
		}, [key, loadThread]);

		const loadThread = useCallback(
			() =>
				getThread(questionId, context.siteKey, page).then((data) => {
					setQuestion(data);
					setAnswers(data.messageBoardMessages.items);
					setSectionTitle(data.messageBoardSection.title);
				}),
			[context.siteKey, page, questionId]
		);

		const postAnswer = () => {
			createAnswer(articleBody, question.id).then(() => {
				setArticleBody('');

				return loadThread();
			});
		};

		const updateMarkAsAnswer = useCallback(
			(answerId) => {
				setAnswers([
					...answers.map((otherAnswer) => {
						otherAnswer.showAsAnswer = otherAnswer.id === answerId;

						return otherAnswer;
					}),
				]);
			},
			[answers]
		);

		const deleteThread = () => {
			deleteMessageBoardThread(question.id).then(() => history.goBack());
		};

		const deleteAnswer = useCallback(
			(answer) => {
				setAnswers([
					...answers.filter(
						(otherAnswer) => answer.id !== otherAnswer.id
					),
				]);
			},
			[answers]
		);

		const answerChange = useCallback(
			(answerId) => {
				const answer = answers.find(
					(answer) => answer.showAsAnswer && answer.id !== answerId
				);

				if (answer) {
					markAsAnswerMessageBoardMessage(answer.id, false).then(
						() => {
							updateMarkAsAnswer(answerId);
						}
					);
				}
				else {
					updateMarkAsAnswer(answerId);
				}
			},
			[answers, updateMarkAsAnswer]
		);

		const filterBy = (filterBy) => {
			let promise;
			if (filterBy === 'votes') {
				promise = getMessages(
					question.id,
					'dateModified:desc',
					1,
					100
				).then((answers) =>
					answers.sort((answer1, answer2) => {
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
					})
				);
			}
			else if (filterBy === 'active') {
				promise = getMessages(question.id, 'dateModified:desc');
			}
			else {
				promise = getMessages(question.id, 'dateModified:asc');
			}

			promise.then((x) => {
				setFilter(filterBy);
				setAnswers(x);
			});
		};

		return (
			<section className="c-mt-5 questions-section questions-section-single">
				<div className="questions-container">
					{question && (
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
									<div className="c-mt-4 c-mt-md-0 col-md-9">
										<Link to={`/questions/${sectionTitle}`}>
											<SectionLabel
												section={
													question.messageBoardSection
												}
											/>
										</Link>

										<h1 className="c-mt-2 question-headline">
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

									<div className="col-md-3 text-right">
										<ClayButton.Group
											className="questions-actions"
											spaced={true}
										>
											{question.actions.subscribe && (
												<Subscription
													onSubscription={(
														subscribed
													) =>
														setQuestion({
															...question,
															subscribed,
														})
													}
													question={question}
												/>
											)}

											{question.actions.delete && (
												<>
													<Modal
														body={Liferay.Language.get(
															'do-you-want-to-delete–this-thread'
														)}
														callback={deleteThread}
														onClose={() =>
															setDeleteModalVisible(
																false
															)
														}
														status="warning"
														textPrimaryButton={Liferay.Language.get(
															'delete'
														)}
														title={Liferay.Language.get(
															'delete-thread'
														)}
														visible={
															deleteModalVisible
														}
													/>
													<ClayButton
														displayType="secondary"
														onClick={() =>
															setDeleteModalVisible(
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
									<TagList tags={question.keywords} />
								</div>

								<div className="c-mt-4 position-relative questions-creator text-center text-md-right">
									<CreatorRow question={question} />
								</div>

								<h3 className="c-mt-4 text-secondary">
									{answers.length}{' '}
									{Liferay.Language.get('answers')}
								</h3>

								{!!answers.length && (
									<div className="border-bottom c-mt-3">
										<ClayNavigationBar triggerLabel="Active">
											<ClayNavigationBar.Item
												active={filter === 'active'}
											>
												<ClayLink
													className="nav-link"
													displayType="unstyled"
													onClick={() =>
														filterBy('active')
													}
												>
													{Liferay.Language.get(
														'active'
													)}
												</ClayLink>
											</ClayNavigationBar.Item>

											<ClayNavigationBar.Item
												active={filter === 'oldest'}
											>
												<ClayLink
													className="nav-link"
													displayType="unstyled"
													onClick={() =>
														filterBy('oldest')
													}
												>
													{Liferay.Language.get(
														'oldest'
													)}
												</ClayLink>
											</ClayNavigationBar.Item>

											<ClayNavigationBar.Item
												active={filter === 'votes'}
											>
												<ClayLink
													className="nav-link"
													displayType="unstyled"
													onClick={() =>
														filterBy('votes')
													}
												>
													{Liferay.Language.get(
														'votes'
													)}
												</ClayLink>
											</ClayNavigationBar.Item>
										</ClayNavigationBar>
									</div>
								)}

								<div className="c-mt-3">
									{answers.map((answer) => (
										<Answer
											answer={answer}
											answerChange={answerChange}
											deleteAnswer={deleteAnswer}
											key={answer.id}
										/>
									))}
								</div>

								{!!answers.totalCount &&
									answers.totalCount > answers.pageSize && (
										<ClayPaginationWithBasicItems
											activePage={page}
											ellipsisBuffer={2}
											onPageChange={setPage}
											totalPages={Math.ceil(
												answers.totalCount /
													answers.pageSize
											)}
										/>
									)}

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
														<Editor
															config={getCKEditorConfig()}
															data={articleBody}
															onBeforeLoad={(
																editor
															) =>
																onBeforeLoadCKEditor(
																	editor,
																	context.imageBrowseURL
																)
															}
															onChange={(event) =>
																setArticleBody(
																	event.editor.getData()
																)
															}
														/>
													</div>
												</ClayForm.Group>
											</ClayForm>

											<ClayButton
												disabled={!articleBody}
												displayType="primary"
												onClick={postAnswer}
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
