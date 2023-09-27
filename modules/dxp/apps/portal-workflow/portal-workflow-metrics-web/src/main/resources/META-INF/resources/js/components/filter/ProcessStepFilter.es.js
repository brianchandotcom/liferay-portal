/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

export default function ProcessStepFilter({
	className,
	disabled,
	filterKey = filterConstants.processStep.key,
	options = {},
	prefixKey = '',
	processId,
}) {
	options = {
		requestUrl: `/processes/${processId}/tasks?page=0&pageSize=0`,
		withAllSteps: false,
		withSelectionTitle: false,
		withoutRouteParams: false,
		...options,
	};

	const {items, selectedItems} = useFilterFetch({
		filterKey,
		prefixKey,
		propertyKey: 'name',
		staticItems: options.withAllSteps ? [allStepsItem] : [],
		...options,
	});

	const defaultItem = useMemo(() => items[0], [items]);

	if (defaultItem && options.withSelectionTitle && !selectedItems.length) {
		selectedItems[0] = defaultItem;
	}

	const filterName = useFilterNameWithLabel({
		labelPropertyName: 'label',
		selectedItems,
		title: Liferay.Language.get('process-step'),
		withSelectionTitle: options.withSelectionTitle,
		...options,
	});

	return (
		<Filter
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
}
