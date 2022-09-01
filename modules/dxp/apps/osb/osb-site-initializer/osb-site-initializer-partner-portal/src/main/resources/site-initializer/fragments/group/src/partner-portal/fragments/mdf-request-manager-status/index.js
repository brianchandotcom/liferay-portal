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

const atualizationStatusApproved = fragmentElement.querySelector(
	'.st-approved'
);
const atualizationStatusRequest = fragmentElement.querySelector('.st-request');
const atualizationStatusReject = fragmentElement.querySelector('.st-reject');
const handleFetch = (status) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	fetch(`/o/c/mdfrequests/${mdfRequestId}`, {
		body: `{"mdfRequestStatus": "${status}"}`,
		headers: {
			'accept': 'application/json',
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'PATCH',
	}).then(() => {
		window.location.reload();
	});
};

const ROLE_TYPES = [
	'Channel Account Manager',
	'Channel Finance Manager',
	'Channel General Manager',
	'Channel Global Marketing Manager',
	'Channel Regional Marketing Manager',
];

const getUserRole = () => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	fetch(`/o/headless-admin-user/v1.0/my-user-account`, {
		headers: {
			'accept': 'application/json',
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'GET',
	}).then((response) => {
		if (response.ok) {
			const result = response.json();

			const hasRoleBriefs = result?.roleBriefs?.reduce((_, role) => {
				return ROLE_TYPES.includes(role.name) ? true : false;
			});

			if (hasRoleBriefs) {
				managerButtons.classList.toggle('d-none');
				managerButtons.classList.toggle('d-flex');
			}
		}
	});
};

atualizationStatusApproved.onclick = () =>
	Liferay.Util.openConfirmModal({
		message: 'Do you want to update the status?',
		onConfirm: (isConfirmed) => {
			if (isConfirmed) {
				handleFetch('Approved');
			}
		},
	});

atualizationStatusRequest.onclick = () =>
	Liferay.Util.openConfirmModal({
		message: 'Do you want to update the status?',
		onConfirm: (isConfirmed) => {
			if (isConfirmed) {
				handleFetch('Request more info');
			}
		},
	});

atualizationStatusReject.onclick = () =>
	Liferay.Util.openConfirmModal({
		message: 'Do you want to update the status?',
		onConfirm: (isConfirmed) => {
			if (isConfirmed) {
				handleFetch('Reject');
			}
		},
	});
getUserRole();
