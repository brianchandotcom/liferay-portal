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
