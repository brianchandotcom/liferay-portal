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

import {useEffect, useMemo, useState} from 'react';
import {ACTIVATION_KEYS_LICENSE_FILTER_TYPES} from '../utils/constants';

export default function usePagination(activationKeys, statusFilter) {
	const [activePage, setActivePage] = useState(1);
	const [itemsPerPage, setItemsPerPage] = useState(5);
	const [currentTotalCount, setCurrentTotalCount] = useState(0);

	useEffect(() => {
		if (statusFilter) {
			setActivePage(1);
		}
	}, [statusFilter]);

	const paginationConfig = useMemo(
		() => ({
			activePage,
			itemsPerPage,
			labels: {
				paginationResults: 'Showing {0} to {1} of {2}',
				perPageItems: 'Show {0} Items',
				selectPerPageItems: '{0} Items',
			},
			listItemsPerPage: [
				{label: 5},
				{label: 10},
				{label: 20},
				{label: 50},
			],
			setActivePage,
			setItemsPerPage,
			showDeltasDropDown: true,
			totalCount: currentTotalCount,
		}),
		[activePage, currentTotalCount, itemsPerPage]
	);

	const activationKeysByStatusPaginated = useMemo(() => {
		const activationKeysFilteredByStatus = activationKeys?.filter(
			(activationKey) =>
				ACTIVATION_KEYS_LICENSE_FILTER_TYPES[statusFilter](
					activationKey
				)
		);

		if (activationKeysFilteredByStatus) {
			setCurrentTotalCount(activationKeysFilteredByStatus.length);

			const activationKeysFilteredByStatusPerPage = activationKeysFilteredByStatus.slice(
				itemsPerPage * activePage - itemsPerPage,
				itemsPerPage * activePage
			);

			return activationKeysFilteredByStatusPerPage?.length
				? activationKeysFilteredByStatusPerPage
				: activationKeysFilteredByStatus;
		}

		return [];
	}, [activationKeys, activePage, itemsPerPage, statusFilter]);

	return {activationKeysByStatusPaginated, paginationConfig};
}
