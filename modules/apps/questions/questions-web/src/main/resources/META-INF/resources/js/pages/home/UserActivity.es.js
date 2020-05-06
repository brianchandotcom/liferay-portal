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
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
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
		const [questions, setQuestions] = useState([]);

		useEffect(() => {
			const pageNumber = queryParams.get('page') || 1;
			setPage(isNaN(pageNumber) ? 1 : parseInt(pageNumber, 10));
		}, [queryParams]);

		useEffect(() => {
			getUserActivity({page, siteKey, userId: creatorId})
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
		}, [creatorId, getCreatorDefaultInfo, page, siteKey]);

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
				<PageHeader />
				<Questions />
			</>
		);

		function PageHeader() {
			return (
				<>
					<div className="d-flex flex-row justify-content-between">
						<div className="d-flex">
							<UserIcon
								fullName={creatorInfo.name}
								portraitURL={creatorInfo.image}
								userId={String(creatorInfo.id)}
							/>
							<div className="c-ml-3 flex-column">
								<div>
									<span className="h3">
										Rank: {creatorInfo.rank}
									</span>
								</div>
								<div>
									<span className="h3">
										{creatorInfo.name}
									</span>
								</div>
								<div>
									<span className="h3">
										Posts: {creatorInfo.postsNumber}
									</span>
								</div>
							</div>
						</div>
						<div>
							<ClayButton
								className="d-none"
								displayType="secondary"
							>
								Manage Subscriptions
							</ClayButton>
						</div>
					</div>
					<div>
						<h1>Latest Questions Asked</h1>
					</div>
				</>
			);
		}

		function Questions() {
			return (
				<>
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
									questions.totalCount / questions.pageSize
								)}
							/>
						)}
				</>
			);
		}
	}
);
