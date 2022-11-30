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

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayTabs from '@clayui/tabs';
import ClayUpperToolbar from '@clayui/upper-toolbar';
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
import EditedTimestamp from '../../components/EditedTimestamp.es';
import Link from '../../components/Link.es';
import PaginatedList from '../../components/PaginatedList.es';
import Rating from '../../components/Rating.es';
import SectionLabel from '../../components/SectionLabel.es';
import SubscritionCheckbox from '../../components/SubscribeCheckbox.es';
import SubscriptionButton from '../../components/SubscriptionButton.es';
import TagList from '../../components/TagList.es';
import {
	createAnswerQuery,
	getMessages,
	getSubscriptionsQuery,
	getThread,
	getUserActivityQuery,
	markAsAnswerMessageBoardMessageQuery,
	subscribeQuery,
	unsubscribeQuery,
} from '../../utils/client.es';
import {ALL_SECTIONS_ID} from '../../utils/contants.es';
import lang from '../../utils/lang.es';
import {
	deleteCacheKey,
	getContextLink,
	getErrorObject,
	getFullPath,
	historyPushWithSlug,
} from '../../utils/utils.es';
import FlagsContainer from './components/FlagsContainer';
import useActiviyQuestionKebabOptions from './hooks/useActivityQuestionKebabOptions.es';

const tabs = [
	{label: Liferay.Language.get('newest'), sortBy: 'dateCreated:desc'},
	{label: Liferay.Language.get('oldest'), sortBy: 'dateCreated:asc'},
	{
		label: Liferay.Language.get('votes'),
		sortBy: 'ratingsStatTotalScore:desc',
	},
];

const Question = ({
	display = {
		actions: true,
		addAnswer: true,
		breadcrumb: true,
		flags: true,
		kebab: false,
		rating: true,
		showAnswer: true,
		showSignature: false,
		styled: false,
		tabs: true,
	},
	history,
	questionId,
	sectionTitle,
	url,
}) => {
	const sectionRef = useRef(null);

	const runScroll = () =>
		sectionRef.current.scrollIntoView({
			behavior: 'smooth',
			block: 'start',
		});

	const [activeIndex, setActiveIndex] = useState(0);

	const [allowSubscription, setAllowSubscription] = useState(false);
	const [answers, setAnswers] = useState({});
	const [createAnswer] = useMutation(createAnswerQuery);
	const [error, setError] = useState(null);
	const [isModerate, setIsModerate] = useState(false);
	const [isPageScroll, setIsPageScroll] = useState(false);
	const [isPostButtonDisable, setIsPostButtonDisable] = useState(true);

	const [isVisibleEditor, setIsVisibleEditor] = useState(false);
	const [loading, setLoading] = useState(true);
	const [loadingAnswer, setLoadingAnswer] = useState(true);
	const [page, setPage] = useState(1);
	const [pageSize, setPageSize] = useState(20);
	const [question, setQuestion] = useState({});
	const [showDeleteModalPanel, setShowDeleteModalPanel] = useState(false);
	const [subscribe] = useMutation(subscribeQuery);
	const context = useContext(AppContext);
	const editorRef = useRef('');
	const historyPushParser = historyPushWithSlug(history.push);

	const {kebabOptions, setIsSubscribe} = useActiviyQuestionKebabOptions({
		context,
		question,
		questionId,
		sectionTitle,
		setError,
		setShowDeleteModalPanel,
	});

	const fetchMessages = useCallback(() => {
		const sortBy = tabs[activeIndex].sortBy;

		if (question && question.id) {
			getMessages(question.id, page, pageSize, sortBy).then(
				({data: {messageBoardThreadMessageBoardMessages}}) => {
					setAnswers(messageBoardThreadMessageBoardMessages);
					setLoadingAnswer(false);
				}
			);

			setIsSubscribe(question.subscribed);
		}
	}, [activeIndex, question, page, pageSize, setIsSubscribe]);

	useEffect(() => {
		if (!questionId) {
			return;
		}

		getThread(questionId, context.siteKey)
			.then(({data: {messageBoardThreadByFriendlyUrlPath}, error}) => {
				if (error) {
					const errorObject = getErrorObject(
						error.graphQLErrors[0].extensions.exception.errno,
						Liferay.Language.get(
							'the-link-you-followed-may-be-broken-or-the-question-no-longer-exists'
						),
						Liferay.Language.get('the-question-is-not-found')
					);

					setError(errorObject);

					setLoading(false);
				}
				else {
					setQuestion(messageBoardThreadByFriendlyUrlPath);
					setLoading(false);
				}
			})
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
		sectionTitle || sectionTitle === ALL_SECTIONS_ID
			? sectionTitle
			: question.messageBoardSection &&
			  question.messageBoardSection.title;

	useEffect(() => {
		document.title = (question && question.title) || questionId;
	}, [question, questionId]);

	useEffect(() => {
		fetchMessages();
	}, [activeIndex, fetchMessages]);

	const questionVisited = context?.questionsVisited?.includes(question.id);

	useEffect(() => {
		setIsSubscribe(question.subscribed);
	}, [question.subscribed, setIsSubscribe]);

	useEffect(() => {
		if (question.id && context?.questionsVisited && !questionVisited) {
			context.setQuestionsVisited([
				...context.questionsVisited,
				question.id,
			]);
		}
	}, [context, question, questionVisited]);

	const onSubscription = useCallback(
		async ({
			allowSubscription: _allowSubscription = allowSubscription,
		} = {}) => {
			if (question.subscribed || !_allowSubscription) {
				return;
			}

			await subscribe({
				variables: {
					messageBoardThreadId: question.id,
				},
			});

			deleteCacheKey(getSubscriptionsQuery, {
				contentType: 'MessageBoardThread',
			});

			setQuestion({...question, subscribed: true});
		},
		[allowSubscription, question, subscribe, setQuestion]
	);

	const onShowEditorAnswer = () => {
		const threadAnswers = answers.items || [];

		const hasUserAnswered = threadAnswers.some(
			(answer) => answer.creator.id === Number(context.userId)
		);
		if (!context.trustedUser && hasUserAnswered) {
			setIsModerate(true);
		}

		return setIsVisibleEditor(true);
	};

	const onCreateAnswer = async () => {
		try {
			await createAnswer({
				fetchOptionsOverrides: getContextLink(
					`${sectionTitle}/${questionId}`
				),
				variables: {
					articleBody: editorRef.current.getContent(),
					messageBoardThreadId: question.id,
				},
			});

			editorRef.current.clearContent();

			await onSubscription();

			fetchMessages();

			deleteCacheKey(getUserActivityQuery, {
				filter: `creatorId eq ${context.userId}`,
				page: 1,
				pageSize: 20,
				siteKey: context.siteKey,
			});
			setIsVisibleEditor(false);
		}
		catch (error) {}
	};

	const deleteAnswer = useCallback(
		(answer) => {
			setAnswers({
				...answers,
				items: [
					...answers.items?.filter(
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
			const answer = answers.items?.find(
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

	useEffect(() => {
		const body = document.body;
		const html = document.documentElement;

		const docHeight = Math.max(
			body.scrollHeight,
			body.offsetHeight,
			html.clientHeight,
			html.scrollHeight,
			html.offsetHeight
		);

		const winHeight = window.innerHeight;
		setIsPageScroll(docHeight > winHeight);
	}, [question, answers]);

	return (
		<section
			className={classNames('', {
				'c-mt-2': display.styled,
				'questions-section questions-section-single': !display.styled,
			})}
		>
			{display.breadcrumb && (
				<Breadcrumb
					section={
						question.messageBoardSection || context.rootTopicId
					}
				/>
			)}

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
								onClick={() => historyPushParser('/questions')}
							>
								{Liferay.Language.get('home')}
							</ClayButton>
						</ClayEmptyState>
					</div>
				</div>
			)}

			{isModerate && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={6000}
						displayType="warning"
						onClose={() => setIsModerate(false)}
						title={Liferay.Language.get(
							'are-you-sure-you-want-to-add-another-answer'
						)}
						variant="inline"
					>
						<div>
							{Liferay.Language.get(
								'you-can-add-a-comment-to-continue-the-existing-thread'
							)}
						</div>

						<ClayAlert.Footer>
							<ClayButton.Group className="ml-0">
								<ClayButton
									alert
									onClick={() => setIsModerate(false)}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							</ClayButton.Group>
						</ClayAlert.Footer>
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}

			<div
				className={classNames('', {
					' c-mt-2 px-3': display.styled,
					'c-mt-5': !display.styled,
				})}
			>
				{!loading && !error && (
					<div className="questions-container">
						{display.actions && display.rating && (
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
						)}

						<div
							className={classNames('', {
								'col-md-10': !display.styled,
								'col-md-12 ': display.styled,
							})}
						>
							<div
								className={classNames('', {
									'align-items-top flex-column-reverse flex-md-row justify-content-between':
										display.styled,
									'align-items-top flex-column-reverse flex-md-row row': !display.styled,
								})}
							>
								<div
									className={classNames('', {
										'c-mt-2 c-mt-md-0': display.styled,
										'c-mt-4 c-mt-md-0 col-md-7': !display.styled,
									})}
								>
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

									<div className="d-flex flex-row justify-content-between">
										<h1
											className={classNames(
												'c-mt-2 text-6 question-headline text-weight-bold',
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

										{display.kebab && (
											<div className="d-flex mt-2">
												<ClayUpperToolbar.Item>
													<ClayDropDownWithItems
														items={kebabOptions}
														trigger={
															<ClayButtonWithIcon
																displayType="unstyled"
																small
																symbol="ellipsis-v"
															/>
														}
													/>
												</ClayUpperToolbar.Item>
											</div>
										)}
									</div>

									<p className="align-items-start d-flex justify-content-start small text-secondary">
										<EditedTimestamp
											dateCreated={question.dateCreated}
											dateModified={question.dateModified}
											operationText={Liferay.Language.get(
												'asked'
											)}
										/>

										{`/ ${lang.sub(
											Liferay.Language.get(
												'viewed-x-times'
											),
											[question.viewCount]
										)}`}
									</p>
								</div>

								{!display.kebab && !question.locked && (
									<div className="col-md-5 text-right">
										<ClayButton.Group
											className="questions-actions"
											spaced
										>
											{display.actions &&
												question.actions.subscribe && (
													<SubscriptionButton
														isSubscribed={
															question.subscribed
														}
														onSubscription={(
															subscribed
														) => {
															deleteCacheKey(
																getSubscriptionsQuery,
																{
																	contentType:
																		'MessageBoardThread',
																}
															);

															setQuestion(
																(
																	prevQuestion
																) => ({
																	...prevQuestion,
																	subscribed,
																})
															);
														}}
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

											{display.actions &&
												question.actions.delete && (
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
												)}

											<FlagsContainer
												content={question}
												context={context}
											/>

											{display.actions &&
												question.actions.replace && (
													<Link to={`${url}/edit`}>
														<ClayButton displayType="secondary">
															{Liferay.Language.get(
																'edit'
															)}
														</ClayButton>
													</Link>
												)}

											{isPageScroll &&
												!!answers.items?.length && (
													<ClayButton
														className="btn btn-secondary"
														displayType="secondary"
														onClick={runScroll}
													>
														{Liferay.Language.get(
															'go-to-answers'
														)}
													</ClayButton>
												)}
										</ClayButton.Group>
									</div>
								)}
							</div>

							<div className="c-mt-4">
								<div>
									<ArticleBodyRenderer {...question} />
								</div>
							</div>

							<div className="c-mt-4" ref={sectionRef}>
								<TagList
									sectionTitle={sectionTitle}
									tags={question.keywords}
								/>
							</div>

							<div className="c-mt-4 position-relative questions-creator text-center text-md-right">
								<CreatorRow
									answers={answers}
									question={question}
								/>
							</div>

							<h3 className="c-mt-4 text-secondary">
								{loadingAnswer
									? `${Liferay.Language.get(
											'loading-answers'
									  )}`
									: `${
											answers.totalCount
									  } ${Liferay.Language.get('answers')}`}
							</h3>

							<ClayTabs
								active={activeIndex}
								modern
								onActiveChange={setActiveIndex}
							>
								{tabs.map((tab, index) => (
									<ClayTabs.Item
										active={activeIndex === index}
										innerProps={{
											'aria-controls': `tabpanel-${index}`,
										}}
										key={index}
										onClick={() => setActiveIndex(index)}
									>
										{tab.label}
									</ClayTabs.Item>
								))}
							</ClayTabs>

							<ClayTabs.Content activeIndex={activeIndex} fade>
								<div
									className={classNames({
										'c-mt-3 font-weight-normal':
											display.styled,
									})}
								>
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
												answers={answers}
												canMarkAsAnswer={
													!question.locked &&
													!!question.actions.replace
												}
												context={context}
												deleteAnswer={deleteAnswer}
												display={display}
												editable={!question.locked}
												key={answer.id}
												onSubscription={onSubscription}
												question={question}
												showItems={display.showAnswer}
												showSignature={
													display.showSignature
												}
												styledItems={display.styled}
											/>
										)}
									</PaginatedList>
								</div>

								{display.actions &&
									question &&
									question.status !== 'pending' &&
									question.actions &&
									question.actions['reply-to-thread'] && (
										<div className="c-mt-5">
											{isVisibleEditor && (
												<>
													<DefaultQuestionsEditor
														label={Liferay.Language.get(
															'your-answer'
														)}
														onContentLengthValid={
															setIsPostButtonDisable
														}
														question={question}
														ref={editorRef}
													/>

													<SubscritionCheckbox
														checked={
															allowSubscription
														}
														setChecked={
															setAllowSubscription
														}
													/>
												</>
											)}

											{display.addAnswer &&
												!isVisibleEditor && (
													<ClayButton
														displayType="primary"
														onClick={
															onShowEditorAnswer
														}
													>
														{Liferay.Language.get(
															'add-answer'
														)}
													</ClayButton>
												)}

											{!question.locked &&
												isVisibleEditor && (
													<ClayButton
														disabled={
															isPostButtonDisable
														}
														displayType="primary"
														onClick={onCreateAnswer}
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
							</ClayTabs.Content>
						</div>
					</div>
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

			<DeleteQuestion
				deleteModalVisibility={showDeleteModalPanel}
				question={question}
				setDeleteModalVisibility={setShowDeleteModalPanel}
			/>
		</section>
	);
};

export default withRouter(
	({
		history,
		match: {
			params: {questionId, sectionTitle},
			url,
		},
	}) => (
		<Question
			history={history}
			questionId={questionId}
			sectionTitle={sectionTitle}
			url={url}
		/>
	)
);

export {Question};
