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
import {useFilterNameWithLabel} from '../../shared/components/filter/hooks/useFilterName.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';

const allStepsItem = {
	dividerAfter: true,
	label: Liferay.Language.get('all-steps'),
	name: 'allSteps',
};

const ProcessStepFilter = ({
	className,
	disabled,
	filterKey = filterConstants.processStep.key,
	options = {},
	prefixKey = '',
	processId,
}) => {
	const defaultOptions = {
		hideControl: false,
		multiple: true,
		position: 'left',
		withAllSteps: false,
		withSelectionTitle: false,
		withoutRouteParams: false,
	};
	// eslint-disable-next-line react-hooks/exhaustive-deps
	options = useMemo(() => ({...defaultOptions, ...options}), [options]);

	const staticItems = useMemo(
		() => (options.withAllSteps ? [allStepsItem] : []),
		[options.withAllSteps]
	);

	const {items, selectedItems} = useFilterFetch({
		filterKey,
		prefixKey,
		propertyKey: 'name',
		requestUrl: `/processes/${processId}/tasks?page=0&pageSize=0`,
		staticItems,
		withoutRouteParams: options.withoutRouteParams,
	});

	const defaultItem = useMemo(() => items[0], [items]);

	if (defaultItem && options.withSelectionTitle && !selectedItems.length) {
		selectedItems[0] = defaultItem;
	}

	const filterName = useFilterNameWithLabel({
		labelPropertyName: 'label',
		multiple: options.multiple,
		selectedItems,
		title: Liferay.Language.get('process-step'),
		withSelectionTitle: options.withSelectionTitle,
	});

	return (
		<Filter
			data-testid="processStepFilter"
			defaultItem={defaultItem}
			disabled={disabled}
			elementClasses={className}
			filterKey={filterKey}
			items={items}
			labelPropertyName="label"
			name={filterName}
			prefixKey={prefixKey}
			{...options}
		/>
	);
};

export default ProcessStepFilter;
