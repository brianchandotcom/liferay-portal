/* eslint-disable no-undef */
/* eslint-disable @liferay/portal/no-global-fetch */
/* eslint-disable radix */
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
