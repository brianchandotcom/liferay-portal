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

import {AppContext} from '../../../../components/AppContext.es';
import {useRouterParams} from '../../../hooks/useRouterParams.es';
import {handleFilterItems, mergeItemsArray} from '../util/filterUtil.es';
import {useFilterState} from './useFilterState.es';

const useFilterFetch = (
	dispatch,
	filterKey,
	prefixKey,
	requestUrl,
	staticItems = []
) => {
	const {client} = useContext(AppContext);
	const {filters} = useRouterParams();

	const prefixedFilterKey = `${prefixKey}${filterKey}`;
	const {items, selectedItems, setItems} = useFilterState(
		dispatch,
		prefixedFilterKey
	);

	useEffect(
		() => {
			client.get(requestUrl).then(({data = {}}) => {
				const mergedItems = mergeItemsArray(staticItems, data.items);

				const mappedItems = handleFilterItems(
					mergedItems,
					filters[prefixedFilterKey]
				);

				setItems(mappedItems);
			});
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	return {
		items,
		selectedItems
	};
};

export {useFilterFetch};
