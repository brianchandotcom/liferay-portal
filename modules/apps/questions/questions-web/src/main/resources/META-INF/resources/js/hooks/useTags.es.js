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

import {AppContext} from '../AppContext.es';
import useQueryParams from '../hooks/useQueryParams.es';
import {
	getTagsOrderByDateCreatedQuery,
	getTagsOrderByNumberOfUsagesQuery,
} from '../utils/client.es';
import {historyPushWithSlug, useDebounceCallback} from '../utils/utils.es';

const useTags = ({history, location}) => {
	const [tags, setTags] = useState([]);
	const [tagsSubscribed, setTagsSubscribed] = useState([]);
	const context = useContext(AppContext);
	const queryParams = useQueryParams(location);

	const [error, setError] = useState({});
	const [searchBoxValue, setSearchBoxValue] = useState('');
	const [loading, setLoading] = useState(true);
	const [orderBy, setOrderBy] = useState('number-of-usages');
	const [page, setPage] = useState(null);
	const [pageSize, setPageSize] = useState(null);
	const [search, setSearch] = useState(null);

	const [tagsByDate] = useManualQuery(getTagsOrderByDateCreatedQuery, {
		variables: {page, pageSize, search, siteKey: context.siteKey},
	});

	const [tagsByRank] = useManualQuery(getTagsOrderByNumberOfUsagesQuery, {
		variables: {page, pageSize, search, siteKey: context.siteKey},
	});

	function tagsItemsSelected() {
		return {
			items: tagsSubscribed,
			lastPage: tags?.lastPage,
			page: tags?.page,
			pageSize: tags?.pageSize,
			totalCount: tags?.totalCount,
		};
	}

	function getOrderByOptions() {
		return [
			{
				label: Liferay.Language.get('latest-created'),
				value: 'latest-created',
			},
			{
				label: Liferay.Language.get('number-of-usages'),
				value: 'number-of-usages',
			},
		];
	}

	useEffect(() => {
		if (!page || !pageSize || search === null || search === undefined) {
			return;
		}

		const fn =
			orderBy === 'latest-created'
				? tagsByDate().then(({data, loading}) => ({
						data: data.keywords,
						loading,
				  }))
				: tagsByRank().then(({data, loading}) => ({
						data: data.keywordsRanked,
						loading,
				  }));
		fn.then(({data, loading}) => {
			setTags(data || []);
			setLoading(loading);
			setSearchBoxValue(search);

			data?.items?.forEach((item) => {
				if (item.subscribed) {
					setTagsSubscribed((tagsSubscribed) => [
						...tagsSubscribed,
						item,
					]);
				}
			});
		}).catch((_) => setError({message: 'Loading Tags', title: 'Error'}));

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		orderBy,
		page,
		pageSize,
		search,
		context.siteKey,
		tagsByDate,
		tagsByRank,
	]);

	useEffect(() => {
		document.title = 'Tags';
	}, []);

	useEffect(() => {
		setPage(+queryParams.get('page') || 1);
	}, [queryParams]);

	useEffect(() => {
		setPageSize(+queryParams.get('pagesize') || 20);
	}, [queryParams]);

	useEffect(() => {
		setSearch(queryParams.get('search') || '');
	}, [queryParams]);

	const historyPushParser = historyPushWithSlug(history.push);

	function buildURL(search, page, pageSize) {
		let url = '/tags?';

		if (search) {
			url += `search=${search}&`;
		}

		url += `page=${page}&pagesize=${pageSize}`;

		return url;
	}

	function changePage(search, page, pageSize) {
		historyPushParser(buildURL(search, page, pageSize));
	}

	const orderByOptions = getOrderByOptions();

	const [debounceCallback] = useDebounceCallback(
		(search) => changePage(search, 1, 20),
		500
	);

	return {
		changePage,
		context,
		debounceCallback,
		error,
		loading,
		orderBy,
		orderByOptions,
		page,
		pageSize,
		search,
		searchBoxValue,
		setLoading,
		setOrderBy,
		setPage,
		setPageSize,
		setSearch,
		setSearchBoxValue,
		tags,
		tagsItemsSelected,
	};
};

export default useTags;
