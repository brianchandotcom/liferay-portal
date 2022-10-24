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
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import React from 'react';

import Alert from '../../../components/Alert.es';
import PaginatedList from '../../../components/PaginatedList.es';
import QuestionRow from '../../../components/QuestionRow.es';
import lang from '../../../utils/lang.es';
import {getFullPath} from '../../../utils/utils.es';

const QuestionList = ({
	changePage,
	context,
	creatorId,
	error,
	historyPushParser,
	loading,
	navigateToNewQuestion,
	page,
	pageSize,
	questions,
	search,
	section,
	sectionTitle,
	urlParams = {},
}) => {
	if (section) {
		return (
			<div className="c-mx-auto c-px-0 col-xl-10 pt-2">
				<PaginatedList
					activeDelta={pageSize}
					activePage={page}
					changeDelta={(pageSize) =>
						changePage({...urlParams, page, pageSize, search})
					}
					changePage={(page) =>
						changePage({...urlParams, page, pageSize, search})
					}
					data={questions}
					emptyState={
						sectionTitle && !search ? (
							<ClayEmptyState
								description={Liferay.Language.get(
									'there-are-no-questions-inside-this-topic-be-the-first-to-ask-something'
								)}
								imgSrc={
									context.includeContextPath +
									'/assets/empty_questions_list.png'
								}
								title={Liferay.Language.get(
									'this-topic-is-empty'
								)}
							>
								{((context.redirectToLogin &&
									!themeDisplay.isSignedIn()) ||
									context.canCreateThread) && (
									<ClayButton
										displayType="primary"
										onClick={navigateToNewQuestion}
									>
										{Liferay.Language.get('ask-question')}
									</ClayButton>
								)}
							</ClayEmptyState>
						) : (
							<ClayEmptyState
								title={Liferay.Language.get(
									'there-are-no-results'
								)}
							/>
						)
					}
					hrefConstructor={(page) =>
						`${getFullPath('questions')}${
							context.historyRouterBasePath ? '' : '#/'
						}questions/${sectionTitle}?page=${page}&pagesize=${pageSize}`
					}
					loading={loading}
					totalCount={questions.totalCount}
				>
					{(question) => (
						<QuestionRow
							context={context}
							creatorId={creatorId}
							currentSection={sectionTitle}
							key={question.id}
							question={question}
							showSectionLabel={
								!!section.numberOfMessageBoardSections
							}
						/>
					)}
				</PaginatedList>

				<ClayButton
					className="btn-monospaced d-block d-sm-none position-fixed questions-button shadow"
					displayType="primary"
					onClick={navigateToNewQuestion}
				>
					<ClayIcon symbol="pencil" />

					<span className="sr-only">
						{Liferay.Language.get('ask-question')}
					</span>
				</ClayButton>

				<Alert info={error} />
			</div>
		);
	}

	return (
		<ClayEmptyState
			className="c-mx-auto c-px-0 col-xl-10"
			description={lang.sub(
				Liferay.Language.get(
					'the-link-you-followed-may-be-broken-or-the-topic-no-longer-exists'
				),
				[sectionTitle]
			)}
			imgSrc={
				context.includeContextPath + '/assets/empty_questions_list.png'
			}
			title={Liferay.Language.get('the-topic-is-not-found')}
		>
			<ClayButton
				displayType="primary"
				onClick={() => historyPushParser('/questions')}
			>
				{Liferay.Language.get('home')}
			</ClayButton>
		</ClayEmptyState>
	);
};

export default QuestionList;
