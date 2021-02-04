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

import React, {useCallback, useState} from 'react';

import SearchContext, {
	reducer,
} from '../../src/main/resources/META-INF/resources/js/components/management-toolbar/SearchContext.es';

export default ({dispatch = jest.fn(), children, defaultQuery = {}}) => {
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
};
