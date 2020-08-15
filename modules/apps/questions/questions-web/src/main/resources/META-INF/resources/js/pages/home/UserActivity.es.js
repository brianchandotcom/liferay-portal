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
		const historyPushParser = historyPushWithSlug(history.push);
		const queryParams = useQueryParams(location);
		const siteKey = context.siteKey;
		const [page, setPage] = useState(1);
		const [pageSize, setPageSize] = useState(20);

		useEffect(() => {
			const pageNumber = queryParams.get('page') || 1;
			setPage(isNaN(pageNumber) ? 1 : parseInt(pageNumber, 10));
		}, [queryParams]);

		useEffect(() => {
			setPageSize(queryParams.get('pagesize') || 20);
		}, [queryParams]);

		const {data, loading} = useQuery(getUserActivityQuery, {
			variables: {
				filter: `creatorId eq ${creatorId}`,
				page,
				pageSize,
				siteKey,
			},
		});

		let creatorInfo = {
			id: creatorId,
			image: null,
			name: decodeURI(
				JSON.parse(`"${Liferay.ThemeDisplay.getUserName()}"`)
			),
			postsNumber: 0,
			rank: context.defaultRank,
		};

		if (
			data &&
			data.messageBoardThreads.items &&
			data.messageBoardThreads.items.length
		) {
			const {
				creator,
				creatorStatistics,
			} = data.messageBoardThreads.items[0];

			creatorInfo = {
				id: creatorId,
				image: creator.image,
				name: creator.name,
				postsNumber: creatorStatistics.postsNumber,
				rank: creatorStatistics.rank,
			};
		}

		const changePage = (page, pageSize) => {
			historyPushParser(
				`/activity/${creatorId}?page=${page}&pagesize=${pageSize}`
			);
		};

		return (
			<section className="questions-section questions-section-list">
				<div className="questions-container">
					<div className="c-p-5 row">
						<PageHeader />
						<Questions />
					</div>
				</div>
			</section>
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
					<PaginatedList
						activeDelta={pageSize}
						activePage={page}
						changeDelta={(pageSize) => changePage(page, pageSize)}
						changePage={(page) => changePage(page, pageSize)}
						data={data && data.messageBoardThreads}
						loading={loading}
					>
						{(question) => (
							<QuestionRow
								key={question.id}
								question={question}
								showSectionLabel={true}
							/>
						)}
					</PaginatedList>
				</div>
			);
		}
	}
);
