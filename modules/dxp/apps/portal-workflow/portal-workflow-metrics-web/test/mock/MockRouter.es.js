/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {createMemoryHistory} from 'history';
import React, {cloneElement, useMemo, useState} from 'react';
import {Route, Router} from 'react-router-dom';

import {AppContext} from '../../src/main/resources/META-INF/resources/js/components/AppContext.es';

const withParamsMock = (...components) => ({
	history,
	location: {search: query},
	match: {params: routeParams}
}) => {
	return components.map(component => {
		if (routeParams.sort) {
			routeParams.sort = decodeURIComponent(routeParams.sort);
		}

		return cloneElement(component, {
			...routeParams,
			history,
			query,
			routeParams
		});
	});
};

const MockRouter = ({
	children,
	client,
	initialPath = '/1/20/title%3Aasc',
	path = '/:page/:pageSize/:sort',
	query = '?backPath=%2F',
	withRouterProps = true
}) => {
	const [title, setTitle] = useState(null);
	const [status, setStatus] = useState(null);

	const contextState = useMemo(
		() => ({
			client,
			defaultDelta: 20,
			deltaValues: [5, 10, 20, 30, 50, 75],
			getClient: () => client,
			maxPages: 3,
			namespace: 'workflow_',
			setStatus,
			setTitle,
			status,
			title
		}),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	const initialEntries = useMemo(
		() => [{pathname: initialPath, search: query}],
		[initialPath, query]
	);

	const history = useMemo(
		() => createMemoryHistory({initialEntries, keyLength: 0}),
		[initialEntries]
	);

	const component = withRouterProps
		? withParamsMock(children)
		: () => cloneElement(children);

	return (
		<Router history={history}>
			<AppContext.Provider value={contextState}>
				<Route path={path} render={component} />
			</AppContext.Provider>
		</Router>
	);
};

export {MockRouter};
