/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import {Question} from '../questions/Question.es';

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
				useCache: false,
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

		const addSectionToQuestion = (question) => {
			return {
				...question,
				messageBoardSection:
					question?.messageBoardThread?.messageBoardSection,
			};
		};

		useEffect(() => {
			if (data) {
				setCurrentQuestion(data?.messageBoardMessages?.items[0]);
			}
		}, [data]);

		const sectionTitleQuestion =
			data?.messageBoardMessages?.items[0]?.messageBoardThread
				.messageBoardSection.title;

		const hasActivities = totalCount > 0;

		return (
			<section className="d-flex justify-content-between p-0 questions-section questions-section-list user-activity-page">
				<div
					className={classNames('activity-panel pl-5 pr-3', {
						'col-xl-8': hasActivities,
						'col-xl-12': !hasActivities,
					})}
				>
					<h2 className="py-2">
						{Liferay.Language.get('latest-activity')}
					</h2>

					<PaginatedList
						activeDelta={pageSize}
						activePage={page}
						changeDelta={(pageSize) => changePage(page, pageSize)}
						changePage={(page) => changePage(page, pageSize)}
						data={data && data.messageBoardMessages}
						emptyState={
							<ClayEmptyState
								aria-label={Liferay.Language.get(
									'there-are-no-results'
								)}
								description={Liferay.Language.get(
									'sorry,-no-results-were-found'
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
						hidden
						loading={loading}
						totalCount={totalCount}
					>
						{(question) => (
							<ActivityQuestionRow
								context={context}
								creatorId={question.creator?.id}
								currentSection={
									context.useTopicNamesInURL
										? question.messageBoardThread
												.messageBoardSection
										: (question.messageBoardThread
												.messageBoardSection &&
												question.messageBoardThread
													.messageBoardSection.id) ||
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
								question={addSectionToQuestion(question)}
								rowSelected={currentQuestion?.friendlyUrlPath}
							/>
						)}
					</PaginatedList>
				</div>

				{hasActivities && (
					<div className="activity-panel border-left col-xl-4 modal-body">
						<Question
							display={{
								actions: false,
								activity: true,
								addAnswer: false,
								breadcrumb: false,
								flags: false,
								preview: true,
								rating: false,
								showAnswer: false,
								showSignature: true,
								styled: true,
							}}
							history={history}
							questionId={currentQuestion?.friendlyUrlPath}
							sectionTitle={sectionTitleQuestion}
							url={url}
						/>
					</div>
				)}
			</section>
		);
	}
);
