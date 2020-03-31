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
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Error from '../../components/Error.es';
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

		const [creatorInfo, setCreatorInfo] = useState({});
		const [error, setError] = useState({});
		const [loading, setLoading] = useState(true);
		const [page, setPage] = useState(1);
		const [questions, setQuestions] = useState([]);

		useEffect(() => {
			getUserActivity(siteKey, creatorId)
				.then(questions => {
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
				.catch(_ => {
					setLoading(false);
					setError({message: 'Loading Questions', title: 'Error'});
				});
		}, [creatorId, siteKey]);

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
						questions.items.map(question => (
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
					<Error error={error} />
				</>
			);
		}
	}
);
