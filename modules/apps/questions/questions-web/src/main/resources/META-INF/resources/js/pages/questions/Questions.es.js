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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useManualQuery} from 'graphql-hooks';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {Helmet} from 'react-helmet';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Alert from '../../components/Alert.es';
import Breadcrumb from '../../components/Breadcrumb.es';
import PaginatedList from '../../components/PaginatedList.es';
import QuestionRow from '../../components/QuestionRow.es';
import ResultsMessage from '../../components/ResultsMessage.es';
import SubscriptionButton from '../../components/SubscriptionButton.es';
import useQueryParams from '../../hooks/useQueryParams.es';
import {
	getRankedThreadsQuery,
	getSectionBySectionTitleQuery,
	getSectionThreadsQuery,
	getSectionsQuery,
	getSubscriptionsQuery,
	getThreadsQuery,
	subscribeSectionQuery,
	unsubscribeSectionQuery,
} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {
	deleteCacheKey,
	getBasePath,
	getFullPath,
	historyPushWithSlug,
	slugToText,
	useDebounceCallback,
} from '../../utils/utils.es';

function getFilterOptions() {
	return [
		{
			label: Liferay.Language.get('latest-created'),
			title: Liferay.Language.get(
				'showing-questions-ordered-by-last-created-first'
			),
			value: 'latest-created',
		},
		{
			label: Liferay.Language.get('latest-edited'),
			title: Liferay.Language.get(
				'showing-questions-ordered-by-last-edited-first'
			),
			value: 'latest-edited',
		},
		{
			label: Liferay.Language.get('voted-in-the-last-week'),
			title: Liferay.Language.get(
				'showing-questions-that-have-at-least-one-vote-in-the-last-week-ordered-by-votes-received'
			),
			value: 'week',
		},
		{
			label: Liferay.Language.get('voted-in-the-last-month'),
			title: Liferay.Language.get(
				'showing-questions-that-have-at-least-one-vote-in-the-last-month-ordered-by-votes-received'
			),
			value: 'month',
		},
		{
			label: Liferay.Language.get('most-voted'),
			title: Liferay.Language.get(
				'showing-questions-that-have-at-least-one-vote-ordered-by-votes-received'
			),
			value: 'most-voted',
		},
	];
}

export default withRouter(
	({
		history,
		location,
		match: {
			params: {creatorId, sectionTitle, tag},
		},
	}) => {
		const MAX_NUMBER_OF_QUESTIONS = 500;
		const [
			allowCreateTopicInRootTopic,
			setAllowCreateTopicInRootTopic,
		] = useState(false);
		const [currentTag, setCurrentTag] = useState('');
		const [error, setError] = useState({});
		const [filter, setFilter] = useState();
		const [loading, setLoading] = useState(true);
		const [page, setPage] = useState(null);
		const [pageSize, setPageSize] = useState(null);
		const [questions, setQuestions] = useState([]);
		const [search, setSearch] = useState(null);
		const [section, setSection] = useState({});
		const [sectionQuery, setSectionQuery] = useState('');
		const [sectionQueryVariables, setSectionQueryVariables] = useState({});
		const [totalCount, setTotalCount] = useState(0);

		const queryParams = useQueryParams(location);

		const context = useContext(AppContext);

		const siteKey = context.siteKey;

		const [getSections] = useManualQuery(getSectionsQuery, {
			variables: {siteKey: context.siteKey},
		});
		const [getSectionBySectionTitle] = useManualQuery(
			getSectionBySectionTitleQuery,
			{
				variables: {
					filter: `title eq '${slugToText(
						sectionTitle
					)}' or id eq '${slugToText(sectionTitle)}'`,
					siteKey: context.siteKey,
				},
			}
		);

		const [getRankedThreads] = useManualQuery(getRankedThreadsQuery);
		const [getSectionThreads] = useManualQuery(getSectionThreadsQuery);
		const [getThreads] = useManualQuery(getThreadsQuery);

		useEffect(() => {
			setCurrentTag(tag ? slugToText(tag) : '');
		}, [tag]);

		useEffect(() => {
			const pageNumber = queryParams.get('page') || 1;
			setPage(isNaN(pageNumber) ? 1 : parseInt(pageNumber, 10));
		}, [queryParams]);

		useEffect(() => {
			setPageSize(queryParams.get('pagesize') || 20);
		}, [queryParams]);

		useEffect(() => {
			setSearch(queryParams.get('search') || '');
		}, [queryParams]);

		useEffect(() => {
			document.title = (section && section.title) || sectionTitle;
		}, [sectionTitle, section]);

		useEffect(() => {
			if (
				+context.rootTopicId === 0 &&
				location.pathname.endsWith('/' + context.rootTopicId)
			) {
				const fn =
					!context.rootTopicId || context.rootTopicId === '0'
						? getSections()
						: getSectionBySectionTitle().then(
								({data}) => data.messageBoardSections.items[0]
						  );

				fn.then((result) => ({
					...result,
					data: result.data.messageBoardSections,
				}))
					.then(({data}) => {
						setAllowCreateTopicInRootTopic(
							data.actions && !!data.actions.create
						);
					})
					.catch((error) => {
						if (process.env.NODE_ENV === 'development') {
							console.error(error);
						}
						setLoading(false);
						setError({message: 'Loading Topics', title: 'Error'});
					});
			}
		}, [
			context.rootTopicId,
			context.siteKey,
			location.pathname,
			getSectionBySectionTitle,
			getSections,
		]);

		useEffect(() => {
			setTotalCount(
				(filter === 'latest-edited' || !!search) &&
					questions.totalCount > MAX_NUMBER_OF_QUESTIONS
					? MAX_NUMBER_OF_QUESTIONS
					: questions.totalCount
			);
		}, [filter, questions.totalCount, search]);

		const getRankedThreadsCallback = useCallback(
			(dateModified, page = 1, pageSize = 20, section, sort = '') =>
				getRankedThreads({
					variables: {
						dateModified:
							dateModified && dateModified.toISOString(),
						messageBoardSectionId: section.id,
						page,
						pageSize,
						sort,
					},
				}).then((result) => ({
					...result,
					data: result.data.messageBoardThreadsRanked,
				})),
			[getRankedThreads]
		);

		const getThreadsCallback = useCallback(
			(
				creatorId = '',
				keywords = '',
				page = 1,
				pageSize = 30,
				search = '',
				section,
				siteKey,
				sort
			) => {
				if (
					!search &&
					!keywords &&
					!creatorId &&
					(!sort || sort === 'dateCreated:desc') &&
					!section.messageBoardSections.items.length &&
					section.id !== 0
				) {
					return getSectionThreads({
						variables: {
							messageBoardSectionId: section.id,
							page,
							pageSize,
						},
					}).then((result) => ({
						...result,
						data:
							result.data.messageBoardSectionMessageBoardThreads,
					}));
				}

				let filter = '';

				if (section && section.id) {
					filter = `(messageBoardSectionId eq ${section.id} `;

					for (
						let i = 0;
						i < section.messageBoardSections.items.length;
						i++
					) {
						filter += `or messageBoardSectionId eq ${section.messageBoardSections.items[i].id} `;
					}

					filter += ')';
				}

				if (keywords) {
					filter += `${
						(section && section.id && ' and ') || ''
					}keywords/any(x:x eq '${keywords}')`;
				}
				else if (creatorId) {
					filter += ` and creator/id eq ${creatorId}`;
				}

				sort = sort || 'dateCreated:desc';

				return getThreads({
					variables: {
						filter,
						page,
						pageSize,
						search,
						siteKey,
						sort,
					},
				}).then((result) => ({
					...result,
					data: result.data.messageBoardThreads,
				}));
			},
			[getSectionThreads, getThreads]
		);

		useEffect(() => {
			if (!page || !pageSize || search == null) {
				return;
			}

			if (!section || (section.id == null && !currentTag)) {
				return;
			}

			let fn;

			if (filter === 'latest-edited') {
				fn = getThreadsCallback(
					creatorId,
					currentTag,
					page,
					pageSize,
					search,
					section,
					siteKey,
					'dateModified:desc'
				);
			}
			else if (filter === 'week') {
				const date = new Date();
				date.setDate(date.getDate() - 7);

				fn = getRankedThreadsCallback(date, page, pageSize, section);
			}
			else if (filter === 'month') {
				const date = new Date();
				date.setDate(date.getDate() - 31);

				fn = getRankedThreadsCallback(date, page, pageSize, section);
			}
			else if (filter === 'most-voted') {
				fn = getRankedThreadsCallback(null, page, pageSize, section);
			}
			else {
				fn = getThreadsCallback(
					creatorId,
					currentTag,
					page,
					pageSize,
					search,
					section,
					siteKey,
					'dateCreated:desc'
				);
			}

			fn.then(({data}) => {
				setQuestions(data || []);
			})
				.catch((error) => {
					if (process.env.NODE_ENV === 'development') {
						console.error(error);
					}
					setError({message: 'Loading Questions', title: 'Error'});
				})
				.finally(() => setLoading(false));
		}, [
			creatorId,
			currentTag,
			filter,
			page,
			pageSize,
			search,
			section,
			siteKey,
			getRankedThreadsCallback,
			getThreadsCallback,
		]);

		const historyPushParser = historyPushWithSlug(history.push);

		function buildURL(search, page, pageSize) {
			let url = '/questions';

			if (sectionTitle || sectionTitle === '0') {
				url += `/${sectionTitle}`;
			}

			if (tag) {
				url += `/tag/${tag}`;
			}
			if (creatorId) {
				url += `/creator/${creatorId}`;
			}
			if (search) {
				url += `?search=${search}&`;
			}
			else {
				url += '?';
			}

			url += `page=${page}&pagesize=${pageSize}`;

			return url;
		}

		function changePage(search, page, pageSize) {
			historyPushParser(buildURL(search, page, pageSize));
		}

		const [debounceCallback] = useDebounceCallback(
			(search) => changePage(search, 1, 20),
			500
		);

		useEffect(() => {
			if (sectionTitle && sectionTitle !== '0') {
				const variables = {
					filter: `title eq '${slugToText(
						sectionTitle
					)}' or id eq '${slugToText(sectionTitle)}'`,
					siteKey: context.siteKey,
				};
				getSectionBySectionTitle({
					variables,
				}).then(({data}) => {
					if (data.messageBoardSections.items[0]) {
						setSection(data.messageBoardSections.items[0]);
						setSectionQuery(getSectionBySectionTitleQuery);
						setSectionQueryVariables(variables);
					}
					else {
						setSection(null);
						setError({message: 'Loading Topics', title: 'Error'});
						setLoading(false);
					}
				});
			}
			else if (sectionTitle === '0') {
				const variables = {siteKey: context.siteKey};
				getSections({
					variables,
				})
					.then(({data: {messageBoardSections}}) => ({
						actions: messageBoardSections.actions,
						id: 0,
						messageBoardSections,
						numberOfMessageBoardSections:
							messageBoardSections &&
							messageBoardSections.items &&
							messageBoardSections.items.length,
					}))
					.then((section) => {
						setSection(section);
						setSectionQuery(getSectionsQuery);
						setSectionQueryVariables(variables);
					});
			}
		}, [
			sectionTitle,
			context.siteKey,
			getSections,
			getSectionBySectionTitle,
		]);

		const filterOptions = getFilterOptions();

		function isVotedFilter(filter) {
			return (
				filter == 'month' || filter == 'most-voted' || filter == 'week'
			);
		}

		const navigateToNewQuestion = () => {
			if (context.redirectToLogin && !themeDisplay.isSignedIn()) {
				const baseURL = getBasePath();

				window.location.replace(
					`/c/portal/login?redirect=${baseURL}${
						context.historyRouterBasePath ? '' : '#'
					}/questions/${sectionTitle}/new`
				);
			}
			else {
				historyPushParser(`/questions/${sectionTitle}/new`);
			}

			return false;
		};

		return (
			<section className="questions-section questions-section-list">
				<Breadcrumb
					allowCreateTopicInRootTopic={allowCreateTopicInRootTopic}
					section={section}
				/>
				<div className="questions-container row">
					<div className="c-mt-3 col col-xl-12">
						<QuestionsNavigationBar />
					</div>

					{!!search && !loading && (
						<ResultsMessage
							maxNumberOfSearchResults={MAX_NUMBER_OF_QUESTIONS}
							searchCriteria={search}
							totalCount={totalCount}
						/>
					)}

					{!section && (
						<ClayEmptyState
							className="c-mx-auto c-px-0 col-xl-10"
							description={lang.sub(
								Liferay.Language.get(
									'the-link-you-followed-may-be-broken-or-the-topic-no-longer-exists'
								),
								[sectionTitle]
							)}
							imgSrc={
								context.includeContextPath +
								'/assets/empty_questions_list.png'
							}
							title={Liferay.Language.get(
								'the-topic-is-not-found'
							)}
						>
							<ClayButton
								displayType="primary"
								onClick={() => historyPushParser('/questions')}
							>
								{Liferay.Language.get('home')}
							</ClayButton>
						</ClayEmptyState>
					)}

					{section && (
						<div className="c-mx-auto c-px-0 col-xl-10">
							<PaginatedList
								activeDelta={pageSize}
								activePage={page}
								changeDelta={(pageSize) =>
									changePage(search, page, pageSize)
								}
								changePage={(page) =>
									changePage(search, page, pageSize)
								}
								data={questions}
								emptyState={
									sectionTitle &&
									!search &&
									!isVotedFilter(filter) ? (
										<ClayEmptyState
											description={Liferay.Language.get(
												'there-are-no-questions-inside-this-topic-be-the-first-to-ask-something'
											)}
											imgSrc={
												context.includeContextPath +
												'/assets/empty_questions_list.png'
											}
											title={Liferay.Language.get(
												'this-topic-is-empty'
											)}
										>
											{((context.redirectToLogin &&
												!themeDisplay.isSignedIn()) ||
												context.canCreateThread) && (
												<ClayButton
													displayType="primary"
													onClick={
														navigateToNewQuestion
													}
												>
													{Liferay.Language.get(
														'ask-question'
													)}
												</ClayButton>
											)}
										</ClayEmptyState>
									) : (
										<ClayEmptyState
											title={Liferay.Language.get(
												'there-are-no-results'
											)}
										/>
									)
								}
								loading={loading}
								totalCount={totalCount}
							>
								{(question) => (
									<QuestionRow
										currentSection={sectionTitle}
										key={question.id}
										question={question}
										showSectionLabel={
											!!section.numberOfMessageBoardSections
										}
									/>
								)}
							</PaginatedList>
							<ClayButton
								className="btn-monospaced d-block d-sm-none position-fixed questions-button shadow"
								displayType="primary"
								onClick={navigateToNewQuestion}
							>
								<ClayIcon symbol="pencil" />

								<span className="sr-only">
									{Liferay.Language.get('ask-question')}
								</span>
							</ClayButton>

							<Alert info={error} />
						</div>
					)}
				</div>
			</section>
		);

		function QuestionsNavigationBar() {
			return (
				<div className="d-flex flex-column flex-xl-row justify-content-between">
					<div className="align-items-center d-flex flex-grow-1">
						{section &&
							section.actions &&
							section.actions.subscribe && (
								<div className="c-ml-3">
									<SubscriptionButton
										isSubscribed={section.subscribed}
										onSubscription={() => {
											deleteCacheKey(
												sectionQuery,
												sectionQueryVariables
											);
											deleteCacheKey(
												getSubscriptionsQuery,
												{
													contentType:
														'MessageBoardSection',
												}
											);
										}}
										parentSection={section.parentSection}
										queryVariables={{
											messageBoardSectionId: section.id,
										}}
										showTitle={true}
										subscribeQuery={subscribeSectionQuery}
										unsubscribeQuery={
											unsubscribeSectionQuery
										}
									/>
								</div>
							)}
					</div>

					{((questions && questions.totalCount > 0) ||
						search ||
						filter) && (
						<div className="c-mt-3 c-mt-xl-0 d-flex flex-column flex-grow-1 flex-md-row">
							<ClayInput.Group className="justify-content-xl-end">
								<ClayInput.GroupItem shrink>
									<label
										className="align-items-center d-inline-flex m-0 text-secondary"
										htmlFor="questionsFilter"
									>
										{Liferay.Language.get('filter-by')}
									</label>
								</ClayInput.GroupItem>

								<ClayInput.GroupItem shrink>
									<ClaySelect
										className="bg-transparent border-0"
										disabled={loading}
										id="questionsFilter"
										onChange={(event) => {
											setLoading(true);
											setFilter(event.target.value);
										}}
										value={filter}
									>
										{filterOptions.map((option) => (
											<ClaySelect.Option
												key={option.value}
												label={option.label}
												title={option.title}
												value={option.value}
											/>
										))}
									</ClaySelect>
								</ClayInput.GroupItem>
							</ClayInput.Group>

							<ClayInput.Group className="c-mt-3 c-mt-md-0">
								<ClayInput.GroupItem>
									<ClayInput
										autoFocus={search ? true : false}
										className="bg-transparent form-control input-group-inset input-group-inset-after"
										defaultValue={
											(search && slugToText(search)) || ''
										}
										disabled={
											!search &&
											questions &&
											questions.items &&
											!questions.items.length
										}
										onChange={(event) =>
											debounceCallback(event.target.value)
										}
										placeholder={Liferay.Language.get(
											'search'
										)}
										type="text"
									/>

									<ClayInput.GroupInsetItem
										after
										className="bg-transparent"
										tag="span"
									>
										{loading && (
											<button
												className="btn btn-monospaced btn-unstyled"
												type="submit"
											>
												<ClayLoadingIndicator
													className="mb-0 mt-0"
													small
												/>
											</button>
										)}
										{!loading &&
											((!!search && (
												<ClayButtonWithIcon
													displayType="unstyled"
													onClick={() => {
														debounceCallback('');
													}}
													symbol="times-circle"
													type="submit"
												/>
											)) || (
												<ClayButtonWithIcon
													displayType="unstyled"
													symbol="search"
													type="search"
												/>
											))}
									</ClayInput.GroupInsetItem>
								</ClayInput.GroupItem>

								{sectionTitle &&
									questions &&
									questions.totalCount > 0 &&
									((context.redirectToLogin &&
										!themeDisplay.isSignedIn()) ||
										(section &&
											section.actions &&
											Boolean(
												section.actions['add-thread']
											)) ||
										context.canCreateThread) && (
										<ClayInput.GroupItem shrink>
											<ClayButton
												className="c-ml-3 d-none d-sm-block text-nowrap"
												displayType="primary"
												onClick={navigateToNewQuestion}
											>
												{Liferay.Language.get(
													'ask-question'
												)}
											</ClayButton>
										</ClayInput.GroupItem>
									)}
							</ClayInput.Group>
						</div>
					)}

					{section && (
						<Helmet>
							<title>{section.title}</title>
							<link
								href={`${getFullPath('questions')}${
									context.historyRouterBasePath ? '' : '#/'
								}questions/${sectionTitle}`}
								rel="canonical"
							/>
						</Helmet>
					)}
				</div>
			);
		}
	}
);
