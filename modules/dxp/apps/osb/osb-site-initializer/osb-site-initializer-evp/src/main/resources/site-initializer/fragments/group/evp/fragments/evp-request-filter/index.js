/* eslint-disable no-undef */
/* eslint-disable @liferay/portal/no-global-fetch */
/* eslint-disable radix */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const evpRequestRows = Array.from(
	document.querySelectorAll('.evp-table-border.evp-request-row')
);

const getUserRequests = async () => {
	const userEmailAddress = Liferay.ThemeDisplay.getUserEmailAddress();

	const response = await fetch(
		`/o/c/evprequests?filter=managerEmailAddress eq '${userEmailAddress}' or emailAddress eq '${userEmailAddress}'&fields=id,requestType`,
		{
			headers: {
				'content-type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
			method: 'GET',
		}
	);

	const requests = await response.json();

	if (!requests) {
		return;
	}

	return requests?.items.map((request) => request.id);
};

const filterRequests = async () => {
	const evpUserRequests = await getUserRequests();

	evpRequestRows.forEach((cur_evpRequestRow) => {
		const cur_evpRequestRow_Id = parseInt(
			cur_evpRequestRow.querySelector('.component-text').innerText
		);

		if (!evpUserRequests.includes(cur_evpRequestRow_Id)) {
			cur_evpRequestRow.remove();
		}
	});

	if (!evpUserRequests.length && evpRequestRows.length) {
		const tablesHeader = document.querySelectorAll('.evp-table-header');

		const notFoundDiv = document.createElement('div');
		notFoundDiv.classList.add('c-empty-state');
		notFoundDiv.innerHTML =
			"<div class='c-empty-state-text'>No Results Found</div>";

		tablesHeader.forEach((element) => {
			if (!element.parentNode.querySelector('.c-empty-state')) {
				element.nextSibling.nextSibling.classList.add('d-none');

				element.parentNode.insertBefore(
					notFoundDiv,
					element.nextSibling
				);
			}
		});
	}
};

if (fragmentElement.querySelector('.evp-request-filter')) {
	filterRequests();
}
