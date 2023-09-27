/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useState} from 'react';

import SearchContext, {
	reducer,
} from '../../src/main/resources/META-INF/resources/js/components/management-toolbar/SearchContext';

export default function SearchContextProviderWrapper({
	dispatch = jest.fn(),
	children,
	defaultQuery = {},
}) {
	const [query, setQuery] = useState(defaultQuery);

	const defaultCallback = useCallback(
		(action) => {
			dispatch(action);
			setQuery(reducer(query, action));
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[query, setQuery]
	);

	return (
		<SearchContext.Provider value={[query, defaultCallback]}>
			{children}
		</SearchContext.Provider>
	);
}
