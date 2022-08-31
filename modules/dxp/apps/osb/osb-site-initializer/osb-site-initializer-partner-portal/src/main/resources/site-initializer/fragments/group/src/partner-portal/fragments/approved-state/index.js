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
	});
};

atualizationStatusApproved.onclick = () =>
	Liferay.Util.openConfirmModal({
		message: 'Do you want to update the status?',
		onConfirm: (isConfirmed) => {
			if (isConfirmed) {
				handleFetch('approved');
			}
		},
	});

atualizationStatusRequest.onclick = () =>
	Liferay.Util.openConfirmModal({
		message: 'Do you want to update the status?',
		onConfirm: (isConfirmed) => {
			if (isConfirmed) {
				handleFetch('request');
			}
		},
	});

atualizationStatusReject.onclick = () =>
	Liferay.Util.openConfirmModal({
		message: 'Do you want to update the status?',
		onConfirm: (isConfirmed) => {
			if (isConfirmed) {
				handleFetch('reject');
			}
		},
	});
