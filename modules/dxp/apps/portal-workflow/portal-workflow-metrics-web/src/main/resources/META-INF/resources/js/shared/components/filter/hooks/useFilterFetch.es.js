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

import {useCallback, useContext, useEffect} from 'react';

import {AppContext} from '../../../../components/AppContext.es';
import {FilterContext} from '../FilterContext.es';
import {
	buildFilterItems,
	getCapitalizedFilterKey,
	mergeItemsArray,
} from '../util/filterUtil.es';
import {useFilterState} from './useFilterState.es';

const useFilterFetch = ({
	filterKey,
	prefixKey,
	requestBody: data = {},
	requestMethod: method = 'get',
	requestParams: params = {},
	requestUrl: url,
	staticItems,
	withoutRouteParams,
}) => {
	const {client} = useContext(AppContext);
	const {dispatchFilterError} = useContext(FilterContext);
	const {items, selectedItems, selectedKeys, setItems} = useFilterState(
		getCapitalizedFilterKey(prefixKey, filterKey),
		withoutRouteParams
	);

	const fetchCallback = useCallback(
		({data = {}}) => {
			const mergedItems = mergeItemsArray(staticItems, data.items);
			const mappedItems = buildFilterItems(mergedItems, selectedKeys);

			setItems(mappedItems);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[selectedKeys, staticItems]
	);

	useEffect(
		() => {
			dispatchFilterError(filterKey, true);

			client
				.request({data, method, params, url})
				.then(fetchCallback)
				.catch(() => {
					dispatchFilterError(filterKey);
				});
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	return {
		items,
		selectedItems,
	};
};

export {useFilterFetch};
