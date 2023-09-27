/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	getThreadsQuery,
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

	const context = useContext(AppContext);
	const [getThreadsFiltered] = useManualQuery(getSectionThreadsQuery, {
		useCache: false,
	});
	const [getThreads] = useManualQuery(getThreadsQuery, {useCache: false});

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
		ALL_SECTIONS_ENABLED,
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

	const {
		buildParams,
		changePage,
		historyPushParser,
		navigateToNewQuestion,
	} = useQuestionsURLUtil({context, history, params});

	const getMbThreads = useCallback(
		async (params) => {
			const fetchAllSections = params.fetchAllSections;

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

			const fn = fetchAllSections ? getThreads : getThreadsFiltered;

			try {
				const {data} = await fn({
					variables: {
						...(fetchAllSections
							? {siteKey}
							: {messageBoardSectionId}),
						filter: filteredValues.filterBy,
						page,
						pageSize,
						search: _search,
						sort: filteredValues.sortBy || 'dateCreated:desc',
					},
				});

				const messageBoardThreads =
					(fetchAllSections
						? data?.messageBoardThreads
						: data?.messageBoardSectionMessageBoardThreads) || {};

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
		[
			getThreads,
			getThreadsFiltered,
			page,
			pageSize,
			search,
			section.id,
			siteKey,
			subscribedTags,
		]
	);

	useEffect(() => {
		if ((section.id || ALL_SECTIONS_ENABLED) && !tagLoading) {
			getMbThreads({
				fetchAllSections: ALL_SECTIONS_ENABLED,
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
		ALL_SECTIONS_ENABLED,
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
					buildParams={buildParams}
					changePage={changePage}
					creatorId={creatorId}
					error={error}
					historyPushParser={historyPushParser}
					urlParams={urlParams}
				/>
			</div>
		</section>
	);
});
