/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Filter from '../../shared/components/filter/Filter.es';
import {useFilterFetch} from '../../shared/components/filter/hooks/useFilterFetch.es';
import {useFilterName} from '../../shared/components/filter/hooks/useFilterName.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';

export default function RoleFilter({
	completed = false,
	className,
	filterKey = filterConstants.roles.key,
	options = {},
	prefixKey = '',
	processId,
}) {
	options = {
		withSelectionTitle: false,
		withoutRouteParams: false,
		...options,
	};

	const {items, selectedItems} = useFilterFetch({
		filterKey,
		labelPropertyName: 'name',
		prefixKey,
		propertyKey: 'id',
		requestUrl: `/processes/${processId}/roles?completed=${completed}`,
		...options,
	});

	const filterName = useFilterName(
		options.multiple,
		selectedItems,
		Liferay.Language.get('role'),
		options.withSelectionTitle
	);

	return (
		<Filter
			elementClasses={className}
			filterKey={filterKey}
			items={items}
			name={filterName}
			prefixKey={prefixKey}
			{...options}
		/>
	);
}
