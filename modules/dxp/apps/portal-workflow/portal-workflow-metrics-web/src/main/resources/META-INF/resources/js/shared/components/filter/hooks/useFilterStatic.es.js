/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

import {buildFilterItems, getCapitalizedFilterKey} from '../util/filterUtil.es';
import {useFilterState} from './useFilterState.es';

const useFilterStatic = ({
	filterKey,
	prefixKey,
	propertyKey,
	staticItems,
	withoutRouteParams,
}) => {
	const {items, selectedItems, selectedKeys, setItems} = useFilterState(
		getCapitalizedFilterKey(prefixKey, filterKey),
		withoutRouteParams
	);

	useEffect(() => {
		const mappedItems = buildFilterItems({
			items: staticItems,
			propertyKey,
			selectedKeys,
		});

		setItems(mappedItems);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedKeys, staticItems]);

	return {
		items,
		selectedItems,
	};
};

export {useFilterStatic};
