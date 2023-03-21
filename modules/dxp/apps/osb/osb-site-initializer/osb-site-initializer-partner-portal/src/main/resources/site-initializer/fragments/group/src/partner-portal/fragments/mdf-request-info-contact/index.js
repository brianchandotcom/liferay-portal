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
const findRequestIdUrl = (paramsUrl) => {
	const splitParamsUrl = paramsUrl.split('?');

	return splitParamsUrl[0];
};
const currentPath = Liferay.currentURL.split('/');
const mdfRequestId = findRequestIdUrl(currentPath.at(-1));

const getContactInfo = async () => {
	if (!isNaN(mdfRequestId)) {
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
			const firstName = data?.r_usrToMDFReqs_user?.givenName;
			const infoEmail = data?.r_usrToMDFReqs_user?.emailAddress;
			const telephone =
				data?.r_usrToMDFReqs_user?.userAccountContactInformation
					.telephones[0].phoneNumber;

			fragmentElement.querySelector(
				'#firstName'
			).innerHTML = `${Liferay.Util.escape(firstName)}`;

			fragmentElement.querySelector(
				'#infoEmail'
			).innerHTML = `${Liferay.Util.escape(infoEmail)}`;

			fragmentElement.querySelector(
				'#telephone'
			).innerHTML = `${Liferay.Util.escape(telephone)}`;

			return;
		}

		Liferay.Util.openToast({
			message: 'An unexpected error occured.',
			type: 'danger',
		});
	}
};

getContactInfo();
