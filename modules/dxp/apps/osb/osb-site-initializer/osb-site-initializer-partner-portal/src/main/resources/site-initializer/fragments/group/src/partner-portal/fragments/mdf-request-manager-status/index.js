/* eslint-disable no-undef */
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

const managerButtons = fragmentElement.querySelector('.manager-buttons');
const currentPath = Liferay.currentURL.split('/');
const mdfRequestId = +currentPath[currentPath.length - 1];

const updateStatusToApproved = fragmentElement.querySelector('.st-approved');
const updateStatusToRequestMoreInfo = fragmentElement.querySelector(
	'.st-request'
);
const updateStatusToReject = fragmentElement.querySelector('.st-reject');

const getMDFRequestStatus = async () => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const statusResponse = await fetch(`/o/c/mdfrequests/${mdfRequestId}`, {
		headers: {
			'accept': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	if (statusResponse.ok) {
		const data = await statusResponse.json();

		fragmentElement.querySelector(
			'#mdfStatus'
		).innerHTML = `Status : ${data.requestStatus}`;
	}
};

const handleFetch = (status) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	fetch(`/o/c/mdfrequests/${mdfRequestId}`, {
		body: `{"requestStatus": "${status}"}`,
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'PATCH',
	}).then(() => {
		fragmentElement.querySelector(
			'#mdfStatus'
		).innerHTML = `Status : ${status}`;
	});
};

const ROLE_TYPES = [
	'Channel Account Manager',
	'Channel Finance Manager',
	'Channel General Manager',
	'Channel Global Marketing Manager',
	'Channel Regional Marketing Manager',
];

const getUserRole = async () => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`/o/headless-admin-user/v1.0/my-user-account`,
		{
			headers: {
				'content-type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);

	if (response.ok) {
		const result = await response.json();
		const hasAllowedRoleBriefs = result.roleBriefs?.some((roleBrief) =>
			ROLE_TYPES.includes(roleBrief.name)
		);

		if (hasAllowedRoleBriefs) {
			managerButtons.classList.toggle('d-none');
			managerButtons.classList.toggle('d-flex');
		}

		return;
	}

	Liferay.Util.openToast({
		message: 'An unexpected error occurred.',
		type: 'danger',
	});
};

updateStatusToApproved.onclick = () =>
	Liferay.Util.openConfirmModal({
		message: 'Do you want to Approve this MDF?',
		onConfirm: () => handleFetch('Approved'),
	});

updateStatusToRequestMoreInfo.onclick = () =>
	Liferay.Util.openConfirmModal({
		message: 'Do you want to Request more info for this MDF?',
		onConfirm: () => handleFetch('Request more info'),
	});

updateStatusToReject.onclick = () =>
	Liferay.Util.openConfirmModal({
		message: 'Do you want to Reject this MDF?',
		onConfirm: () => handleFetch('Reject'),
	});

getMDFRequestStatus();
getUserRole();
