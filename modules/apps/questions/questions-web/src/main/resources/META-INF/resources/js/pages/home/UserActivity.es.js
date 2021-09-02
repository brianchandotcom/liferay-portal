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

import ClayEmptyState from '@clayui/empty-state';
import {useManualQuery} from 'graphql-hooks';
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import PaginatedList from '../../components/PaginatedList.es';
import QuestionRow from '../../components/QuestionRow.es';
import UserIcon from '../../components/UserIcon.es';
import useQueryParams from '../../hooks/useQueryParams.es';
import {getUserActivityQuery} from '../../utils/client.es';
import {historyPushWithSlug} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		location,
		match: {
			params: {creatorId},
		},
	}) => {
		const context = useContext(AppContext);
		const queryParams = useQueryParams(location);
		const siteKey = context.siteKey;
		const [loading, setLoading] = useState(true);
		const [page, setPage] = useState(null);
		const [pageSize, setPageSize] = useState(null);
		const [totalCount, setTotalCount] = useState(0);
		const [userInfo, setUserInfo] = useState({
			id: creatorId,
			image: null,
			name: decodeURI(
				JSON.parse(`"${Liferay.ThemeDisplay.getUserName()}"`)
			),
			postsNumber: 0,
			rank: context.defaultRank,
		});

		useEffect(() => {
			const pageNumber = queryParams.get('page') || 1;
			setPage(isNaN(pageNumber) ? 1 : parseInt(pageNumber, 10));
		}, [queryParams]);

		useEffect(() => {
			setPageSize(queryParams.get('pagesize') || 20);
		}, [queryParams]);

		useEffect(() => {
			document.title = creatorId;
		}, [creatorId]);

		const [fetchUserActivity, {data}] = useManualQuery(
			getUserActivityQuery,
			{
				variables: {
					filter: `creatorId eq ${creatorId}`,
					page,
					pageSize,
					siteKey,
				},
			}
		);

		useEffect(() => {
			if (!page || !pageSize) {
				return;
			}

			setLoading(true);

			fetchUserActivity().then(({data, loading}) => {
				if (data.messageBoardMessages.items.length) {
					const {
						creator,
						creatorStatistics,
					} = data.messageBoardMessages.items[0];
					setUserInfo({
						id: creator.id,
						image: creator.image,
						name: creator.name,
						postsNumber: creatorStatistics.postsNumber,
						rank: creatorStatistics.rank,
					});
				}
				setTotalCount(data?.messageBoardMessages.totalCount || 0);
				setLoading(loading);
			});
		}, [fetchUserActivity, page, pageSize]);

		const historyPushParser = historyPushWithSlug(history.push);

		function buildUrl(page, pageSize) {
			return `/questions/activity/${creatorId}?page=${page}&pagesize=${pageSize}`;
		}

		function changePage(page, pageSize) {
			historyPushParser(buildUrl(page, pageSize));
		}

		const addSectionToQuestion = (question) => {
			return {
				messageBoardSection:
					question.messageBoardThread.messageBoardSection,
				...question,
			};
		};

		return (
			<section className="questions-section questions-section-list">
				<div className="c-p-5 questions-container row">
					<div className="c-mt-3 c-mx-auto c-px-0 col-xl-10">
						<div className="d-flex flex-row">
							<div className="c-mt-3">
								<UserIcon
									fullName={userInfo.name}
									portraitURL={userInfo.image}
									size="xl"
									userId={String(userInfo.id)}
								/>
							</div>
							<div className="c-ml-4 flex-column">
								<div>
									<span className="small">
										{Liferay.Language.get('rank')}:{' '}
										{userInfo.rank}
									</span>
								</div>
								<div>
									<strong className="h2">
										{userInfo.name}
									</strong>
								</div>
								<div>
									<span className="small">
										{Liferay.Language.get('posts')}:{' '}
										{userInfo.postsNumber}
									</span>
								</div>
							</div>
						</div>
						<div className="border-bottom c-mt-5">
							<h2>
								{Liferay.Language.get('latest-questions-asked')}
							</h2>
						</div>
					</div>
					<div className="c-mx-auto c-px-0 col-xl-10">
						<PaginatedList
							activeDelta={pageSize}
							activePage={page}
							changeDelta={(pageSize) =>
								changePage(page, pageSize)
							}
							changePage={(page) => changePage(page, pageSize)}
							data={data && data.messageBoardMessages}
							emptyState={
								<ClayEmptyState
									description={Liferay.Language.get(
										'sorry-there-are-no-results-found'
									)}
									imgSrc={
										context.includeContextPath +
										'/assets/empty_questions_list.png'
									}
									title={Liferay.Language.get(
										'there-are-no-results'
									)}
								/>
							}
							loading={loading}
							totalCount={totalCount}
						>
							{(question) => (
								<QuestionRow
									currentSection={
										context.useTopicNamesInURL
											? question.messageBoardThread
													.messageBoardSection &&
											  question.messageBoardThread
													.messageBoardSection.title
											: (question.messageBoardThread
													.messageBoardSection &&
													question.messageBoardThread
														.messageBoardSection
														.id) ||
											  context.rootTopicId
									}
									key={question.id}
									question={addSectionToQuestion(question)}
									showSectionLabel={true}
								/>
							)}
						</PaginatedList>
					</div>
				</div>
			</section>
		);
	}
);
