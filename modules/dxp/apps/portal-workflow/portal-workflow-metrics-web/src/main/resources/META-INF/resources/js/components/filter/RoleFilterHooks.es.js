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

import React from 'react';

import Filter from '../../shared/components/filter/Filter.es';
import {useFilterFetch} from '../../shared/components/filter/hooks/useFilterFetch.es';
import {useFilterName} from '../../shared/components/filter/hooks/useFilterName.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';

const RoleFilter = ({
	completed = false,
	className,
	dispatch,
	filterKey = filterConstants.roles.key,
	options = {
		hideControl: false,
		multiple: true,
		position: 'left',
		withSelectionTitle: false
	},
	prefixKey = '',
	processId
}) => {
	const {items, selectedItems} = useFilterFetch(
		dispatch,
		filterKey,
		prefixKey,
		`/processes/${processId}/roles?completed=${completed}`
	);

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
			{...options}
		/>
	);
};

export default RoleFilter;
