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

import {useEffect, useState} from 'react';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';

export default function useFilters(setFilterTerm) {
	const [filters, setFilters] = useState(INITIAL_FILTER);

	useEffect(() => {
		let initialFilter = '';
		let hasFilterPill = false;

		if (filters.searchTerm) {
			hasFilterPill = true;

			const seachTermSplitted = filters.searchTerm
				.split(' ')
				.map((term) => {
					return `(contains(givenName, '${term}') or contains(familyName, '${term}') or contains(emailAddress, '${term}'))`;
				})
				.join(' and ');

			initialFilter = initialFilter.concat(`${seachTermSplitted}`);
		}

		setFilters((previousFilter) => ({
			...previousFilter,
			hasValue: hasFilterPill,
		}));

		setFilterTerm(`${initialFilter}`);
	}, [filters.searchTerm, setFilterTerm]);

	return [filters, setFilters];
}
