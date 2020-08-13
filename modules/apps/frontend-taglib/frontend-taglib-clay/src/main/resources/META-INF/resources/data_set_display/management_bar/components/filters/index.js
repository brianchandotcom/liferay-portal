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

import {getComponentByModuleUrl} from '../../../utilities/modules';
import AutocompleteFilter from './AutocompleteFilter';
import CheckboxesFilter from './CheckboxesFilter';
import DateRangeFilter from './DateRangeFilter';
import NumberFilter from './NumberFilter';
import RadioFilter from './RadioFilter';
import TextFilter from './TextFilter';

export const filterIdToComponentMap = {
	autocomplete: AutocompleteFilter,
	checkbox: CheckboxesFilter,
	dateRange: DateRangeFilter,
	number: NumberFilter,
	radio: RadioFilter,
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
			getComponentByModuleUrl(moduleUrl).then((FetchedComponent) =>
				updateComponent(() => FetchedComponent)
			);
		}
	}, [moduleUrl]);

	return Component ? (
		<div className="data-set-filter test">
			<Component {...props} />
		</div>
	) : (
		<ClayLoadingIndicator small />
	);
}
