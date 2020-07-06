/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useEffect, useState} from 'react';

import {getJsModule} from '../../../../../utilities/modules';
import AutocompleteFilter from './AutocompleteFilter';
import CheckboxesFilter from './CheckboxesFilter';
import DateRangeFilter from './DateRangeFilter';
import NumberFilter from './NumberFilter';
import RadioFilter from './RadioFilter';
import SelectFilter from './SelectFilter';
import TextFilter from './TextFilter';

export const filterIdToComponentMap = {
	autocomplete: AutocompleteFilter,
	checkbox: CheckboxesFilter,
	dateRange: DateRangeFilter,
	number: NumberFilter,
	radio: RadioFilter,
	select: SelectFilter,
	text: TextFilter,
};

export function Filter(props) {
	const {moduleUrl, type} = props;

	const [Component, updateComponent] = useState(() => {
		if (!moduleUrl) {
			const Matched = filterIdToComponentMap[type];

			if (!Matched) {
				throw new Error(`Filter type '${type}' not found.`);
			}

			return Matched;
		}
		else {
			return null;
		}
	});

	useEffect(() => {
		if (moduleUrl) {
			getFilterByModuleUrl(moduleUrl).then((FetchedComponent) =>
				updateComponent(() => FetchedComponent)
			);
		}
	}, [moduleUrl]);

	return Component ? (
		<Component {...props} />
	) : (
		<ClayLoadingIndicator small />
	);
}

export const fetchedCustomFiltersModules = [];

export function getFilterByModuleUrl(url) {
	return new Promise((resolve, reject) => {
		const addedCustomFilter = fetchedCustomFiltersModules.find(
			(cr) => cr.url === url
		);
		if (addedCustomFilter) {
			resolve(addedCustomFilter.component);
		}

		return getJsModule(url)
			.then((fetchedComponent) => {
				fetchedCustomFiltersModules.push({
					component: fetchedComponent,
					url,
				});

				return resolve(fetchedComponent);
			})
			.catch(reject);
	});
}
