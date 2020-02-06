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
import {useFilterFetch} from '../../shared/components/filter/hooks/useFilterFetch.es';
import {useFilterName} from '../../shared/components/filter/hooks/useFilterName.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';

const RoleFilter = ({
	completed = false,
	className,
	filterKey = filterConstants.roles.key,
	options = {},
	prefixKey = '',
	processId
}) => {
	const defaultOptions = {
		hideControl: false,
		multiple: true,
		position: 'left',
		withSelectionTitle: false,
		withoutRouteParams: false
	};
	// eslint-disable-next-line react-hooks/exhaustive-deps
	options = useMemo(() => ({...defaultOptions, ...options}), [options]);

	const {items, selectedItems} = useFilterFetch({
		filterKey,
		prefixKey,
		requestUrl: `/processes/${processId}/roles?completed=${completed}`,
		withoutRouteParams: options.withoutRouteParams
	});

	const filterName = useFilterName(
		options.multiple,
		selectedItems,
		Liferay.Language.get('role'),
		options.withSelectionTitle
	);

	return (
		<Filter
			dataTestId="RoleFilter"
			elementClasses={className}
			filterKey={filterKey}
			items={items}
			name={filterName}
			prefixKey={prefixKey}
			{...options}
		/>
	);
};

export default RoleFilter;
