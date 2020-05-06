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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Error from '../../components/Error.es';
import QuestionRow from '../../components/QuestionRow.es';
import useQuery from '../../hooks/useQuery.es';
import {getRankedThreads, getThreads} from '../../utils/client.es';
import {historyPushWithSlug, slugToText} from '../../utils/utils.es';
import QuestionsNavigationBar from '../QuestionsNavigationBar.es';

export default withRouter(
	({
		history,
		location,
		match: {
			params: {creatorId, sectionTitle, tag},
		},
	}) => {
		const [currentTag, setCurrentTag] = useState('');
		const [error, setError] = useState({});
		const [loading, setLoading] = useState(true);
		const [page, setPage] = useState(1);
		const [pageSize] = useState(20);
		const [questions, setQuestions] = useState([]);
		const [search, setSearch] = useState('');
		const [section, setSection] = useState({});

		const queryParams = useQuery(location);

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
			const searchParameter = queryParams.get('search') || '';
			setSearch(searchParameter);
		}, [queryParams]);

		useEffect(() => {
			if (section.id) {
				renderQuestions(loadThreads());
			}
		}, [
			creatorId,
			page,
			pageSize,
			section,
			search,
			siteKey,
			currentTag,
			loadThreads,
		]);

		const renderQuestions = (questions) => {
			questions
				.then((data) => setQuestions(data || []))
				.then(() => setLoading(false))
				.catch((error) => {
					if (process.env.NODE_ENV === 'development') {
						console.error(error);
					}
					setLoading(false);
					setError({message: 'Loading Questions', title: 'Error'});
				});
		};

		const loadThreads = useCallback(
			(sort) =>
				getThreads({
					creatorId,
					keywords: currentTag,
					page,
					pageSize,
					search,
					section,
					siteKey,
					sort,
				}),
			[creatorId, currentTag, page, pageSize, search, section, siteKey]
		);

		const filterChange = (type) => {
			if (type === 'latest-edited') {
				renderQuestions(loadThreads('dateModified:desc'));
			}
			else if (type === 'week') {
				const date = new Date();
				date.setDate(date.getDate() - 7);

				renderQuestions(
					getRankedThreads(date, page, pageSize, section)
				);
			}
			else if (type === 'month') {
				const date = new Date();
				date.setDate(date.getDate() - 31);

				renderQuestions(
					getRankedThreads(date, page, pageSize, section)
				);
			}
			else {
				renderQuestions(loadThreads('dateCreated:desc'));
			}
		};

		const loadSearch = (search) => {
			historyPushParser(
				`/questions/${sectionTitle}${
					search && search !== '' ? '?search=' + search : ''
				}`
			);
		};

		const changePage = (number) => {
			setPage(number);
			historyPushParser(
				`/questions/${context.section}${tag ? '/tag/' + tag : ''}${
					search && search !== '' ? '?search=' + search + '&' : '?'
				}page=${number}`
			);
		};

		return (
			<section className="questions-section questions-section-list">
				<div className="questions-container">
					<div className="row">
						<div className="c-mt-3 col col-xl-12">
							<QuestionsNavigationBar
								filterChange={filterChange}
								searchChange={loadSearch}
								sectionChange={(section) => setSection(section)}
							/>
						</div>

						<div className="c-mt-5 c-mx-auto c-px-0 col-xl-10">
							{loading ? (
								<ClayLoadingIndicator />
							) : (
								questions.items &&
								questions.items.map((question) => (
									<QuestionRow
										key={question.id}
										question={question}
									/>
								))
							)}

							{!!questions.totalCount &&
								questions.totalCount > questions.pageSize && (
									<ClayPaginationWithBasicItems
										activePage={page}
										ellipsisBuffer={2}
										onPageChange={changePage}
										totalPages={Math.ceil(
											questions.totalCount /
												questions.pageSize
										)}
									/>
								)}
							<Error error={error} />
						</div>
					</div>
				</div>
			</section>
		);
	}
);
