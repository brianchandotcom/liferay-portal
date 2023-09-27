/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
