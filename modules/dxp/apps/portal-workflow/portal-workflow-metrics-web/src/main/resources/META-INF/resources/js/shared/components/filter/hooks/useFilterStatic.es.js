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

import {useEffect} from 'react';

import {useRouterParams} from '../../../hooks/useRouterParams.es';
import {handleFilterItems} from '../util/filterUtil.es';
import {useFilterState} from './useFilterState.es';

const useFilterStatic = (dispatch, filterKey, staticItems) => {
	const {filters} = useRouterParams();
	const {items, selectedItems, setItems} = useFilterState(
		dispatch,
		filterKey
	);

	useEffect(() => {
		const mappedItems = handleFilterItems(staticItems, filters[filterKey]);

		setItems(mappedItems);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [staticItems]);

	return {
		items,
		selectedItems
	};
};

export {useFilterStatic};
