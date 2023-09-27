/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useEffect} from 'react';

import {useFetch} from '../../../hooks/useFetch.es';
import {usePost} from '../../../hooks/usePost.es';
import {FilterContext} from '../FilterContext.es';
import {
	buildFilterItems,
	getCapitalizedFilterKey,
	mergeItemsArray,
} from '../util/filterUtil.es';
import {useFilterState} from './useFilterState.es';

const useFilterFetch = ({
	filterKey,
	formatItem,
	labelPropertyName = 'label',
	prefixKey,
	requestBody: body = {},
	propertyKey,
	requestMethod: method = 'get',
	requestParams: params = {},
	requestUrl: url,
	staticData,
	staticItems,
	withoutRouteParams,
}) => {
	const {dispatchFilterError} = useContext(FilterContext);
	const {items, selectedItems, selectedKeys, setItems} = useFilterState(
		getCapitalizedFilterKey(prefixKey, filterKey),
		withoutRouteParams
	);

	const parseResponse = (data = {}) => {
		data?.items.sort((current, next) =>
			current[labelPropertyName]?.localeCompare(next[labelPropertyName])
		);

		const mergedItems = mergeItemsArray(staticItems, data?.items);

		const mappedItems = buildFilterItems({
			formatItem,
			items: mergedItems,
			propertyKey,
			selectedKeys,
		});

		setItems(mappedItems);
	};

	const {fetchData: fetch} = useFetch({callback: parseResponse, params, url});

	const {postData: fetchPost} = usePost({
		body,
		callback: parseResponse,
		params,
		url,
	});

	const request = method === 'post' ? fetchPost : fetch;

	useEffect(
		() => {
			dispatchFilterError(filterKey, true);

			if (staticData) {
				parseResponse({items: staticData});
			}
			else {
				request().catch(() => dispatchFilterError(filterKey));
			}
		},

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	return {items, selectedItems};
};

export {useFilterFetch};
