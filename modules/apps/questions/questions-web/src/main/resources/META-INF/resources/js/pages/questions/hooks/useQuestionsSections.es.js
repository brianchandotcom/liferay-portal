/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useManualQuery} from 'graphql-hooks';
import {useContext, useEffect, useState} from 'react';

import {AppContext} from '../../../AppContext.es';
import {
	getMessageBoardSectionByFriendlyUrlPathQuery,
	getSectionBySectionTitleQuery,
	getSectionsQuery,
} from '../../../utils/client.es';
import {ALL_SECTIONS_ID} from '../../../utils/contants.es';

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
		getMessageBoardSectionByFriendlyUrlPathQuery,
		{
			variables: {
				filter: sectionTitle,
				siteKey: context.siteKey,
			},
		}
	);

	useEffect(() => {
		if (sectionTitle && sectionTitle !== ALL_SECTIONS_ID) {
			const variables = {
				friendlyUrlPath: sectionTitle,
				siteKey: Number(context.siteKey),
			};
			getSectionBySectionTitle({
				variables,
			}).then(({data}) => {
				if (data.messageBoardSectionByFriendlyUrlPath) {
					setSection(data.messageBoardSectionByFriendlyUrlPath);
					setSectionQuery(getSectionBySectionTitleQuery);
					setSectionQueryVariables(variables);
				}
				else {
					setSection(null);
					setError({message: 'Loading Topics', title: 'Error'});
					setLoading(false);
				}
			});
		}
		else if (ALL_SECTIONS_ENABLED) {
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
