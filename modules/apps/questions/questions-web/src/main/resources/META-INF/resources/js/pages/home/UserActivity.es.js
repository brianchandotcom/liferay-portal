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
import {getUserActivity} from '../../utils/client.es';
import NavigationBar from '../NavigationBar.es';

export default withRouter(
	({
		match: {
			params: {creatorId},
		},
	}) => {
		const context = useContext(AppContext);
		const siteKey = context.siteKey;
		const defaultPostsNumber = 0;
		const defaultRank = context.defaultRank;

		const [creatorInfo, setCreatorInfo] = useState({});
		const [loading, setLoading] = useState(true);
		const [page, setPage] = useState(1);
		const [questions, setQuestions] = useState([]);

		useEffect(() => {
			getUserActivity(siteKey, creatorId)
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
		}, [creatorId, getCreatorDefaultInfo, siteKey]);

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

		return (
			<>
				<NavigationBar />
				<section className="questions-section questions-section-list">
					<div className="questions-container">
						<div className="row c-p-5">
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
								userId={String(creatorInfo.id)}
								size="xl"
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
					<div className="c-mt-5 border-bottom">
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
								onPageChange={setPage}
								totalPages={Math.ceil(
									questions.totalCount / questions.pageSize
								)}
							/>
						)}
				</div>
			);
		}
	}
);
