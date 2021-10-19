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

	const request = method == 'post' ? fetchPost : fetch;

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
