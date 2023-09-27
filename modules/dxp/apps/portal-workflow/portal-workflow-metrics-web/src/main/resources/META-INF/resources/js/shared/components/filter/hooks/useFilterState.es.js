/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo, useState} from 'react';

import {useFilter} from '../../../hooks/useFilter.es';
import {useRouterParams} from '../../../hooks/useRouterParams.es';
import {buildFallbackItems} from '../util/filterEvents.es';

const useFilterState = (prefixedKey, withoutRouteParams) => {
	const {dispatchFilter, filterValues} = useFilter({withoutRouteParams});

	const {filters} = useRouterParams();
	const [items, setItems] = useState([]);

	const selectedKeys = withoutRouteParams
		? filterValues[prefixedKey]
		: filters[prefixedKey];

	const selectedItems = useMemo(() => {
		let selectedItems = buildFallbackItems(selectedKeys) || [];

		if (items.length && selectedKeys) {
			selectedItems = items.filter((item) =>
				selectedKeys.includes(item.key)
			);
		}

		if (!withoutRouteParams) {
			dispatchFilter(prefixedKey, selectedItems);
		}

		return selectedItems;
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items, selectedKeys]);

	return {items, selectedItems, selectedKeys, setItems};
};

export {useFilterState};
