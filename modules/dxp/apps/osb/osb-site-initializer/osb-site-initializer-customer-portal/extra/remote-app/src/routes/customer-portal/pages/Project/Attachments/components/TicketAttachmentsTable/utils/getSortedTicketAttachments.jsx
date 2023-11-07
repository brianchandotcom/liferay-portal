/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getSortedTicketAttachments(ticketAttachments, sortConfig) {

	let sortedTicketAttachments = undefined;

	if (ticketAttachments) {
		sortedTicketAttachments = ticketAttachments.sort((a, b) => {
			if (a[sortConfig.columnName] < b[sortConfig.columnName]) {
				return sortConfig.direction === 'ascending' ? -1 : 1;
			}
			if (a[sortConfig.columnName] > b[sortConfig.columnName]) {
				return sortConfig.direction === 'ascending' ? 1 : -1;
			}

			return 0;
		});
	}

	return sortedTicketAttachments;
}
