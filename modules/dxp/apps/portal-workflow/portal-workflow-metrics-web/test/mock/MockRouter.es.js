/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {isValidElement, useMemo, useState} from 'react';
import {MemoryRouter, Route, Routes} from 'react-router';

import {AppContext} from '../../src/main/resources/META-INF/resources/js/components/AppContext.es';
import {FilterContextProvider} from '../../src/main/resources/META-INF/resources/js/shared/components/filter/FilterContext.es';

const MockRouter = ({
	children,
	initialPath = '/1/20/title%3Aasc',
	initialReindexStatuses = [],
	isAmPm,
	path = '/:page/:pageSize/:sort',
	query = '?backPath=%2F',
	userId = '1',
	userName = 'Test Test',
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

		[reindexStatuses, title, fetchDateModified, isAmPm, userId, userName]
	);

	const initialEntries = useMemo(
		() => [`${initialPath}${query}`],
		[initialPath, query]
	);

	const content = useMemo(() => {
		if (!children) {
			return null;
		}

		if (isValidElement(children)) {
			return children;
		}

		if (typeof children === 'function') {
			const Component = children;

			return <Component />;
		}

		return children;
	}, [children]);

	return (
		<AppContext.Provider value={contextState}>
			<FilterContextProvider>
				<MemoryRouter initialEntries={initialEntries}>
					<Routes>
						<Route element={content} path={path} />
					</Routes>
				</MemoryRouter>
			</FilterContextProvider>
		</AppContext.Provider>
	);
};

export {MockRouter};
