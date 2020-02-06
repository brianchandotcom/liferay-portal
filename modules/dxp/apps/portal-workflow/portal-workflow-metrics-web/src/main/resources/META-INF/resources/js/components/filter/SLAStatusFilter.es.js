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

const SLAStatusFilter = ({
	className,
	filterKey = filterConstants.slaStatus.key,
	options = {},
	prefixKey = ''
}) => {
	const defaultOptions = {
		hideControl: false,
		multiple: true,
		position: 'left',
		withSelectionTitle: true,
		withoutRouteParams: false
	};
	// eslint-disable-next-line react-hooks/exhaustive-deps
	options = useMemo(() => ({...defaultOptions, ...options}), [options]);

	const {items, selectedItems} = useFilterStatic(
		filterKey,
		prefixKey,
		options.withoutRouteParams,
		slaStatuses
	);

	const defaultItem = useMemo(() => (items ? items[0] : undefined), [items]);

	const filterName = useFilterName(
		options.multiple,
		selectedItems,
		Liferay.Language.get('sla-status'),
		options.withSelectionTitle
	);

	return (
		<Filter
			dataTestId="SLAStatusFilter"
			defaultItem={defaultItem}
			elementClasses={className}
			filterKey={filterKey}
			items={items}
			name={filterName}
			prefixKey={prefixKey}
			{...options}
		/>
	);
};

const slaStatusConstants = {
	onTime: 'OnTime',
	overdue: 'Overdue',
	untracked: 'Untracked'
};

const slaStatuses = [
	{
		key: slaStatusConstants.onTime,
		name: Liferay.Language.get('on-time')
	},
	{
		key: slaStatusConstants.overdue,
		name: Liferay.Language.get('overdue')
	},
	{
		key: slaStatusConstants.untracked,
		name: Liferay.Language.get('untracked')
	}
];

export default SLAStatusFilter;
export {slaStatusConstants};
