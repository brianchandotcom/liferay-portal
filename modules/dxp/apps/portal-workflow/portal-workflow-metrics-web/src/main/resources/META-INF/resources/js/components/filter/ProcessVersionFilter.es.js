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

const allVersionsItem = {
	dividerAfter: true,
	label: Liferay.Language.get('all-versions'),
	name: 'allVersions',
};

function formatItem(item) {
	if (!item.label) {
		item.label = item.name;
	}

	return item;
}

export default function ProcessVersionFilter({
	className,
	disabled,
	filterKey = filterConstants.processVersion.key,
	options = {},
	prefixKey = '',
	processId,
}) {
	options = {
		requestUrl: `/processes/${processId}/process-versions?page=0&pageSize=0`,
		withAllVersions: false,
		withSelectionTitle: false,
		withoutRouteParams: false,
		...options,
	};

	const {items, selectedItems} = useFilterFetch({
		filterKey,
		formatItem,
		prefixKey,
		propertyKey: 'name',
		staticItems: options.withAllVersions ? [allVersionsItem] : [],
		...options,
	});

	const defaultItem = useMemo(() => items[0], [items]);

	if (defaultItem && options.withSelectionTitle && !selectedItems.length) {
		selectedItems[0] = defaultItem;
	}

	const filterName = useFilterNameWithLabel({
		labelPropertyName: 'label',
		selectedItems,
		title: Liferay.Language.get('process-version'),
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
