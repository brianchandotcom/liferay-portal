/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Helmet} from 'react-helmet';

import SubscriptionButton from '../../../components/SubscriptionButton.es';
import {
	getSubscriptionsQuery,
	subscribeSectionQuery,
	unsubscribeSectionQuery,
} from '../../../utils/client.es';
import {ALL_SECTIONS_ID} from '../../../utils/contants.es';
import {deleteCacheKey, getFullPath} from '../../../utils/utils.es';
import AskQuestionButton from './AskQuestionButton.es';
import ManagementToolbar from './ManagementToolbar/ManagementToolbar.es';
import QuestionsFilter from './QuestionFilter.es';

const MAX_NUMBER_OF_QUESTIONS = 500;

const QuestionsNavigationBar = ({
	context,
	loading,
	navigateToNewQuestion,
	onSearch,
	page,
	pageSize,
	questions,
	resultBar,
	search,
	section,
	sectionQuery,
	sectionQueryVariables,
	sectionTitle,
	urlParams,
}) => {
	const canAddAThread =
		sectionTitle &&
		((context.redirectToLogin && !themeDisplay.isSignedIn()) ||
			(section &&
				section.actions &&
				Boolean(section.actions['add-thread'])) ||
			context.canCreateThread) &&
		sectionTitle !== ALL_SECTIONS_ID;

	return (
		<>
			{section && (
				<Helmet>
					<title>{section.title}</title>

					<link
						href={`${getFullPath('questions')}${
							context.historyRouterBasePath ? '' : '#/'
						}questions/${sectionTitle}?page=${page}&pagesize=${pageSize}`}
						rel="canonical"
					/>
				</Helmet>
			)}

			<ManagementToolbar
				filterAndOrder={
					<QuestionsFilter
						onApplyFilter={(params) => {
							onSearch(search, params);
						}}
						urlParams={urlParams}
					/>
				}
				keywords={search}
				loading={loading}
				maxNumberOfSearchResults={MAX_NUMBER_OF_QUESTIONS}
				onClear={() =>
					onSearch('', {filterBy: '', sortBy: '', taggedWith: ''})
				}
				onSearch={onSearch}
				plusButton={
					canAddAThread && (
						<AskQuestionButton
							navigateToNewQuestion={navigateToNewQuestion}
						/>
					)
				}
				resultBar={resultBar}
				search={search}
				subscribeButton={
					section &&
					section.actions &&
					section.actions.subscribe && (
						<div className="c-mr-2">
							<SubscriptionButton
								isSubscribed={section.subscribed}
								onSubscription={() => {
									deleteCacheKey(
										sectionQuery,
										sectionQueryVariables
									);
									deleteCacheKey(getSubscriptionsQuery, {
										contentType: 'MessageBoardSection',
									});
								}}
								parentSection={section.parentSection}
								queryVariables={{
									messageBoardSectionId: section.id,
								}}
								showTitle={false}
								subscribeQuery={subscribeSectionQuery}
								unsubscribeQuery={unsubscribeSectionQuery}
							/>
						</div>
					)
				}
				totalResults={questions.totalCount}
			/>
		</>
	);
};

export default QuestionsNavigationBar;
