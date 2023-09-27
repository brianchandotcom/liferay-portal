/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
