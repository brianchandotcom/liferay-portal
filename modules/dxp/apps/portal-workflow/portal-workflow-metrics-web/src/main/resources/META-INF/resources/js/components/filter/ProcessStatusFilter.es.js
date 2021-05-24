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
