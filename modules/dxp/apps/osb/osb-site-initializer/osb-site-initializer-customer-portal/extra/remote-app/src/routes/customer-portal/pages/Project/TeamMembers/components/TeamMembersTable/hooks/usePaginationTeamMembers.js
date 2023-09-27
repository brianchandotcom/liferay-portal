/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo, useState} from 'react';
import i18n from '~/common/I18n';
export default function usePagination(teamMembers) {
	const [activePage, setActivePage] = useState(1);
	const [itemsPerPage, setItemsPerPage] = useState(5);
	const paginationConfig = useMemo(
		() => ({
			activePage,
			itemsPerPage,
			labels: {
				paginationResults: i18n.translate('showing-x-to-x-of-x'),
				perPageItems: i18n.translate('show-x-items'),
				selectPerPageItems: i18n.translate('x-items'),
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
			totalCount: teamMembers?.length,
		}),
		[activePage, itemsPerPage, teamMembers?.length]
	);
	const teamMembersByStatusPaginated = useMemo(() => {
		const teamMembersFilteredByStatus = teamMembers;
		if (teamMembersFilteredByStatus) {
			const teamMembersFilteredByStatusPerPage = teamMembersFilteredByStatus.slice(
				itemsPerPage * activePage - itemsPerPage,
				itemsPerPage * activePage
			);

			return teamMembersFilteredByStatusPerPage?.length
				? teamMembersFilteredByStatusPerPage
				: teamMembersFilteredByStatus;
		}

		return [];
	}, [activePage, itemsPerPage, teamMembers]);

	return {paginationConfig, teamMembersByStatusPaginated};
}
