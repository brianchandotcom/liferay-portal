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
import classNames from 'classnames';
import {useManualQuery} from 'graphql-hooks';
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import ActivityQuestionRow from '../../components/ActivityQuestionRow.es';
import PaginatedList from '../../components/PaginatedList.es';
import useQueryParams from '../../hooks/useQueryParams.es';
import {getUserActivityQuery} from '../../utils/client.es';
import {historyPushWithSlug} from '../../utils/utils.es';
import {Question} from '../questions/Question.new.es';

export default withRouter(
	({
		history,
		location,
		match: {
			params: {creatorId},
			url,
		},
	}) => {
		const [currentQuestion, setCurrentQuestion] = useState(null);
		const [loading, setLoading] = useState(true);
		const [page, setPage] = useState(null);
		const [pageSize, setPageSize] = useState(null);
		const [totalCount, setTotalCount] = useState(0);
		const context = useContext(AppContext);
		const queryParams = useQueryParams(location);
		const siteKey = context.siteKey;

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

			fetchUserActivity().then(({data}) => {
				setTotalCount(data?.messageBoardMessages.totalCount || 0);
				setLoading(false);
			});
		}, [fetchUserActivity, page, pageSize]);

		const historyPushParser = historyPushWithSlug(history.push);

		function buildUrl(page, pageSize) {
			return `/questions/activity/${creatorId}?page=${page}&pagesize=${pageSize}`;
		}

		function changePage(page, pageSize) {
			historyPushParser(buildUrl(page, pageSize));
		}

		useEffect(() => {
			if (data) {
				setCurrentQuestion(data?.messageBoardMessages?.items[0]);
			}
		}, [data]);

		const sectionTitleQuestion =
			data?.messageBoardMessages?.items[0]?.messageBoardThread
				.messageBoardSection.title;

		return (
			<section className="questions-section questions-section-list">
				<div className="c-p-5 questions-container row">
					<div className="c-mt-3 c-mx-auto c-px-0 w-100">
						<div className="c-mt-5 container d-flex flex-row">
							<h2>{Liferay.Language.get('latest-activity')}</h2>
						</div>
					</div>

					<div
						className={classNames(
							'border-top container d-flex flex-row',
							{
								'justify-content-between': currentQuestion,
								'justify-content-center': !currentQuestion,
							}
						)}
					>
						<div
							className={classNames('panel-from-activity', {
								'col-xl-7': currentQuestion,
								'col-xl-12': !currentQuestion,
							})}
						>
							<PaginatedList
								activeDelta={pageSize}
								activePage={page}
								changeDelta={(pageSize) =>
									changePage(page, pageSize)
								}
								changePage={(page) =>
									changePage(page, pageSize)
								}
								data={data && data.messageBoardMessages}
								emptyState={
									<ClayEmptyState
										description={Liferay.Language.get(
											'there-is-are-no-new-activities'
										)}
										imgSrc={
											context.includeContextPath +
											'/assets/empty_questions_activity.png'
										}
										title={null}
									/>
								}
								hidden
								loading={loading}
								totalCount={totalCount}
							>
								{(question) => (
									<ActivityQuestionRow
										context={context}
										currentSection={
											context.useTopicNamesInURL
												? question.messageBoardThread
														.messageBoardSection
												: (question.messageBoardThread
														.messageBoardSection &&
														question
															.messageBoardThread
															.messageBoardSection
															.id) ||
												  context.rootTopicId
										}
										key={question.id}
										linkProps={{
											id: 'user-activity-row',
											onClick: (event) => {
												event.preventDefault();

												setCurrentQuestion(question);
											},
										}}
										question={{
											...question,
											messageBoardSection:
												question?.messageBoardThread
													?.messageBoardSection,
										}}
										rowSelected={
											currentQuestion?.friendlyUrlPath
										}
										showSectionLabel={true}
									/>
								)}
							</PaginatedList>
						</div>

						{currentQuestion && (
							<div className="border-left c-p-4 col-xl-5 modal-body panel-from-activity">
								<Question
									display={{
										actions: false,
										addAnswer: false,
										breadcrumb: false,
										kebab: true,
										rating: false,
										styled: true,
										tabs: true,
									}}
									history={history}
									questionId={currentQuestion.friendlyUrlPath}
									sectionTitle={sectionTitleQuestion}
									url={url}
								/>
							</div>
						)}
					</div>
				</div>
			</section>
		);
	}
);
