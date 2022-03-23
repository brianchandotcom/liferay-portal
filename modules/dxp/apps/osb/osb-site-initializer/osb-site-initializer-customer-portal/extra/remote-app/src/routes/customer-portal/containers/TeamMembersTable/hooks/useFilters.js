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
import {STATUS_TAG_TYPE_NAMES} from '../../../utils/constants';

// import {STATUS_TAG_TYPE_NAMES} from '../../../utils/constants';

import {INITIAL_FILTER} from '../utils/constants/initialFilter';

export default function useFilters(setFilterTerm) {
	const [filters, setFilters] = useState(INITIAL_FILTER);

	useEffect(() => {
		let initialFilter = '';
		let hasFilterPill = false;

		if (filters.searchTerm) {
			const searchTermFilter = `(contains(givenName, '${filters.searchTerm}') or contains(familyName, '${filters.searchTerm}') or contains(emailAddress, '${filters.searchTerm}'))`;

			initialFilter = initialFilter.concat(`${searchTermFilter}`);
		}

		if (filters.roles.value.length) {
			hasFilterPill = true;

			const rolesFilter = `(${filters.roles.value.reduce(
				(accumulatorRolesFilter, role, index) => {
					return `${accumulatorRolesFilter}${
						index > 0 ? ' or ' : ''
					}contains(roles,'${role}')`;
				},
				''
			)})`;

			initialFilter = initialFilter.concat(` and ${rolesFilter}`);
		}

		if (filters.status.value.length) {
			hasFilterPill = true;

			const statusFilter = filters.status.value.reduce(
				(accumulatorStatusFilter, status, index) => {
					let filter = '';
					if (status === STATUS_TAG_TYPE_NAMES.invited) {
						filter = `lastLoginDate eq null`;
					}

					if (status === STATUS_TAG_TYPE_NAMES.active) {
						filter = `lastLoginDate ne null`;
					}

					return `${accumulatorStatusFilter}${
						index > 0 ? ' or ' : ''
					}${filter}`;
				},
				''
			);

			initialFilter = initialFilter.concat(` and ${statusFilter}`);
		}

		if (filters.supportSeat.value.length) {
			hasFilterPill = true;

			const supportSeatFilter = `(${filters.supportSeat.value.reduce(
				(accumulatorSupportSeatFilterFilter, supportSeat, index) => {
					return `${accumulatorSupportSeatFilterFilter}${
						index > 0 ? ' or ' : ''
					}instanceSize eq 'Sizing ${supportSeat}'`;
				},
				''
			)})`;

			initialFilter = initialFilter.concat(` and ${supportSeatFilter}`);
		}

		setFilters((previousFilter) => ({
			...previousFilter,
			hasValue: hasFilterPill,
		}));

		setFilterTerm(`${initialFilter}`);
	}, [
		filters.roles.value,
		filters.searchTerm,
		filters.status.value,
		filters.supportSeat.value,
		setFilterTerm,
	]);

	return [filters, setFilters];
}
