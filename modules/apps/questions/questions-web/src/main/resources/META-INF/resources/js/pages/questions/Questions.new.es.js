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

import {useManualQuery, useQuery} from 'graphql-hooks';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Breadcrumb from '../../components/Breadcrumb.es';
import {
	getSectionThreadsQuery,
	getTagsOrderByDateCreatedQuery,
} from '../../utils/client.es';
import {getFilterValues} from './components/QuestionFilter.es';
import QuestionList from './components/QuestionList.es';
import QuestionsNavigationBar from './components/QuestionNavigationBar.es';
import useQuestionsSections from './hooks/useQuestionsSections.es';
import useQuestionsURLParameters from './hooks/useQuestionsURLParameters.es';
import useQuestionsURLUtil from './hooks/useQuestionsURLUtil.es';

const MAX_NUMBER_OF_QUESTIONS = 500;

export default withRouter(({history, location, match: {params}}) => {
	const {creatorId, sectionTitle} = params;

	const context = useContext(AppContext);
	const [getThreadsFiltered] = useManualQuery(getSectionThreadsQuery);
	const siteKey = context.siteKey;

	const {data, loading: tagLoading} = useQuery(
		getTagsOrderByDateCreatedQuery,
		{
			useCache: false,
			variables: {
				filter: 'subscribed eq true',
				page: 1,
				pageSize: 20,
				siteKey,
			},
		}
	);

	const subscribedTags = useMemo(() => data?.keywords?.items || [], [data]);

	const [error, setError] = useState({});
	const [loading, setLoading] = useState(true);
	const [questions, setQuestions] = useState({});
	const [resultBar, setResultBar] = useState({});

	const {
		allowCreateTopicInRootTopic,
		section,
		sectionQuery,
		sectionQueryVariables,
	} = useQuestionsSections({
		location,
		sectionTitle,
		setError,
		setLoading,
	});
	const urlParams = useQuestionsURLParameters(location);

	const {
		filterBy,
		page,
		pageSize,
		search,
		selectedTags,
		sortBy,
		taggedWith,
	} = urlParams;

	const {
		changePage,
		historyPushParser,
		navigateToNewQuestion,
	} = useQuestionsURLUtil({context, history, params});

	const getMbThreads = useCallback(
		async (params) => {
			const filteredValues = getFilterValues(
				{
					filterBy: params.filterBy,
					sortBy: params.sortBy,
					taggedWith: params.taggedWith,
				},
				params.taggedWith === 'my-watched-tags'
					? subscribedTags.map((tag) => ({
							label: tag.name,
							value: tag.name,
					  }))
					: params.selectedTags
			);

			const {
				resultBar = filteredValues?.resultBar,
				messageBoardSectionId = section.id,
				search: _search = search,
			} = params;

			if (resultBar) {
				setResultBar(resultBar);
			}

			try {
				const {data} = await getThreadsFiltered({
					variables: {
						filter: filteredValues.filterBy,
						messageBoardSectionId,
						page,
						pageSize,
						search: _search,
						sort: filteredValues.sortBy,
					},
				});

				const messageBoardThreads =
					data?.messageBoardSectionMessageBoardThreads || {};

				setQuestions({
					...messageBoardThreads,
					totalCount:
						messageBoardThreads.totalCount > MAX_NUMBER_OF_QUESTIONS
							? MAX_NUMBER_OF_QUESTIONS
							: messageBoardThreads.totalCount,
				});
			}
			catch (error) {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
				setError({
					message: 'Loading Questions',
					title: 'Error',
				});
			}

			setLoading(false);
		},
			siteKey,
			subscribedTags,
	);

	useEffect(() => {
		if (section.id) {
			getMbThreads({
				filterBy,
				messageBoardSectionId: section.id,
				search,
				selectedTags,
				sortBy,
				taggedWith,
			});
		}
	}, [
		filterBy,
		sortBy,
		getMbThreads,
		selectedTags,
		taggedWith,
		search,
		section.id,
		tagLoading,
	]);

	const commonProps = {
		context,
		loading,
		navigateToNewQuestion,
		page,
		pageSize,
		questions,
		search,
		section,
		sectionTitle,
	};

	return (
		<section className="questions-section questions-section-list">
			<Breadcrumb
				allowCreateTopicInRootTopic={allowCreateTopicInRootTopic}
				section={section}
			/>

			<div className="questions-container row">
				<div className="col-xl-12 mb-4">
					<QuestionsNavigationBar
						{...commonProps}
						onSearch={(searchText, params = {}) =>
							changePage({
								...urlParams,
								search: searchText,
								selectedTags: urlParams.selectedTags?.map(
									({value}) => value
								),
								...params,
							})
						}
						resultBar={resultBar}
						sectionQuery={sectionQuery}
						sectionQueryVariables={sectionQueryVariables}
						urlParams={urlParams}
					/>
				</div>

				<QuestionList
					{...commonProps}
					changePage={changePage}
					creatorId={creatorId}
					error={error}
					historyPushParser={historyPushParser}
				/>
			</div>
		</section>
	);
});
