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
/* eslint-disable no-undef */

const currentPath = Liferay.currentURL.split('/');
const mdfRequestId = +currentPath[currentPath.length - 1];

const getContactInfo = async () => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`/o/c/mdfrequests/${mdfRequestId}?nestedFields=user`,
		{
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);
	if (response.ok) {
		const data = await response.json();
		const firstName = data.r_userToMDFRequests_user.givenName;
		const infoEmail = data.r_userToMDFRequests_user.emailAddress;

		fragmentElement.querySelector('#firstName').innerHTML = htmlUtil.escape(
			firstName
		);
		fragmentElement.querySelector('#infoEmail').innerHTML = htmlUtil.escape(
			infoEmail
		);

		return;
	}

	Liferay.Util.openToast({
		message: 'An unexpected error occured.',
		type: 'danger',
	});
};

getContactInfo();
