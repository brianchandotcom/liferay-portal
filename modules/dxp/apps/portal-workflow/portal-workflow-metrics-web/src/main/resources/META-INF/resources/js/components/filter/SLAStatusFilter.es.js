/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import Filter from '../../shared/components/filter/Filter.es';
import {useFilterName} from '../../shared/components/filter/hooks/useFilterName.es';
import {useFilterStatic} from '../../shared/components/filter/hooks/useFilterStatic.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';

const slaStatusConstants = {
	onTime: 'OnTime',
	overdue: 'Overdue',
	untracked: 'Untracked',
};

const slaStatuses = [
	{
		key: slaStatusConstants.onTime,
		name: Liferay.Language.get('on-time'),
	},
	{
		key: slaStatusConstants.overdue,
		name: Liferay.Language.get('overdue'),
	},
	{
		key: slaStatusConstants.untracked,
		name: Liferay.Language.get('untracked'),
	},
];

export default function SLAStatusFilter({
	className,
	filterKey = filterConstants.slaStatus.key,
	options = {},
	prefixKey = '',
}) {
	options = {
		withSelectionTitle: true,
		withoutRouteParams: false,
		...options,
	};

	const {items, selectedItems} = useFilterStatic({
		filterKey,
		prefixKey,
		staticItems: slaStatuses,
		...options,
	});

	const defaultItem = useMemo(() => items[0], [items]);

	const filterName = useFilterName(
		options.multiple,
		selectedItems,
		Liferay.Language.get('sla-status'),
		options.withSelectionTitle
	);

	return (
		<Filter
			defaultItem={defaultItem}
			elementClasses={className}
			filterKey={filterKey}
			items={items}
			name={filterName}
			prefixKey={prefixKey}
			{...options}
		/>
	);
}

export {slaStatusConstants};
