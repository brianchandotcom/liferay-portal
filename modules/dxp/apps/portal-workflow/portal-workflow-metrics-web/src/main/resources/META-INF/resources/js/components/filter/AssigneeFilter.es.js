/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import Filter from '../../shared/components/filter/Filter.es';
import {useFilterFetch} from '../../shared/components/filter/hooks/useFilterFetch.es';
import {useFilterName} from '../../shared/components/filter/hooks/useFilterName.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';

const unassigned = {
	dividerAfter: true,
	id: -1,
	key: '-1',
	name: Liferay.Language.get('unassigned'),
};

export default function AssigneeFilter({
	className,
	filterKey = filterConstants.assignee.key,
	options = {},
	prefixKey = '',
	processId,
	staticData,
}) {
	options = {
		withSelectionTitle: false,
		withoutRouteParams: false,
		withoutUnassigned: false,
		...options,
	};

	const {items, selectedItems} = useFilterFetch({
		filterKey,
		prefixKey,
		propertyKey: 'id',
		requestMethod: 'post',
		requestUrl: `/processes/${processId}/assignees`,
		staticData,
		staticItems: !options.withoutUnassigned ? [unassigned] : [],
		withoutRouteParams: options.withoutRouteParams,
	});

	const defaultItem = useMemo(() => items[0], [items]);

	const filterName = useFilterName(
		options.multiple,
		selectedItems,
		Liferay.Language.get('assignee'),
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
