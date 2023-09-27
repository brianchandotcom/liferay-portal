/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import Filter from '../../shared/components/filter/Filter.es';
import {useFilterName} from '../../shared/components/filter/hooks/useFilterName.es';
import {useFilterStatic} from '../../shared/components/filter/hooks/useFilterStatic.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import {getVelocityUnits} from './util/velocityUnitUtil.es';

export default function VelocityUnitFilter({
	disabled,
	className,
	filterKey = filterConstants.velocityUnit.key,
	options = {},
	prefixKey = '',
	timeRange,
}) {
	options = {
		hideControl: true,
		multiple: false,
		withSelectionTitle: true,
		withoutRouteParams: false,
		...options,
	};
	const velocityUnits = useMemo(() => getVelocityUnits(timeRange), [
		timeRange,
	]);

	const {items, selectedItems} = useFilterStatic({
		filterKey,
		prefixKey,
		staticItems: velocityUnits,
		...options,
	});

	const defaultItem = useMemo(
		() => items.find((item) => item.defaultVelocityUnit) || items[0],
		[items]
	);

	if (defaultItem && options.withSelectionTitle && !selectedItems.length) {
		selectedItems[0] = defaultItem;
	}

	const filterName = useFilterName(
		options.multiple,
		selectedItems,
		Liferay.Language.get('velocity-unit'),
		options.withSelectionTitle
	);

	return (
		<Filter
			defaultItem={defaultItem}
			disabled={disabled}
			elementClasses={className}
			filterKey={filterKey}
			items={items}
			name={filterName}
			prefixKey={prefixKey}
			{...options}
		/>
	);
}
