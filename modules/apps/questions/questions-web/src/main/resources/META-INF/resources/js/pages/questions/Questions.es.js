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
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Alert from '../../components/Alert.es';
import Breadcrumb from '../../components/Breadcrumb.es';
import PaginatedList from '../../components/PaginatedList.es';
import QuestionRow from '../../components/QuestionRow.es';
import ResultsMessage from '../../components/ResultsMessage.es';
import SectionSubscription from '../../components/SectionSubscription.es';
import useQueryParams from '../../hooks/useQueryParams.es';
import {getQuestionThreads, getSections} from '../../utils/client.es';
import {
	getBasePath,
	historyPushWithSlug,
	slugToText,
	useDebounceCallback,
} from '../../utils/utils.es';

function getFilterOptions() {
	return [
		{
			label: Liferay.Language.get('latest-created'),
			value: 'latest-created',
		},
		{
			label: Liferay.Language.get('latest-edited'),
			value: 'latest-edited',
		},
		{
			label: Liferay.Language.get('voted-in-the-last-week'),
			value: 'week',
		},
		{
			label: Liferay.Language.get('voted-in-the-last-month'),
			value: 'month',
		},
		{
			label: Liferay.Language.get('most-voted'),
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
		const [currentTag, setCurrentTag] = useState('');
		const [error, setError] = useState({});
		const [filter, setFilter] = useState();
		const [loading, setLoading] = useState(true);
		const [page, setPage] = useState(1);
		const [pageSize, setPageSize] = useState(20);
		const [questions, setQuestions] = useState([]);
		const [search, setSearch] = useState('');
		const [section, setSection] = useState({});
		const [totalCount, setTotalCount] = useState(0);

		const queryParams = useQueryParams(location);

		const context = useContext(AppContext);

		const siteKey = context.siteKey;

		const historyPushParser = historyPushWithSlug(history.push);

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
			setTotalCount(
				(filter === 'latest-edited' || !!search) &&
					questions.totalCount > MAX_NUMBER_OF_QUESTIONS
					? MAX_NUMBER_OF_QUESTIONS
					: questions.totalCount
			);
		}, [filter, questions.totalCount, search]);

		useEffect(() => {
			if (section.id == null && !currentTag) {
				return;
			}

			getQuestionThreads(
				creatorId,
				filter,
				currentTag,
				page,
				pageSize,
				search,
				section,
				siteKey
			)
				.then(({data, loading}) => {
					setQuestions(data || []);
					setLoading(loading);
				})
				.catch((error) => {
					if (process.env.NODE_ENV === 'development') {
						console.error(error);
					}
					setLoading(false);
					setError({message: 'Loading Questions', title: 'Error'});
				});
		}, [
			creatorId,
			currentTag,
			filter,
			page,
			pageSize,
			search,
			section,
			siteKey,
		]);

		function buildURL(search, page, pageSize) {
			let url = '/questions';

			if (sectionTitle) {
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

		const changePage = (page, pageSize) => {
			historyPushParser(buildURL(search, page, pageSize));
		};

		const [debounceCallback] = useDebounceCallback((search) => {
			setLoading(true);
			historyPushParser(buildURL(search, 1, 20));
		}, 500);

		useEffect(() => {
			if (sectionTitle) {
				getSections(slugToText(sectionTitle), context.siteKey).then(
					setSection
				);
			}
		}, [sectionTitle, context.siteKey]);

		const filterOptions = getFilterOptions();

		const navigateToNewQuestion = () => {
			if (context.redirectToLogin && !themeDisplay.isSignedIn()) {
				const baseURL = getBasePath();

				window.location.replace(
					`/c/portal/login?redirect=${baseURL}#/questions/${sectionTitle}/new`
				);
			}
			else {
				historyPushParser(`/questions/${sectionTitle}/new`);
			}

			return false;
		};

		return (
			<section className="questions-section questions-section-list">
				<Breadcrumb section={section} />
				<div className="questions-container">
					<div className="row">
						<div className="c-mt-3 col col-xl-12">
							<QuestionsNavigationBar />
						</div>

						{!!search && !loading && (
							<ResultsMessage
								maxNumberOfSearchResults={
									MAX_NUMBER_OF_QUESTIONS
								}
								searchCriteria={search}
								totalCount={totalCount}
							/>
						)}

						<div className="c-mx-auto c-px-0 col-xl-10">
							<PaginatedList
								activeDelta={pageSize}
								activePage={page}
								changeDelta={(pageSize) =>
									changePage(page, pageSize)
								}
								changePage={(page) =>
									changePage(page, pageSize)
								}
								data={questions}
								emptyState={
									!search && !filter ? (
										<ClayEmptyState
											description="There are no questions inside this topic, be the first to ask something!"
											imgSrc={
												context.includeContextPath +
												'/assets/empty_questions_list.png'
											}
											title="This topic is empty."
										>
											<ClayButton
												displayType="primary"
												onClick={navigateToNewQuestion}
											>
												{Liferay.Language.get(
													'ask-question'
												)}
											</ClayButton>
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

							<Alert info={error} />
						</div>
					</div>
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
									<SectionSubscription section={section} />
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
												value={option.value}
											/>
										))}
									</ClaySelect>
								</ClayInput.GroupItem>
							</ClayInput.Group>

							<ClayInput.Group className="c-mt-3 c-mt-md-0">
								<ClayInput.GroupItem>
									<ClayInput
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
														debounceCallback('')
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
									(context.redirectToLogin ||
										(section &&
											section.actions &&
											section.actions['add-thread'])) && (
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

											<ClayButton
												className="btn-monospaced d-block d-sm-none position-fixed questions-button shadow"
												displayType="primary"
												onClick={navigateToNewQuestion}
											>
												<ClayIcon symbol="pencil" />

												<span className="sr-only">
													{Liferay.Language.get(
														'ask-question'
													)}
												</span>
											</ClayButton>
										</ClayInput.GroupItem>
									)}
							</ClayInput.Group>
						</div>
					)}
				</div>
			);
		}
	}
);
