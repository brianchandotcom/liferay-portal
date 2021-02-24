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

import {useQuery} from '@apollo/client';
import ClayEmptyState from '@clayui/empty-state';
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import PaginatedList from '../../components/PaginatedList.es';
import QuestionRow from '../../components/QuestionRow.es';
import UserIcon from '../../components/UserIcon.es';
import useQueryParams from '../../hooks/useQueryParams.es';
import {getUserActivityQuery} from '../../utils/client.es';
import {isWebCrawler} from '../../utils/utils.es';

export default withRouter(
	({
		location,
		match: {
			params: {creatorId},
		},
	}) => {
		const context = useContext(AppContext);
		const queryParams = useQueryParams(location);
		const siteKey = context.siteKey;
		const [page, setPage] = useState(1);
		const [pageSize, setPageSize] = useState(20);
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

		const {data, loading} = useQuery(getUserActivityQuery, {
			onCompleted(data) {
				if (data.messageBoardMessages.items.lenght) {
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
			},
			variables: {
				filter: `creatorId eq ${creatorId}`,
				page,
				pageSize,
				siteKey,
			},
		});

		const hrefConstructor = (page) =>
			`${
				isWebCrawler() ? '/-' : '#'
			}/activity/${creatorId}?page=${page}&pagesize=${pageSize}`;

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
							changeDelta={setPageSize}
							data={data && data.messageBoardMessages}
							emptyState={
								<ClayEmptyState
									imgSrc={
										context.includeContextPath +
										'/assets/empty_questions_list.png'
									}
								/>
							}
							hrefConstructor={hrefConstructor}
							loading={loading}
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
