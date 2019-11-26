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

const ProcessStepFilter = ({
	className,
	dispatch,
	filterKey = filterConstants.processStep.key,
	options = {
		hideControl: false,
		multiple: true,
		position: 'left',
		withAllSteps: false,
		withSelectionTitle: false
	},
	prefixKey = '',
	processId
}) => {
	const staticItems = useMemo(
		() => (options.withAllSteps ? [allStepsItem] : []),
		[options.withAllSteps]
	);

	const {items, selectedItems} = useFilterFetch(
		dispatch,
		filterKey,
		prefixKey,
		`/processes/${processId}/tasks?page=0&pageSize=0`,
		staticItems
	);

	const defaultItem = useMemo(() => (items ? items[0] : undefined), [items]);

	const filterName = useFilterName(
		options.multiple,
		selectedItems,
		Liferay.Language.get('process-step'),
		options.withSelectionTitle
	);

	return (
		<Filter
			defaultItem={defaultItem}
			elementClasses={className}
			filterKey={filterKey}
			items={items}
			name={filterName}
			{...options}
		/>
	);
};

const allStepsItem = {
	dividerAfter: true,
	key: 'allSteps',
	name: Liferay.Language.get('all-steps')
};

export default ProcessStepFilter;
