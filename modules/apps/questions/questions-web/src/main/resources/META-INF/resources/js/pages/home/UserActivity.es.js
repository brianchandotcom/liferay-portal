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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import PaginatedList from '../../components/PaginatedList.es';
import QuestionRow from '../../components/QuestionRow.es';
import UserIcon from '../../components/UserIcon.es';
import useQuery from '../../hooks/useQuery.es';
import {getUserActivity} from '../../utils/client.es';
import {historyPushWithSlug} from '../../utils/utils.es';
import NavigationBar from '../NavigationBar.es';

export default withRouter(
	({
		history,
		location,
		match: {
			params: {creatorId},
		},
	}) => {
		const context = useContext(AppContext);
		const defaultPostsNumber = 0;
		const defaultRank = context.defaultRank;
		const historyPushParser = historyPushWithSlug(history.push);
		const queryParams = useQuery(location);
		const siteKey = context.siteKey;

		const [creatorInfo, setCreatorInfo] = useState({});
		const [loading, setLoading] = useState(true);
		const [page, setPage] = useState(1);
		const [pageSize, setPageSize] = useState(20);
		const [questions, setQuestions] = useState([]);

		useEffect(() => {
			const pageNumber = queryParams.get('page') || 1;
			setPage(isNaN(pageNumber) ? 1 : parseInt(pageNumber, 10));
		}, [queryParams]);

		useEffect(() => {
			getUserActivity(page, pageSize, siteKey, creatorId)
				.then((questions) => {
					const creatorBasicInfo = questions.items[0].creator;
					const creatorStatistics =
						questions.items[0].creatorStatistics;
					const creatorInfo = {
						id: creatorId,
						image: creatorBasicInfo.image,
						name: creatorBasicInfo.name,
						postsNumber: creatorStatistics.postsNumber,
						rank: creatorStatistics.rank,
					};
					setCreatorInfo(creatorInfo);
					setQuestions(questions);
					setLoading(false);
				})
				.catch((_) => {
					setLoading(false);
					setCreatorInfo(getCreatorDefaultInfo(creatorId));
				});
		}, [creatorId, getCreatorDefaultInfo, page, pageSize, siteKey]);

		const getCreatorDefaultInfo = useCallback(
			(creatorId) => ({
				id: creatorId,
				image: null,
				name: decodeURI(
					JSON.parse(`"${Liferay.ThemeDisplay.getUserName()}"`)
				),
				postsNumber: defaultPostsNumber,
				rank: defaultRank,
			}),
			[defaultPostsNumber, defaultRank]
		);

		const changePage = (number) => {
			historyPushParser(`/activity/${creatorId}?page=${number}`);
		};

		return (
			<>
				<NavigationBar />
				<section className="questions-section questions-section-list">
					<div className="questions-container">
						<div className="c-p-5 row">
							<PageHeader />
							<Questions />
						</div>
					</div>
				</section>
			</>
		);

		function PageHeader() {
			return (
				<div className="c-mt-3 c-mx-auto c-px-0 col-xl-10">
					<div className="d-flex flex-row">
						<div className="c-mt-3">
							<UserIcon
								fullName={creatorInfo.name}
								portraitURL={creatorInfo.image}
								size="xl"
								userId={String(creatorInfo.id)}
							/>
						</div>
						<div className="c-ml-4 flex-column">
							<div>
								<span className="small">
									Rank: {creatorInfo.rank}
								</span>
							</div>
							<div>
								<strong className="h2">
									{creatorInfo.name}
								</strong>
							</div>
							<div>
								<span className="small">
									Posts: {creatorInfo.postsNumber}
								</span>
							</div>
						</div>
						<div className="flex-column justify-content-end">
							<ClayButton
								className="d-none"
								displayType="secondary"
							>
								Manage Subscriptions
							</ClayButton>
						</div>
					</div>
					<div className="border-bottom c-mt-5">
						<h2>Latest Questions Asked</h2>
					</div>
				</div>
			);
		}

		function Questions() {
			return (
				<div className="c-mx-auto c-px-0 col-xl-10">
					{loading ? (
						<ClayLoadingIndicator />
					) : (
						<PaginatedList
							activeDelta={pageSize}
							activePage={page}
							changeDelta={setPageSize}
							changePage={changePage}
							data={questions}
						>
							{(question) => (
								<QuestionRow
									key={question.id}
									question={question}
								/>
							)}
						</PaginatedList>
					)}
				</div>
			);
		}
	}
);
