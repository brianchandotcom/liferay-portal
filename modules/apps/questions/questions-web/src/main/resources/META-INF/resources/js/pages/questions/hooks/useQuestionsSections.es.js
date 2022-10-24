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

import {useManualQuery} from 'graphql-hooks';
import {useContext, useEffect, useState} from 'react';

import {AppContext} from '../../../AppContext.es';
import {
	getSectionBySectionTitleQuery,
	getSectionsQuery,
} from '../../../utils/client.es';
import {ALL_SECTIONS_ID} from '../../../utils/contants.es';
import {slugToText} from '../../../utils/utils.es';

const useQuestionsSections = ({
	location,
	sectionTitle,
	setError,
	setLoading,
}) => {
	const [
		allowCreateTopicInRootTopic,
		setAllowCreateTopicInRootTopic,
	] = useState(false);
	const [section, setSection] = useState({});
	const [sectionQuery, setSectionQuery] = useState('');
	const [sectionQueryVariables, setSectionQueryVariables] = useState({});

	const ALL_SECTIONS_ENABLED = sectionTitle === ALL_SECTIONS_ID;

	const context = useContext(AppContext);

	const [getSections] = useManualQuery(getSectionsQuery, {
		variables: {siteKey: context.siteKey},
	});

	const [getSectionBySectionTitle] = useManualQuery(
		getSectionBySectionTitleQuery,
		{
			variables: {
				filter: `title eq '${slugToText(
					sectionTitle
				)}' or id eq '${slugToText(sectionTitle)}'`,
				siteKey: context.siteKey,
			},
		}
	);

	useEffect(() => {
		if (sectionTitle && sectionTitle !== ALL_SECTIONS_ID) {
			const variables = {
				filter: `title eq '${slugToText(
					sectionTitle
				)}' or id eq '${slugToText(sectionTitle)}'`,
				siteKey: context.siteKey,
			};
			getSectionBySectionTitle({
				variables,
			}).then(({data}) => {
				if (data.messageBoardSections.items[0]) {
					setSection(data.messageBoardSections.items[0]);
					setSectionQuery(getSectionBySectionTitleQuery);
					setSectionQueryVariables(variables);
				} else {
					setSection(null);
					setError({message: 'Loading Topics', title: 'Error'});
					setLoading(false);
				}
			});
		} else if (ALL_SECTIONS_ENABLED) {
			const variables = {siteKey: context.siteKey};
			getSections({
				variables,
			})
				.then(({data: {messageBoardSections}}) => ({
					actions: messageBoardSections.actions,
					id: 0,
					messageBoardSections,
					numberOfMessageBoardSections:
						messageBoardSections &&
						messageBoardSections.items &&
						messageBoardSections.items.length,
				}))
				.then((section) => {
					setSection(section);
					setSectionQuery(getSectionsQuery);
					setSectionQueryVariables(variables);
				});
		}
	}, [
		sectionTitle,
		context.siteKey,
		getSections,
		getSectionBySectionTitle,
		setError,
		setLoading,
		ALL_SECTIONS_ENABLED,
	]);

	useEffect(() => {
		if (
			+context.rootTopicId === 0 &&
			location.pathname.endsWith('/' + context.rootTopicId)
		) {
			const fn =
				!context.rootTopicId || context.rootTopicId === '0'
					? getSections()
					: getSectionBySectionTitle().then(
							({data}) => data.messageBoardSections.items[0]
					  );

			fn.then((result) => ({
				...result,
				data: result.data.messageBoardSections,
			}))
				.then(({data}) => {
					setAllowCreateTopicInRootTopic(
						data.actions && !!data.actions.create
					);
				})
				.catch((error) => {
					if (process.env.NODE_ENV === 'development') {
						console.error(error);
					}
					setLoading(false);
					setError({message: 'Loading Topics', title: 'Error'});
				});
		}
	}, [
		context.rootTopicId,
		context.siteKey,
		location.pathname,
		getSectionBySectionTitle,
		getSections,
		setLoading,
		setError,
	]);

	useEffect(() => {
		document.title =
			sectionTitle === ALL_SECTIONS_ID
				? Liferay.Language.get('all-questions')
				: (section && section.title) || sectionTitle;
	}, [sectionTitle, section]);

	return {
		ALL_SECTIONS_ENABLED,
		allowCreateTopicInRootTopic,
		section,
		sectionQuery,
		sectionQueryVariables,
	};
};

export default useQuestionsSections;
