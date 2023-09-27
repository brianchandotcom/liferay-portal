/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import Filter from '../../shared/components/filter/Filter.es';
import {useFilterName} from '../../shared/components/filter/hooks/useFilterName.es';
import {useFilterStatic} from '../../shared/components/filter/hooks/useFilterStatic.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';

const processStatusConstants = {
	completed: 'Completed',
	pending: 'Pending',
};

const processStatuses = [
	{
		key: processStatusConstants.completed,
		name: Liferay.Language.get('completed'),
	},
	{
		key: processStatusConstants.pending,
		name: Liferay.Language.get('pending'),
	},
];

export default function ProcessStatusFilter({
	className,
	filterKey = filterConstants.processStatus.key,
	options = {},
	prefixKey = '',
}) {
	options = {
		withSelectionTitle: false,
		withoutRouteParams: false,
		...options,
	};

	const {items, selectedItems} = useFilterStatic({
		filterKey,
		prefixKey,
		staticItems: processStatuses,
		...options,
	});

	const defaultItem = useMemo(() => items[0], [items]);

	const filterName = useFilterName(
		options.multiple,
		selectedItems,
		Liferay.Language.get('process-status'),
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

export {processStatusConstants};
