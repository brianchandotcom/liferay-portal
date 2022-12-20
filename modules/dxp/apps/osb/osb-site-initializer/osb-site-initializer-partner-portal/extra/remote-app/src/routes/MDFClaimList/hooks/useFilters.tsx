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

import {getCamelCase} from '../../../common/utils/getCamelCase';
import getSearchFilterTerm from '../../../common/utils/getSearchFilterTerm';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';
import getDateCreatedFilterTerm from '../utils/getDateCreatedFilterTerm';

export default function useFilters() {
	const [filters, setFilters] = useState(INITIAL_FILTER);

	const [filtersTerm, setFilterTerm] = useState('');

	const onFilter = (newFilters: Partial<typeof INITIAL_FILTER>) =>
		setFilters((previousFilters) => ({...previousFilters, ...newFilters}));

	useEffect(() => {
		let initialFilter = '';
		let hasFilter = false;

		if (
			filters.dateCreated.dates.endDate ||
			filters.dateCreated.dates.startDate
		) {
			hasFilter = true;
			initialFilter = getDateCreatedFilterTerm(
				initialFilter,
				filters.dateCreated
			);
		}

		if (filters.status.value.length) {
			hasFilter = true;

			const statusFilter = filters.status.value
				.map((status) => {
					return `(mdfClaimStatus eq '${getCamelCase(status)}')`;
				})
				.join(' or ');

			initialFilter = initialFilter
				? initialFilter.concat(` and (${statusFilter})`)
				: initialFilter.concat(`(${statusFilter})`);
		}

		if (filters.partner.value.length) {
			hasFilter = true;

			const partnerFilter = filters.partner.value
				.map((partner) => {
					return `(companyName eq '${partner}')`;
				})
				.join(' or ');

			initialFilter = initialFilter
				? initialFilter.concat(` and (${partnerFilter})`)
				: initialFilter.concat(`(${partnerFilter})`);
		}

		if (filters.type.value.length) {
			hasFilter = true;

			const partnerFilter = filters.type.value
				.map((type) => {
					return `(partial eq ${type === 'Partial' ? true : false})`;
				})
				.join(' or ');

			initialFilter = initialFilter
				? initialFilter.concat(` and (${partnerFilter})`)
				: initialFilter.concat(`(${partnerFilter})`);
		}

		if (filters.searchTerm) {
			initialFilter = getSearchFilterTerm(
				initialFilter,
				filters.searchTerm
			);
		}

		onFilter({
			hasValue: hasFilter,
		});

		setFilterTerm(initialFilter);
	}, [
		filters.dateCreated,
		filters.partner,
		filters.searchTerm,
		filters.status,
		filters.type,

		setFilters,
	]);

	return {filters, filtersTerm, onFilter, setFilters};
}
