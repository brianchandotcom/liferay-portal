/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createMemoryHistory} from 'history';
import React, {cloneElement, useMemo, useState} from 'react';
import {Route, Router} from 'react-router-dom';

import {AppContext} from '../../src/main/resources/META-INF/resources/js/components/AppContext.es';
import {FilterContextProvider} from '../../src/main/resources/META-INF/resources/js/shared/components/filter/FilterContext.es';

const withParamsMock = (...components) => ({
	history,
	location: {search: query},
	match: {params: routeParams},
}) => {
	return components.map((component, key) => {
		if (routeParams.sort) {
			routeParams.sort = decodeURIComponent(routeParams.sort);
		}

		return cloneElement(component, {
			...routeParams,
			history,
			key,
			query,
			routeParams,
		});
	});
};

const MockRouter = ({
	children,
	initialPath = '/1/20/title%3Aasc',
	initialReindexStatuses = [],
	isAmPm,
	path = '/:page/:pageSize/:sort',
	query = '?backPath=%2F',
	userId = '1',
	userName = 'Test Test',
	withoutRouterProps,
}) => {
	const [title, setTitle] = useState(null);
	const [reindexStatuses, setReindexStatuses] = useState(
		initialReindexStatuses
	);
	const [fetchDateModified, setFetchDateModified] = useState(false);

	const contextState = useMemo(
		() => ({
			defaultDelta: 20,
			deltaValues: [5, 10, 20, 30, 50, 75],
			fetchDateModified,
			isAmPm,
			maxPages: 3,
			portletNamespace: 'workflow',
			reindexStatuses,
			setFetchDateModified,
			setReindexStatuses,
			setTitle,
			title,
			userId,
			userName,
		}),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[reindexStatuses, title]
	);

	const initialEntries = useMemo(
		() => [{pathname: initialPath, search: query}],
		[initialPath, query]
	);

	const history = useMemo(
		() => createMemoryHistory({initialEntries, keyLength: 0}),
		[initialEntries]
	);

	const component = withoutRouterProps
		? () => cloneElement(children)
		: withParamsMock(children);

	return (
		<Router history={history}>
			<AppContext.Provider value={contextState}>
				<FilterContextProvider>
					<Route path={path} render={component} />
				</FilterContextProvider>
			</AppContext.Provider>
		</Router>
	);
};

export {MockRouter};
