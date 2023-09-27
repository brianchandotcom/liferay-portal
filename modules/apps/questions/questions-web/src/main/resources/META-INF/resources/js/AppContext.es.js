/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from 'graphql-hooks';
import React, {createContext, useEffect, useState} from 'react';

import {
	client,
	getSectionsQuery,
	hasListPermissionsQuery,
} from './utils/client.es';

const AppContext = createContext({});

const AppContextProvider = ({children, ...context}) => {
	const [canCreateThread, setCanCreateThread] = useState(false);
	const {data: {messageBoardSections} = {}} = useQuery(getSectionsQuery, {
		variables: {siteKey: context.siteKey},
	});
	const [questionsVisited, setQuestionsVisited] = useState([]);

	useEffect(() => {
		client
			.request({
				query: hasListPermissionsQuery,
				variables: {
					siteKey: context.siteKey,
				},
			})
			.then(({data: {messageBoardThreads}}) => {
				setCanCreateThread(
					Boolean(messageBoardThreads.actions['create'])
				);
			});
	}, [context.siteKey]);

	return (
		<AppContext.Provider
			value={{
				...context,
				canCreateThread,
				questionsVisited,
				sections: messageBoardSections?.items || [],
				setQuestionsVisited,
			}}
		>
			{children}
		</AppContext.Provider>
	);
};

export {AppContext, AppContextProvider};
