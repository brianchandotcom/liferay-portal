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

const evpRequestRows = document.querySelectorAll('.evp-table-border');
const evpServicesRequests = Array.from(evpRequestRows).filter(({classList}) =>
	classList.contains('evp-service-requests')
);

const getServicesRequests = async () => {
	const userEmailAddress = Liferay.ThemeDisplay.getUserEmailAddress();

	const response = await fetch(
		`/o/c/evprequests?filter=requestType eq 'service' and managerEmailAddress eq '${userEmailAddress}' or emailAddress eq '${userEmailAddress}'&fields=id`,
		{
			headers: {
				'content-type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
			method: 'GET',
		}
	);

	const data = await response.json();

	if (!data) {
		return;
	}

	return data?.items.map(({id}) => id);
};

const filterRequests = async () => {
	const evpUserServiceRequests = await getServicesRequests();

	evpServicesRequests.forEach((cur_evpServiceRequest) => {
		const cur_evpServiceRequest_Id = parseInt(
			cur_evpServiceRequest.querySelector('.component-text').innerText
		);
		if (!evpUserServiceRequests.includes(cur_evpServiceRequest_Id)) {
			cur_evpServiceRequest.remove();
		}
	});
};

if (fragmentElement.querySelector('.evp-request-filter')) {
	filterRequests();
}
