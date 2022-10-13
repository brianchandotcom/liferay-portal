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

import {useEffect, useState} from 'react';

import useQueryParams from '../../../hooks/useQueryParams.es';

const useQuestionsURLParameters = (location) => {
	const queryParams = useQueryParams(location);
	const [params, setParams] = useState({
		filterBy: null,
		page: null,
		pageSize: null,
		search: null,
		selectedTags: null,
		sortBy: null,
		taggedWith: null,
	});

	useEffect(() => {
		const pageNumber = queryParams.get('page') || 1;
		const params = {
			filterBy: queryParams.get('filterby') || '',
			page: isNaN(pageNumber) ? 1 : parseInt(pageNumber, 10),
			pageSize: queryParams.get('pagesize') || 20,
			search: queryParams.get('search') || '',
			selectedTags: queryParams.get('selectedtags')
				? queryParams
						.get('selectedtags')
						.trim()
						.split(',')
						.map((tag) => ({label: tag, value: tag}))
				: [],
			sortBy: queryParams.get('sortby') || '',
			taggedWith: queryParams.get('taggedwith') || '',
		};

		setParams(params);
	}, [queryParams]);

	return params;
};

export default useQuestionsURLParameters;
