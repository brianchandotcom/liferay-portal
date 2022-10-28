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

const intervalButton = setInterval(() => {
	main();
}, 100);

const organizationStatusListing = [
	'Rejected',
	'Awaiting Finance Approval',
	'Verified',
];

const currentPath = Liferay.currentURL.split('/');
const organizationId = currentPath.at(-1);

function main() {
	const organizationStatusField = document.querySelector(
		'.evp-organization-status'
	);

	const updateStatusToApproved = fragmentElement.querySelector(
		'.status-approved'
	);

	const updateStatusToReject = fragmentElement.querySelector(
		'.status-reject'
	);

	const evpStatusOrganization = organizationStatusField.querySelector(
		'div h4'
	).textContent;

	if (evpStatusOrganization) {
		clearInterval(intervalButton);
	}

	if (!organizationStatusListing.includes(evpStatusOrganization)) {
		updateStatusToReject.style.display = 'block';
		updateStatusToApproved.style.display = 'block';
	}

	const updateStatus = async (key, name) => {
		// eslint-disable-next-line @liferay/portal/no-global-fetch
		await fetch(`/o/c/evporganizations/${organizationId}`, {
			body: `{"organizationStatus": {  
						"key": "${key}",
						"name": "${name}"}
					}`,
			headers: {
				'content-type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
			method: 'PATCH',
		});

		location.reload();
	};

	updateStatusToApproved.onclick = () => {
		Liferay.Util.openConfirmModal({
			message: 'Do you want to Approve this Organization ?',
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateStatus(
						'awaitingFinanceApproval',
						'Awaiting Finance Approval'
					);
				}
			},
		});
	};

	updateStatusToReject.onclick = () => {
		Liferay.Util.openConfirmModal({
			message: 'Do you want to Reject this Organization ?',
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateStatus('rejected', 'Rejected');
				}
			},
		});
	};
}
